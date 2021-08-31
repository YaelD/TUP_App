package mta.finalproject.TupApp.tripCreation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import mta.finalproject.TupApp.attractionSearch.SearchAttractionsActivity;
import mta.finalproject.TupApp.favoriteAttractions.FavoriteAttractionsActivity;
import mta.finalproject.TupApp.javaClasses.Attraction;
import mta.finalproject.TupApp.javaClasses.DesiredHoursInDay;
import mta.finalproject.TupApp.javaClasses.Hotel;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.TripDetails;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.tripView.TripViewActivity;

import mta.finalproject.TupApp.R;
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

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;


public class CreateNewTripFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CreateNewTripFragment";
    private TextView txtTripDetails, txtEndDatePickError, txtFrom, txtTo;
    private Button btnDestination, btnTripDates, btnDesiredHoursInDay, btnMustVisitAtt, btnHotel, btnFinishCreation;
    private Spinner destinationSpinner, spinnerHotels;
    private FloatingActionButton fltBtnAddAttr, fltBtnSearchAttr;
    private boolean isBtnDestinationClicked = false, isBtnTripDatesClicked = false, isBtnDesiredHoursInDayClicked = false, isBtnMustVisitAtt = false;
    private boolean isSelectedEndDate = false, isSelectedStartDate = false;
    private EditText txtSelectDateFrom, txtSelectDateTo;
    private RecyclerView recViewDesiredHours, recViewMustVisitAttr;
    private List<LocalDate> rangeDates = new ArrayList<>();
    private ArrayList<DesiredHoursInDay> desiredHours;
    private LocalDate startDate, endDate;
    private MaterialDatePicker<Long> materialDatePicker2;
    private ProgressDialog progressDialog;
    ArrayList<String> hotelsName = new ArrayList<>();



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_trip, container, false);
        initView(view);

        initSpinnerDestination();
        initSpinnerHotels();



        //מניעה מהמשתמש להזין מידע בeditText
        txtSelectDateFrom.setInputType(InputType.TYPE_NULL);
        txtSelectDateTo.setInputType(InputType.TYPE_NULL);

        //הגדרת לוח השנה
        //TODO: CHANGE THE RANGE OF YEARS
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.setTimeInMillis(MaterialDatePicker.todayInUtcMilliseconds());
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        Long january = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        Long december = calendar.getTimeInMillis();


        //בניה של לוח השנה
        CalendarConstraints.Builder constraintBuilder1 = new CalendarConstraints.Builder();
        constraintBuilder1.setStart(january);
        constraintBuilder1.setEnd(december);
        constraintBuilder1.setValidator(DateValidatorPointForward.now());
        CalendarConstraints.Builder constraintBuilder2 = new CalendarConstraints.Builder();
        constraintBuilder2.setStart(january);
        constraintBuilder2.setEnd(december);

        //בניה של הטיים פיקרים
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


        //TODO: Here we have a material Date Picker!!!!



        materialDatePicker1.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                txtSelectDateFrom.setText(materialDatePicker1.getHeaderText());
                startDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
                endDate = startDate;
                isSelectedStartDate = true;

                //Calendar Constraints
                //CalendarConstraints.Builder constraintBuilder2 = new CalendarConstraints.Builder();
                //TODO: show in 2nd datepicker from the startdate
                ArrayList<CalendarConstraints.DateValidator> validators = new ArrayList<CalendarConstraints.DateValidator>();
                validators.add(DateValidatorPointBackward.before(selection + TimeUnit.DAYS.toMillis(6)));
                validators.add(DateValidatorPointForward.from(selection));

                //Material datePicker
                //MaterialDatePicker.Builder<Long> builder2 = MaterialDatePicker.Builder.datePicker();
                builder2.setTitleText("Select end date");
                builder2.setSelection(selection);
                builder2.setCalendarConstraints(constraintBuilder2.setValidator(CompositeDateValidator.allOf(validators)).build());
                materialDatePicker2 = builder2.build();
                txtSelectDateTo.setText(materialDatePicker1.getHeaderText());
                if (isSelectedEndDate && (endDate.isBefore(startDate) || ((startDate.plusDays(6)).isBefore(endDate)))) {
                    //materialDatePicker2 = builder2.build();
                    materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                    isSelectedEndDate = false;
                    endDate = positiveButtonClick(materialDatePicker2, startDate);

                }
                else {
                    txtSelectDateTo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //materialDatePicker2 = builder2.build();
                            materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                            endDate = positiveButtonClick(materialDatePicker2, startDate);
                        }
                    });
                }
                saveRangeOfDates(startDate, endDate);


                //materialDatePicker2.setCancelable(false);
                //dismissButtonclick(materialDatePicker2, startDate, endDate);
                //cancelButtonClick(materialDatePicker2, startDate, endDate);
