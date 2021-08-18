package MyTrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import JavaClasses.Utility;

public class MyTripsFragment extends Fragment {

    private RecyclerView recViewMyTrips;
    private TextView txtEmptyTripList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        initViews(view);


        return view;
    }

    private void initViews(View view) {
        recViewMyTrips = view.findViewById(R.id.recViewMyTrips);
        txtEmptyTripList = view.findViewById(R.id.txtEmptyTripList);
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter()
    {
        if(Utility.getInstance(getContext()).getAllTrips().size() != 0){
            txtEmptyTripList.setVisibility(View.GONE);
            MyTripsRecAdapter adapter = new MyTripsRecAdapter(getActivity());
            adapter.setTrips(Utility.getInstance(getContext()).getAllTrips());
            adapter.setEmptyListTxt(txtEmptyTripList);
            recViewMyTrips.setAdapter(adapter);
            recViewMyTrips.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
        else {
            txtEmptyTripList.setVisibility(View.VISIBLE);
        }
    }





}
