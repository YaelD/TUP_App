package mta.finalproject.TupApp.loginAndRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import mta.finalproject.TupApp.R;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.VolleyCallBack;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.Traveler;

public class UserDetailsFragment extends Fragment {

    private static final String TAG = "UserDetailsFragment";
    private Button btnSaveChanges, btnCancel;
    private EditText txtFirstName, txtLastName,txtEmail, txtPassword, txtConfirmPassword;
    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    private TextView txtTitleConfirmPassword, txtEmailExistError;
    private Traveler traveler;
    private String firstName, lastName, email, password;
    private ConstraintLayout userDetailsParent;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        initView(view);


        traveler = Utility.getInstance(getContext()).getTraveler();
        txtFirstName.setHint(traveler.getFirstName());
        txtLastName.setHint(traveler.getLastName());
        txtEmail.setHint(traveler.getEmailAddress());
        txtPassword.setHint(traveler.getPassword());
        txtConfirmPassword.setHint(traveler.getPassword());

        firstName = traveler.getFirstName();
        lastName = traveler.getLastName();
        email = traveler.getEmailAddress();
        password = traveler.getPassword();

        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()){
                    case R.id.txtFirstNameUserDetails:
                        if(hasFocus)
                            if(txtFirstName.getText().toString().isEmpty())
                                firstNameLayout.setError("Please enter in English only");
                        break;
                    case R.id.txtLastNameUserDetails:
                        if(hasFocus)
                            if(txtLastName.getText().toString().isEmpty())
                                lastNameLayout.setError("Please enter in English only");
                        break;
                    case R.id.txtPasswordUserDetails:
                        if(hasFocus)
                            if(txtPassword.getText().toString().isEmpty())
                                passwordLayout.setError("Only English Letters, Numbers, Signs");
                        break;
                    case R.id.txtConfirmPasswordUserDetails:
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


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtFirstName.getText().toString().length() == 0 &&
                        txtLastName.getText().toString().length() == 0 &&
                        txtEmail.getText().toString().length() == 0 &&
                        txtPassword.getText().toString().length() == 0){
                    btnSaveChanges.setEnabled(false);
                }
                else {
                    btnSaveChanges.setEnabled(true);
                }

                if (txtPassword.getText().toString().length() > 0 && txtPassword.getText().toString().length() < 6)
                    passwordLayout.setError("Enter at least 6 characters");
                else if (txtPassword.getText().toString().length() > passwordLayout.getCounterMaxLength())
                    passwordLayout.setError("password is too long");
                else
                    passwordLayout.setError(null);

                if(txtEmail.getText().toString().length() > 0)
                    if (!UserDetailsFragment.this.isEmailValid(txtEmail.getText().toString())) {
                        emailLayout.setError("Invalid email address");
                }
                else if (txtEmail.getText().toString().isEmpty() || isEmailValid(txtEmail.getText().toString())){
                    emailLayout.setError(null);
                }

                if (!txtPassword.getText().toString().isEmpty()){
                    confirmPasswordLayout.setVisibility(View.VISIBLE);
                    txtTitleConfirmPassword.setVisibility(View.VISIBLE);
                }
                else if(txtPassword.getText().toString().isEmpty()){
                    confirmPasswordLayout.setVisibility(View.GONE);
                    txtTitleConfirmPassword.setVisibility(View.GONE);
                }

                if(confirmPasswordLayout.getVisibility() == View.VISIBLE){
                    if (txtConfirmPassword.getText().toString().length() > passwordLayout.getCounterMaxLength())
                        confirmPasswordLayout.setError("password is too long");
                    if(txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                        confirmPasswordLayout.setError(null);
                    }
                    else if(!txtConfirmPassword.getText().toString().isEmpty()){
                        confirmPasswordLayout.setError(null);
                    }
                }

            }
        };

        txtFirstName.addTextChangedListener(watcher);
        txtLastName.addTextChangedListener(watcher);
        txtEmail.addTextChangedListener(watcher);
        txtPassword.addTextChangedListener(watcher);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        };
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You didn't make any changes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstNameLayout.setError(null);
                lastNameLayout.setError(null);
                emailLayout.setError(null);
                passwordLayout.setError(null);
                confirmPasswordLayout.setError(null);
                txtEmailExistError.setVisibility(View.GONE);

                if(!txtFirstName.getText().toString().isEmpty())
                    firstName = txtFirstName.getText().toString();
                if(!txtLastName.getText().toString().isEmpty())
                    lastName = txtLastName.getText().toString();
                if(!txtEmail.getText().toString().isEmpty()) {
                    email = txtEmail.getText().toString();
                }
                boolean isVaild = UserDetailsFragment.this.validateData();
                if(UserDetailsFragment.this.validateData()) {
                    if(!txtPassword.getText().toString().isEmpty())
                        password = txtPassword.getText().toString();

                    Traveler newTraveler = new Traveler(firstName, lastName, email, password);
                    Log.e("HERE==>", newTraveler.toString());

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Processing... Please wait ");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ServerConnection.getInstance(getContext()).updateUser(newTraveler, new VolleyCallBack() {
                        @Override
                        public void onSuccessResponse(Object result) {
                            progressDialog.dismiss();
                            Utility.getInstance(getContext()).setTraveler((Traveler) result);
                            Utility.getInstance(getContext()).writeToSharedPreferences();

                            //Toast.makeText(getActivity(), "Changes saved successfully", Toast.LENGTH_LONG).show();
                            //Utility.getInstance(getContext()).setTraveler(newTraveler);
                            //Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                            //startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void onErrorResponse(String error) {
                            progressDialog.dismiss();
                            txtEmailExistError.setVisibility(View.VISIBLE);

                        }
                    });
                }

            }
        });


        return view;
    }

    private boolean validateData() {
        boolean isValid = true;
        Log.d(TAG, "validateData: started");

        if (txtPassword.getText().toString().length() > 0 && txtPassword.getText().toString().length() < 6) {
            passwordLayout.setError("Enter at least 6 characters");
            isValid = false;
            Log.e("HERE==>", String.valueOf(isValid) + "  1");

        }
        else if (txtPassword.getText().toString().length() > passwordLayout.getCounterMaxLength()) {
            passwordLayout.setError("password is too long");
            isValid = false;
            Log.e("HERE==>", String.valueOf(isValid) + "   2");

        }
        if(!txtPassword.getText().toString().isEmpty()) {
            if (txtConfirmPassword.getText().toString().isEmpty()) {
                confirmPasswordLayout.setError("Enter again your password");
                isValid = false;
                Log.e("HERE==>", String.valueOf(isValid) + "   3");
            } else if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                confirmPasswordLayout.setError("Password doesn't match");
                isValid = false;
                Log.e("HERE==>", String.valueOf(isValid) + "   4");
            }
        }

        if(firstNameLayout.getError() != null || lastNameLayout.getError() != null || emailLayout.getError() != null ||
                passwordLayout.getError() != null || confirmPasswordLayout.getError() != null) {
            isValid = false;

            Log.e("HERE==>", String.valueOf(isValid) + "   5");

        }
        if(!txtEmail.getText().toString().isEmpty()){
            if(!isEmailValid(txtEmail.getText().toString())) {
                emailLayout.setError("Invalid email address");
                isValid = false;
                Log.e("HERE==>", String.valueOf(isValid) + "   6");

            }
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

    private void initView(View view) {
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnCancel = view.findViewById(R.id.btnCancel);
        txtTitleConfirmPassword = view.findViewById(R.id.txtTitleConfirmPassword);
        txtFirstName = view.findViewById(R.id.txtFirstNameUserDetails);
        txtLastName = view.findViewById(R.id.txtLastNameUserDetails);
        txtEmail = view.findViewById(R.id.txtEmailUserDetails);
        txtPassword = view.findViewById(R.id.txtPasswordUserDetails);
        txtConfirmPassword = view.findViewById(R.id.txtConfirmPasswordUserDetails);
        firstNameLayout = view.findViewById(R.id.userDetailsFirstNameLayout);
        lastNameLayout = view.findViewById(R.id.userDetailsLastNameLayout);
        emailLayout = view.findViewById(R.id.userDetailsMailLayout);
        passwordLayout = view.findViewById(R.id.userDetailsPasswordLayout);
        confirmPasswordLayout = view.findViewById(R.id.userDetailsConfirmPasswordLayout);
        userDetailsParent = view.findViewById(R.id.userDetailsParent);
        txtEmailExistError = view.findViewById(R.id.txtEmailExistError);
    }
}
