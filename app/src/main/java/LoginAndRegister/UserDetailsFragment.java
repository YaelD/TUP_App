package LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
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

import org.jetbrains.annotations.NotNull;

import NavigationDrawer.NavigationDrawerActivity;
import JavaClasses.Utility;
import JavaClasses.Traveler;

public class UserDetailsFragment extends Fragment {
    private Button btnSaveChanges, btnCancel;
    private EditText editTxtFirstName, editTxtLastName, editTxtEmail, editTxtPassword, editTxtConfirmPassword;
    private TextView txtEmailError, txtConfirmPassError, txtTitleConfirmPassword;
    private Traveler traveler;
    private String firstName, lastName, email, password;
    private ImageView imgShowPassword, imgShowConfirmPassword, imgHidePassword, imgHideConfirmPassword;



    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        initView(view);

        traveler = Utility.getInstance(getContext()).getTraveler();
        editTxtFirstName.setHint(traveler.getFirstName());
        editTxtLastName.setHint(traveler.getLastName());
        editTxtEmail.setHint(traveler.getEmailAddress());
        editTxtPassword.setHint(traveler.getPassword());
        editTxtConfirmPassword.setHint(traveler.getPassword());

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
                if (editTxtFirstName.getText().toString().length() == 0 &&
                        editTxtLastName.getText().toString().length() == 0 &&
                        editTxtEmail.getText().toString().length() == 0 &&
                        editTxtPassword.getText().toString().length() == 0){
                    btnSaveChanges.setEnabled(false);
                }
                else {
                    btnSaveChanges.setEnabled(true);
                }
                if (!editTxtPassword.getText().toString().isEmpty()){
                    editTxtConfirmPassword.setVisibility(View.VISIBLE);
                    txtTitleConfirmPassword.setVisibility(View.VISIBLE);
                    imgShowConfirmPassword.setVisibility(View.VISIBLE);
                }
                else if(editTxtPassword.getText().toString().isEmpty()){
                    editTxtConfirmPassword.setVisibility(View.GONE);
                    txtTitleConfirmPassword.setVisibility(View.GONE);
                    imgShowConfirmPassword.setVisibility(View.GONE);
                }
            }
        };

        editTxtFirstName.addTextChangedListener(watcher);
        editTxtLastName.addTextChangedListener(watcher);
        editTxtEmail.addTextChangedListener(watcher);
        editTxtPassword.addTextChangedListener(watcher);


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

                if(!editTxtFirstName.getText().toString().equals(""))
                    firstName = editTxtFirstName.getText().toString();
                if(!editTxtLastName.getText().toString().equals(""))
                    lastName = editTxtLastName.getText().toString();
                if(!editTxtEmail.getText().toString().equals("")) {
                    email = editTxtEmail.getText().toString();
                    //TODO: check if email exists in DB
                }
                if(!editTxtPassword.getText().toString().equals("")){
                    if (!editTxtPassword.getText().toString().equals(editTxtConfirmPassword.getText().toString()))
                        txtConfirmPassError.setVisibility(View.VISIBLE);
                    else{
                        txtConfirmPassError.setVisibility(View.GONE);
                        password = editTxtPassword.getText().toString();
                        Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_LONG).show();
                    }
                }

                Traveler newTraveler = new Traveler(firstName, lastName, email, password);
                Utility.getInstance(getContext()).setTraveler(newTraveler);

            }
        });





        imgShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTxtPassword.setTransformationMethod(null);
                imgShowPassword.setVisibility(View.GONE);
                imgHidePassword.setVisibility(View.VISIBLE);
            }
        });

        imgShowConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTxtConfirmPassword.setTransformationMethod(null);
                imgShowConfirmPassword.setVisibility(View.GONE);
                imgHideConfirmPassword.setVisibility(View.VISIBLE);
            }
        });

        imgHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTxtPassword.setTransformationMethod(new PasswordTransformationMethod());
                imgShowPassword.setVisibility(View.VISIBLE);
                imgHidePassword.setVisibility(View.GONE);
            }
        });

        imgHideConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTxtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                imgShowConfirmPassword.setVisibility(View.VISIBLE);
                imgHideConfirmPassword.setVisibility(View.GONE);
            }
        });


        return view;
    }

    private void initView(View view) {
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnCancel = view.findViewById(R.id.btnCancle);
        editTxtFirstName = view.findViewById(R.id.editTxtFirstName);
        editTxtLastName = view.findViewById(R.id.editTxtLastName);
        editTxtEmail = view.findViewById(R.id.editTxtEmail);
        editTxtPassword = view.findViewById(R.id.editTxtPassword);
        editTxtConfirmPassword = view.findViewById(R.id.editTxtConfirmPassword);
        txtEmailError = view.findViewById(R.id.txtEmailError);
        txtConfirmPassError = view.findViewById(R.id.txtConfirmPassError);
        imgShowPassword = view.findViewById(R.id.imgShowPassword);
        imgShowConfirmPassword = view.findViewById(R.id.imgShowConfirmPassword);
        imgHidePassword = view.findViewById(R.id.imgHidePassword);
        imgHideConfirmPassword = view.findViewById(R.id.imgHideConfirmPassword);
        txtTitleConfirmPassword = view.findViewById(R.id.txtTitleConfirmPassword);
    }
}
