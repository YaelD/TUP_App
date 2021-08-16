package MyTrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.TupApp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import BaseActivity.BaseActivity;

public class MyTripsActivity extends BaseActivity {

    private static final String TAG= "MyTripsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);
        setContainer(new MyTripsFragment());
    }

}