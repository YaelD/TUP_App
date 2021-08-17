package LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import BaseActivity.BaseActivity;
import JavaClasses.ServerUtility;
import JavaClasses.Traveler;

public class UserDetailsFragment extends Fragment {
    private Button btnSaveChanges, btnCancle;
    private EditText editTxtFirstName, editTxtLastName, editTxtEmail, editTxtPassword, editTxtConfirmPassword;
    private TextView txtEmailError, txtConfirmPassError;
    private Traveler traveler;
    private String firstName, lastName, email, password;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        initView(view);

        traveler = ServerUtility.getInstance(getContext()).getTraveler();
        editTxtFirstName.setHint(traveler.getFirstName());
        editTxtLastName.setHint(traveler.getLastName());
        editTxtEmail.setHint(traveler.getEmailAddress());
        editTxtPassword.setHint(traveler.getPassword());
        editTxtConfirmPassword.setHint(traveler.getPassword());

        firstName = traveler.getFirstName();
        lastName = traveler.getLastName();
        email = traveler.getEmailAddress();
        password = traveler.getPassword();

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You didn't make any changes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BaseActivity.class);
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
                        Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                    }
                }

                Traveler newTraveler = new Traveler(firstName, lastName, email, password);
                ServerUtility.getInstance(getContext()).setTraveler(newTraveler);

            }
        });


        return view;
    }

    private void initView(View view) {
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnCancle = view.findViewById(R.id.btnCancle);
        editTxtFirstName = view.findViewById(R.id.editTxtFirstName);
        editTxtLastName = view.findViewById(R.id.editTxtLastName);
        editTxtEmail = view.findViewById(R.id.editTxtEmail);
        editTxtPassword = view.findViewById(R.id.editTxtPassword);
        editTxtConfirmPassword = view.findViewById(R.id.editTxtConfirmPassword);
        txtEmailError = view.findViewById(R.id.txtEmailError);
        txtConfirmPassError = view.findViewById(R.id.txtConfirmPassError);
    }
}
