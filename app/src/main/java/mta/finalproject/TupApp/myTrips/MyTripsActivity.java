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
        setContainer(new MyTripsFragment());
    }


    @Override
    public void navBarListeners() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.userDetails:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent = new Intent(MyTripsActivity.this, UserDetailsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.home:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        finish();
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setTitle(getString(R.string.app_name));
                        builder.setMessage("Matan is a genius (AWWWWWW <3)"); //AWWWWWWWW <3
                        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                        break;
                    case R.id.createTrip:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent = new Intent(MyTripsActivity.this, CreateNewTripActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.search:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent = new Intent(MyTripsActivity.this, SearchAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.favorites:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent = new Intent(MyTripsActivity.this, FavoriteAttractionsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.trips:
                        break;
                    case R.id.logout:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                        alertDialog.setTitle("Logout");
                        alertDialog.setMessage("Are you sure you want to logout the application?");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utility.getInstance(getApplicationContext()).clearSharedPreferences();
                                Intent intent = new Intent(MyTripsActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
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
}