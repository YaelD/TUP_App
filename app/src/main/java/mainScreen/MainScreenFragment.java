package mainScreen;

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

import myTrips.MyTripsActivity;
import com.example.TupApp.R;
import attractionSearch.SearchAttractionsActivity;

import org.jetbrains.annotations.NotNull;

import favoriteAttractions.FavoriteAttractionsActivity;
import tripCreation.CreateNewTripActivity;

public class MainScreenFragment extends Fragment {

    public static final String CALLING_ACTIVITY = "calling activity";

    private ImageView btnLogoImage;
    private TextView txtHelloUser;
    private Button btnCreateTrip, btnSearchAtt, btnFavoriteAtt, btnMyTrips, btnTestAttractionDetails;



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
                getActivity().finish();
            }
        });
        btnSearchAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
                getActivity().finish();

            }
        });
        btnFavoriteAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
                getActivity().finish();

            }
        });
        btnMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTripsActivity.class);
                startActivity(intent);
                getActivity().finish();
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
       // btnTestAttractionDetails = view.findViewById(R.id.btnTestAttractionDetails);
    }

}
