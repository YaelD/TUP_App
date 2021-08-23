package tripView;

import android.os.Bundle;

import navigationDrawer.NavigationDrawerActivity;

public class TripViewActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);

        setContainer(new TripViewFragment());
    }
}