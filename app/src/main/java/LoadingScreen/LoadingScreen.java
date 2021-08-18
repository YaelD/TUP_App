package LoadingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.TupApp.MainActivity;
import com.example.TupApp.R;

import BaseActivity.BaseActivity;
import JavaClasses.Utility;

public class LoadingScreen extends AppCompatActivity {

    private final String COOKIE_USER_TOKEN = "cookie";

    private String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);


        cookie = null;
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if(sharedPreferences != null)
        {
            cookie = sharedPreferences.getString(COOKIE_USER_TOKEN, null);
        }
        Utility.getInstance(this).getAttractions();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
                if(cookie != null)
                {
                    intent = new Intent(LoadingScreen.this, BaseActivity.class);
                }
                startActivity(intent);
                finish();
                startActivity(intent);
            }
        }, 4000);
    }
}