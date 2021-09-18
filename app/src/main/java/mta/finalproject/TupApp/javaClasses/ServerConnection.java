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

    private final String baseURL = "http://tup1-env.eba-qvijjvbu.us-west-2.elasticbeanstalk.com";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";
    private final String loginURL = "/login";
    private final String registerURL = "/traveler";
    private final String updateURL = "/traveler";
    private final String favAttractionsURL = "/attractions/favorites";
    private final String hotelsURL = "/hotels";
    private final String destinationsURL = "/destinations";
    private final String deleteURL = "/delete";
    private final String createURL = "/create";

    //====================================================================================//

    public static synchronized ServerConnection getInstance(Context context) {
        if (null == instance) {
            instance = new ServerConnection(context);
        }
        return instance;
    }
    //====================================================================================//

    private ServerConnection(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }
    //====================================================================================//

    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }
    //====================================================================================//

    private void makeStrRequest(String functionName,int method, String url, String body, final VolleyCallBack callBack) {
        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                Log.e(functionName+"==>","Ok Response=" + response);
                                callBack.onSuccessResponse(jsonObject.getString("message"));
                            }
                            else
                            {
                                Log.e(functionName+ "==>", "Error Response=" + jsonObject.getString("message"));
                                callBack.onErrorResponse("Error Connecting to Server");
                            }
                            } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(functionName +"==>", "error=JSON");
                            callBack.onErrorResponse("Error Connecting to Server");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onErrorResponse("Error Connecting to Server");
                        Log.e(functionName+ "==>", "Error: " + error);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("travelerID", Utility.getInstance(context).getTravelerID());
                params.put("Content-Type", "application/json");
                return params;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }
        };
        addToRequestQueue(stringRequest);
    }
    //====================================================================================//

    private  <T> void addToRequestQueue(Request<T> req) {
        getQueue().add(req);
    }
    //====================================================================================//

    public void getHotelsFromServer(String destination) {
        makeStrRequest("getHotels", Request.Method.POST, baseURL + hotelsURL, destination,
                new VolleyCallBack() {
                    @Override
                    public void onSuccessResponse(String result) {
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                Utility.getInstance(context).getHotels().add(new Gson().fromJson(jsonArray.get(i).toString(), Hotel.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("getHotels=>", "Error JSON");
                        }
                    }
                    @Override
                    public void onErrorResponse(String error) {

                    }
                });
    }
    //====================================================================================//

    public void getDestinationsFromServer(){
        makeStrRequest("getDestinations", Request.Method.GET, baseURL + destinationsURL, "",
                new VolleyCallBack() {
                    @Override
                    public void onSuccessResponse(String result) {
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            Utility.getInstance(context).getDestinations().add("Select");
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                Utility.getInstance(context).getDestinations().add(jsonArray.getString(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("getDestinations=>", "Error JSON");
                        }

                    }

                    @Override
                    public void onErrorResponse(String error) {

                    }
                });
    }
    //====================================================================================//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendTripDetailsToServer(TripDetails tripDetails, final VolleyCallBack callBack) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        makeStrRequest("sendTripDetails", Request.Method.POST,
                baseURL+tripURL+createURL, gson.toJson(tripDetails), callBack);
    }
    //====================================================================================//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendTripPlan(TripPlan tripPlan, final VolleyCallBack callBack) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        makeStrRequest("sendTripPlan",Request.Method.PUT,baseURL+tripURL, gson.toJson(tripPlan), callBack);
    }
    //====================================================================================//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getMyTripsFromServer(){
        ArrayList<TripPlan> trips = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        makeStrRequest("getMyTrips", Request.Method.GET, baseURL + tripURL, "", new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        TripPlan tripPlan = gson.fromJson(jsonArray.get(i).toString(), TripPlan.class);
                        trips.add(tripPlan);
                    }
                    Utility.getInstance(context).setAllTrips(trips);
                } catch (JSONException e) {
                    Log.e("getMyTrips=>", "Error JSON");
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String error) {

            }
        });
    }
    //====================================================================================//

    public void deleteTripFromServer() {
        String body = Utility.getInstance(context).getTripsToDelete().toString();
        body = "{" +
                "\"tripsIdToDeleteList\":" + body +
                "}";
        Utility.getInstance(context).getTripsToDelete().clear();
        makeStrRequest("deleteTrips", Request.Method.POST, baseURL + tripURL + deleteURL, body, new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
            }

            @Override
            public void onErrorResponse(String error) {
            }
        });
    }
    //====================================================================================//

    public void getAttractionsFromServer(String destination) {
        makeStrRequest("getAllAttractions", Request.Method.POST, baseURL + allAttractionsURL, destination, new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    ArrayList<Attraction> attractions = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        String attractionJsonString = jsonArray.getString(i);
                        attractions.add(gson.fromJson(attractionJsonString, Attraction.class));
                    }
                    Utility.getInstance(context).setAttractions(attractions);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("getAllAttractions=>", "Error=JSON");
                }
            }
            @Override
            public void onErrorResponse(String error) {

            }
        });
    }
    //====================================================================================//

    public void getFavoritesFromServer() {
        makeStrRequest("getFavorites", Request.Method.GET, baseURL + favAttractionsURL, "", new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            Utility.getInstance(context).getFavoriteAttractions().add(new Gson().fromJson(jsonArray.get(i).toString(), Attraction.class));
                        }
                }
                catch (JSONException e) {
                    Log.e("getFavorites=>", "Error=JSON");
                    e.printStackTrace();
                }
            }
            @Override
            public void onErrorResponse(String error) {

            }
        });
    }
    //====================================================================================//

    public void sendFavAttractionsToAdd() {
        String body = new Gson().toJson(Utility.getInstance(context).getFavAttractionsToAdd());
        body = "{" +
                "\"favoriteAttractionsList\":" + body +
                "}";
        Utility.getInstance(context).getFavAttractionsToAdd().clear();
        makeStrRequest("sendFavsToAdd", Request.Method.PUT, baseURL + favAttractionsURL, body,
                new VolleyCallBack() {
                    @Override
                    public void onSuccessResponse(String result) {
                    }
                    @Override
                    public void onErrorResponse(String error) {
                    }
                });
    }
    //====================================================================================//

    public void sendFavAttractionsToDelete() {
        String body = new Gson().toJson(Utility.getInstance(context).getFavAttractionsToDelete());
        body = "{" +
                "\"favoriteAttractionsList\":" + body +
                "}";
        Utility.getInstance(context).getFavAttractionsToDelete().clear();
        makeStrRequest("SendFavsToDelete", Request.Method.POST, baseURL + favAttractionsURL + deleteURL,
                body, new VolleyCallBack() {
                    @Override
                    public void onSuccessResponse(String result) {

                    }

                    @Override
                    public void onErrorResponse(String error) {

                    }
                });
    }
    //====================================================================================//

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
                            callBack.onSuccessResponse(jsonUserString);
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
            addToRequestQueue(stringRequest);
        }
    //====================================================================================//

    public void register(Traveler traveler, final VolleyCallBack callBack) {
        Gson gson = new Gson();
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
                return super.parseNetworkResponse(response);
            }
        };
        addToRequestQueue(stringRequest);

    }
    //====================================================================================//

    public void updateUser(Traveler newTravelerDetails, VolleyCallBack callBack) {
        String body = new Gson().toJson(newTravelerDetails);
        makeStrRequest("updateUser", Request.Method.PUT, baseURL+updateURL, body, callBack);
    }
    //====================================================================================//

    public static class LocalTimeAdapter extends TypeAdapter<LocalTime> {

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
            return LocalTime.parse(time.substring(1, 6));
        }
    }
    //====================================================================================//

    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {
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
    //====================================================================================//

}