package com.example.tupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.util.Pair;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CreateNewTripActivity extends AppCompatActivity{

    private static final String TAG= "CreateNewTripActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initViewsCreateNewTripActivity();

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
                        Toast.makeText(CreateNewTripActivity.this, "userDetails selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home:
                        Intent intent= new Intent(CreateNewTripActivity.this, MainScreenActivity.class);
                        startActivity(intent);
                    default:
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new CreateNewTripFragment());
        transaction.commit();

    }

    private void initViewsCreateNewTripActivity() {
        Log.d(TAG, "initViewsCreateNewTripActivity: started");

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }


    //pressLint("ParcelCreator")
    //public class RangeDateValidator implements CalendarConstraints.DateValidator{
    //
    // private MaterialDatePicker rangePicker;
    // final int numberOfDays;
    //
    // public RangeDateValidator(int numberOfDays){
    //     this.numberOfDays = numberOfDays;
    // }
    //
    // public void setDatePicker(MaterialDatePicker rangePicker){
    //     this.rangePicker = rangePicker;
    // }
    // @Override
    // public boolean isValid(long date) {
    //     Pair<Long, Long> selection = (Pair<Long, Long>) rangePicker.getSelection();
    //     if(selection != null){
    //         Long startDate = selection.first;
    //         if(startDate != null){
    //             long days = (numberOfDays - 1) * TimeUnit.DAYS.toMillis(1);
    //             if(date > startDate + days)
    //                 return false;
    //             if(date < startDate)
    //                 return false;
    //         }
    //     }
    //     return true;
    // }
    //
    // @Override
    // public int describeContents() {
    //     return 0;
    // }
    //
    // @Override
    // public void writeToParcel(Parcel dest, int flags) {
    //
    // }
    //
}