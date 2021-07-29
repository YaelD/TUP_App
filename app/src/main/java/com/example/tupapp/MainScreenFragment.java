package com.example.tupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class MainScreenFragment extends Fragment {

    private ImageView btnLogoImage;
    private TextView txtHelloUser;
    private Button btnCreateTrip, btnSearchAtt, btnFavoriteAtt, btnMyTrips, btnAttraction;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);

        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateNewTripActivity.class);
                startActivity(intent);
            }
        });
        btnSearchAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchAttractionsActivity.class);
                startActivity(intent);
            }
        });
        btnFavoriteAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteAttractionsActivity.class);
                startActivity(intent);
            }
        });
        btnMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTripsActivity.class);
                startActivity(intent);
            }
        });
        btnAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initView(View view) {
        btnLogoImage = view.findViewById(R.id.btnLogoImage);
        txtHelloUser = view.findViewById(R.id.txtHelloUser);
        btnCreateTrip = view.findViewById(R.id.btnCreateTrip);
        btnSearchAtt = view.findViewById(R.id.btnSearchAtt);
        btnFavoriteAtt = view.findViewById(R.id.btnFavoriteAtt);
        btnMyTrips = view.findViewById(R.id.btnMyTrips);
        btnAttraction = view.findViewById(R.id.btnAttractions);
    }

}
