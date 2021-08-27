package mta.finalproject.TupApp.favoriteAttractions;

import android.os.Bundle;

import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class FavoriteAttractionsActivity extends NavigationDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);
        setContainer(new FavoriteAttractionsFragment());

    }

}