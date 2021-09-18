package mta.finalproject.TupApp.tripCreation;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.loginAndRegister.UserDetailsActivity;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class CreateNewTripActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.getInstance(getApplicationContext()).addActivity(this);
        super.onCreate(savedInstanceState);
        setContainer(new CreateNewTripFragment());
        Utility.setLocale(this, "en");
        setCallBack();
    }
    //====================================================================================//

    @Override
    protected void moveToCreateTrip() {
    }
    //====================================================================================//

    @Override
    protected void setCallBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Utility.getInstance(getApplicationContext()).getTripSelectedAttractions().clear();
                Utility.getInstance(getApplicationContext()).finishAllActivities();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

    }


    //====================================================================================//

    @Override
    protected void moveToFavoriteAttractions() {
        Utility.getInstance(getApplicationContext()).finishAllActivities();
        Intent intent= new Intent(CreateNewTripActivity.this, FavoriteAttractionsActivity.class);
        intent.putExtra(CALLING_ACTIVITY, NavigationDrawerActivity.class.getName());
        startActivity(intent);
    }

    //====================================================================================//

    @Override
    protected void moveToSearchAttractions() {
        Utility.getInstance(getApplicationContext()).finishAllActivities();
        Intent intent= new Intent(CreateNewTripActivity.this, SearchAttractionsActivity.class);
        intent.putExtra(CALLING_ACTIVITY, NavigationDrawerActivity.class.getName());
        startActivity(intent);
    }

    //====================================================================================//

    @Override
    protected void onStop() {
        super.onStop();
    }
}