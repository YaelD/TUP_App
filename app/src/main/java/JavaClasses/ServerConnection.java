package JavaClasses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {

    private static ServerConnection instance;
    private RequestQueue queue;
    private Context context;

    private final String baseURL = "http://10.0.0.5:8080/web_war_exploded";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";

    public static synchronized ServerConnection getInstance(Context context)
    {
        if(null == instance)
        {
            instance = new ServerConnection(context);
        }
        return instance;
    }

    private ServerConnection(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }


    private ArrayList<DayPlan> sendTripDetailsToServer(TripDetails tripDetails)
    {
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,baseURL+tripURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getString("status").equals("ok"))
                    {
                        JSONArray jsonArray = jsonResponse.getJSONArray("message");
                        Gson gson = new Gson();
                        for(int i =0; i < jsonArray.length(); ++i)
                        {
                            String attractionJsonString = jsonArray.getString(i);
                            arr.add(gson.fromJson(attractionJsonString, DayPlan.class));
                        }

                        ServerUtility.getInstance(context).setLastCreatedTrip(arr);

                    }
                    else
                    {
                        //Toast toast = Toast.makeText(instance.context, "Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
                        //toast.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast toast = Toast.makeText(instance.context, "Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
                //toast.show();


            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String body = new Gson().toJson(tripDetails);
                return body.getBytes();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        instance.queue.add(stringRequest);
        return arr;
    }


    //----------------------------------------------------------------------------------------


    public void getHotelsFromServer(String destination)
    {
        StringRequest request = new StringRequest(Request.Method.POST,baseURL+allAttractionsURL ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HERE==>", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getString("status").equals("error"))
                    {
                        Toast toast = Toast.makeText(context,"Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        //attractions = new Gson().fromJson(jsonResponse.getString("message"), ArrayList.class);
                        ServerUtility.getInstance(context).setHotels(new Gson().fromJson(jsonResponse.getString("message"), ArrayList.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context,"Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
                toast.show();
            }
        })
        {
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
        queue.add(request);
    }

//----------------------------------------------------------------------------------------


    public void getAttractionsFromServer(String destination)
    {
        ArrayList<Attraction> arr = new ArrayList<>();
        String url =baseURL + allAttractionsURL;
        StringRequest request = new StringRequest(Request.Method.POST,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("HERE==>", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getString("status").equals("error"))
                    {
                        Toast toast = Toast.makeText(instance.context, jsonResponse.getString("message") ,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {

                        ArrayList<Attraction> attractions = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        Log.e("HERE==>", "GOOD RESPONSE");
                        Gson gson = new Gson();
                        for(int i =0; i < jsonArray.length(); ++i)
                        {
                            String attractionJsonString = jsonArray.getString(i);
                            Log.e("attraction==>", attractionJsonString);
                            attractions.add(gson.fromJson(attractionJsonString, Attraction.class));

                        }
                        ServerUtility.getInstance(context).setAttractions(attractions);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(instance.context,error.toString() ,Toast.LENGTH_SHORT);
                toast.show();
            }
        })
        {
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
        instance.queue.add(request);
    }


}
