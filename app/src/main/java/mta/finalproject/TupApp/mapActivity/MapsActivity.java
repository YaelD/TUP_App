package mta.finalproject.TupApp.mapActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.databinding.ActivityMapsBinding;
import mta.finalproject.TupApp.javaClasses.Attraction;
import mta.finalproject.TupApp.javaClasses.DayPlan;
import mta.finalproject.TupApp.javaClasses.Hotel;
import mta.finalproject.TupApp.javaClasses.OnePlan;
import mta.finalproject.TupApp.javaClasses.TripPlan;
import mta.finalproject.TupApp.javaClasses.Utility;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static String DAY_PLAN_JSON = "DAYPLANJSON";

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private DayPlan dayPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("MapActivity==>", "Got To MapS");


        Intent intent = getIntent();
        String dayPlanJson = intent.getStringExtra(DAY_PLAN_JSON);
        this.dayPlan = new Gson().fromJson(dayPlanJson, DayPlan.class);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.addMarker(new MarkerOptions().position(dayPlan.getHotel().getGeometry().geometryToLatLng()).title(dayPlan.getHotel().getName())
        .icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        ArrayList<LatLng> attractionsLatLng = new ArrayList<>();
        attractionsLatLng.add(dayPlan.getHotel().getGeometry().geometryToLatLng());
        for(OnePlan onePlan: dayPlan.getDaySchedule())
        {
            Marker currMarger = mMap.addMarker(new MarkerOptions().position(onePlan.getAttraction().getGeometry().geometryToLatLng())
            .title(onePlan.getAttraction().getName()).snippet(onePlan.getAttraction().getAddress()));
            currMarger.setTag(onePlan.getAttraction());
            attractionsLatLng.add(onePlan.getAttraction().getGeometry().geometryToLatLng());
        }
        attractionsLatLng.add(dayPlan.getHotel().getGeometry().geometryToLatLng());

        int arrLen = attractionsLatLng.size();
        for(int i =0; i < arrLen-1;++i)
        {
            mMap.addPolyline(new PolylineOptions().add(attractionsLatLng.get(i), attractionsLatLng.get(i+1)).color(Color.RED).width(3));
        }



        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dayPlan.getHotel().getGeometry().geometryToLatLng(), 13));
    }




}