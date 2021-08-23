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

import com.example.TupApp.R;

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