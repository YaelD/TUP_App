package javaClasses;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {

    private static ServerConnection instance;
    private RequestQueue queue;
    private Context context;
    private ServerConnection.serverErrorException exception;

    private final String baseURL = "http://10.0.2.2:8080/web_war_exploded";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";
    private final String loginURL = "/login";
    private final String registerURL = "/traveler";
    private final String updateURL = "/traveler";
    private final String favAttractionsURL = "/attractions/favorites";
    private final String hotelsURL = "/hotles";

    //TODO: function that sends trips to DB


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
        ServerConnection.serverErrorException exceptionSaver = this.exception;
        this.exception = null;
        return exceptionSaver;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendTripDetailsToServer(TripDetails tripDetails) throws serverErrorException {
        Log.e("HERE==>", "Send A trip!!!!");
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class,new LocalDateAdapter()).create();
        TripPlan tripPlan = new TripPlan("", null);
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + tripURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {

                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        //Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            String dayPlan = jsonArray.getString(i);
                            JSONObject json = new JSONObject(dayPlan);

                            LocalTime localTime = gson.fromJson(json.getString("startTime"), LocalTime.class);

                            arr.add(gson.fromJson(dayPlan, DayPlan.class));
                        }
                        tripPlan.setPlans(arr);
                        Utility.getInstance(context).setLastCreatedTrip(tripPlan);
                    } else {
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
        //return arr;
    }

//----------------------------------------------------------------------------------------

    public void sendTripPlan(TripPlan tripPlan)
    {
        Log.e("HERE==>", "Send A trip!!!!");
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + tripURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {

                        int id  = jsonResponse.getInt("message");
                        Utility.getInstance(context).getLastCreatedTrip().setTripID(id);
                    } else {
                        Log.e("ERROR==>", jsonResponse.getString("message"));
                        ServerConnection.getInstance(context).setException(new serverErrorException(jsonResponse.getString("message")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("ERROR==>", error.getMessage());
                ServerConnection.getInstance(context).setException(new serverErrorException("Error Connecting to Server"));

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
                String body = new Gson().toJson(tripPlan);
                return body.getBytes();
            }
        };

        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }

//----------------------------------------------------------------------------------------

    public void getMyTripsFromServer()
    {
        ArrayList<TripPlan> trips = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + tripURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("HERE==>", "Got OK Response");
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        for(int i =0; i < jsonArray.length(); ++i)
                        {
                            TripPlan tripPlan  = new Gson().fromJson(jsonArray.get(i).toString(), TripPlan.class);
                            trips.add(tripPlan);
                        }

                        Utility.getInstance(context).setAllTrips(trips);
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
                return params;
            }
        };
        addToRequestQueue(stringRequest);
    }


