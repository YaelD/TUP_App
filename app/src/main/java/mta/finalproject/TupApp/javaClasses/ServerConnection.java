package mta.finalproject.TupApp.javaClasses;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;


import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {

    private static ServerConnection instance;
    private RequestQueue queue;
    private Context context;

    //private final String baseURL = "http://tup1-env.eba-qvijjvbu.us-west-2.elasticbeanstalk.com";
    //private final String baseURL = "http://10.0.2.2:8080/web_war_exploded";
    private final String baseURL = "http://10.0.0.5:8080/web_war_exploded";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";
    private final String loginURL = "/login";
    private final String registerURL = "/traveler";
    private final String updateURL = "/traveler";
    private final String favAttractionsURL = "/attractions/favorites";
    private final String hotelsURL = "/hotels";
    private final String destinationsURL = "/destinations";
    private final String deleteURL = "/delete";

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
    }
//----------------------------------------------------------------------------------------

    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }
//----------------------------------------------------------------------------------------




//----------------------------------------------------------------------------------------
    public <T> void addToRequestQueue(Request<T> req) {
        getQueue().add(req);
    }

//----------------------------------------------------------------------------------------

    public void getHotelsFromServer(String destination) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + hotelsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                Log.e("getAllHotels==>", "Ok Response=" + response);
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("message"));
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    Utility.getInstance(context).getHotels().add(new Gson().fromJson(jsonArray.get(i).toString(), Hotel.class));
                                }
                            }
                            else
                            {
                                Log.e("getAllHotels==>", "Error Response=" + jsonObject.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("getAllHotels==>", "error=JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getAllHotels==>", "error=" + error);

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
                return destination.getBytes();
            }
        };
        addToRequestQueue(stringRequest);
    }

    public void getDestinationsFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + destinationsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                Log.e("getAllDestinations==>", "OK Response=" + response);
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("message"));
                                Utility.getInstance(context).getDestinations().add("Select");
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    Utility.getInstance(context).getDestinations().add(jsonArray.getString(i));
                                }
                            }
                            else
                            {
                                Log.e("getAllDestinations==>", "Error Response=" + jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("getAllDestinations==>", "Error=JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getAllDestinations==>", "Error=" + error);
            }
        }) {
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendTripDetailsToServer(TripDetails tripDetails, final VolleyCallBack callBack) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        TripPlan tripPlan = new TripPlan("", null);
        tripPlan.setDestination(tripDetails.getDestination());
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + tripURL + "/create", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("sendTripDetails==>", "Ok Response=" + jsonResponse.getString("message"));
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            String dayPlanjson = jsonArray.getString(i);
                            DayPlan dayPlan = gson.fromJson(dayPlanjson, DayPlan.class);

                            OnePlan currentPlan = dayPlan.getDaySchedule().get(0);
                            dayPlan.setHotel(new Hotel(currentPlan.getAttraction().getName(), currentPlan.getAttraction().getPlaceID(), currentPlan.getAttraction().getGeometry()));
                            dayPlan.getDaySchedule().remove(0);
                            arr.add(dayPlan);
                        }
                        tripPlan.setPlans(arr);
                        callBack.onSuccessResponse(tripPlan);
                        //Utility.getInstance(context).setLastCreatedTrip(tripPlan);
                    } else {
                        Log.e("sendTripDetails=>", "Error Response=" + jsonResponse.getString("message") );
                        callBack.onErrorResponse("Error Connecting to Server");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("sendTripDetails=>", "Error JSON");
                    callBack.onErrorResponse("Error Connecting to Server");


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("sendTripDetails=>", "Error: " + error);
                callBack.onErrorResponse("Error Connecting to Server");
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
                String body = gson.toJson(tripDetails);
                return body.getBytes();
            }
        };



        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------
    public void sendTripPlan(TripPlan tripPlan, final VolleyCallBack callBack) {

        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + tripURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("sendTripPlan==>", "Ok Response=" + jsonResponse.getString("message"));

                        int id = jsonResponse.getInt("message");
                        callBack.onSuccessResponse(id);
                    } else {
                         Log.e("sendTripPlan=>", "Error Response=" + jsonResponse.getString("message"));
                        callBack.onErrorResponse("Error Connecting to Server");
                    }
                } catch (JSONException e) {
                    Log.e("sendTripPlan=>", "Error JSON");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("sendTripPlan=>", "Error: " + error);
                callBack.onErrorResponse("Error Connecting to Server");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("travelerID", Utility.getInstance(context).getTravelerID());
                params.put("Content-Type", "application/json");
                return params;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
                String body = gson.toJson(tripPlan);
                Log.e("sendTripPlan==>", "TripPlan body" + body);
                return body.getBytes(StandardCharsets.UTF_8);
            }
        };

        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }

