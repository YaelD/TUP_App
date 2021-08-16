package TripCreation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import AttractionSearch.SearchAttractionsActivity;
import FavoriteAttractions.FavoriteAttractionsActivity;
import JavaClasses.Attraction;
import JavaClasses.DesiredHoursInDay;
import JavaClasses.ServerUtility;
import JavaClasses.TripDetails;
import TripView.TripViewActivity;

import com.bumptech.glide.Glide;
import com.example.TupApp.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;


public class CreateNewTripFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CreateNewTripFragment";
    private TextView txtTripDetails, txtEndDatePickError, txtFrom, txtTo;
    private Button btnDestination, btnTripDates, btnDesiredHoursInDay, btnMustVisitAtt, btnHotel, btnFinishCreation;
    private Spinner destinationSpinner, spinnerHotels;
    private FloatingActionButton fltBtnAddAttr, fltBtnSearchAttr;
    private boolean isBtnDestinationClicked = false, isBtnTripDatesClicked = false, isBtnDesiredHoursInDayClicked = false, isBtnMustVisitAtt = false;
    private boolean isDateToSelected = false;
    private EditText txtSelectDateFrom, txtSelectDateTo;
    private RecyclerView recViewDesiredHours, recViewMustVisitAttr;
    private List<LocalDate> rangeDates;
    private ArrayList<DesiredHoursInDay> desiredHours;
    private LocalDate startDate, endDate;
    private MaterialDatePicker<Long> materialDatePicker2;
    private ArrayList<String> selectedHours;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_trip, container, false);

        initView(view);

        txtSelectDateFrom.setInputType(InputType.TYPE_NULL);
        txtSelectDateTo.setInputType(InputType.TYPE_NULL);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.setTimeInMillis(MaterialDatePicker.todayInUtcMilliseconds());
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        Long january = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        Long december = calendar.getTimeInMillis();


        CalendarConstraints.Builder constraintBuilder1 = new CalendarConstraints.Builder();
        constraintBuilder1.setStart(january);
        constraintBuilder1.setEnd(december);
        constraintBuilder1.setValidator(DateValidatorPointForward.now());
        CalendarConstraints.Builder constraintBuilder2 = new CalendarConstraints.Builder();
        constraintBuilder2.setStart(january);
        constraintBuilder2.setEnd(december);

        MaterialDatePicker.Builder<Long> builder1 = MaterialDatePicker.Builder.datePicker();
        builder1.setTitleText("Select start date");
        builder1.setCalendarConstraints(constraintBuilder1.build());
        MaterialDatePicker<Long> materialDatePicker1 = builder1.build();
        MaterialDatePicker.Builder<Long> builder2 = MaterialDatePicker.Builder.datePicker();


        txtSelectDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker1.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });


        materialDatePicker1.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "This is on Negative", Toast.LENGTH_SHORT).show();
            }
        });

        materialDatePicker1.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getContext(), "This is on Dissmiss", Toast.LENGTH_SHORT).show();
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
                //CalendarConstraints.Builder constraintBuilder2 = new CalendarConstraints.Builder();
                ArrayList<CalendarConstraints.DateValidator> validators = new ArrayList<CalendarConstraints.DateValidator>();
                validators.add(DateValidatorPointBackward.before(selection + TimeUnit.DAYS.toMillis(6)));
                validators.add(DateValidatorPointForward.from(selection));

                //Material datePicker
                //MaterialDatePicker.Builder<Long> builder2 = MaterialDatePicker.Builder.datePicker();
                builder2.setTitleText("Select end date");
                builder2.setCalendarConstraints(constraintBuilder2.setValidator(CompositeDateValidator.allOf(validators)).build());

                if (isDateToSelected && (endDate.isBefore(startDate) || ((startDate.plusDays(6)).isBefore(endDate)))) {
                    materialDatePicker2 = builder2.build();
                    materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                    isDateToSelected = false;

                    endDate = positiveButtonClick(materialDatePicker2, startDate);
                } else {
                    txtSelectDateTo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialDatePicker2 = builder2.build();
                            materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                            endDate = positiveButtonClick(materialDatePicker2, startDate);
                        }
                    });
                }
                //dismissButtonclick(materialDatePicker2, startDate, endDate);
                //cancelButtonClick(materialDatePicker2, startDate, endDate);
                if (isDateToSelected)
                    saveRangeOfDates(startDate, endDate);
            }
        });



        btnDestination.setOnClickListener(this);
        btnTripDates.setOnClickListener(this);
        btnDesiredHoursInDay.setOnClickListener(this);
        btnMustVisitAtt.setOnClickListener(this);
        btnHotel.setOnClickListener(this);
        btnFinishCreation.setOnClickListener(this);

        fltBtnSearchAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
            }
        });

        fltBtnAddAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
            }
        });



        /*
            MustVisitAttrRecViewAdapter adapter = new MustVisitAttrRecViewAdapter(getActivity());
            adapter.setMustVisitAttractions(ServerUtility.getInstance(getContext()).getTripSelectedAttrations());

            recViewMustVisitAttr.setAdapter(adapter);
            recViewMustVisitAttr.setLayoutManager(new LinearLayoutManager(getActivity()));
         */

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };



        btnFinishCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFinButton();
            }
        });

        return view;
    }


    private void initFinButton() {
        TripDetails tripDetails = new TripDetails();
        tripDetails.setHotelID(spinnerHotels.getSelectedItem().toString());
        tripDetails.setDestination(destinationSpinner.getSelectedItem().toString());
        for (Attraction attraction : ServerUtility.getInstance(getContext()).getTripSelectedAttrations()) {
            tripDetails.getMustSeenAttractionsID().add(attraction.getPlaceID());
        }
        ServerUtility.getInstance(getContext()).getTripSelectedAttrations().clear();
        tripDetails.setHoursEveryDay(desiredHours);
        ServerUtility.getInstance(getContext()).getTrip(tripDetails);
        Intent intent = new Intent(this.getActivity(), TripViewActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        MustVisitAttrRecViewAdapter adapter = new MustVisitAttrRecViewAdapter(getActivity());
        adapter.setMustVisitAttractions(ServerUtility.getInstance(getContext()).getTripSelectedAttrations());

        recViewMustVisitAttr.setAdapter(adapter);
        recViewMustVisitAttr.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

/*


    //   @RequiresApi(api = Build.VERSION_CODES.O)
//    private void cancelButtonClick(MaterialDatePicker<Long> materialDatePicker2, LocalDate startDate, LocalDate endDate) {
//        materialDatePicker2.addOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                if(isDateToSelected && (endDate.isBefore(startDate) || ((startDate.plusDays(6)).isBefore(endDate)))){
//                    txtEndDatePickError.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void dismissButtonclick(MaterialDatePicker<Long> materialDatePicker2, LocalDate startDate, LocalDate endDate) {
//        materialDatePicker2.addOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                if(isDateToSelected && (endDate.isBefore(startDate) || ((startDate.plusDays(6)).isBefore(endDate)))){
//                    txtEndDatePickError.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
 */

    private LocalDate positiveButtonClick(MaterialDatePicker<Long> materialDatePicker2, LocalDate startDate) {
        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                isDateToSelected = true;
                txtEndDatePickError.setVisibility(View.GONE);
                txtSelectDateTo.setText(materialDatePicker2.getHeaderText());
                endDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
                saveRangeOfDates(startDate, endDate);
            }
        });
        return endDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveRangeOfDates(LocalDate startDate, LocalDate endDate) {
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        rangeDates = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDays)
                .mapToObj(i -> CreateNewTripFragment.this.startDate.plusDays(i))
                .collect(Collectors.toList());

        desiredHours = new ArrayList<>();
        for (LocalDate date : rangeDates) {
            desiredHours.add(new DesiredHoursInDay(date.toString()));
        }

        callAdapter(desiredHours);
