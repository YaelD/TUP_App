package mta.finalproject.TupApp.attractionSearch;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.MainActivity;
import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.loginAndRegister.UserDetailsActivity;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

public class SearchAttractionsActivity extends NavigationDrawerActivity {

    private static final String TAG= "SearchAttractionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Matan Added this
        Utility.getInstance(getApplicationContext()).addActivity(this);
        setContainer(new SearchAttractionsFragment());
        Utility.setLocale(this, "en");
    }

    @Override
    protected void moveToSearchAttractions() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("SearchAttActivity==>", "SAVING and Deleting Favs Attractions");
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