//----------------------------------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getMyTripsFromServer() {

        ArrayList<TripPlan> trips = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + tripURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("getMyTrips==>", "Ok Response=" + jsonResponse.getString("message"));
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            TripPlan tripPlan = gson.fromJson(jsonArray.get(i).toString(), TripPlan.class);
                            trips.add(tripPlan);
                        }

                        Utility.getInstance(context).setAllTrips(trips);
                    } else {
                        Log.e("getMyTrips=>", "Error Response=" + jsonResponse.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.e("getMyTrips=>", "Error JSON");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getMyTrips=>", "Error: " + error);
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
    //TODO: Delete trip from server function
    public void deleteTripFromServer() {
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+tripURL+deleteURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("deleteTrip==>", "Ok Response=" + jsonResponse.getString("message"));
                    } else {
                        Log.e("deleteTrip=>", "Error Response=" + jsonResponse.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.e("deleteTrip=>", "Error JSON");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("deleteTrip=>", "Error: " + error);
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
                //String body = new Gson().toJson(Utility.getInstance(context).getTripsToDelete());
                String body = Utility.getInstance(context).getTripsToDelete().toString();
                body = "{" +
                        "\"tripsIdToDeleteList\":" + body +
                        "}";
                Utility.getInstance(context).getTripsToDelete().clear();
                return body.getBytes();
            }
        };
        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }


    //----------------------------------------------------------------------------------------
    public void getAttractionsFromServer(String destination) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+allAttractionsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("getAllAttractions==>", "Ok Response=" + jsonResponse.getString("message"));
                        ArrayList<Attraction> attractions = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            String attractionJsonString = jsonArray.getString(i);
                            attractions.add(gson.fromJson(attractionJsonString, Attraction.class));

                        }
                        Utility.getInstance(context).setAttractions(attractions);
                    }
                    else
                    {
                        Log.e("getAllAttractions=>", "Error Response=" + jsonResponse.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("getAllAttractions=>", "Error=JSON");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getAllAttractions=>", "Error: " + error);
                //Toast toast = Toast.makeText(instance.context, error.toString(), Toast.LENGTH_SHORT);
                //toast.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                //headers.put("travelerID", Utility.getInstance(context).getTravelerID());
                headers.put("travelerID", Utility.getInstance(context).getTravelerID());
                headers.put("Content-Type", "text/html");
                return headers;
            }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return destination.getBytes();

                }
        };
        addToRequestQueue(stringRequest);
    }

    //----------------------------------------------------------------------------------------
    public void getFavoritesFromServer() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("ok")) {
                                Log.e("getFavorites==>", "Ok Response=" + jsonResponse.getString("message"));
                                JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    Utility.getInstance(context).getFavoriteAttractions().add(new Gson().fromJson(jsonArray.get(i).toString(), Attraction.class));
                                }
                            }
                            else
                            {
                                Log.e("getFavorites=>", "Error Response=" + jsonResponse.getString("message"));

                            }
                        } catch (JSONException e) {
                            Log.e("getFavorites=>", "Error=JSON");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getFavoritess=>", "Error: " + error);
            }
        }) {
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
    public void sendFavAttractionsToAdd()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("ok")) {
                                 Log.e("sendFavToAdd==>", "Ok Response=" + jsonResponse.getString("message"));
                            } else {
                                Log.e("sendFavToAdd=>", "Error Response=" + jsonResponse.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sendFavToAdd=>", "Error=JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("sendFavToAdd=>", "Error: " + error);
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
                String body = new Gson().toJson(Utility.getInstance(context).getFavAttractionsToAdd());
                body = "{" +
                        "\"favoriteAttractionsList\":" + body +
                        "}";
                Log.e("SendFavToAdd==>", "Json=>" + body);
                Utility.getInstance(context).getFavAttractionsToAdd().clear();
                return body.getBytes();
            }
        };
        addToRequestQueue(stringRequest);
    }

    public void sendFavAttractionsToDelete() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+favAttractionsURL+deleteURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SendFavsToDelete==>", "Response" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("ok")) {
                                 Log.e("SendFavsToDelete==>", "Ok Response=" + jsonResponse.getString("message"));
                            } else {
                                Log.e("SendFavsToDelete=>", "Error Response=" + jsonResponse.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("SendFavsToDelete=>", "Error=JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SendFavsToDelete=>", "Error: " + error);
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
                //TODO: Convert FavAttractions arrayList to Json
                String body = new Gson().toJson(Utility.getInstance(context).getFavAttractionsToDelete());
                body = "{" +
                        "\"favoriteAttractionsList\":" + body +
                        "}";
                Log.e("SendFavToDel==>", "Json=>" + body);
                Utility.getInstance(context).getFavAttractionsToDelete().clear();
                return body.getBytes();
            }
        };
        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------
    public void logIn(String email, String password, final VolleyCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.e("HERE==>", "log in response=" + response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("logIn==>", "Ok Response=" + jsonResponse.getString("message"));
                        String jsonUserString = jsonResponse.getString("message");
                        Traveler traveler = new Gson().fromJson(jsonUserString, Traveler.class);
                        callBack.onSuccessResponse(traveler);
                    } else {
                        Log.e("logIn=>", "Error Response=" + jsonResponse.getString("message"));
                        callBack.onErrorResponse("Error Connecting to Server");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("logIn=>", "Error=JSON");
                    callBack.onErrorResponse("Error Connecting to Server");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("logIn=>", "Error: " + error);
                callBack.onErrorResponse("Error Connecting to Server");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("travelerID", "0");
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

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> header = response.headers;
                Utility.getInstance(context).setTravelerID(header.get("travelerID"));
                return super.parseNetworkResponse(response);
            }
        };

        //queue.add(stringRequest);
        addToRequestQueue(stringRequest);
    }

    //----------------------------------------------------------------------------------------
    public void register(Traveler traveler, final VolleyCallBack callBack) {

        Gson gson = new Gson();

        //make a new request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + registerURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("register==>", "Ok Response=" + jsonResponse.getString("message"));
                        callBack.onSuccessResponse(jsonResponse.getString("message"));

                    } else {
                        callBack.onErrorResponse("Don't");
                        Log.e("register=>", "Error Response=" + jsonResponse.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("register=>", "Error=JSON");
                    callBack.onErrorResponse("Don't");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("register=>", "Error: " + error);
                callBack.onErrorResponse("Don't");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("travelerID", "0");
                data.put("Content-Type", "application/json");
                return data;

            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String json = gson.toJson(traveler);
                Log.e("HERE==>", json);
                return json.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> headers = response.headers;
                Utility.getInstance(context).setTravelerID(headers.get("travelerID"));
                //Log.e("travelerID==>", Utility.getInstance(context).getTravelerID());
                return super.parseNetworkResponse(response);
            }
        };

        //queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }

    //----------------------------------------------------------------------------------------
    public void updateUser(Traveler newTravelerDetails, VolleyCallBack callBack)  {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + updateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Log.e("updateUser==>", "Ok Response=" + jsonResponse.getString("message"));
                        callBack.onSuccessResponse(newTravelerDetails);
                    } else {
                        Log.e("updateUser=>", "Error Response=" + jsonResponse.getString("message"));
                        callBack.onErrorResponse("Error Connecting to Server");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("updateUser=>", "Error=JSON");
                    callBack.onErrorResponse("Error Connecting to Server");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("updateUser=>", "Error: " + error);
                callBack.onErrorResponse("Error Connecting to Server");
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
                return new Gson().toJson(newTravelerDetails).getBytes(StandardCharsets.UTF_8);
            }
        };
        addToRequestQueue(stringRequest);
    }

