package mta.finalproject.TupApp.tripView;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;

import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class TripViewActivity extends NavigationDrawerActivity {

    private String callingActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance(getApplicationContext()).addActivity(this);
        callingActivity =  getIntent().getStringExtra(CALLING_ACTIVITY);
        setContainer(new TripViewFragment());
        Utility.setLocale(this, "en");
        setCallBack();
    }

    //====================================================================================//

    @Override
    protected void setCallBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    //====================================================================================//

    @Override
    protected void moveToMTrips() {
        if(!callingActivity.equals(MyTripsActivity.class.getName()))
        {
            super.moveToMTrips();
        }
        finish();
    }

    //====================================================================================//

    @Override
    protected void moveToCreateTrip() {
        super.moveToCreateTrip();
        finish();
    }
}