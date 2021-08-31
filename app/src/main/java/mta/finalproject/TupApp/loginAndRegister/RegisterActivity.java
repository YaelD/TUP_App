package mta.finalproject.TupApp.loginAndRegister;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mta.finalproject.TupApp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.regex.Pattern;

import mta.finalproject.TupApp.javaClasses.Geometry;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Traveler;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.VolleyCallBack;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    public final static String LATIN_STRING = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
    private ProgressDialog progressDialog;
    private EditText txtFirstName,txtLastName, txtEmail, txtPassword, txtConfirmPassword;
    private Button btnRegister;
    private TextView txtInvalidInputError;
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



        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()){
                    case R.id.txtFirstNameRegister:
                        if(hasFocus)
                            if(txtFirstName.getText().toString().isEmpty())
                                FirstNameLayout.setError("Please enter in English only");
                        break;
                    case R.id.txtLastNameRegister:
                        if(hasFocus)
                            if(txtLastName.getText().toString().isEmpty())
                                LastNameLayout.setError("Please enter in English only");
                        break;
                    case R.id.txtPasswordRegister:
                        if(hasFocus)
                            if(txtPassword.getText().toString().isEmpty())
                                passwordLayout.setError("Only English Letters, Numbers, Signs");
                        break;
                    case R.id.txtConfirmPasswordRegister:
                        if(hasFocus)
                            if(txtConfirmPassword.getText().toString().isEmpty())
                                confirmPasswordLayout.setError("Only English Letters, Numbers, Signs");
                        break;
                    default:
                        break;
                }
            }
        };

        txtFirstName.setOnFocusChangeListener(listener);
        txtLastName.setOnFocusChangeListener(listener);
        txtEmail.setOnFocusChangeListener(listener);
        txtPassword.setOnFocusChangeListener(listener);
        txtConfirmPassword.setOnFocusChangeListener(listener);


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
        txtFirstName = findViewById(R.id.txtFirstNameRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.txtEmailRegister);
        mailLayout = findViewById(R.id.registerMailLayout);
        txtLastName = findViewById(R.id.txtLastNameRegister);
        txtPassword = findViewById(R.id.txtPasswordRegister);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordRegister);
        FirstNameLayout = findViewById(R.id.registerFirstNameLayout);
        LastNameLayout = findViewById(R.id.registerLastNameLayout);
        passwordLayout = findViewById(R.id.registerPasswordLayout);
        confirmPasswordLayout = findViewById(R.id.registerConfirmPasswordLayout);
        txtInvalidInputError = findViewById(R.id.txtInvalidInputError);
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
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Processing... Please wait ");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServerConnection.getInstance(this).register(registerTraveler, new VolleyCallBack() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccessResponse(Object result) {
                    progressDialog.dismiss();
                    Traveler traveler = new Gson().fromJson((String) result, Traveler.class);
                    Utility.getInstance(getApplicationContext()).setTraveler(traveler);
                    Log.e("HERE==>", "travelerID is--" +
                            Utility.getInstance(getApplicationContext()).getTravelerID());
                    Utility.getInstance(getApplicationContext()).getDataFromServer();
                    Utility.getInstance(getApplicationContext()).saveData();
                    Intent intent = new Intent(RegisterActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onErrorResponse(String error) {
                    progressDialog.dismiss();
                    txtInvalidInputError.setVisibility(View.VISIBLE);
                }
            });
            /*


            new Handler().postDelayed(runnable, 0);
             */

        }
}