package mta.finalproject.TupApp.loginAndRegister;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mta.finalproject.TupApp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import mta.finalproject.TupApp.javaClasses.Traveler;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.VolleyCallBack;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.javaClasses.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ProgressDialog progressDialog;
    private EditText txtEmail, txtPassword;
    private String email, password;
    private TextInputLayout passwordLoginLayout, mailLoginLayout;
    private TextView txtInvalidInputLoginError;

    //====================================================================================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        Utility.setLocale(this, "en");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(callback);

        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    if(txtPassword.getText().toString().isEmpty())
                        passwordLoginLayout.setError("Only English Letters, Numbers, Signs");
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (txtPassword.getText().toString().length() > 0 && txtPassword.getText().toString().length() < 6)
                    passwordLoginLayout.setError("Enter at least 6 characters");
                else if (txtPassword.getText().toString().length() > passwordLoginLayout.getCounterMaxLength())
                    passwordLoginLayout.setError("password is too long");
                else
                    passwordLoginLayout.setError(null);

                if(txtEmail.getText().toString().length() > 0 && (!LoginActivity.this.isEmailValid(txtEmail.getText().toString()))) {
                    mailLoginLayout.setError("Invalid email address");
                }
                else if (isEmailValid(txtEmail.getText().toString())){
                    mailLoginLayout.setError(null);
                }
            }
        };

        txtEmail.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);
    }
    //====================================================================================//

    private void initLogin(){
        Log.d(TAG, "initLogin: started");

        if(validateData()){
            getLogInInfoFromDB();
        }
    }
    //====================================================================================//

    private boolean validateData() {
        Log.d(TAG, "validateData: started");
        boolean validData = true;
        if(txtEmail.getText().toString().isEmpty()) {
            mailLoginLayout.setError("Enter your Email");
            validData = false;
        }
        if(txtPassword.getText().toString().equals("")){
            passwordLoginLayout.setError("Enter your password");
            validData = false;
        }
        if (txtPassword.getText().toString().length() > 0 && txtPassword.getText().toString().length() < 6){
            passwordLoginLayout.setError("Enter at least 6 characters");
            validData = false;
        }
        if (txtPassword.getText().toString().length() > passwordLoginLayout.getCounterMaxLength()){
            passwordLoginLayout.setError("password is too long");
            validData = false;
        }
        if(txtEmail.getText().toString().length() > 0 && (!LoginActivity.this.isEmailValid(txtEmail.getText().toString()))) {
            mailLoginLayout.setError("Invalid email address");
            validData = false;
        }
        return validData;
    }
    //====================================================================================//

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    //====================================================================================//

    private void initViews(){
        Log.d(TAG, "initViews: started");
        txtEmail= findViewById(R.id.txtLoginEmail);
        txtPassword= findViewById(R.id.txtLoginPassword);
        passwordLoginLayout = findViewById(R.id.passwordLoginLayout);
        mailLoginLayout = findViewById(R.id.mailLoginLayout);
        txtInvalidInputLoginError = findViewById(R.id.txtInvalidInputLoginError);
    }
    //====================================================================================//

    public void login(View view) {
        mailLoginLayout.setError(null);
        passwordLoginLayout.setError(null);
        initLogin();
    }
    //====================================================================================//

    public void getLogInInfoFromDB() {
        txtInvalidInputLoginError.setVisibility(View.GONE);
        email = txtEmail.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Processing... Please wait ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ServerConnection.getInstance(getApplicationContext()).logIn(email, password, new VolleyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccessResponse(String result) {
                progressDialog.dismiss();
                Traveler traveler = new Gson().fromJson(result, Traveler.class);
                Utility.getInstance(getApplicationContext()).setTraveler(traveler);
                Utility.getInstance(getApplicationContext()).getDataFromServer();
                Utility.getInstance(getApplicationContext()).writeToSharedPreferences();
                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
            @Override
            public void onErrorResponse(String error) {
                txtInvalidInputLoginError.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                //Toast.makeText(getApplicationContext(), "Error Connecting to Server", Toast.LENGTH_SHORT);
            }
        });
    }
    //====================================================================================//

}