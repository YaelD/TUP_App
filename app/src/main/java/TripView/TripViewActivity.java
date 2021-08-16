package TripView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.example.TupApp.R;

import BaseActivity.BaseActivity;
import FavoriteAttractions.FavoriteAttractionsFragment;

public class TripViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);

        setContainer(new TripViewFragment());
    }
}