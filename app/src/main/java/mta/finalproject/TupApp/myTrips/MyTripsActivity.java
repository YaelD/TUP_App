package mta.finalproject.TupApp.myTrips;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.MainActivity;
import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.loginAndRegister.UserDetailsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

public class MyTripsActivity extends NavigationDrawerActivity {

    private static final String TAG= "MyTripsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance(getApplicationContext()).addActivity(this);
        setContainer(new MyTripsFragment());
        Utility.setLocale(this, "en");
    }

    //====================================================================================//

    @Override
    protected void moveToMTrips() {
    }
}