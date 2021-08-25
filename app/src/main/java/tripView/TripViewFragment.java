package tripView;

import static mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.content.Context;
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
import android.widget.Toast;

import com.example.TupApp.R;

import javaClasses.ServerConnection;
import javaClasses.Utility;
import myTrips.MyTripsActivity;
import navigationDrawer.NavigationDrawerActivity;

public class TripViewFragment extends Fragment {


    private RecyclerView dateRecView;
    private Button btnSaveTrip;
    private String callingActivity, tripName;




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
        if(callingActivity.equals(MyTripsActivity.class.getName()))
            btnSaveTrip.setVisibility(View.GONE);
        else
            btnSaveTrip.setVisibility(View.VISIBLE);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


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
                input.setHint("Trip " +Utility.getInstance(getContext()).getAllTrips().size());
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tripName = input.getText().toString();
                        Utility.getInstance(getContext()).getLastCreatedTrip().setTripName(tripName);
                        ServerConnection.getInstance(getContext()).sendTripPlan(Utility.getInstance(getContext()).getLastCreatedTrip());
                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
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