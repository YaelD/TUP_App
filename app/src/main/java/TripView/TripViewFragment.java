package TripView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.TupApp.R;

import JavaClasses.Utility;

public class TripViewFragment extends Fragment {


    private RecyclerView dateRecView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_view, container, false);
        dateRecView = view.findViewById(R.id.dateRecView);
        DatesRecViewAdapter adapter = new DatesRecViewAdapter(getContext());
        adapter.setPlans(Utility.getInstance(getContext()).getLastCreatedTrip().getPlans());
        //TODO: setLastCreatedTrip in Utility class

        dateRecView.setAdapter(adapter);
        Log.e("HERE==>>", "Set Adapter DateRecView");
        dateRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
}