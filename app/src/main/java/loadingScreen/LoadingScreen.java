package loadingScreen;

import static javaClasses.Utility.TRAVELER;
import static javaClasses.Utility.TRAVELER_ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.TupApp.MainActivity;
import com.example.TupApp.R;
import com.google.gson.Gson;

import javaClasses.ServerConnection;
import javaClasses.Traveler;
import navigationDrawer.NavigationDrawerActivity;
import javaClasses.Utility;

public class LoadingScreen extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        SharedPreferences sharedPreferences = Utility.getInstance(getApplicationContext()).getSharedPreferences();
        Utility.getInstance(getApplicationContext()).setTravelerID(sharedPreferences.getString(TRAVELER_ID, "0"));
        //ServerConnection.getInstance(getApplicationContext()).getHotelsFromServer("london");
        ServerConnection.getInstance(getApplicationContext()).getAttractionsFromServer("london");
        if(sharedPreferences.contains((TRAVELER_ID)))
        {
            String jsonTraveler = sharedPreferences.getString(TRAVELER, "");
            Log.e("LoadingScreen==>", "Traveler in App" + jsonTraveler);
            Log.e("LoadingScreen==>", "TravelerID " + Utility.getInstance(getApplicationContext()).getTravelerID());
            Traveler traveler = new Gson().fromJson(jsonTraveler, Traveler.class);
            Utility.getInstance(getApplicationContext()).setTraveler(traveler);
            //ServerConnection.getInstance(getApplicationContext()).getFavoritesFromServer();
            //ServerConnection.getInstance(getApplicationContext()).getMyTripsFromServer();
            Handler handler = new Handler();
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
        else
        {
            Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

}