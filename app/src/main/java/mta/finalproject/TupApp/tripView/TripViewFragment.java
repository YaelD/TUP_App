package mta.finalproject.TupApp.tripView;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import mta.finalproject.TupApp.myTrips.MyTripsActivity;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class TripViewFragment extends Fragment {


    private RecyclerView dateRecView;
    private Button btnSaveTrip, btnCancelTripView;
    private String callingActivity, tripName;
    private RelativeLayout relativeLayoutTripView;
    private ProgressDialog progressDialog;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                alertDialog.setMessage("Enter trip's name:"); //AWWWWWWWW <3

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setHint("Trip " + (Utility.getInstance(getContext()).getAllTrips().size() + 1));
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().equals("")){
                            tripName = input.getHint().toString();
                        }
                        else{
                            tripName = input.getText().toString();
                        }
                        Utility.getInstance(getContext()).getLastCreatedTrip().setTripName(tripName);
                        Utility.getInstance(getContext()).getLastCreatedTrip().setDestination("london");
                        ServerConnection.getInstance(getContext()).sendTripPlan(Utility.getInstance(getContext()).getLastCreatedTrip());
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Processing... Please wait ");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                ServerConnection.serverErrorException exception =
                                        ServerConnection.getInstance(getContext()).getException();
                                if(exception == null)
                                {
                                    Utility.getInstance(getContext()).getAllTrips().add
                                            (Utility.getInstance(getContext()).getLastCreatedTrip());
                                    Toast.makeText(getContext(), "Trip Saved Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
                                    getActivity().startActivity(intent);
                                    getActivity().finish();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        new Handler().postDelayed(run, 3000);

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
        relativeLayoutTripView = view.findViewById(R.id.relativeLayoutTripView);
        btnCancelTripView = view.findViewById(R.id.btnCancelTripView);
    }


    private void setAdapter()
    {
        DatesRecViewAdapter adapter = new DatesRecViewAdapter(getContext());
        adapter.setPlans(Utility.getInstance(getContext()).getLastCreatedTrip().getPlans());
        //TODO: setLastCreatedTrip in Utility class

        dateRecView.setAdapter(adapter);
        dateRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }


}