//----------------------------------------------------------------------------------------

    static class LocalTimeAdapter extends TypeAdapter<LocalTime> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.beginObject();
            out.name("hour");
            out.value(value.getHour());
            out.name("nano");
            out.value(0);
            out.name("minute");
            out.value(value.getMinute());
            out.name("second");
            out.value(0);
            out.endObject();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public LocalTime read(JsonReader in) throws IOException {
            in.beginObject();
            String time = "";
            String fieldName = null;
            while (in.hasNext()) {
                JsonToken token = in.peek();
                if (token.equals(JsonToken.NAME)) {
                    fieldName = in.nextName();
                }
                String curr = in.nextString();
                if (curr.length() == 1) {
                    curr = "0" + curr;
                }
                time += ":" + curr;
            }
            in.endObject();
            //Log.e("GSON ADAPTER==>", time.substring(1,6));
            return LocalTime.parse(time.substring(1, 6));
        }
    }

    static class LocalDateAdapter extends TypeAdapter<LocalDate> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.beginObject();
            out.name("day");
            out.value(value.getDayOfMonth());
            out.name("year");
            out.value(value.getYear());
            out.name("month");
            out.value(value.getMonthValue());
            out.endObject();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            in.beginObject();
            String date = "";
            String fieldName = null;
            while (in.hasNext()) {
                JsonToken token = in.peek();
                if (token.equals(JsonToken.NAME)) {
                    fieldName = in.nextName();
                }
                String curr = in.nextString();
                if (curr.length() == 1) {
                    curr = "0" + curr;
                }
                date = date + "-" + curr;
            }
            in.endObject();
            return LocalDate.parse(date.substring(1, 11));

        }
    }

}