package mta.finalproject.TupApp.attractionDetails;

import android.os.Bundle;

import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

import mta.finalproject.TupApp.R;

public class AttractionDetailsActivity extends NavigationDrawerActivity {

    private static final String TAG = "AttractionDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        setContainer(new AttractionDetailsFragment());
        initViews();
    }

}