package mta.finalproject.TupApp.tripView;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mta.finalproject.TupApp.R;

import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.VolleyCallBack;
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class TripViewFragment extends Fragment {


    private int scrollPosition = 0;
    private RecyclerView dateRecView;
    private Button btnSaveTrip, btnCancelTripView;
    private String callingActivity, tripName;
    private SlidingPaneLayout slidingPaneLayoutTripView;
    private ProgressDialog progressDialog;
    private LinearLayoutManager layoutManager;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_view, container, false);
        initViews(view);

        callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);
        if(callingActivity.equals(MyTripsActivity.class.getName())) {
            btnSaveTrip.setVisibility(View.GONE);
            btnCancelTripView.setVisibility(View.GONE);
        }
        else {
            btnSaveTrip.setVisibility(View.VISIBLE);
            btnCancelTripView.setVisibility(View.VISIBLE);
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


        btnCancelTripView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Trip name");
                alertDialog.setMessage("Enter trip's name:");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setHint("Trip " + (Utility.getInstance(getContext()).getAllTrips().size() + 1));
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().equals("")){
                            tripName = input.getHint().toString();
                        }
                        else{
                            tripName = input.getText().toString();
                        }
                        Utility.getInstance(getContext()).getLastCreatedTrip().setTripName(tripName);
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Processing... Please wait ");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        ServerConnection.getInstance(getContext()).sendTripPlan(Utility.getInstance(getContext()).getLastCreatedTrip(),
                                new VolleyCallBack() {
                                    @Override
                                    public void onSuccessResponse(String result) {
                                        progressDialog.dismiss();
                                        Utility.getInstance(getContext()).getLastCreatedTrip().setTripId(Integer.parseInt(result.toString()));
                                        Utility.getInstance(getContext()).getAllTrips().add
                                                (Utility.getInstance(getContext()).getLastCreatedTrip());
                                        Toast.makeText(getContext(), "Trip Saved Successfully", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }

                                    @Override
                                    public void onErrorResponse(String error) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "This Trip is already exist", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                alertDialog.create().show();

            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }
    private void initViews(View view) {
        dateRecView = view.findViewById(R.id.dateRecView);
        btnSaveTrip = view.findViewById(R.id.btnSaveTrip);
        slidingPaneLayoutTripView = view.findViewById(R.id.slidingPaneLayoutTripView);
        btnCancelTripView = view.findViewById(R.id.btnCancelTripView);
    }


    private void setAdapter()
    {
        DatesRecViewAdapter adapter = new DatesRecViewAdapter(getContext());
        adapter.setPlans(Utility.getInstance(getContext()).getLastCreatedTrip().getPlans());
        //dateRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dateRecView.setAdapter(adapter);
        dateRecView.setLayoutManager(layoutManager);
        dateRecView.scrollToPosition(scrollPosition);
        dateRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }


}