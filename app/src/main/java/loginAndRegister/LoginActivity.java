package loginAndRegister;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TupApp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import navigationDrawer.NavigationDrawerActivity;
import javaClasses.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private final String URL = "http://10.0.0.5/login.php";
    private static final String TAG = "LoginActivity";
    private EditText txtEmail, txtPassword;
    private Button btnLoginActivity;
    private String email, password;
    private TextInputLayout passwordLoginLayout, mailLoginLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(callback);



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

    private void initViews(){
        Log.d(TAG, "initViews: started");
        txtEmail= findViewById(R.id.txtLoginEmail);
        txtPassword= findViewById(R.id.txtLoginPassword);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        passwordLoginLayout = findViewById(R.id.passwordLoginLayout);
        mailLoginLayout = findViewById(R.id.mailLoginLayout);
    }

    public void login(View view) {
        mailLoginLayout.setError(null);
        passwordLoginLayout.setError(null);
        initLogin();
    }

    public void getLogInInfoFromDB()
    {
        email = txtEmail.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        ServerConnection.getInstance(this).logIn(email, password);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(ServerConnection.getInstance(getApplicationContext()).getException()== null)
                {
                    ServerConnection.getInstance(getApplicationContext()).setException(null);
                    ServerConnection.getInstance(getApplicationContext()).getMyTripsFromServer();
                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            ServerConnection.getInstance(getApplicationContext()).getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        new Handler().postDelayed(r, 2000);

    }
}