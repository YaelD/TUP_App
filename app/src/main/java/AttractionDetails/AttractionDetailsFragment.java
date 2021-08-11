package AttractionDetails;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import JavaClasses.Attraction;

public class AttractionDetailsFragment extends Fragment {

    public static final String ATTRACTION_KEY = "attraction";

    private TextView txtAttrName, txtAddress, txtOpeningHours, txtPhone, txtWebsite, txtDescription,txtRestaurants, txtMap;
    private TextView txtAttrAddress, txtAttrOpeningHours, txtAttrPhone, txtAttrWebsite, txtAttrDescription, txtAddToFavorites;
    private ImageView imgFavorite, imgMap, imgAttr;
    private boolean isImgFavoriteClicked = false;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);

        initViews(view);

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!isImgFavoriteClicked) {
                    imgFavorite.setColorFilter(getActivity().getColor(R.color.red));
                    isImgFavoriteClicked = true;
                } else {
                    imgFavorite.setColorFilter(getActivity().getColor(R.color.black));
                    isImgFavoriteClicked = false;
                }
            }
        });

        Intent intent = getActivity().getIntent();
        if(null != intent){
            Attraction attraction = intent.getParcelableExtra(ATTRACTION_KEY);
            if(attraction != null){
                txtAttrName.setText(attraction.getName());
                txtAttrPhone.setText(attraction.getPhoneNumber());
                txtAttrAddress.setText(attraction.getAddress());
                txtAttrWebsite.setText(attraction.getWebsite());
                Glide.with(getActivity())
                        .asBitmap()
                        .load(attraction.getImageUrl())
                        .into(imgAttr);
            }
        }

        return view;
    }

    private void initViews(View view) {
        txtAddress = view.findViewById(R.id.AddressText);
        txtOpeningHours = view.findViewById(R.id.OpeningHoursText);
        txtPhone = view.findViewById(R.id.PhoneText);
        txtWebsite = view.findViewById(R.id.WebsiteText);
        txtDescription = view.findViewById(R.id.DescriptionText);
        txtRestaurants = view.findViewById(R.id.RestaurantsText);
        txtAttrAddress = view.findViewById(R.id.txtAttrAddress);
        txtAttrOpeningHours = view.findViewById(R.id.txtAttrOpeningHours);
        txtAttrPhone = view.findViewById(R.id.txtAttrPhone);
        txtAttrWebsite = view.findViewById(R.id.txtAttrWebsite);
        txtAttrDescription = view.findViewById(R.id.txtAttrDescription);
        txtAttrName = view.findViewById(R.id.txtAttrName);
        imgFavorite = view.findViewById(R.id.imgFavorite);
        imgMap = view.findViewById(R.id.imgMap);
        txtAddToFavorites = view.findViewById(R.id.txtAddToFavorite);
        txtMap = view.findViewById(R.id.txtMap);
        imgAttr = view.findViewById(R.id.imgAttr);
    }



}




