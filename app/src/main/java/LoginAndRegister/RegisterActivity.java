package LoginAndRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.TupApp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import JavaClasses.Traveler;
import MainScreen.MainScreenActivity;

public class RegisterActivity extends AppCompatActivity {


    private final String URL = "http://10.0.0.5:8080/web_war_exploded/register";
    private static final String TAG = "RegisterActivity";
    private EditText txtFirstName, txtLastName, txtEmailAddr, txtPasswordRegister, txtRePassword;
    private TextView txtWarnFirstName, txtWarnLastName, txtWarningEmail, txtWarningPassword, txtWarningComfirmPass;
    private Button btnRegisterSystem;
    private ConstraintLayout parent;
    private  String firstName, lastName, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName = lastName = email = password = "";
        initViews();


    }

    private void initRegister(){
        Log.d(TAG, "initRegister: started");

        if(validateData()){
            sendRegisterInfoToServer();
            //showSnackBar();
        }

    }

    private void showSnackBar() {
        Log.d(TAG, "showSnackBar: started");
        txtWarnFirstName.setVisibility(View.GONE);
        txtWarnLastName.setVisibility(View.GONE);
        txtWarningEmail.setVisibility(View.GONE);
        txtWarningPassword.setVisibility(View.GONE);
        txtWarningComfirmPass.setVisibility(View.GONE);

        Snackbar.make(parent, "You have successfully registered", Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtFirstName.setText("");
                        txtLastName.setText("");
                        txtEmailAddr.setText("");
                        txtPasswordRegister.setText("");
                        txtRePassword.setText("");
                    }
                })
                .show();
    }


    private boolean validateData() {
        boolean isValid = true;
        Log.d(TAG, "validateData: started");

        if(txtFirstName.getText().toString().equals("")){
            txtWarnFirstName.setVisibility(View.VISIBLE);
            txtWarnFirstName.setText("Enter your first name");
            isValid = false;
        }
        if(txtLastName.getText().toString().equals("")){
            txtWarnLastName.setVisibility(View.VISIBLE);
            txtWarnLastName.setText("Enter your last name");
            isValid = false;
        }
        if(txtEmailAddr.getText().toString().equals("")){
            txtWarningEmail.setVisibility(View.VISIBLE);
            txtWarningEmail.setText("Enter your Email");
            isValid = false;
        }
        if(txtPasswordRegister.getText().toString().equals("")){
            txtWarningPassword.setVisibility(View.VISIBLE);
            txtWarningPassword.setText("Enter your password");
            isValid = false;
        }
        if(txtRePassword.getText().toString().equals("")){
            txtWarningComfirmPass.setVisibility(View.VISIBLE);
            txtWarningComfirmPass.setText("Enter again your password");
            isValid = false;
        }
        if(!txtPasswordRegister.getText().toString().equals(txtRePassword.getText().toString())){
            txtWarningComfirmPass.setVisibility(View.VISIBLE);
            txtWarningComfirmPass.setText("Password doesn't match");
            isValid = false;
        }
        return isValid;
    }

    private void initViews() {
        Log.d(TAG, "initViews: started");
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmailAddr = findViewById(R.id.txtEmailAddr);
        txtPasswordRegister = findViewById(R.id.txtPasswordRegister);
        txtRePassword = findViewById(R.id.txtRePassword);

        txtWarnFirstName = findViewById(R.id.txtWarnFirstName);
        txtWarnLastName = findViewById(R.id.txtWarnLastName);
        txtWarningEmail = findViewById(R.id.txtWarningEmail);
        txtWarningPassword = findViewById(R.id.txtWarningPassword);
        txtWarningComfirmPass = findViewById(R.id.txtWarningComfirmPass);

        btnRegisterSystem= findViewById(R.id.btnRegisterSystem);
        parent = findViewById(R.id.parent);
    }

    public void register(View view) {
        txtWarnFirstName.setVisibility(View.GONE);
        txtWarnLastName.setVisibility(View.GONE);
        txtWarningEmail.setVisibility(View.GONE);
        txtWarningPassword.setVisibility(View.GONE);
        txtWarningComfirmPass.setVisibility(View.GONE);

        initRegister();

    }

    private void sendRegisterInfoToServer()
    {
        firstName = txtFirstName.getText().toString().trim();
        lastName = txtLastName.getText().toString().trim();
        email = txtEmailAddr.getText().toString().trim();
        password = txtPasswordRegister.getText().toString().trim();
        Traveler registerTraveler = new Traveler(firstName, lastName, email, password);
        Gson gson = new Gson();
        //make a new request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getString("status").equals("error")) {
                        Toast.makeText(RegisterActivity.this, json.getString("message").trim(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        btnRegisterSystem.setClickable(false);
                        Intent intent= new Intent(RegisterActivity.this, MainScreenActivity.class);
                        Traveler traveler = new Gson().fromJson(json.getString("message"), Traveler.class);
                        intent.putExtra("Traveler", traveler);
                        showSnackBar();
                        finish();
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return gson.toJson(registerTraveler).getBytes(StandardCharsets.UTF_8);
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}