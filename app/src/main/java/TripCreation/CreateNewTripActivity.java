package TripCreation;

import android.os.Bundle;

import NavigationDrawer.NavigationDrawerActivity;

public class CreateNewTripActivity extends NavigationDrawerActivity {

    private static final String TAG = "CreateNewTripActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContainer(new CreateNewTripFragment());
    }
}