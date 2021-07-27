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

public class CreateNewTripActivity extends AppCompatActivity /*implements View.OnClickListener*/{

    private static final String TAG= "CreateNewTripActivity";
/*    private TextView txtTripDetails;
    private Button btnDestination, btnNumOfDays, btnDesiredHoursInDay, btnMustVisitAtt, btnTest;
    private Spinner destinationSpinner;
    private boolean isbtnDestinationClicked = false, isbtnNumOfDaysClicked = false, isbtnDesiredHoursInDay = false, isbtnMustVisitAtt = false; */

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_new_trip);
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
                    default:
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new CreateNewTripFragment());
        transaction.commit();

       /* MaterialDatePicker.Builder dateRangePickerBuilder = MaterialDatePicker.Builder.dateRangePicker();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();

        RangeDateValidator dateValidator = new RangeDateValidator(7);
        constraintBuilder.setValidator(dateValidator);
        dateRangePickerBuilder.setCalendarConstraints(constraintBuilder.build());


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.setTimeInMillis(MaterialDatePicker.todayInUtcMilliseconds());

        //calendarConstraints
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        //MaterialDateRangePicker
        MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Dates");
        builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();




        btnNumOfDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATES_PICKER");
            }
        });



       /* ArrayList<String> destinations = new ArrayList<>();
        destinations.add("select");
        destinations.add("London, UK");

        ArrayAdapter<String> destinationsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,destinations
        );
        destinationSpinner.setAdapter(destinationsAdapter);
        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDestination.setOnClickListener(this);
        btnMustVisitAtt.setOnClickListener(this);*/

    }

    private void initViewsCreateNewTripActivity() {
        Log.d(TAG, "initViewsCreateNewTripActivity: started");

    /*    txtTripDetails = findViewById(R.id.txtTripDetails);
        btnDestination = findViewById(R.id.btnDestination);
        btnNumOfDays = findViewById(R.id.btnNumOfDays);
        btnDesiredHoursInDay = findViewById(R.id.btnDesiredHoursInDay);
        btnMustVisitAtt = findViewById(R.id.btnMustVisitAtt);
        destinationSpinner = findViewById(R.id.destinationSpinner);

        btnTest = findViewById(R.id.btnTest);*/

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }

 /*   @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: started");
        switch(v.getId()) {
            case R.id.btnDestination:
                if(!isbtnDestinationClicked){
                    destinationSpinner.setVisibility(View.VISIBLE);
                    isbtnDestinationClicked= true;
                }
                else {
                    destinationSpinner.setVisibility(View.GONE);
                    isbtnDestinationClicked= false;
                }
                break;
            case R.id.btnDesiredHoursInDay:

                break;
            case R.id.btnMustVisitAtt:
                btnTest.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }

    }*/

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