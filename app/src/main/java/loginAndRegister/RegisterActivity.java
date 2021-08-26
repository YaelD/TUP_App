package loginAndRegister;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import javaClasses.ServerConnection;
import javaClasses.Traveler;
import javaClasses.Utility;
import navigationDrawer.NavigationDrawerActivity;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private ProgressDialog progressDialog;
    private EditText txtFirstName,txtLastName, txtEmail, txtPassword, txtConfirmPassword;
    private Button btnRegister;
    private TextInputLayout FirstNameLayout, LastNameLayout, mailLayout, passwordLayout, confirmPasswordLayout;
    private  String firstName, lastName, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = lastName = email = password = "";
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
                    passwordLayout.setError("Enter at least 6 characters");
                else if (txtPassword.getText().toString().length() > passwordLayout.getCounterMaxLength())
                    passwordLayout.setError("password is too long");
                else
                    passwordLayout.setError(null);

                if(txtEmail.getText().toString().length() > 0 && (!RegisterActivity.this.isEmailValid(txtEmail.getText().toString()))) {
                    mailLayout.setError("Invalid email address");
                }
                else if (isEmailValid(txtEmail.getText().toString())){
                    mailLayout.setError(null);

                }

                if(txtFirstName.getText().toString().length() > 0){
                    FirstNameLayout.setError(null);
                }
                if(txtLastName.getText().toString().length() > 0){
                    LastNameLayout.setError(null);
                }

                if(txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                    confirmPasswordLayout.setError(null);
                }
                else if(!txtConfirmPassword.getText().toString().isEmpty()){
                    confirmPasswordLayout.setError(null);
                }
            }
        };

        txtFirstName.addTextChangedListener(textWatcher);
        txtLastName.addTextChangedListener(textWatcher);
        txtEmail.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);
        txtConfirmPassword.addTextChangedListener(textWatcher);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirstNameLayout.setError(null);
                LastNameLayout.setError(null);
                mailLayout.setError(null);
                passwordLayout.setError(null);
                confirmPasswordLayout.setError(null);

                if(RegisterActivity.this.validateData()) {
                    sendRegisterInfoToServer();
                }
            }
        });

    }

    private void initViews() {
        txtFirstName = findViewById(R.id.txtFirstNameUserDetails);
        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.txtEmailUserDetails);
        mailLayout = findViewById(R.id.userDetailsMailLayout);
        txtLastName = findViewById(R.id.txtLastNameUserDetails);
        txtPassword = findViewById(R.id.txtPasswordUserDetails);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordUserDetails);
        FirstNameLayout = findViewById(R.id.userDetailsFirstNameLayout);
        LastNameLayout = findViewById(R.id.userDetailsLastNameLayout);
        passwordLayout = findViewById(R.id.userDetailsPasswordLayout);
        confirmPasswordLayout = findViewById(R.id.userDetailsConfirmPasswordLayout);
    }

    private boolean validateData() {
        boolean isValid = true;
        Log.d(TAG, "validateData: started");

        if(txtFirstName.getText().toString().isEmpty()){
            FirstNameLayout.setError("Enter your first name");
            isValid = false;
        }
        if(txtLastName.getText().toString().isEmpty()){
            LastNameLayout.setError("Enter your last name");
            isValid = false;
        }
        if(txtEmail.getText().toString().isEmpty()){
            mailLayout.setError("Enter your email");
            isValid = false;
        }
        if(txtPassword.getText().toString().isEmpty()){
            passwordLayout.setError("Enter your password");
            isValid = false;
        }
        else if (txtPassword.getText().toString().length() > 0 && txtPassword.getText().toString().length() < 6) {
            passwordLayout.setError("Enter at least 6 characters");
            isValid = false;
        }
        else if (txtPassword.getText().toString().length() > passwordLayout.getCounterMaxLength()) {
            passwordLayout.setError("password is too long");
            isValid = false;
        }
        if(txtConfirmPassword.getText().toString().isEmpty()){
            confirmPasswordLayout.setError("Enter again your password");
            isValid = false;
        }
        if(!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
            confirmPasswordLayout.setError("Password doesn't match");
            isValid = false;
        }
        if(FirstNameLayout.getError() != null || LastNameLayout.getError() != null || mailLayout.getError() != null ||
                passwordLayout.getError() != null || confirmPasswordLayout.getError() != null) {
            isValid = false;
        }
        if(!isEmailValid(txtEmail.getText().toString())) {
            mailLayout.setError("Invalid email address");
            isValid = false;
        }

        return isValid;
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

        private void sendRegisterInfoToServer()
        {
            firstName = txtFirstName.getText().toString().trim();
            lastName = txtLastName.getText().toString().trim();
            email = txtEmail.getText().toString().trim();
            password = txtPassword.getText().toString().trim();
            Traveler registerTraveler = new Traveler(firstName, lastName, email, password);
            ServerConnection.getInstance(this).register(registerTraveler);
            Log.e("HERE==>", "In Register activity");
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Processing... Please wait ");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ServerConnection.serverErrorException exception = ServerConnection.getInstance(getApplication().getApplicationContext()).getException();
                    if(exception != null)
                    {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.e("HERE==>", "travelerID is--" + Utility.getInstance(getApplicationContext()).getTravelerID());
                        Intent intent = new Intent(RegisterActivity.this, NavigationDrawerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            new Handler().postDelayed(runnable, 3000);

        }
}