package mta.finalproject.TupApp.attractionSearch;
import android.os.Bundle;
import android.util.Log;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class SearchAttractionsActivity extends NavigationDrawerActivity {

    private static final String TAG= "SearchAttractionsActivity";
    //====================================================================================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance(getApplicationContext()).addActivity(this);
        setContainer(new SearchAttractionsFragment());
        Utility.setLocale(this, "en");
    }
    //====================================================================================//

    @Override
    protected void moveToSearchAttractions() { }
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