package mta.finalproject.TupApp.tripCreation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import mta.finalproject.TupApp.javaClasses.DayPlan;
import mta.finalproject.TupApp.javaClasses.DesiredHoursInDay;
import mta.finalproject.TupApp.javaClasses.Hotel;
import mta.finalproject.TupApp.javaClasses.OnePlan;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.TripPlan;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.TripDetails;
import mta.finalproject.TupApp.javaClasses.VolleyCallBack;
import mta.finalproject.TupApp.tripView.TripViewActivity;

import mta.finalproject.TupApp.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private TextView txtEndDatePickError, txtFrom, txtTo;
    private Button btnDestination, btnTripDates, btnDesiredHoursInDay, btnMustVisitAtt, btnHotel, btnFinishCreation;
    private Spinner destinationSpinner, spinnerHotels;
    private FloatingActionButton fltBtnAddAttrFromFavorite, fltBtnAddAttrFromSearchAttr;
    private boolean isBtnDestinationClicked = false, isBtnTripDatesClicked = false, isBtnDesiredHoursInDayClicked = false,
            isBtnMustVisitAttrClicked = false, isBtnHotelsClicked = false;
    private boolean isSelectedEndDate = false, isSelectedStartDate = false;
    private EditText txtSelectStartDate, txtSelectEndDate;
    private RecyclerView recViewDesiredHours, recViewMustVisitAttr;
    private ArrayList<DesiredHoursInDay> desiredHours = new ArrayList<>();
    private LocalDate startDate, endDate;
    private MaterialDatePicker<Long> materialDatePicker2;
    private ProgressDialog progressDialog;
    ArrayList<String> hotelsName = new ArrayList<>();



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_trip, container, false);
        initViews(view);

        initSpinnerDestination();
        initSpinnerHotels();

        //Prevent from the user to enter something from the keyboard in the editTexts
        txtSelectStartDate.setInputType(InputType.TYPE_NULL);
        txtSelectEndDate.setInputType(InputType.TYPE_NULL);

        //Building a calendar
        Long january = buildCalendar();

        //Building a calendar constraints
        CalendarConstraints.Builder constraintBuilder1 = new CalendarConstraints.Builder();
        CalendarConstraints.Builder constraintBuilder2 = new CalendarConstraints.Builder();
        //Set constraints
        setConstraintBuildersValidators(january, constraintBuilder1, constraintBuilder2);

        //Building two date pickers for starting date and ending date
        MaterialDatePicker.Builder<Long> builder1 = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> materialDatePicker1 = buildDatePickerForStartDate(builder1, constraintBuilder1);
        MaterialDatePicker.Builder<Long> builder2 = MaterialDatePicker.Builder.datePicker();

        setClickListenerForEditTxtStartDate(materialDatePicker1);

        positiveButtonClickOnMaterialDatePicker1(builder1, builder2, constraintBuilder2, materialDatePicker1);

        setClickListenersForButtons();

        setSearchAndFavoriteFloatingActionButtonsClickListeners();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

    //====================================================================================//

    private void setClickListenerForEditTxtStartDate(MaterialDatePicker<Long> materialDatePicker1) {
        txtSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker1.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });
    }

    //====================================================================================//

    private MaterialDatePicker<Long> buildDatePickerForStartDate(MaterialDatePicker.Builder<Long> builder1,
                                                                 CalendarConstraints.Builder constraintBuilder1) {
        builder1.setTitleText("Select start date");
        builder1.setCalendarConstraints(constraintBuilder1.build());
        MaterialDatePicker<Long> materialDatePicker1 = builder1.build();
        return materialDatePicker1;
    }

    //====================================================================================//

    private void setClickListenersForButtons() {
        btnDestination.setOnClickListener(this);
        btnTripDates.setOnClickListener(this);
        btnDesiredHoursInDay.setOnClickListener(this);
        btnMustVisitAtt.setOnClickListener(this);
        btnHotel.setOnClickListener(this);
        btnFinishCreation.setOnClickListener(this);
    }

    //====================================================================================//

    private void setSearchAndFavoriteFloatingActionButtonsClickListeners() {
        fltBtnAddAttrFromSearchAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
            }
        });

        fltBtnAddAttrFromFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteAttractionsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
            }
        });
    }

    //====================================================================================//

    //saves the start date which the user select in the first date picker.
    // Builds the second date picker and saves the range of dates that user choose
    private void positiveButtonClickOnMaterialDatePicker1(MaterialDatePicker.Builder<Long> builder1, MaterialDatePicker.Builder<Long> builder2, CalendarConstraints.Builder constraintBuilder2, MaterialDatePicker<Long> materialDatePicker1) {
        materialDatePicker1.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                txtSelectStartDate.setText(materialDatePicker1.getHeaderText());
                startDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
                endDate = startDate;
                isSelectedStartDate = true;
                ArrayList<CalendarConstraints.DateValidator> validators = new ArrayList<CalendarConstraints.DateValidator>();
                validators.add(DateValidatorPointBackward.before(selection + TimeUnit.DAYS.toMillis(6)));
                validators.add(DateValidatorPointForward.from(selection));

                builder2.setTitleText("Select end date");
                builder2.setSelection(selection);
                builder2.setCalendarConstraints(constraintBuilder2.setValidator(CompositeDateValidator.allOf(validators)).build());
                materialDatePicker2 = builder2.build();
                txtSelectEndDate.setText(materialDatePicker1.getHeaderText());
                if (isSelectedEndDate && (endDate.isBefore(startDate) || ((startDate.plusDays(6)).isBefore(endDate)))) {
                    materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                    isSelectedEndDate = false;
                    endDate = positiveButtonClick(materialDatePicker2, startDate);
                }
                else {
                    txtSelectEndDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialDatePicker2.show(getActivity().getSupportFragmentManager(), "DATE_PICKER2");
                            endDate = positiveButtonClick(materialDatePicker2, startDate);
                        }
                    });
                }
                saveRangeOfDates(startDate, endDate);
            }
        });
    }

    //====================================================================================//

    //Set constraints and validators to calendar
    private void setConstraintBuildersValidators(Long january, CalendarConstraints.Builder constraintBuilder1, CalendarConstraints.Builder constraintBuilder2) {
        constraintBuilder1.setStart(january);
        constraintBuilder1.setValidator(DateValidatorPointForward.now());
        constraintBuilder2.setStart(january);
    }

    //====================================================================================//

    //Building a calendar
    private Long buildCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.setTimeInMillis(MaterialDatePicker.todayInUtcMilliseconds());
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        Long january = calendar.getTimeInMillis();
        return january;
    }

    //====================================================================================//

    private void initSpinnerDestination(){
        ArrayList<String> destinations = Utility.getInstance(getContext()).getDestinations();
        ArrayAdapter<String> destinationsAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                destinations
        );
        destinationSpinner.setAdapter(destinationsAdapter);
    }

    //====================================================================================//

    private void initSpinnerHotels() {
        ArrayList<Hotel> hotels = Utility.getInstance(getContext()).getHotels();
        if(hotelsName.isEmpty()){
            hotelsName.add("Select");
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

    //====================================================================================//

    //TODO: ask from Matan
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
        //TODO: delete this log
        Log.e("TRIP To send=>", tripDetails.toString());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing... Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ServerConnection.getInstance(getContext()).sendTripDetailsToServer(tripDetails, new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                progressDialog.dismiss();
                Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new ServerConnection.LocalTimeAdapter())
                        .registerTypeAdapter(LocalDate.class, new ServerConnection.LocalDateAdapter()).create();
                TripPlan tripPlan = new TripPlan("", null);
                tripPlan.setDestination(tripDetails.getDestination());
                ArrayList<DayPlan> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        String dayPlanjson = jsonArray.getString(i);
                        DayPlan dayPlan = gson.fromJson(dayPlanjson, DayPlan.class);
                        OnePlan currentPlan = dayPlan.getDaySchedule().get(0);
                        dayPlan.setHotel(new Hotel(currentPlan.getAttraction().getName(), currentPlan.getAttraction().getPlaceID(), currentPlan.getAttraction().getGeometry()));
                        dayPlan.getDaySchedule().remove(0);
                        arr.add(dayPlan);
                    }
                    tripPlan.setPlans(arr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utility.getInstance(getContext()).setLastCreatedTrip(tripPlan);
                Intent intent = new Intent(getActivity(), TripViewActivity.class);
                intent.putExtra(CALLING_ACTIVITY, getActivity().getClass().getName());
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onErrorResponse(String error) {
                //TODO: delete this log
                Log.e("createTrip==>", "JSON error");
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error Connecting to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //====================================================================================//

    @Override
    public void onResume() {
        super.onResume();
        if(desiredHours.size() > 0)
        {
            //TODO: delete this log
            Log.e("CreateTripHours==>", "Date:" + desiredHours.get(0).getDate() +
                    " Start=" + desiredHours.get(0).getStartTime() + " End=" +
                    desiredHours.get(0).getEndTime());
        }

        callAdapter(desiredHours);
    }

    //====================================================================================//

    //Saves the endDate which the user select in the second material date picker
    private LocalDate positiveButtonClick(MaterialDatePicker<Long> materialDatePicker2, LocalDate startDate) {
        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                isSelectedEndDate = true;
                txtEndDatePickError.setVisibility(View.GONE);
                txtSelectEndDate.setText(materialDatePicker2.getHeaderText());
                endDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
                saveRangeOfDates(startDate, endDate);
            }
        });
        return endDate;
    }

    //====================================================================================//

    //Saving the range of the dates between start and end dates in an arrayList
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveRangeOfDates(LocalDate startDate, LocalDate endDate) {
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        List<LocalDate> rangeDates = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDays)
                .mapToObj(i -> CreateNewTripFragment.this.startDate.plusDays(i))
                .collect(Collectors.toList());
        desiredHours = new ArrayList<>();
        for (LocalDate date : rangeDates) {
            desiredHours.add(new DesiredHoursInDay(date.toString()));
        }
        callAdapter(desiredHours);
    }

    //====================================================================================//

    //Init the recyclerView adapters of desired hours and must visit attractions with the corresponding arrayLiasts
    private void callAdapter(ArrayList<DesiredHoursInDay> desiredHours) {
        DesiredHoursRecViewAdapter desiredHoursAdapter = new DesiredHoursRecViewAdapter(getActivity());
        desiredHoursAdapter.setDesiredHours(desiredHours);
        recViewDesiredHours.setAdapter(desiredHoursAdapter);
        recViewDesiredHours.setLayoutManager(new LinearLayoutManager(getActivity()));

        MustVisitAttrRecViewAdapter mustVisitAttrAdapter = new MustVisitAttrRecViewAdapter(getActivity());
        mustVisitAttrAdapter.setMustVisitAttractions(Utility.getInstance(getContext()).getTripSelectedAttrations());
        recViewMustVisitAttr.setAdapter(mustVisitAttrAdapter);
        recViewMustVisitAttr.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    //====================================================================================//

    //Connect all the views in the layout to the class's members
    private void initViews(View view) {
        btnDestination = view.findViewById(R.id.btnDestination);
        btnTripDates = view.findViewById(R.id.btnTripDates);
        btnDesiredHoursInDay = view.findViewById(R.id.btnDesiredHoursInDay);
        btnMustVisitAtt = view.findViewById(R.id.btnMustVisitAtt);
        destinationSpinner = view.findViewById(R.id.destinationSpinner);
        txtSelectStartDate = view.findViewById(R.id.txtSelectDateFrom);
        txtSelectEndDate = view.findViewById(R.id.txtSelectDateTo);
        recViewDesiredHours = view.findViewById(R.id.recViewDesiredHours);
        txtEndDatePickError = view.findViewById(R.id.txtEndDatePickError);
        txtFrom = view.findViewById(R.id.txtFrom);
        txtTo = view.findViewById(R.id.txtTo);
        spinnerHotels = view.findViewById(R.id.spinnerHotels);
        btnHotel = view.findViewById(R.id.btnHotel);
        fltBtnAddAttrFromFavorite = view.findViewById(R.id.fltBtnAddAttr);
        fltBtnAddAttrFromSearchAttr = view.findViewById(R.id.fltBtnSearchAttr);
        recViewMustVisitAttr = view.findViewById(R.id.recViewMustVisitAttr);
        btnFinishCreation = view.findViewById(R.id.btnFinishCreation);
    }

    //====================================================================================//

    //set on click listeners to all the buttons
    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnDestination:
                btnDestinationClicked();
                break;
            case R.id.btnTripDates:
                btnTripDatesClicked();
                break;
            case R.id.btnDesiredHoursInDay:
                btnDesiredHoursInDayClicked();
                break;
            case R.id.btnMustVisitAtt:
                btnMustVisitAttractionClicked();
                break;
            case R.id.btnHotel:
                btnHotelClicked();
                break;
            case R.id.btnFinishCreation:
                if(validation())
                    initFinButton();
                break;
            default:
                break;
        }
    }

    //====================================================================================//

    private void btnHotelClicked() {
        if(!isBtnHotelsClicked){
            spinnerHotels.setVisibility(View.VISIBLE);
            isBtnHotelsClicked= true;
        }
        else {
            spinnerHotels.setVisibility(View.GONE);
            isBtnHotelsClicked= false;
        }
    }

    //====================================================================================//

    private void btnMustVisitAttractionClicked() {
        if(!isBtnMustVisitAttrClicked) {

            fltBtnAddAttrFromSearchAttr.setVisibility(View.VISIBLE);
            fltBtnAddAttrFromFavorite.setVisibility(View.VISIBLE);
            recViewMustVisitAttr.setVisibility(View.VISIBLE);
            isBtnMustVisitAttrClicked = true;
        }
        else {
            fltBtnAddAttrFromSearchAttr.setVisibility(View.GONE);
            fltBtnAddAttrFromFavorite.setVisibility(View.GONE);
            recViewMustVisitAttr.setVisibility(View.GONE);
            isBtnMustVisitAttrClicked = false;
        }
    }

    //====================================================================================//

    private void btnDesiredHoursInDayClicked() {
        if(!isBtnDesiredHoursInDayClicked){
            recViewDesiredHours.setVisibility(View.VISIBLE);
            isBtnDesiredHoursInDayClicked= true;
        }
        else {
            recViewDesiredHours.setVisibility(View.GONE);
            isBtnDesiredHoursInDayClicked= false;
        }
    }

    //====================================================================================//

    private void btnTripDatesClicked() {
        if(!isBtnTripDatesClicked){
            txtSelectEndDate.setVisibility(View.VISIBLE);
            txtSelectStartDate.setVisibility(View.VISIBLE);
            txtFrom.setVisibility(View.VISIBLE);
            txtTo.setVisibility(View.VISIBLE);
            isBtnTripDatesClicked= true;
        }
        else {
            txtSelectEndDate.setVisibility(View.GONE);
            txtSelectStartDate.setVisibility(View.GONE);
            txtFrom.setVisibility(View.GONE);
            txtTo.setVisibility(View.GONE);
            isBtnTripDatesClicked= false;
        }
    }

    //====================================================================================//

    private void btnDestinationClicked() {
        if(!isBtnDestinationClicked){
            destinationSpinner.setVisibility(View.VISIBLE);
            isBtnDestinationClicked= true;
        }
        else {
            destinationSpinner.setVisibility(View.GONE);
            isBtnDestinationClicked= false;
        }
    }

    //====================================================================================//

    //Validate if all the necessary details have been entered
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





