package com.example.tupapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AttractionDetailsActivity extends AppCompatActivity {

    private TextView txtAttrName, txtAddress, txtOpeningHours, txtPhone, txtWebsite, txtDescription,txtRestaurants, txtMap;
    private TextView txtAttrAddress, txtAttrOpeningHours, txtAttrPhone, txtAttrWebsite, txtAttrDescription, txtAddToFavorites;
    private ImageView imgFavorite, imgMap;
    private boolean isImgFavoriteClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_details);

        initViews();

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!isImgFavoriteClicked) {
                    imgFavorite.setColorFilter(getColor(R.color.red));
                    isImgFavoriteClicked = true;
                } else {
                    imgFavorite.setColorFilter(getColor(R.color.black));
                    isImgFavoriteClicked = false;
                }
            }
        });
    }

    private void initViews() {
        txtAddress = findViewById(R.id.AddressText);
        txtOpeningHours = findViewById(R.id.OpeningHoursText);
        txtPhone = findViewById(R.id.PhoneText);
        txtWebsite = findViewById(R.id.WebsiteText);
        txtDescription = findViewById(R.id.DescriptionText);
        txtRestaurants = findViewById(R.id.RestaurantsText);
        txtAttrAddress = findViewById(R.id.txtAttrAddress);
        txtAttrOpeningHours = findViewById(R.id.txtAttrOpeningHours);
        txtAttrPhone = findViewById(R.id.txtAttrPhone);
        txtAttrWebsite = findViewById(R.id.txtAttrWebsite);
        txtAttrDescription = findViewById(R.id.txtAttrDescription);
        txtAttrName = findViewById(R.id.txtAttrName);
        imgFavorite = findViewById(R.id.imgFavorite);
        imgMap = findViewById(R.id.imgMap);
        txtAddToFavorites = findViewById(R.id.txtAddToFavorite);
        txtMap = findViewById(R.id.txtMap);
    }
}