//                if (isDateToSelected)
//                    saveRangeOfDates(startDate, endDate);
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


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), NavigationDrawerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        };

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        /*
            btnFinishCreation.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    initFinButton();
                }
            });
         */

        return view;
    }

    private void initSpinnerDestination(){
        ArrayList<String> destinations = Utility.getInstance(getContext()).getDestinations();
        if(!destinations.get(0).equals("Select")){
            destinations.add(0,"Select");
        }
        ArrayAdapter<String> destinationsAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                destinations
        );
        destinationSpinner.setAdapter(destinationsAdapter);
    }

    private void initSpinnerHotels() {
        ArrayList<Hotel> hotels = Utility.getInstance(getContext()).getHotels();
        if(hotelsName.isEmpty()){
            for(Hotel hotel:hotels){
                hotelsName.add(hotel.getName());
            }
        }
        ArrayAdapter<String> hotelsAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                hotelsName
        );
        spinnerHotels.setAdapter(hotelsAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initFinButton() {
        TripDetails tripDetails = new TripDetails();
        Hotel hotel = Utility.getInstance(getContext())
                .FindHotelByName(spinnerHotels.getSelectedItem().toString());
        tripDetails.setHotelID(hotel.getPlaceID());
        tripDetails.setDestination(destinationSpinner.getSelectedItem().toString());
        for (Attraction attraction : Utility.getInstance(getContext()).getTripSelectedAttrations()) {
            tripDetails.getMustSeenAttractionsID().add(attraction.getPlaceID());
        }
        Utility.getInstance(getContext()).getTripSelectedAttrations().clear();
        tripDetails.setHoursEveryDay(desiredHours);
        Log.e("TRIP To send=>", tripDetails.toString());
        //ServerConnection.getInstance(getContext()).sendTripDetailsToServer(TripDetails.getTrip2());
        ServerConnection.getInstance(getContext()).sendTripDetailsToServer(tripDetails);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing... Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ServerConnection.serverErrorException exception = ServerConnection.getInstance(getContext()).getException();
                progressDialog.dismiss();
                if(exception == null)
                {
                    Intent intent = new Intent(getActivity(), TripViewActivity.class);
                    intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName()); //TODO: this is for test
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    //Toast.makeText(getContext(),exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, 10000);

    }

    @Override
    public void onResume() {
        super.onResume();

        desiredHours = new ArrayList<>();
        for (LocalDate date : rangeDates) {
            desiredHours.add(new DesiredHoursInDay(date.toString()));
        }

        callAdapter(desiredHours);

        MustVisitAttrRecViewAdapter adapter = new MustVisitAttrRecViewAdapter(getActivity());
        adapter.setMustVisitAttractions(Utility.getInstance(getContext()).getTripSelectedAttrations());

        recViewMustVisitAttr.setAdapter(adapter);
        recViewMustVisitAttr.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }


    private LocalDate positiveButtonClick(MaterialDatePicker<Long> materialDatePicker2, LocalDate startDate) {
        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                isSelectedEndDate = true;
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

        //TODO: remove it to another function, and before that check if all the dates are correct
        desiredHours = new ArrayList<>();
        for (LocalDate date : rangeDates) {
            desiredHours.add(new DesiredHoursInDay(date.toString()));
        }

        callAdapter(desiredHours);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                if(validation())
                {
                    initFinButton();
                }
                break;
            default:
                break;
        }
    }

    private boolean validation() {
        boolean res = true;
        if(destinationSpinner.getSelectedItem().toString().equals("Select"))
        {
            Toast.makeText(getContext(), "Please select Destination", Toast.LENGTH_SHORT).show();
            res = false;
        }
        else if(!isSelectedStartDate)
        {
            Toast.makeText(getContext(), "Please select Start date", Toast.LENGTH_SHORT).show();
            res = false;
        }
        else if(spinnerHotels.getSelectedItem().toString().equals("Select"))
        {
            Toast.makeText(getContext(), "Please select Hotel", Toast.LENGTH_SHORT).show();
            res = false;
        }
        return res;

    }
}





