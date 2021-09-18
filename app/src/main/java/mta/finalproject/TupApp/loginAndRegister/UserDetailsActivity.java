package mta.finalproject.TupApp.loginAndRegister;

import android.os.Bundle;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class UserDetailsActivity extends NavigationDrawerActivity {

    //====================================================================================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance(getApplicationContext()).addActivity(this);
        initViews();
        setContainer(new UserDetailsFragment());
        Utility.setLocale(this, "en");
    }
    //====================================================================================//

    @Override
    protected void moveToUserDetails() {
    }
    //====================================================================================//

}