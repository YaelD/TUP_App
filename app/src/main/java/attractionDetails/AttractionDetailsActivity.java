package attractionDetails;

import android.os.Bundle;

import navigationDrawer.NavigationDrawerActivity;

import com.example.TupApp.R;

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