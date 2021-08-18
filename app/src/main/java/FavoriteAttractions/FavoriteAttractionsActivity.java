package FavoriteAttractions;

import android.os.Bundle;

import NavigationDrawer.NavigationDrawerActivity;

public class FavoriteAttractionsActivity extends NavigationDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);
        setContainer(new FavoriteAttractionsFragment());

    }

}