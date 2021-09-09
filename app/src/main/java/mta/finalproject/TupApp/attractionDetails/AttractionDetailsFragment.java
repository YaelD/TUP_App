package mta.finalproject.TupApp.attractionDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mta.finalproject.TupApp.javaClasses.Attraction;
import mta.finalproject.TupApp.javaClasses.Utility;

public class AttractionDetailsFragment extends Fragment {

    public static final String ATTRACTION_KEY = "attraction";

    private TextView txtAttrName, txtAddress, txtOpeningHours, txtPhone, txtWebsite, txtDescription,txtRestaurants, txtMap;
    private TextView txtAttrAddress, txtAttrOpeningHours, txtAttrPhone, txtAttrWebsite, txtAttrDescription, txtAddToFavorites, txtRemoveFromFavorite;
    private ImageView imgFavorite, imgLocation, imgAttr, imgFavoriteBorder, imgRestaurants, imgNavigate;
    private boolean isImgAddToFavoriteClicked = false, isImgRemoveFromFavoriteClicked = false;
    private AlertDialog dialog1, dialog2;
    private AlertDialog.Builder builder1, builder2;
    private String[] navigationMode = {"Driving", "Bicycling", "Two-wheeler", "Walking"};
    private String[] navigationAvoid = {"Tolls", "Highways", "Ferries"};
    private String selection = "Driving";
    private String uriStr = "";
    private ArrayList<String> arrChecked = new ArrayList<>();
    Uri gmmIntentUri;
    Intent mapIntent;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        txtAddress = view.findViewById(R.id.AddressText);
        txtOpeningHours = view.findViewById(R.id.OpeningHoursText);
        txtPhone = view.findViewById(R.id.PhoneText);
        txtWebsite = view.findViewById(R.id.WebsiteText);
        txtDescription = view.findViewById(R.id.DescriptionText);
        txtRestaurants = view.findViewById(R.id.txtRestaurants);
        txtAttrAddress = view.findViewById(R.id.txtAttrAddress);
        txtAttrOpeningHours = view.findViewById(R.id.txtAttrOpeningHours);
        txtAttrPhone = view.findViewById(R.id.txtAttrPhone);
        txtAttrWebsite = view.findViewById(R.id.txtAttrWebsite);
        txtAttrDescription = view.findViewById(R.id.txtAttrDescription);
        txtAttrName = view.findViewById(R.id.txtAttrName);
        imgFavorite = view.findViewById(R.id.imgFavorite);
        imgLocation = view.findViewById(R.id.imgLocation);
        txtAddToFavorites = view.findViewById(R.id.txtAddToFavorite);
        txtMap = view.findViewById(R.id.txtMap);
        imgAttr = view.findViewById(R.id.imgAttr);
        imgFavoriteBorder = view.findViewById(R.id.imgFavoriteBorder);
        txtRemoveFromFavorite = view.findViewById(R.id.txtRemoveFromFavorite);
        imgRestaurants = view.findViewById(R.id.imgRestaurants);
        imgNavigate = view.findViewById(R.id.imgNavigate);
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
                txtAttrDescription.setText(attraction.getDescription());
                txtAttrOpeningHours.setText(attraction.OpeningHoursStr());
                Glide.with(getActivity())
                        .asBitmap()
                        .load(attraction.getImageUrl())
                        .into(imgAttr);
                    if(Utility.getInstance(getContext()).isAttractionIsInFavorites(attraction.getPlaceID()))
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
                            {
                                Toast.makeText(getActivity(), attraction.getName()+" removed from favorites successfully" ,
                                        Toast.LENGTH_SHORT).show();
                                //Utility.getInstance(getContext()).addAttractionToDelete(attraction.getPlaceID());
                            }
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
                            {
                                Toast.makeText(getActivity(), attraction.getName()+" added to favorites successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    imgNavigate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setTitle("Choose navigation mode:");
                            builder1.setSingleChoiceItems(navigationMode, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selection = navigationMode[which];
                                }
                            });
                            builder1.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    uriStr = "google.navigation:q=" + attraction.getGeometry().getLat()+","+attraction.getGeometry().getLng();
                                    switch (selection){
                                        case "Driving":
                                            uriStr = uriStr+"&mode=d";
                                            break;
                                        case "Bicycling":
                                            uriStr = uriStr+"&mode=b";
                                            break;
                                        case "Two-wheeler":
                                            uriStr = uriStr+"&mode=l";
                                            break;
                                        case "Walking":
                                            uriStr = uriStr+"&mode=w";
                                            break;
                                        default:
                                            break;
                                    }
                                    builder2 = new AlertDialog.Builder(getActivity());
                                    builder2.setTitle("Avoid from:");
                                    builder2.setMultiChoiceItems(navigationAvoid, null, new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                            if(isChecked) {
                                                if(!arrChecked.contains(navigationAvoid[which]))
                                                    arrChecked.add(navigationAvoid[which]);
                                            }else {
                                                if(arrChecked.contains(navigationAvoid[which]))
                                                    arrChecked.remove(navigationAvoid[which]);
                                            }
                                        }
                                    });
                                    builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(!arrChecked.isEmpty()) {
                                                uriStr = uriStr + "&avoid=";
                                                for (String selectedAvoid : arrChecked) {
                                                    if (selectedAvoid.equals("Tolls"))
                                                        uriStr += "t";
                                                    if (selectedAvoid.equals("Highways"))
                                                        uriStr += "h";
                                                    if (selectedAvoid.equals("Ferries"))
                                                        uriStr += "f";
                                                }
                                            }
                                            gmmIntentUri = Uri.parse(uriStr);
                                            mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                            mapIntent.setPackage("com.google.android.apps.maps");
                                            startActivity(mapIntent);
                                        }
                                    });
                                    builder2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    dialog2 = builder2.create();
                                    dialog2.show();
                                }
                            });
                            builder1.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog1 = builder1.create();
                            dialog1.show();
                        }
                    });

                    imgLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri gmmIntentUri = Uri.parse("geo:"+ attraction.getGeometry().toString() + "?q=" +attraction.getGeometry().toString()+"(" + attraction.getName() + ")");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    });
                    txtAttrWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attraction.getWebsite()));
                            startActivity(browserIntent);
                        }
                    });
                    txtAttrPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + attraction.getPhoneNumber()));
                            startActivity(intent);
                        }
                    });
                    imgRestaurants.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri gmmIntentUri = Uri.parse("geo:"+attraction.getGeometry().toString()+"?q=restaurants");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    });
            }
        }
    }
}




