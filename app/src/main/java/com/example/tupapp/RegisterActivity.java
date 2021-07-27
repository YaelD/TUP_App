package com.example.tupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText txtFirstName, txtLastName, txtEmailAddr, txtPasswordRegister, txtRePassword;
    private TextView txtWarnFirstName, txtWarnLastName, txtWarningEmail, txtWarningPassword, txtWarningComfirmPass;
    private Button btnRegisterSystem;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        btnRegisterSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtWarnFirstName.setVisibility(View.GONE);
                txtWarnLastName.setVisibility(View.GONE);
                txtWarningEmail.setVisibility(View.GONE);
                txtWarningPassword.setVisibility(View.GONE);
                txtWarningComfirmPass.setVisibility(View.GONE);
                initRegister();
            }
        });
    }

    private void initRegister(){
        Log.d(TAG, "initRegister: started");

        if(validateData()){
            showSnackBar();
            Intent intent= new Intent(RegisterActivity.this, MainScreenActivity.class);
            startActivity(intent);
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
}