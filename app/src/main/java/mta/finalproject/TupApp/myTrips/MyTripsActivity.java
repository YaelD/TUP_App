package mta.finalproject.TupApp.myTrips;

import android.os.Bundle;

import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class MyTripsActivity extends NavigationDrawerActivity {

    private static final String TAG= "MyTripsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);
        setContainer(new MyTripsFragment());
    }

}