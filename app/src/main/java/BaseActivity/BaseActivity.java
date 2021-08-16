package BaseActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.TupApp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import AttractionSearch.SearchAttractionsActivity;
import FavoriteAttractions.FavoriteAttractionsActivity;
import MainScreen.MainScreenFragment;
import MyTrips.MyTripsActivity;
import TripCreation.CreateNewTripActivity;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class BaseActivity extends AppCompatActivity{

    private static final String TAG = "MainScreenActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initViews();
        setToolBarAndDrawer();


        this.func();


        this.setContainer(new MainScreenFragment());

    }

    public void setContainer(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void func()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.userDetails:
                        Toast.makeText(BaseActivity.this, "userDetails selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home:
                        intent= new Intent(BaseActivity.this, BaseActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                        builder.setTitle(getString(R.string.app_name));
                        builder.setMessage("Matan is a genius (AWWWWWW <3)"); //AWWWWWWWW <3
                        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                        break;
                    case R.id.createTrip:
                        intent= new Intent(BaseActivity.this, CreateNewTripActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        intent= new Intent(BaseActivity.this, SearchAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        break;
                    case R.id.favorites:
                        intent= new Intent(BaseActivity.this, FavoriteAttractionsActivity.class);
                        intent.putExtra(CALLING_ACTIVITY, getClass().getName());
                        startActivity(intent);
                        break;
                    case R.id.trips:
                        intent= new Intent(BaseActivity.this, MyTripsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    public void setToolBarAndDrawer(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void initViews() {
        Log.d(TAG, "initViews: started");
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }
}