package mta.finalproject.TupApp.myTrips;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class MyTripsFragment extends Fragment {

    private RecyclerView recViewMyTrips;
    private TextView txtEmptyTripList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        initViews(view);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), NavigationDrawerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
        };

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);




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


    @Override
    public void onStop() {
        super.onStop();
        if(Utility.getInstance(getContext()).getTripsToDelete().size() > 0)
        {
            ServerConnection.getInstance(getContext()).sendTripPlansToDelete();
        }
    }
}
