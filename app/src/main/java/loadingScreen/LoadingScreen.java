package loadingScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.example.TupApp.MainActivity;
import com.example.TupApp.R;
import com.google.gson.Gson;

import javaClasses.ServerConnection;
import javaClasses.Traveler;
import navigationDrawer.NavigationDrawerActivity;
import javaClasses.Utility;

public class LoadingScreen extends AppCompatActivity {

    private final String COOKIE_USER_TOKEN = "cookie";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Handler handler = new Handler();
        //TODO: upload all the relevant data from the server
        ServerConnection.getInstance(getApplicationContext()).getAttractionsFromServer("london");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: CHECK FOR COOKIE TO NAVIGATE THE USER TO RELEVANT INTENT
                Intent intent = new Intent(LoadingScreen.this, NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }

}