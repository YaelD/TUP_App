package com.example.tupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText txtEmail, txtPassword;
    private TextView txtWarnEmail, txtWarnPassword;
    private Button btnLoginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtWarnEmail.setVisibility(View.GONE);
                txtWarnPassword.setVisibility(View.GONE);
                initLogin();
            }
        });
    }

    private void initLogin(){
        Log.d(TAG, "initLogin: started");

        if(validateData()){
            Intent intent= new Intent(LoginActivity.this, MainScreenActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: started");
        boolean validData = true;
        if(txtEmail.getText().toString().equals("")) {
            txtWarnEmail.setVisibility(View.VISIBLE);
            txtWarnEmail.setText("Enter your Email");
            validData = false;
        }
        if(txtPassword.getText().toString().equals("")){
            txtWarnPassword.setVisibility(View.VISIBLE);
            txtWarnPassword.setText("Enter your password");
            validData = false;
        }
        return validData;
    }

    private void initViews(){
        Log.d(TAG, "initViews: started");
        txtEmail= findViewById(R.id.txtEmail);
        txtPassword= findViewById(R.id.txtPassword);
        txtWarnEmail= findViewById(R.id.txtWarnEmail);
        txtWarnPassword = findViewById(R.id.txtWarnPassword);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
    }
}