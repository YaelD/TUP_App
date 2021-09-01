package mta.finalproject.TupApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.loginAndRegister.LoginActivity;
import mta.finalproject.TupApp.loginAndRegister.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnLogin, btnRegister;
    private ImageView backgroundImage, logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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
    }

    private void initViews() {
        Log.d(TAG, "initViews: started");
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        backgroundImage = findViewById(R.id.backgroundImage);
        logoImage = findViewById(R.id.logoImage);
    }

}