package FavoriteAttractions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import AttractionSearch.SearchAttractionsActivity;
import MainScreen.MainScreenActivity;
import com.example.TupApp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class FavoriteAttractionsActivity extends AppCompatActivity {

    private static final String TAG= "FavoriteAttractionsActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initViewsFavoriteAttractions();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.userDetails:
                        Toast.makeText(FavoriteAttractionsActivity.this, "userDetails selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home:
                        Intent intent= new Intent(FavoriteAttractionsActivity.this, MainScreenActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.about:
                        new AlertDialog.Builder(FavoriteAttractionsActivity.this)
                                .setTitle(getString(R.string.app_name))
                                .setMessage("Matan is a genius")
                                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create().show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FavoriteAttractionsFragment());
        transaction.commit();
    }

    private void initViewsFavoriteAttractions() {
        //Log.d(TAG, "initViewsFavoriteAttractions: started");

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }
}