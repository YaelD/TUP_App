package AttractionSearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import JavaClasses.Attraction;
import JavaClasses.Utility;
import NavigationDrawer.NavigationDrawerActivity;
import TripCreation.CreateNewTripActivity;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class SearchAttractionsFragment extends Fragment {

    public static final String SELECTED_ATTRACTIONS = "selected attractions";


    private SearchView searchViewAttr;
    private RecyclerView attractionsRecView;
    private Button btnFinish;
    private AttractionsSearchRecAdapter adapterToDetailesAttr;
    private AddingAttrToMustVisitAttrAdapter adapterToMustVisitAttr;
    private String callingActivity;


    private ArrayList<Attraction> attractions = new ArrayList<>();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_attractions, container, false);

        initView(view);
        callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);

        attractions = Utility.getInstance(getContext()).getAttractions();

        searchViewAttr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("HERE==>", "In Query Text change");
                filter(newText);
                return false;
            }
        });


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        setRecView();
    }




    private void filter(String text)
    {
        ArrayList<Attraction> filteredList = new ArrayList<>();
        for(Attraction attraction: attractions)
        {
            if(attraction.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(attraction);
            }
        }
        if(callingActivity.equals(CreateNewTripActivity.class.getName()))
        {
            Log.e("HERE==>", "In adapterMustVisit");
            adapterToMustVisitAttr.setMustVisitAttractions(filteredList);
        }
        else
        {
            Log.e("HERE==>", "In adapterDoDetails");
            adapterToDetailesAttr.setAttractions(filteredList);
        }
    }


    //WE are in the Search attractions!!
    private void setRecView()
    {
        callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);

        if(callingActivity.equals(CreateNewTripActivity.class.getName())){

            btnFinish.setVisibility(View.VISIBLE);

            adapterToMustVisitAttr = new AddingAttrToMustVisitAttrAdapter(getActivity());
            attractionsRecView.setAdapter(adapterToMustVisitAttr);
            attractionsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            //adapterToMustVisitAttr.setMustVisitAttractions(ServerUtility.getInstance(getContext()).getAttractionsTest());
            adapterToMustVisitAttr.setMustVisitAttractions(Utility.getInstance(getContext()).getAttractions());

            adapterToMustVisitAttr.setSelectedAttractions(Utility.getInstance(getContext()).getTripSelectedAttrations());
            btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        else{
            btnFinish.setVisibility(View.GONE);

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


            adapterToDetailesAttr = new AttractionsSearchRecAdapter(getActivity());
            attractionsRecView.setAdapter(adapterToDetailesAttr);
            attractionsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ArrayList<Attraction> array = Utility.getInstance(getContext()).getAttractions();
            adapterToDetailesAttr.setAttractions(array);
        }
     }


    private void initView(View view) {
        attractionsRecView = view.findViewById(R.id.searchAttrRecView);
        btnFinish = view.findViewById(R.id.btnFinish);
        searchViewAttr = view.findViewById(R.id.attSearchView);
    }
}

