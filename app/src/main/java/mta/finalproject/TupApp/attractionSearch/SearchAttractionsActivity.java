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

        setContainer(new SearchAttractionsFragment());
        Utility.setLocale(this, "en");
    }

    @Override
    public void navBarListeners() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.userDetails:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent= new Intent(SearchAttractionsActivity.this, UserDetailsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.home:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        finish();
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(SearchAttractionsActivity.this);
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
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent= new Intent(SearchAttractionsActivity.this, CreateNewTripActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.search:
                        break;
                    case R.id.favorites:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent= new Intent(SearchAttractionsActivity.this, FavoriteAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.trips:
                        Utility.getInstance(getApplicationContext()).finishOldActivity();
                        intent= new Intent(SearchAttractionsActivity.this, MyTripsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.logout:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchAttractionsActivity.this);
                        alertDialog.setTitle("Logout");
                        alertDialog.setMessage("Are you sure you want to logout the application?");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utility.getInstance(getApplicationContext()).clearSharedPreferences();
                                Intent intent= new Intent(SearchAttractionsActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
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