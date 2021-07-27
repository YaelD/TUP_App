package com.example.tupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainScreenActivity extends AppCompatActivity{

    //Matan is the most handsome man i Know
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private static final String TAG = "MainScreenActivity";

    /*private ImageView btnLogoImage;
    private TextView txtHelloUser;
    private Button btnCreateTrip, btnSearchAtt, btnFavoriteAtt, btnMyTrips, btnAttraction;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initViews();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.userDetails:
                        Toast.makeText(MainScreenActivity.this, "userDetails selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();

      /*  btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, CreateNewTripActivity.class);
                startActivity(intent);
            }
        });
        btnSearchAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, SearchAttractionsActivity.class);
                startActivity(intent);
            }
        });
        btnFavoriteAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, FavoriteAttractionsActivity.class);
                startActivity(intent);
            }
        });
        btnMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, MyTripsActivity.class);
                startActivity(intent);
            }
        });
        btnAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, AttractionDetailsActivity.class);
                startActivity(intent);
            }
        });

         btnCreateTrip.setOnClickListener(this);
        btnSearchAtt.setOnClickListener(this);
        btnFavoriteAtt.setOnClickListener(this);
        btnMyTrips.setOnClickListener(this);
        btnAttraction.setOnClickListener(this);*/

    }


/*    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.btnCreateTrip:
                intent = new Intent(MainScreenActivity.this,CreateNewTripActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSearchAtt:
                intent = new Intent(MainScreenActivity.this,SearchAttractionsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFavoriteAtt:
                intent = new Intent(MainScreenActivity.this,FavoriteAttractionsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMyTrips:
                intent = new Intent(MainScreenActivity.this,MyTripsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAttractions:
                intent = new Intent(MainScreenActivity.this,AttractionDetailsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }*/

    private void initViews() {
        Log.d(TAG, "initViews: started");
        /*btnLogoImage = findViewById(R.id.btnLogoImage);
        txtHelloUser = findViewById(R.id.txtHelloUser);
        btnCreateTrip = findViewById(R.id.btnCreateTrip);
        btnSearchAtt = findViewById(R.id.btnSearchAtt);
        btnFavoriteAtt = findViewById(R.id.btnFavoriteAtt);
        btnMyTrips = findViewById(R.id.btnMyTrips);
        btnAttraction = findViewById(R.id.btnAttractions);*/

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }
}