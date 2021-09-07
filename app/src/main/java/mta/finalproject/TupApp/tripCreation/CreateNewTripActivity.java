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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import mta.finalproject.TupApp.MainActivity;
import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.loginAndRegister.UserDetailsActivity;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class CreateNewTripActivity extends NavigationDrawerActivity {

    private static final String TAG = "CreateNewTripActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContainer(new CreateNewTripFragment());
        Utility.setLocale(this, "en");
    }

    @Override
    public void navBarListeners()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.userDetails:
                        intent= new Intent(CreateNewTripActivity.this, UserDetailsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.home:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        finish();
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewTripActivity.this);
                        builder.setTitle(getString(R.string.app_name));
                        builder.setMessage("When it comes to planning and taking a vacation, travelers have to choose destination and traveling times, attractions they want to visit, collect information ,search maps, etc. \n" +
                                "We are group of Computer Science students that want to help travelers to plan their trips in the best way they can do.This is why we created \"TUP\".\n" +
                                "\"TUP\" is a Traveling Using Application which helps to travelers plan their trips wisely with an AI algorithem that calculates the optimal route trip by personal preferences.");
                        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                        break;
                    case R.id.createTrip:
                        break;
                    case R.id.search:
                        intent= new Intent(CreateNewTripActivity.this, SearchAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.favorites:
                        intent= new Intent(CreateNewTripActivity.this, FavoriteAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.trips:
                        intent= new Intent(CreateNewTripActivity.this, MyTripsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.logout:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateNewTripActivity.this);
                        alertDialog.setTitle("Logout");
                        alertDialog.setMessage("Are you sure you want to logout the application?");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();

                    default:
                        break;
                }
                drawer.closeDrawers();
                return false;
            }
        });

    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}