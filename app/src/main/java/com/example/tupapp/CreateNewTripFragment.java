package com.example.tupapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.TimeZone;

public class CreateNewTripFragment extends Fragment implements View.OnClickListener{

    private static final String TAG= "CreateNewTripFragment";
    private TextView txtTripDetails;
    private Button btnDestination, btnNumOfDays, btnDesiredHoursInDay, btnMustVisitAtt, btnTest;
    private Spinner destinationSpinner;
    private boolean isbtnDestinationClicked = false, isbtnNumOfDaysClicked = false, isbtnDesiredHoursInDay = false, isbtnMustVisitAtt = false;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_trip, container, false);

        initView(view);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.setTimeInMillis(MaterialDatePicker.todayInUtcMilliseconds());

        //calendarConstraints
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        //MaterialDateRangePicker
        MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Dates");
        builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        btnNumOfDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(),"DATES_PICKER");
            }
        });

        btnDestination.setOnClickListener(this);
        btnMustVisitAtt.setOnClickListener(this);


        return view;
    }

    private void initView(View view) {
        Log.d(TAG, "CreateNewTripFragment: started");

        txtTripDetails = view.findViewById(R.id.txtTripDetails);
        btnDestination = view.findViewById(R.id.btnDestination);
        btnNumOfDays = view.findViewById(R.id.btnNumOfDays);
        btnDesiredHoursInDay = view.findViewById(R.id.btnDesiredHoursInDay);
        btnMustVisitAtt = view.findViewById(R.id.btnMustVisitAtt);
        destinationSpinner = view.findViewById(R.id.destinationSpinner);

        btnTest = view.findViewById(R.id.btnTest);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: started");
        switch(v.getId()) {
            case R.id.btnDestination:
                if(!isbtnDestinationClicked){
                    destinationSpinner.setVisibility(View.VISIBLE);
                    isbtnDestinationClicked= true;
                }
                else {
                    destinationSpinner.setVisibility(View.GONE);
                    isbtnDestinationClicked= false;
                }
                break;
            case R.id.btnDesiredHoursInDay:

                break;
            case R.id.btnMustVisitAtt:
                btnTest.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
    }
}





