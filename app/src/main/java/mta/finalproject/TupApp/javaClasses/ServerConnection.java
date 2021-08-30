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
    private ServerConnection.serverErrorException exception;

    private final String baseURL = "http://tup1-env.eba-qvijjvbu.us-west-2.elasticbeanstalk.com";
    private final String allAttractionsURL = "/attractions/all";
    private final String tripURL = "/trip";
    private final String loginURL = "/login";
    private final String registerURL = "/traveler";
    private final String updateURL = "/traveler";
    private final String favAttractionsURL = "/attractions/favorites";
    private final String hotelsURL = "/hotels";

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
        if (queue == null) {
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

    public <T> void addToRequestQueue(Request<T> req) {
        getQueue().add(req);
    }

//----------------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendTripDetailsToServer(TripDetails tripDetails) {
        Log.e("HERE==>", "Send A trip!!!!");
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
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
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            String dayPlanjson = jsonArray.getString(i);
                            DayPlan dayPlan = gson.fromJson(dayPlanjson, DayPlan.class);

                            OnePlan currentPlan = dayPlan.getDaySchedule().get(0);
                            dayPlan.setHotel(new Hotel(currentPlan.getAttraction().getName(), currentPlan.getAttraction().getPlaceID()));
                            dayPlan.getDaySchedule().remove(0);
                            arr.add(dayPlan);
                        }
                        tripPlan.setPlans(arr);
                        Utility.getInstance(context).setLastCreatedTrip(tripPlan);
                    } else {
                        ServerConnection.getInstance(context).
                                setException(new serverErrorException(jsonResponse.getString("message")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ServerConnection.getInstance(context).
                            setException(new serverErrorException(e.getMessage()));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("sendTripDetails==>", "Error: " + error.getMessage());
                ServerConnection.getInstance(context).
                        setException(new serverErrorException(error.getMessage()));

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

        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);
        //return arr;
    }

//----------------------------------------------------------------------------------------

    public void sendTripPlan(TripPlan tripPlan) {
        Log.e("sendTripPlan==>", "Send A trip!!!!");
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + tripURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {

                        int id = jsonResponse.getInt("message");
                        Utility.getInstance(context).getLastCreatedTrip().setTripID(id);
                    } else {
                        //Log.e("sendTripPlan==>==>", jsonResponse.getString("message"));
                        ServerConnection.getInstance(context).setException(new serverErrorException(jsonResponse.getString("message")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
                String body = gson.toJson(tripPlan);
                //Log.e("sendTripPlan==>", "TripPlan body" + body);
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
                    Log.e("getMyTripsFromServer=>", "Got Response:" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        JSONArray jsonArray = new JSONArray(jsonResponse.getString("message"));
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            TripPlan tripPlan = gson.fromJson(jsonArray.get(i).toString(), TripPlan.class);
                            Log.e("getMyTripsFromServer=>", "Got plan with id-" + tripPlan.getTripID());
                            trips.add(tripPlan);
                        }

                        Utility.getInstance(context).setAllTrips(trips);
                    } else {
                        //Log.e("ERROR==>", jsonResponse.getString("message"));
                        ServerConnection.getInstance(context).setException(
                                new serverErrorException(jsonResponse.getString("message")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ServerConnection.getInstance(context).setException(new serverErrorException("Error Connecting to Server"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ServerConnection.getInstance(context).setException(new serverErrorException("Error Connecting to Server"));
                //Log.e("ERROR==>", error.getMessage());
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
    public void sendTripPlansToDelete() {
        Log.e("HERE==>", "Send A tripPlans To delete!!!!");
        ArrayList<DayPlan> arr = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, baseURL + tripURL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
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
                String body = new Gson().toJson(Utility.getInstance(context).getTripsToDelete());
                body = "{" +
                        "\"tripsIdToDeleteList\":" + body +
                        "}";
                return body.getBytes();
            }
        };
        //instance.queue.add(stringRequest);
        addToRequestQueue(stringRequest);

    }

    //----------------------------------------------------------------------------------------
    public void getAttractionsFromServer(String destination) {
        String url = baseURL + allAttractionsURL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Log.e("HERE==>", response);
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
    public void getFavoritesFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("message"));
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    Utility.getInstance(context).getFavoriteAttractions().add(new Gson().fromJson(jsonArray.get(i).toString(), Attraction.class));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    public void sendFavAttractionsToAdd() {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SendFavsToAdd==>", "Response" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("error")) {
                                ServerConnection.getInstance(context).setException(new ServerConnection.serverErrorException(jsonResponse.getString("message")));
                            } else {
                                Log.e("SendFavsToAdd==>", "Got Good Response!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ServerConnection.getInstance(context)
                                    .setException(new ServerConnection.serverErrorException("Error Connecting to Server"));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ServerConnection.getInstance(context)
                        .setException(new ServerConnection.serverErrorException("Error Connecting to Server"));
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
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, baseURL + favAttractionsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SendFavsToDelete==>", "Response" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("error")) {
                                ServerConnection.getInstance(context)
                                        .setException(new ServerConnection.serverErrorException(jsonResponse.getString("message")));
                            } else {
                                Log.e("SendFavsToDelete==>", "Got Good Response!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ServerConnection.getInstance(context)
                                    .setException(new ServerConnection.serverErrorException("Error Connecting to Server"));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ServerConnection.getInstance(context)
                        .setException(new ServerConnection.serverErrorException("Error Connecting to Server"));
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
    public void getHotelsFromServer(String destination) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL + hotelsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("message"));
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    Utility.getInstance(context).getHotels().add(new Gson().fromJson(jsonArray.get(i).toString(), Hotel.class));
                                }
                                Log.e("HERE==>", "Hotels==>" + Utility.getInstance(context).getFavoriteAttractions().toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    //----------------------------------------------------------------------------------------
    public void logIn(String email, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL + loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Log.e("HERE==>", "log in response=" + response);
                    if (json.getString("status").equals("ok")) {
                        String jsonUserString = json.getString("message");
                        Traveler traveler = new Gson().fromJson(jsonUserString, Traveler.class);
                        Utility.getInstance(context).setTraveler(traveler);

                        ServerConnection.getInstance(context).setException(null);
                        Log.e("HERE==>", "Successfully LoggedIn");
                        Log.e("HERE==>", "Traveler ID after log in=" + Utility.getInstance(context).getTravelerID());
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
                ServerConnection.getInstance(context).setException
                        (new serverErrorException("Error connecting to server, please try again"));
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
    public void updateUser(Traveler newTravelerDetails) throws serverErrorException {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, baseURL + updateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("status").equals("ok")) {
                        Utility.getInstance(context).setTraveler(newTravelerDetails);
                    } else {
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

    public static class serverErrorException extends RuntimeException {
        public serverErrorException(String message) {
            super(message);
        }
    }


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