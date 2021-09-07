package mta.finalproject.TupApp.navigationDrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import mta.finalproject.TupApp.MainActivity;
import mta.finalproject.TupApp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.loginAndRegister.UserDetailsActivity;
import mta.finalproject.TupApp.mainScreen.MainScreenFragment;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import java.util.Locale;

public class NavigationDrawerActivity extends AppCompatActivity{

    private static final String TAG = "mta/finalproject/TupApp/navigationDrawer";
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initViews();
        setToolBarAndDrawer();

        this.navBarListeners();

        this.setContainer(new MainScreenFragment());

    }

    public void setContainer(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void navBarListeners()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.userDetails:
                        intent= new Intent(NavigationDrawerActivity.this, UserDetailsActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case R.id.home:
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawerActivity.this);
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
                        intent= new Intent(NavigationDrawerActivity.this, CreateNewTripActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case R.id.search:
                        intent= new Intent(NavigationDrawerActivity.this, SearchAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        //finish();
                        break;
                    case R.id.favorites:
                        intent= new Intent(NavigationDrawerActivity.this, FavoriteAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                       // finish();
                        break;
                    case R.id.trips:
                        intent= new Intent(NavigationDrawerActivity.this, MyTripsActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case R.id.logout:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NavigationDrawerActivity.this);
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

    public void setToolBarAndDrawer(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void initViews() {
        //Log.d(TAG, "initViews: started");
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }

    protected void logout()
    {
        Intent intent= new Intent(NavigationDrawerActivity.this, MainActivity.class);
        Utility.logOut();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        if(Utility.getInstance(getApplicationContext()).getTripsToDelete().size() != 0)
//        {
//            ServerConnection.getInstance(getApplicationContext()).deleteTripFromServer();
//        }
//
//
//    }

}