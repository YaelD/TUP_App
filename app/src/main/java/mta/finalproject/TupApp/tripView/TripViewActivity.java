package mta.finalproject.TupApp.tripView;

import android.os.Bundle;

import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class TripViewActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);

        setContainer(new TripViewFragment());
    }
}