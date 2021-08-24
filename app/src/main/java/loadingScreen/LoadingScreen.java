package loadingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.TupApp.MainActivity;
import com.example.TupApp.R;

import navigationDrawer.NavigationDrawerActivity;
import javaClasses.Utility;

public class LoadingScreen extends AppCompatActivity {

    private final String COOKIE_USER_TOKEN = "cookie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
    }

}