/*

//        DesiredHoursRecViewAdapter adapter = new DesiredHoursRecViewAdapter(getActivity());
//        adapter.setDesiredHours(desiredHours);
//
//        recViewDesiredHours.setAdapter(adapter);
//        recViewDesiredHours.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                desiredHours = adapter.getDesiredHours();
//                System.out.println(desiredHours.toString());
//            }
//        });
 */
    }

    private void callAdapter(ArrayList<DesiredHoursInDay> desiredHours) {

        DesiredHoursRecViewAdapter adapter = new DesiredHoursRecViewAdapter(getActivity());
        adapter.setDesiredHours(desiredHours);

        recViewDesiredHours.setAdapter(adapter);
        recViewDesiredHours.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initView(View view) {
        Log.d(TAG, "CreateNewTripFragment: started");

        txtTripDetails = view.findViewById(R.id.txtTripDetails);
        btnDestination = view.findViewById(R.id.btnDestination);
        btnTripDates = view.findViewById(R.id.btnTripDates);
        btnDesiredHoursInDay = view.findViewById(R.id.btnDesiredHoursInDay);
        btnMustVisitAtt = view.findViewById(R.id.btnMustVisitAtt);
        destinationSpinner = view.findViewById(R.id.destinationSpinner);
        txtSelectDateFrom = view.findViewById(R.id.txtSelectDateFrom);
        txtSelectDateTo = view.findViewById(R.id.txtSelectDateTo);
        recViewDesiredHours = view.findViewById(R.id.recViewDesiredHours);
        txtEndDatePickError = view.findViewById(R.id.txtEndDatePickError);
        txtFrom = view.findViewById(R.id.txtFrom);
        txtTo = view.findViewById(R.id.txtTo);
        spinnerHotels = view.findViewById(R.id.spinnerHotels);
        btnHotel = view.findViewById(R.id.btnHotel);
        fltBtnAddAttr = view.findViewById(R.id.fltBtnAddAttr);
        fltBtnSearchAttr = view.findViewById(R.id.fltBtnSearchAttr);
        recViewMustVisitAttr = view.findViewById(R.id.recViewMustVisitAttr);
        btnFinishCreation = view.findViewById(R.id.btnFinishCreation);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: started");
        switch(v.getId()) {
            case R.id.btnDestination:
                if(!isBtnDestinationClicked){
                    destinationSpinner.setVisibility(View.VISIBLE);
                    isBtnDestinationClicked= true;
                }
                else {
                    destinationSpinner.setVisibility(View.GONE);
                    isBtnDestinationClicked= false;
                }
                break;
            case R.id.btnTripDates:
                if(!isBtnTripDatesClicked){
                    txtSelectDateTo.setVisibility(View.VISIBLE);
                    txtSelectDateFrom.setVisibility(View.VISIBLE);
                    txtFrom.setVisibility(View.VISIBLE);
                    txtTo.setVisibility(View.VISIBLE);
                    isBtnTripDatesClicked= true;
                }
                else {
                    txtSelectDateTo.setVisibility(View.GONE);
                    txtSelectDateFrom.setVisibility(View.GONE);
                    txtFrom.setVisibility(View.GONE);
                    txtTo.setVisibility(View.GONE);
                    isBtnTripDatesClicked= false;
                }
                break;
            case R.id.btnDesiredHoursInDay:
                if(!isBtnDesiredHoursInDayClicked){
                    recViewDesiredHours.setVisibility(View.VISIBLE);
                    isBtnDesiredHoursInDayClicked= true;
                }
                else {
                    recViewDesiredHours.setVisibility(View.GONE);
                    isBtnDesiredHoursInDayClicked= false;
                }
                break;
            case R.id.btnMustVisitAtt:
                if(!isBtnMustVisitAtt) {
                    fltBtnSearchAttr.setVisibility(View.VISIBLE);
                    fltBtnAddAttr.setVisibility(View.VISIBLE);
                    recViewMustVisitAttr.setVisibility(View.VISIBLE);
                    isBtnMustVisitAtt = true;
                }
                else {
                    fltBtnSearchAttr.setVisibility(View.GONE);
                    fltBtnAddAttr.setVisibility(View.GONE);
                    recViewMustVisitAttr.setVisibility(View.GONE);
                    isBtnMustVisitAtt = false;
                }
                break;
            case R.id.btnHotel:
                spinnerHotels.setVisibility(View.VISIBLE);
                break;
            case R.id.btnFinishCreation:
                break;
            default:
                break;
        }
    }

}





