package mta.finalproject.TupApp.favoriteAttractions;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.MainActivity;
import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.loginAndRegister.UserDetailsActivity;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

public class FavoriteAttractionsActivity extends NavigationDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance(getApplicationContext()).addActivity(this);
        setContainer(new FavoriteAttractionsFragment());
        Utility.setLocale(this, "en");
        setCallBack();
    }

    @Override
    protected void moveToFavoriteAttractions() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("FavAttractionsAct==>", "SAVING and Deleting Favs Attractions");
        if(Utility.getInstance(getApplicationContext()).getFavAttractionsToAdd().size() > 0)
        {
            ServerConnection.getInstance(getApplicationContext()).sendFavAttractionsToAdd();
        }
        if(Utility.getInstance(getApplicationContext()).getFavAttractionsToDelete().size() > 0)
        {
            ServerConnection.getInstance(getApplicationContext()).sendFavAttractionsToDelete();
        }

    }
}