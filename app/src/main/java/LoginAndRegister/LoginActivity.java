package LoginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TupApp.R;

import NavigationDrawer.NavigationDrawerActivity;
import JavaClasses.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private final String URL = "http://10.0.0.5/login.php";
    private static final String TAG = "LoginActivity";
    private EditText txtEmail, txtPassword;
    private TextView txtWarnEmail, txtWarnPassword;
    private Button btnLoginActivity;
    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

    }

    private void initLogin(){
        Log.d(TAG, "initLogin: started");

        if(validateData()){
            getLogInInfoFromDB();
            //Intent intent= new Intent(LoginActivity.this, MainScreenActivity.class);
            //startActivity(intent);
        }
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: started");
        boolean validData = true;
        if(txtEmail.getText().toString().equals("")) {
            txtWarnEmail.setVisibility(View.VISIBLE);
            txtWarnEmail.setText("Enter your Email");
            validData = false;
        }
        if(txtPassword.getText().toString().equals("")){
            txtWarnPassword.setVisibility(View.VISIBLE);
            txtWarnPassword.setText("Enter your password");
            validData = false;
        }
        return validData;
    }

    private void initViews(){
        Log.d(TAG, "initViews: started");
        txtEmail= findViewById(R.id.txtEmail);
        txtPassword= findViewById(R.id.txtPassword);
        txtWarnEmail= findViewById(R.id.txtWarnEmail);
        txtWarnPassword = findViewById(R.id.txtWarnPassword);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
    }

    public void login(View view) {
        txtWarnEmail.setVisibility(View.GONE);
        txtWarnPassword.setVisibility(View.GONE);
        initLogin();

    }

    public void getLogInInfoFromDB()
    {


        email = txtEmail.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        try {
            ServerConnection.getInstance(this).logIn(email, password);
            Intent intent = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intent);
            finish();
        } catch (ServerConnection.serverErrorException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }




        /*

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getString("status").equals("ok")) {
                        String jsonUserString = json.getString("message");
                        Traveler traveler = new Gson().fromJson(jsonUserString, Traveler.class);
                        Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                return data;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
         */

    }
}