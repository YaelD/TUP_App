package attractionDetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import javaClasses.Attraction;
import javaClasses.Utility;

public class AttractionDetailsFragment extends Fragment {

    public static final String ATTRACTION_KEY = "attraction";

    private TextView txtAttrName, txtAddress, txtOpeningHours, txtPhone, txtWebsite, txtDescription,txtRestaurants, txtMap;
    private TextView txtAttrAddress, txtAttrOpeningHours, txtAttrPhone, txtAttrWebsite, txtAttrDescription, txtAddToFavorites, txtRemoveFromFavorite;
    private ImageView imgFavorite, imgMap, imgAttr, imgFavoriteBorder;
    private boolean isImgAddToFavoriteClicked = false, isImgRemoveFromFavoriteClicked = false;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);




        initViews(view);

        /*


//        imgFavorite.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                if (!isImgFavoriteClicked) {
//                    imgFavorite.setColorFilter(getActivity().getColor(R.color.red));
//                    isImgFavoriteClicked = true;
//                } else {
//                    imgFavorite.setColorFilter(getActivity().getColor(R.color.black));
//                    isImgFavoriteClicked = false;
//                }
//            }
//        });

//        String callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);
//
//        Intent intent = getActivity().getIntent();
//        if(null != intent){
//            Attraction attraction = intent.getParcelableExtra(ATTRACTION_KEY);
//            if(attraction != null){
//                txtAttrName.setText(attraction.getName());
//                txtAttrPhone.setText(attraction.getPhoneNumber());
//                txtAttrAddress.setText(attraction.getAddress());
//                txtAttrWebsite.setText(attraction.getWebsite());
//                Glide.with(getActivity())
//                        .asBitmap()
//                        .load(attraction.getImageUrl())
//                        .into(imgAttr);
//
//                if(callingActivity.equals(SearchAttractionsActivity.class.getName())){
//                    imgFavorite.setVisibility(View.VISIBLE);
//                    imgFavorite.setOnClickListener(new View.OnClickListener() {
//                        @RequiresApi(api = Build.VERSION_CODES.M)
//                        @Override
//                        public void onClick(View v) {
//                            if (!isImgFavoriteClicked) {
//                                imgFavorite.setColorFilter(getActivity().getColor(R.color.red));
//                                isImgFavoriteClicked = true;
//                                boolean isAdded = ServerUtility.getInstance(getContext()).addAttractionToFavoriteList(attraction);
//                                if(isAdded)
//                                    Toast.makeText(getActivity(), attraction.getName()+" added to favorites successfully", Toast.LENGTH_SHORT).show();
//                            } else {
//                                imgFavorite.setColorFilter(getActivity().getColor(R.color.black));
//                                isImgFavoriteClicked = false;
//                                boolean isRemoved = ServerUtility.getInstance(getContext()).removeAttractionFromFavoriteList(attraction);
//                                if(isRemoved)
//                                    Toast.makeText(getActivity(), attraction.getName()+" removed from favorites successfully" ,Toast.LENGTH_SHORT).show();;
//                            }
//                        }
//                    });
//                }
//                else{
//                    imgFavorite.setVisibility(View.GONE);
//                }
//
//            }
//        }
         */

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
        imgFavoriteBorder = view.findViewById(R.id.imgFavoriteBorder);
        txtRemoveFromFavorite = view.findViewById(R.id.txtRemoveFromFavorite);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        //String callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);

        Intent intent = getActivity().getIntent();
        if(null != intent){
            String attractionID = getActivity().getIntent().getStringExtra(ATTRACTION_KEY);
            //Attraction attraction = intent.getParcelableExtra(ATTRACTION_KEY);
            Attraction attraction = Utility.getInstance(getContext()).getAttractionByID(attractionID);
            if(attraction != null){
                txtAttrName.setText(attraction.getName());
                txtAttrPhone.setText(attraction.getPhoneNumber());
                txtAttrAddress.setText(attraction.getAddress());
                txtAttrWebsite.setText(attraction.getWebsite());
                Glide.with(getActivity())
                        .asBitmap()
                        .load(attraction.getImageUrl())
                        .into(imgAttr);
                    if(Utility.getInstance(getContext()).getFavoriteAttractions().contains(attraction))
                    {
                        imgFavorite.setVisibility(View.VISIBLE);
                        imgFavoriteBorder.setVisibility(View.GONE);
                        txtRemoveFromFavorite.setVisibility(View.VISIBLE);
                        txtAddToFavorites.setVisibility(View.GONE);
                    }
                    else
                    {
                        imgFavorite.setVisibility(View.GONE);
                        imgFavoriteBorder.setVisibility(View.VISIBLE);
                        txtRemoveFromFavorite.setVisibility(View.GONE);
                        txtAddToFavorites.setVisibility(View.VISIBLE);
                    }

                    imgFavorite.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            imgFavorite.setVisibility(View.GONE);
                            txtRemoveFromFavorite.setVisibility(View.GONE);
                            imgFavoriteBorder.setVisibility(View.VISIBLE);
                            txtAddToFavorites.setVisibility(View.VISIBLE);
                            boolean isRemoved = Utility.getInstance(getContext()).removeAttractionFromFavoriteList(attraction);
                            if(isRemoved)
                                Toast.makeText(getActivity(), attraction.getName()+" removed from favorites successfully" ,Toast.LENGTH_SHORT).show();
//                            if (!isImgFavoriteClicked) {
//                                imgFavorite.setColorFilter(getActivity().getColor(R.color.red));
//                                isImgFavoriteClicked = true;
//                                boolean isAdded = Utility.getInstance(getContext()).addAttractionToFavoriteList(attraction);
//                                if(isAdded)
//                                    Toast.makeText(getActivity(), attraction.getName()+" added to favorites successfully", Toast.LENGTH_SHORT).show();
//                            } else {
//                                imgFavorite.setColorFilter(getActivity().getColor(R.color.black));
//                                isImgFavoriteClicked = false;
//                                boolean isRemoved = Utility.getInstance(getContext()).removeAttractionFromFavoriteList(attraction);
//                                if(isRemoved)
//                                    Toast.makeText(getActivity(), attraction.getName()+" removed from favorites successfully" ,Toast.LENGTH_SHORT).show();;
//                            }

                        }
                    });

                    imgFavoriteBorder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgFavoriteBorder.setVisibility(View.GONE);
                            txtAddToFavorites.setVisibility(View.GONE);
                            imgFavorite.setVisibility(View.VISIBLE);
                            txtRemoveFromFavorite.setVisibility(View.VISIBLE);
                            boolean isAdded = Utility.getInstance(getContext()).addAttractionToFavoriteList(attraction);
                            if(isAdded)
                                Toast.makeText(getActivity(), attraction.getName()+" added to favorites successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    imgMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri gmmIntentUri = Uri.parse("geo:"+ attraction.getGeometry().toString() + "?q=" +attraction.getGeometry().toString()+"(" + attraction.getName() + ")");
                            //+ "?q=" + attraction.getAddress()
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);

                        }
                    });

            }
        }
    }
}




