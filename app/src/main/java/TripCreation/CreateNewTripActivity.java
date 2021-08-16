package TripCreation;

import android.os.Bundle;

import BaseActivity.BaseActivity;
import com.example.TupApp.R;

public class CreateNewTripActivity extends BaseActivity {

    private static final String TAG = "CreateNewTripActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContainer(new CreateNewTripFragment());
    }
}