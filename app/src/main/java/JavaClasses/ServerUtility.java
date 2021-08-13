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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private static ArrayList<Attraction> tripSelectedAttractions = new ArrayList<>();
    private static ArrayList<Attraction> favoriteAttractions = new ArrayList<>();
    private static ArrayList<Attraction> attractions = new ArrayList<>();
    private static ArrayList<Attraction> hotels = new ArrayList<>();
    private static ArrayList<Attraction> attractionsTest = new ArrayList<>();
    private static ArrayList<DayPlan> lastCreatedTrip = new ArrayList<>();


    private String cookie;

    public static void attTestFiller()
    {
        attractionsTest.add(new Attraction("London eye", "Riverside Building, County Hall, London SE1 7PB, United Kingdom",
                "+44 20 7967 8021", "https://www.londoneye.com/", "1",
                "https://media.cntraveler.com/photos/55c8be0bd36458796e4ca38a/master/pass/london-eye-2-cr-getty.jpg"));
        attractionsTest.add(new Attraction("Buckingham Palace", "London SW1A 1AA, United Kingdom",
                "+44 303 123 7300", "https://www.royal.uk/royal-residences-buckingham-palace", "2",
                "https://zamanturkmenistan.com.tm/wp-content/uploads/2021/04/buckingham-palace-london.jpg"));
    }


    //REMEMBER TO CHANGE TO attractions ArrayList;
    public Attraction getAttractionByID(String id)
    {
        Attraction selectedAttraction = null;
        for(Attraction currentAttraction: attractionsTest)
        {
            if(currentAttraction.getPlaceID().equals(id))
            {
                selectedAttraction = currentAttraction;
            }
        }
        return selectedAttraction;
    }


    public ArrayList<Attraction> getFavoriteAttractions() {
        return favoriteAttractions;
    }

    public void setFavoriteAttractions(ArrayList<Attraction> favoriteAttractions) {
        this.favoriteAttractions = favoriteAttractions;
    }

    public boolean addAttractionToFavoriteList(Attraction attraction){
        if(favoriteAttractions.contains(attraction))
            return false;
        else
            favoriteAttractions.add(attraction);
        return true;
    }

    public boolean removeAttractionFromFavoriteList(Attraction attraction){
        if(favoriteAttractions.contains(attraction))
            favoriteAttractions.remove(attraction);
        else
            return false;
        return true;
    }


    public ArrayList<Attraction> getHotelsByDestination(String destination) {
        getHotelsFromServer(destination.toLowerCase().trim()+"_hotels");
        return hotels;
    }

    public ArrayList<Attraction> getAttractions() {
        if(instance.attractions.size() == 0)
        {
            getAttractionsFromServer("london");
        }
        return instance.attractions;
    }

    public ArrayList<Attraction> getHotels() {
        if(hotels == null)
        {
            getHotelsFromServer("london_hotels");
        }
        return hotels;
    }

    public ArrayList<Attraction> getTripSelectedAttrations() {
        return tripSelectedAttractions;
    }

     /*

        public void setTripSelectedAttrations(ArrayList<Attraction> tripSelectedAttrations) {
            this.tripSelectedAttractions = tripSelectedAttrations;
        }
      */




    public static synchronized ServerUtility getInstance(Context context)
    {
        if(null == instance)
        {
            instance = new ServerUtility(context);
        }
        return instance;
    }


    public ArrayList<Attraction> getAttractionsTest() {
        return attractionsTest;
    }

    public void setAttractionsTest(ArrayList<Attraction> attractionsTest) {
        ServerUtility.attractionsTest = attractionsTest;
    }

    private ServerUtility(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
        attTestFiller();

    }

    public void getTrip(TripDetails tripDetails)
    {
        lastCreatedTrip = instance.sendTripDetailsToServer(tripDetails);
    }



    private void getHotelsFromServer(String destination)
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



    private void getAttractionsFromServer(String destination)
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
                        JSONArray jsonArray = jsonResponse.getJSONArray("message");
                        Gson gson = new Gson();
                        for(int i =0; i < jsonArray.length(); ++i)
                        {
                            String attractionJsonString = jsonArray.getString(i);

                            arr.add(gson.fromJson(attractionJsonString, Attraction.class));
                        }
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
        };
        instance.queue.add(stringRequest);
        return arr;
    }





}