//----------------------------------------------------------------------------------------

    public void sendTripPlansToDelete()
    {
        Log.e("HERE==>", "Send A trip!!!!");
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + tripURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {

                        int id  = jsonResponse.getInt("message");
                        int lastIndex =Utility.getInstance(context).getAllTrips().size() -1;
                        Utility.getInstance(context).getAllTrips().get(lastIndex).setTripID(id);
                        Utility.getInstance(context).getLastCreatedTrip().setTripID(id);
                        Utility.getInstance(context).setLastCreatedTrip(null);

                    } else {
                        Log.e("ERROR==>", jsonResponse.getString("message"));
                        ServerConnection.getInstance(context).setException(new serverErrorException(jsonResponse.getString("message")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR==>", error.getMessage());
                ServerConnection.getInstance(context).setException(new serverErrorException(error.getMessage()));

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
                String body = "";

                return body.getBytes();
            }
        };
        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }

//----------------------------------------------------------------------------------------
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

    public void logIn(String email, String password) {
        /*

         */
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
                                (new serverErrorException("Invalid password or email address. Please try again"));
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
                //Log.e("HERE==>", error.getMessage());
                ServerConnection.getInstance(context).setException
                        (new serverErrorException("Error connecting to server, please try again"));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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

    public void register(Traveler traveler) {
        Log.e("HERE==>", "In Register");
        Gson gson = new Gson();
        //make a new request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + registerURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("HERE==>", "In Register- good Response");
                    Log.e("HERE==>", "Response Register==" + response);

                    JSONObject json = new JSONObject(response);
                    if (json.getString("status").equals("ok")) {
                        Traveler traveler = new Gson().fromJson(json.getString("message"), Traveler.class);
                        Utility.getInstance(context).setTraveler(traveler);
                        //Utility.getInstance(context).SharedPreferencesWriter();
                        Log.e("HERE==>", "Successfully Registered!");

                    } else {
                        ServerConnection.getInstance(context).setException(new serverErrorException(json.getString("message")));
                        Log.e("HERE==>", "Json Bad");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("HERE==>", e.getMessage());
                    ServerConnection.getInstance(context).setException(new serverErrorException(e.getMessage()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("HERE==>", error.getMessage());
                ServerConnection.getInstance(context).setException
                        (new serverErrorException("Error connecting to server, please try later"));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("Content-Type", "application/json");
                return data;

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
    public void getFavoritesFromServer()
    {
        ArrayList<Attraction> favAttractions = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("ok"))
                            {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("message"));
                                for(int i =0; i < jsonArray.length(); ++i)
                                {
                                    Utility.getInstance(context).getFavoriteAttractions().add(new Gson().fromJson(jsonArray.get(i).toString(), Attraction.class));
                                }
                                //Utility.getInstance(context).setFavoriteAttractions(favAttractions);
                                Log.e("HERE==>", "FAVS Attractions==>"+ Utility.getInstance(context).getFavoriteAttractions().toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("travelerID", Utility.getInstance(context).getTravelerID());
                data.put("Content-Type", "application/json");
                return data;

            }
        };
        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------

    public void getHotelsFromServer(String destination)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + hotelsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("ok"))
                            {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("message"));
                                for(int i =0; i < jsonArray.length(); ++i)
                                {
                                    Utility.getInstance(context).getHotels().add(new Gson().fromJson(jsonArray.get(i).toString(), Hotel.class));
                                }
                                Log.e("HERE==>", "Hotels==>"+ Utility.getInstance(context).getFavoriteAttractions().toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("travelerID", Utility.getInstance(context).getTravelerID());
                data.put("Content-Type", "application/json");
                return data;

            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return destination.getBytes();
            }
        };
        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------

    public void sendFavAttractions()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("travelerID", Utility.getInstance(context).getTravelerID());
                data.put("Content-Type", "application/json");
                return data;

            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String body = new Gson().toJson(Utility.getInstance(context).getFavoriteAttractions());
                return body.getBytes();
            }
        };
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
                        ServerConnection.getInstance(context).setException(new serverErrorException(jsonResponse.getString("message")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("VolleyError==>", e.getMessage());
                    ServerConnection.getInstance(context).setException(new serverErrorException("Error connecting to Server"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("VolleyError==>", error.getMessage());
                ServerConnection.getInstance(context).setException(new serverErrorException("Error connecting to Server"));
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("travelerID", Utility.getInstance(context).getTravelerID());
                data.put("Content-Type", "application/json");
                return data;

            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return new Gson().toJson(newTravelerDetails).getBytes(StandardCharsets.UTF_8);
            }
        };
        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------

    public static class serverErrorException extends RuntimeException
    {
        public serverErrorException(String message) {
            super(message);
        }
    }


    class LocalTimeAdapter extends TypeAdapter<LocalTime> {

        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public LocalTime read(JsonReader in) throws IOException {
            in.beginObject();
            String time = "";
            String fieldName = null;
            while(in.hasNext())
            {
                JsonToken token = in.peek();
                if(token.equals(JsonToken.NAME))
                {
                    fieldName = in.nextName();
                }
                String curr = in.nextString();
                if(curr.length() == 1)
                {
                    curr = "0" + curr;
                }
                time += ":" + curr;
            }
            in.endObject();
            //Log.e("GSON ADAPTER==>", time.substring(1,6));
            return LocalTime.parse(time.substring(1,6));
        }
    }

    class LocalDateAdapter extends TypeAdapter<LocalDate>
    {

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            in.beginObject();
            String date = "";
            String fieldName = null;
            while(in.hasNext())
            {
                JsonToken token = in.peek();
                if(token.equals(JsonToken.NAME))
                {
                    fieldName = in.nextName();
                }
                String curr = in.nextString();
                if(curr.length() == 1)
                {
                    curr = "0" + curr;
                }
                date = date + "-" + curr;
            }
            in.endObject();
            return LocalDate.parse(date.substring(1,11));

        }
    }



}
