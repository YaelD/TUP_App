package TripView;

import android.os.Bundle;

import BaseActivity.BaseActivity;

public class TripViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);

        setContainer(new TripViewFragment());
    }
}