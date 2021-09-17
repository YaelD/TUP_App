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