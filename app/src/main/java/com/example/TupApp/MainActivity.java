package com.example.TupApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import JavaClasses.DayPlan;
import JavaClasses.ServerUtility;
import JavaClasses.TripDetails;
import LoginAndRegister.LoginActivity;
import LoginAndRegister.RegisterActivity;
import MainScreen.MainScreenActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnLogin, btnRegister, btnGeust, button1;
    private ImageView backgroundImage, logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //Matan Test
        //ServerUtility.getInstance(this).getAttractions();

        //ServerUtility.getInstance(this).getTrip(TripDetails.staticTrip());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnGeust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        Log.d(TAG, "initViews: started");
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnGeust = findViewById(R.id.btnGuset);

        backgroundImage = findViewById(R.id.backgroundImage);
        logoImage = findViewById(R.id.logoImage);


    }
}