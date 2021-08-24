package tripView;

import static mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.TupApp.R;

import javaClasses.ServerConnection;
import javaClasses.Utility;
import myTrips.MyTripsActivity;

public class TripViewFragment extends Fragment {


    private RecyclerView dateRecView;
    private Button btnSaveTrip;
    private String callingActivity;



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
                ServerConnection.getInstance(getContext()).sendTripPlan(Utility.getInstance(getContext()).getLastCreatedTrip());
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        ServerConnection.serverErrorException exception =
                                ServerConnection.getInstance(getContext()).getException();
                        if(exception == null)
                        {
                            //TODO: add a new name for the created trip!
                            Utility.getInstance(getContext()).getLastCreatedTrip().setTripName("Test");
                            int last = Utility.getInstance(getContext()).getAllTrips().size()-1;
                            Utility.getInstance(getContext()).getAllTrips().get(last).setTripName("test");
                        }
                        else
                        {
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

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