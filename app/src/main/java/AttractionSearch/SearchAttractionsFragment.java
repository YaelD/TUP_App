package AttractionSearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import JavaClasses.Attraction;
import JavaClasses.ServerUtility;
import MainScreen.MainScreenActivity;
import MainScreen.MainScreenFragment;
import TripCreation.CreateNewTripActivity;
import TripCreation.CreateNewTripFragment;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class SearchAttractionsFragment extends Fragment {

    private SearchView searchViewAttractions;
    private RecyclerView attractionsRecView;
    private Button btnFinish;
    private AttractionsSearchRecAdapter adapterToDetailesAttr;
    private AddingAttrToMustVisitAttrAdapter adapterToMustVisitAttr;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_attractions, container, false);

        String callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);

        initView(view);
        //String name = getActivity().getCallingActivity().getClassName();

        if(callingActivity.equals(MainScreenActivity.class.getName())){

            btnFinish.setVisibility(View.GONE);
            adapterToDetailesAttr = new AttractionsSearchRecAdapter(getActivity());

            attractionsRecView.setAdapter(adapterToDetailesAttr);
            attractionsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

            ArrayList<Attraction> attractions = new ArrayList<>();
            attractions.add(new Attraction("London eye", "Riverside Building, County Hall, London SE1 7PB, United Kingdom",
                    "+44 20 7967 8021", "https://www.londoneye.com/", "1",
                    "https://media.cntraveler.com/photos/55c8be0bd36458796e4ca38a/master/pass/london-eye-2-cr-getty.jpg"));
            attractions.add(new Attraction("Buckingham Palace", "London SW1A 1AA, United Kingdom",
                    "+44 303 123 7300", "https://www.royal.uk/royal-residences-buckingham-palace", "2",
                    "https://zamanturkmenistan.com.tm/wp-content/uploads/2021/04/buckingham-palace-london.jpg"));
            //adapter.setAttractions(ServerUtility.getInstance(getContext()).getAttractions());
            adapterToDetailesAttr.setAttractions(attractions);
        }
        else{

            btnFinish.setVisibility(View.VISIBLE);

            adapterToMustVisitAttr = new AddingAttrToMustVisitAttrAdapter(getActivity());
            attractionsRecView.setAdapter(adapterToMustVisitAttr);
            attractionsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ArrayList<Attraction> attractions = new ArrayList<>();
            attractions.add(new Attraction("London eye", "Riverside Building, County Hall, London SE1 7PB, United Kingdom",
                    "+44 20 7967 8021", "https://www.londoneye.com/", "1",
                    "https://media.cntraveler.com/photos/55c8be0bd36458796e4ca38a/master/pass/london-eye-2-cr-getty.jpg"));
            attractions.add(new Attraction("Buckingham Palace", "London SW1A 1AA, United Kingdom",
                    "+44 303 123 7300", "https://www.royal.uk/royal-residences-buckingham-palace", "2",
                    "https://zamanturkmenistan.com.tm/wp-content/uploads/2021/04/buckingham-palace-london.jpg"));
            adapterToMustVisitAttr.setMustVisitAttractions(attractions);

            btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServerUtility.getInstance(getContext()).setTripSelectedAttrations(adapterToMustVisitAttr.getSelectedAttractions());
                    Intent intent = new Intent(getActivity(), CreateNewTripActivity.class);
                    startActivity(intent);
                }
            });
        }



        return view;
    }

    private void initView(View view) {
        searchViewAttractions = view.findViewById(R.id.searchViewAttr);
        attractionsRecView = view.findViewById(R.id.searchAttrRecView);
        btnFinish = view.findViewById(R.id.btnFinish);
    }
}
