package mta.finalproject.TupApp.mainScreen;

import android.annotation.SuppressLint;
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

import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.R;
import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

public class MainScreenFragment extends Fragment {

    public static final String CALLING_ACTIVITY = "calling activity";
    private TextView txtHelloUser;
    private Button btnCreateTrip, btnSearchAtt, btnFavoriteAtt, btnMyTrips;



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
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
            }
        });
        btnFavoriteAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
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
        return view;
    }

    //====================================================================================//

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        txtHelloUser.setText("Hello " + Utility.getInstance(getContext()).getTraveler().getFirstName());
    }

    //====================================================================================//

    private void initView(View view) {
        txtHelloUser = view.findViewById(R.id.txtHelloUser);
        btnCreateTrip = view.findViewById(R.id.btnCreateTrip);
        btnSearchAtt = view.findViewById(R.id.btnSearchAtt);
        btnFavoriteAtt = view.findViewById(R.id.btnFavoriteAtt);
        btnMyTrips = view.findViewById(R.id.btnMyTrips);
    }

    //====================================================================================//


    @Override
    public void onStop() {
        super.onStop();
        if(Utility.getInstance(getContext()) != null)
        {
            Utility.sendDataToServer();
        }
    }

}
