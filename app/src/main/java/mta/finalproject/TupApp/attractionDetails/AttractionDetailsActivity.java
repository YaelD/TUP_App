package mta.finalproject.TupApp.attractionDetails;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;

import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;


public class AttractionDetailsActivity extends NavigationDrawerActivity {

    private static final String TAG = "AttractionDetailsActivity";
    private String callingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Matan Added this
        Utility.getInstance(getApplicationContext()).addActivity(this);
        callingActivity =  getIntent().getStringExtra(CALLING_ACTIVITY);
        setContainer(new AttractionDetailsFragment());
        Utility.setLocale(this, "en");
        //setCallBack();
    }

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

    @Override
    protected void onStop() {
        super.onStop();
        Utility.sendDataToServer();
    }

    /*
    @Override
    public void navBarListeners() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.userDetails:
                        Utility.getInstance(getApplicationContext()).finishAllActivities();
                        intent= new Intent(AttractionDetailsActivity.this, UserDetailsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.home:
                        Utility.getInstance(getApplicationContext()).finishAllActivities();
                        finish();
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(AttractionDetailsActivity.this);
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
                        Utility.getInstance(getApplicationContext()).finishAllActivities();
                        intent= new Intent(AttractionDetailsActivity.this, CreateNewTripActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.search:
                        Utility.getInstance(getApplicationContext()).finishAllActivities();
                        intent= new Intent(AttractionDetailsActivity.this, SearchAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.favorites:
                        Utility.getInstance(getApplicationContext()).finishAllActivities();
                        intent= new Intent(AttractionDetailsActivity.this, FavoriteAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.trips:
                        Utility.getInstance(getApplicationContext()).finishAllActivities();
                        intent= new Intent(AttractionDetailsActivity.this, MyTripsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.logout:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttractionDetailsActivity.this);
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

     */

    @Override
    protected void moveToSearchAttractions() {
        if(!callingActivity.equals(SearchAttractionsActivity.class.getName()))
        {
            Utility.getInstance(getApplicationContext()).finishAllActivities();
            Intent intent= new Intent(AttractionDetailsActivity.this, SearchAttractionsActivity.class);
            intent.putExtra(CALLING_ACTIVITY, getClass().getName());
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void moveToFavoriteAttractions() {
        if(!callingActivity.equals(FavoriteAttractionsActivity.class.getName()))
        {
            Utility.getInstance(getApplicationContext()).finishAllActivities();
            Intent intent= new Intent(AttractionDetailsActivity.this, FavoriteAttractionsActivity.class);
            intent.putExtra(CALLING_ACTIVITY, getClass().getName());
            startActivity(intent);
        }
        finish();
    }
}