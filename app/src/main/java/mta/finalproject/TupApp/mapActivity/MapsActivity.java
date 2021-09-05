package mta.finalproject.TupApp.mapActivity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.databinding.ActivityMapsBinding;
import mta.finalproject.TupApp.javaClasses.DayPlan;
import mta.finalproject.TupApp.javaClasses.Hotel;
import mta.finalproject.TupApp.javaClasses.OnePlan;
import mta.finalproject.TupApp.javaClasses.TripPlan;
import mta.finalproject.TupApp.javaClasses.Utility;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    final static String TRIP_INDEX = "TRIPID";
    final static String DAY_INDEX = "DAY";

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private ArrayList<OnePlan> allDayAttractions = new ArrayList<>();
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        int tripIndex = intent.getIntExtra(TRIP_INDEX, 0);
        int day = intent.getIntExtra(DAY_INDEX, 0);


        TripPlan tripPlan = Utility.getInstance(getApplicationContext()).getAllTrips().get(tripIndex);
        DayPlan dayPlans = tripPlan.getPlans().get(day);
        hotel = dayPlans.getHotel();
        allDayAttractions = dayPlans.getDaySchedule();


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(hotel.getGeometry().geometryToLatLng()));



        ArrayList<LatLng> attractionsLatLng = new ArrayList<>();
        for(OnePlan onePlan: allDayAttractions)
        {
            mMap.addMarker(new MarkerOptions().position(onePlan.getAttraction().getGeometry().geometryToLatLng())
            .title(onePlan.getAttraction().getName()).snippet(onePlan.getAttraction().getAddress()));
            attractionsLatLng.add(onePlan.getAttraction().getGeometry().geometryToLatLng());
        }



        for(LatLng latLng: attractionsLatLng)
        {
            mMap.addPolyline(new PolylineOptions().add(latLng).color(Color.BLUE));
        }


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotel.getGeometry().geometryToLatLng(), 13));
    }
}