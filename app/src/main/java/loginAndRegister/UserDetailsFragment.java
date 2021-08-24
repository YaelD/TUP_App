package loginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.TupApp.R;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import javaClasses.ServerConnection;
import navigationDrawer.NavigationDrawerActivity;
import javaClasses.Utility;
import javaClasses.Traveler;

public class UserDetailsFragment extends Fragment {
    private Button btnSaveChanges, btnCancel;
    private EditText txtFirstName, txtLastName,txtEmail, txtPassword, txtConfirmPassword;
    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    private TextView txtTitleConfirmPassword;
    private Traveler traveler;
    private String firstName, lastName, email, password;


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
                if (!txtPassword.getText().toString().isEmpty()){
                    confirmPasswordLayout.setVisibility(View.VISIBLE);
                    txtTitleConfirmPassword.setVisibility(View.VISIBLE);
                }
                else if(txtPassword.getText().toString().isEmpty()){
                    confirmPasswordLayout.setVisibility(View.GONE);
                    txtTitleConfirmPassword.setVisibility(View.GONE);
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
                getActivity().finish();
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
                //TODO: show snackBar

                if(!txtFirstName.getText().toString().isEmpty())
                    firstName = txtFirstName.getText().toString();
                if(!txtLastName.getText().toString().isEmpty())
                    lastName = txtLastName.getText().toString();
                if(!txtEmail.getText().toString().isEmpty()) {
                    email = txtEmail.getText().toString();
                    //TODO: check if email exists in DB
                }
                if(!txtPassword.getText().toString().isEmpty()){
                    if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString()))
                        confirmPasswordLayout.setError("Password doesn't match");
                    else{
                        password = txtPassword.getText().toString();
                        Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_LONG).show();
                    }
                }

                Traveler newTraveler = new Traveler(firstName, lastName, email, password);
                ServerConnection.getInstance(getContext()).updateUser(newTraveler);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        ServerConnection.serverErrorException exception = ServerConnection.getInstance(getContext()).getException();
                        if(exception!= null)
                        {
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Utility.getInstance(getContext()).setTraveler(newTraveler);
                            Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                };
                new Handler().postDelayed(runnable, 3000);

            }
        });


        return view;
    }

    private void initView(View view) {
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnCancel = view.findViewById(R.id.btnCancle);
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
    }
}
