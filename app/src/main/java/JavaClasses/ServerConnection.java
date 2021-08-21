package JavaClasses;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {

    private static ServerConnection instance;
    private static RequestQueue queue;
    private static Context context;
    private static ServerConnection.serverErrorException exception;

    private final String baseURL = "http://10.0.0.5:8080/web_war_exploded";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";
    private final String loginURL = "/login";
    private final String registerURL = "/register";
    private final String updateURL = "/login";
//----------------------------------------------------------------------------------------

    public static synchronized ServerConnection getInstance(Context context) {
        if (null == instance) {
            instance = new ServerConnection(context);
        }
        return instance;
    }
//----------------------------------------------------------------------------------------

    private ServerConnection(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
        this.exception = null;
    }
//----------------------------------------------------------------------------------------

    public RequestQueue getQueue() {
        if(queue == null)
        {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }
//----------------------------------------------------------------------------------------

    public serverErrorException getException() {
        return this.exception;
    }

    public void setException(serverErrorException exception) {
        this.exception = exception;
    }


//----------------------------------------------------------------------------------------

    public <T> void addToRequestQueue(Request<T> req)
    {
        getQueue().add(req);
    }

//----------------------------------------------------------------------------------------

    public ArrayList<DayPlan> sendTripDetailsToServer(TripDetails tripDetails) throws serverErrorException {
        Log.e("HERE==>", "Send A trip!!!!");

        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + tripURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.e("HERE==>", "Got A response!" + response);

                    if (jsonResponse.getString("status").equals("ok")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("message");
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            String attractionJsonString = jsonArray.getString(i);
                            arr.add(gson.fromJson(attractionJsonString, DayPlan.class));
                        }

                        TripPlan tripPlan = new TripPlan(-1, "", arr);
                        Utility.getInstance(context).setLastCreatedTrip(tripPlan);
                        Log.e("HERE==>", "Successfully got Trip!");

                    } else {
                        Log.e("ERROR==>", jsonResponse.getString("message"));
                        throw new serverErrorException(jsonResponse.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast toast = Toast.makeText(instance.context, "Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
                Log.e("ERROR==>", error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("travelerID", Utility.getInstance(context).getTravelerID());
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String body = new Gson().toJson(tripDetails);
                return body.getBytes();
            }
        };

        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);
        return arr;
    }

//----------------------------------------------------------------------------------------


    public void getHotelsFromServer(String destination) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + allAttractionsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HERE==>", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("error")) {
                        Toast toast = Toast.makeText(context, "Error Connecting to Server, please try again", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        //attractions = new Gson().fromJson(jsonResponse.getString("message"), ArrayList.class);
                        Utility.getInstance(context).setHotels(new Gson().fromJson(jsonResponse.getString("message"), ArrayList.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Error Connecting to Server, please try again", Toast.LENGTH_SHORT);
                toast.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/html");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return destination.getBytes();
            }
        };
        //queue.add(request);
        addToRequestQueue(stringRequest);

    }

//----------------------------------------------------------------------------------------


    public void getAttractionsFromServer(String destination) {
        String url = baseURL + allAttractionsURL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("HERE==>", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("error")) {
                        Toast toast = Toast.makeText(instance.context, jsonResponse.getString("message"), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {

                        ArrayList<Attraction> attractions = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        Log.e("HERE==>", "GOOD RESPONSE");
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            String attractionJsonString = jsonArray.getString(i);
                            Log.e("attraction==>", attractionJsonString);
                            attractions.add(gson.fromJson(attractionJsonString, Attraction.class));

                        }
                        Utility.getInstance(context).setAttractions(attractions);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(instance.context, error.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/html");
                headers.put("travelerID", Utility.getInstance(context).getTravelerID());
                return headers;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                return destination.getBytes();
            }
        };
        //instance.queue.add(request);
        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------

    public void logIn(String email, String password) throws serverErrorException {
            StringRequest stringRequest = new StringRequest( Request.Method.POST, baseURL + loginURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getString("status").equals("ok")) {
                            String jsonUserString = json.getString("message");
                            Traveler traveler = new Gson().fromJson(jsonUserString, Traveler.class);
                            Utility.getInstance(context).setTraveler(traveler);
                            ServerConnection.getInstance(context).setException(null);
                            Log.e("HERE==>", "Successfully LoggedIn");
                        } else {
                            Log.e("HERE==>", "Didn't Login");
                            ServerConnection.getInstance(context).setException
                                    (new serverErrorException(json.getString("message")));
                        }
                    } catch (JSONException e) {
                        Log.e("HERE==>", e.getMessage());
                        ServerConnection.getInstance(context).setException
                                (new serverErrorException(e.getMessage()));

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("HERE==>", error.getMessage());
                    ServerConnection.getInstance(context).setException
                            (new serverErrorException("Error connecting to server, please try again"));
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("travelerID", Utility.getInstance(context).getTravelerID());
                    data.put("Content-Type", "application/json");
                    return data;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    String res = "{" +
                            "\"emailAddress\":\"" + email + "\",\n" +
                            "\"password\":\"" + password + "\"" +
                            "}";
                    return res.getBytes(StandardCharsets.UTF_8);
                }
            };
            //queue.add(stringRequest);
            addToRequestQueue(stringRequest);
    }
//----------------------------------------------------------------------------------------

    public void register(Traveler traveler) throws serverErrorException {
        Gson gson = new Gson();
        //make a new request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + registerURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getString("status").equals("ok")) {
                        Traveler traveler = new Gson().fromJson(json.getString("message"), Traveler.class);
                        Utility.getInstance(context).setTraveler(traveler);
                        //Utility.getInstance(context).SharedPreferencesWriter();
                        Log.e("HERE==>", "Successfully Registered!");
                    } else {
                        throw new serverErrorException(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("HERE==>", e.getMessage());
                    throw new serverErrorException(e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HERE==>", error.getMessage());
                throw new serverErrorException("Error connecting to server, please try again");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return gson.toJson(traveler).getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> headers = response.headers;
                Utility.getInstance(context).setTravelerID(headers.get("travelerID"));
                return super.parseNetworkResponse(response);
            }
        };

        //queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }

//----------------------------------------------------------------------------------------


    public void updateUser(Traveler newTravelerDetails) throws serverErrorException
    {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + updateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getString("status").equals("ok"))
                    {
                        Utility.getInstance(context).setTraveler(newTravelerDetails);
                    }
                    else
                    {
                        throw new serverErrorException("Error connecting to Server");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("VolleyError==>", e.getMessage());
                    throw new serverErrorException("Error connecting to Server");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError==>", error.getMessage());
                throw new serverErrorException("Error connecting to Server");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("travelerID", Utility.getInstance(context).getTravelerID());
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return new Gson().toJson(newTravelerDetails).getBytes(StandardCharsets.UTF_8);
            }
        };
        addToRequestQueue(stringRequest);
    }


    public static class serverErrorException extends RuntimeException
    {
        public serverErrorException(String message) {
            super(message);
        }
    }


}
