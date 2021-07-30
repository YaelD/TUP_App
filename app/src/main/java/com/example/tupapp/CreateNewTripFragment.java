package com.example.tupapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
        MaterialDatePicker.Builder<Pair<Long,Long>> picker = MaterialDatePicker.Builder.dateRangePicker();
        picker.setTitleText("Select Dates");
        picker.setCalendarConstraints(constraintBuilder.build());

        //RangeDateValidator rangeDateValidator = new RangeDateValidator(7);
        final MaterialDatePicker materialDatePicker = picker.build();

        //rangeDateValidator.setDatePicker(materialDatePicker);
        //constraintBuilder.setValidator(rangeDateValidator);
        picker.setCalendarConstraints(constraintBuilder.build());

        btnNumOfDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), materialDatePicker.getTag());
            }
        });


        /*
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                @Override
                public void onPositiveButtonClick(Pair<Long, Long> selection) {
                    Toast.makeText(getActivity(), "POSITIVEButtonClick!!!", Toast.LENGTH_SHORT).show();
                }
            });

        materialDatePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getActivity(), "CANCEL LISTENER!!!", Toast.LENGTH_SHORT).show();
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "NAVIGATION LISTENER!!!", Toast.LENGTH_SHORT).show();

            }
        });
         */


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

    @SuppressLint("ParcelCreator")
    public class RangeDateValidator implements CalendarConstraints.DateValidator {

        private MaterialDatePicker rangePicker;
        final int numberOfDays;

        public RangeDateValidator(int numberOfDays) {
            this.numberOfDays = numberOfDays;
        }

        public void setDatePicker(MaterialDatePicker rangePicker) {
            this.rangePicker = rangePicker;
        }

        @Override
        public boolean isValid(long date) {
            Pair<Long, Long> selection = (Pair<Long, Long>) rangePicker.getSelection();

            if (selection != null) {
                Long startDate = selection.first;
                if (startDate != null) {
                    long days = (numberOfDays - 1) * TimeUnit.DAYS.toMillis(1);
                    if (date > startDate + days)
                        return false;
                    if (date < startDate)
                        return false;
                }
            }
            return true;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

    }
}





