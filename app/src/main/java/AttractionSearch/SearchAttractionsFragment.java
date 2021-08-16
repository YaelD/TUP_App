package AttractionSearch;

import android.os.Bundle;
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
import TripCreation.CreateNewTripActivity;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class SearchAttractionsFragment extends Fragment {

    public static final String SELECTED_ATTRACTIONS = "selected attractions";

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
        initView(view);


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


    //WE are in the Search attractions!!
    private void setRecView()
    {
        String callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);


        if(callingActivity.equals(CreateNewTripActivity.class.getName())){

            btnFinish.setVisibility(View.VISIBLE);

            adapterToMustVisitAttr = new AddingAttrToMustVisitAttrAdapter(getActivity());
            attractionsRecView.setAdapter(adapterToMustVisitAttr);
            attractionsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            //adapterToMustVisitAttr.setMustVisitAttractions(ServerUtility.getInstance(getContext()).getAttractionsTest());
            adapterToMustVisitAttr.setMustVisitAttractions(ServerUtility.getInstance(getContext()).getAttractions());

            adapterToMustVisitAttr.setSelectedAttractions(ServerUtility.getInstance(getContext()).getTripSelectedAttrations());
            btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        else{
            btnFinish.setVisibility(View.GONE);
            adapterToDetailesAttr = new AttractionsSearchRecAdapter(getActivity());
            attractionsRecView.setAdapter(adapterToDetailesAttr);
            attractionsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ArrayList<Attraction> array = ServerUtility.getInstance(getContext()).getAttractions();
            adapterToDetailesAttr.setAttractions(array);
        }
     }

    private void initView(View view) {
        searchViewAttractions = view.findViewById(R.id.searchViewAttr);
        attractionsRecView = view.findViewById(R.id.searchAttrRecView);
        btnFinish = view.findViewById(R.id.btnFinish);
    }
}
