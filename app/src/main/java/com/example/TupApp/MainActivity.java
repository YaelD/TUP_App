package com.example.TupApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import javaClasses.ServerConnection;
import javaClasses.Utility;
import navigationDrawer.NavigationDrawerActivity;
import loginAndRegister.LoginActivity;
import loginAndRegister.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnLogin, btnRegister, btnGeust, button1;
    private ImageView backgroundImage, logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        preLaunch();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnGeust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
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


    private void preLaunch()
    {
        ServerConnection.getInstance(this.getApplicationContext()).getAttractionsFromServer("london");
        Utility.getInstance(getApplicationContext()).setTravelerID("3");
        ServerConnection.getInstance(getApplicationContext()).getFavoritesFromServer();

    }
}