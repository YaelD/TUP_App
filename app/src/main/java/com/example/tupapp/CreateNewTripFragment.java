package com.example.tupapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.Util;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class CreateNewTripFragment extends Fragment implements View.OnClickListener{

    private static final String TAG= "CreateNewTripFragment";
    private TextView txtTripDetails;
    private Button btnDestination, btnNumOfDays, btnDesiredHoursInDay, btnMustVisitAtt, btnTest;
    private Spinner destinationSpinner;
    private boolean isbtnDestinationClicked = false, isbtnNumOfDaysClicked = false, isbtnDesiredHoursInDay = false, isbtnMustVisitAtt = false;
    private EditText txtSelectDateFrom, txtSelectDateTo;
    private RecyclerView recViewDesiredHours;
    private List<LocalDate> rangeDates;
    private LocalDate startDate, endDate;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_trip, container, false);

        initView(view);

        txtSelectDateFrom.setInputType(InputType.TYPE_NULL);
        txtSelectDateTo.setInputType(InputType.TYPE_NULL);

        CalendarConstraints.Builder constraintBuilder1 = new CalendarConstraints.Builder();
        constraintBuilder1.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder<Long> builder1 = MaterialDatePicker.Builder.datePicker();
        builder1.setTitleText("Select a date");
        builder1.setCalendarConstraints(constraintBuilder1.build());
        MaterialDatePicker<Long> materialDatePicker1 = builder1.build();


        txtSelectDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker1.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });


        materialDatePicker1.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                txtSelectDateFrom.setText(materialDatePicker1.getHeaderText());

                rangeDates = new ArrayList<LocalDate>();
                startDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();


                //Calendar Constraints
                CalendarConstraints.Builder constraintBuilder2 = new CalendarConstraints.Builder();
                ArrayList<CalendarConstraints.DateValidator> validators = new ArrayList<CalendarConstraints.DateValidator>();
                validators.add(DateValidatorPointBackward.before(selection+ TimeUnit.DAYS.toMillis(6)));
                validators.add(DateValidatorPointForward.from(selection));

                //Material datePicker
                MaterialDatePicker.Builder<Long> builder2 = MaterialDatePicker.Builder.datePicker();
                builder2.setTitleText("Select a date");
                builder2.setCalendarConstraints(constraintBuilder2.setValidator(CompositeDateValidator.allOf(validators)).build());
                MaterialDatePicker<Long> materialDatePicker2 = builder2.build();

                txtSelectDateTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                    }
                });

                materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        txtSelectDateTo.setText(materialDatePicker2.getHeaderText());

                        endDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
                        long numOfDays = ChronoUnit.DAYS.between(startDate,endDate) + 1;
                        rangeDates = IntStream.iterate(0, i -> i+1)
                                .limit(numOfDays)
                                .mapToObj(i -> startDate.plusDays(i))
                                .collect(Collectors.toList());

                        DesiredHoursRecViewAdapter adapter = new DesiredHoursRecViewAdapter(getActivity());
                        adapter.setDesiredHours(rangeDates);

                        recViewDesiredHours.setAdapter(adapter);
                        recViewDesiredHours.setLayoutManager(new LinearLayoutManager(getActivity()));

                        for(LocalDate date:rangeDates){
                            System.out.println(date);
                        }
                    }
                });
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
        txtSelectDateFrom = view.findViewById(R.id.txtSelectDateFrom);
        txtSelectDateTo = view.findViewById(R.id.txtSelectDateTo);
        recViewDesiredHours = view.findViewById(R.id.recViewDesiredHours);

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





