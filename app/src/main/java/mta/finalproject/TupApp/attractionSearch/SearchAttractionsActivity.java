package mta.finalproject.TupApp.attractionSearch;

import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class SearchAttractionsActivity extends NavigationDrawerActivity {

    private static final String TAG= "SearchAttractionsActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContainer(new SearchAttractionsFragment());


    }
}