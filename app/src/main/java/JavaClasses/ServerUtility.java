package JavaClasses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerUtility {
    private final String baseURL = "http://10.0.2.2:8080/web_war_exploded";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";

    private static ServerUtility instance;
    private RequestQueue queue;
    private Context context;

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Attraction> hotels = new ArrayList<>();
    private Traveler travelerDetails;
    //private



    private String cookie;


/*
    public ArrayList<Attraction> getAttractionsByDestination(String destination) {
        getAllAttractions(destination.toLowerCase().trim());
        return attractions;
    }

 */

    public ArrayList<Attraction> getHotelsByDestination(String destination) {
        getAllHotels(destination.toLowerCase().trim()+"_hotels");
        return hotels;
    }

    public ArrayList<Attraction> getAttractions() {
        if(instance.attractions.size() == 0)
        {
            getAllAttractions("london");
        }
        return instance.attractions;
    }

    public ArrayList<Attraction> getHotels() {
        if(hotels == null)
        {
            getAllHotels("london_hotels");
        }
        return hotels;
    }

    public static synchronized ServerUtility getInstance(Context context)
    {
        if(null == instance)
        {
            instance = new ServerUtility(context);
        }
        return instance;
    }
    private ServerUtility(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    private void setAttractions(ArrayList<Attraction> attractions) {
        instance.attractions = attractions;
        Log.e("InSET====>", instance.attractions.toString());
    }

    private void getAllHotels(String destination)
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
                        attractions = new Gson().fromJson(jsonResponse.getString("message"), ArrayList.class);
                        Log.e("HERE==>", attractions.get(0).toString());
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



    private static void getAllAttractions(String destination)
    {
        ArrayList<Attraction> arr = new ArrayList<>();
        String url =ServerUtility.instance.baseURL + ServerUtility.instance.allAttractionsURL;
        StringRequest request = new StringRequest(Request.Method.POST,"http://10.0.2.2:8080/web_war_exploded/attractions/all" ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getString("status").equals("error"))
                    {
                        Toast toast = Toast.makeText(instance.context, "Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        Log.e("HERE==>>", "Got good response");
                        Log.e("Here==>", jsonResponse.getString("message"));
                        JSONArray jsonArray = jsonResponse.getJSONArray("message");
                        Gson gson = new Gson();
                        for(int i =0; i < jsonArray.length(); ++i)
                        {
                            String attractionJsonString = jsonArray.getString(i);

                            arr.add(gson.fromJson(attractionJsonString, Attraction.class));
                        }
                        Log.e("Here==>", arr.toString());
                        instance.attractions = arr;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(instance.context,"Error Connecting to Server, please try again" ,Toast.LENGTH_SHORT);
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



    private void sendTripDetails(TripDetails tripDetails)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,baseURL+tripURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

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
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String body = new Gson().toJson(tripDetails);
                return body.getBytes();
            }
        };
    }





}
