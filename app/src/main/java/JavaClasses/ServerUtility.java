package JavaClasses;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerUtility {
    private final String baseURL = "http://localhost:8080/web_war_exploded";
    private final String allAttractionsURL = "/attractions/all";
    private static ServerUtility instance;
    private RequestQueue queue;
    private Context context;

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Attraction> hotels = new ArrayList<>();

    private Traveler travelerDetails;

    private String cookie;


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
    }

    public void getAllAttractions()
    {
        StringRequest request = new StringRequest(Request.Method.POST,baseURL+allAttractionsURL ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("status").equals("error"))
                    {
                        Toast toast = Toast.makeText(this, "Server Connection Error", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        String attractionsStr = json.getString("message");
                        attractions = new Gson().fromJson(attractionsStr, ArrayList.class);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(this, "Server Connection Error", Toast.LENGTH_SHORT);
                    toast.show();
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/html");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return "london".getBytes();
            }
        };

    }


}
