package mta.finalproject.TupApp.favoriteAttractions;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;

import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

public class FavoriteAttractionsActivity extends NavigationDrawerActivity {

    String callingActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callingActivity = getIntent().getStringExtra(CALLING_ACTIVITY);
        Utility.getInstance(getApplicationContext()).addActivity(this);
        setContainer(new FavoriteAttractionsFragment());
        Utility.setLocale(this, "en");
    }
    //====================================================================================//

    @Override
    protected void moveToFavoriteAttractions() {
    }
    //====================================================================================//

    @Override
    protected void setCallBack() {
        OnBackPressedCallback callback;
        if(!callingActivity.equals(CreateNewTripActivity.class.getName()))
        {
            callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    Utility.getInstance(getApplicationContext()).finishAllActivities();
                }
            };
        }
        else
        {
            callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    finish();
                }
            };
        }
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
    //====================================================================================//

    @Override
    protected void onStop() {
        super.onStop();
        if(Utility.getInstance(getApplicationContext()) != null)
        {
            Utility.sendDataToServer();
        }

    }
    //====================================================================================//

}