package mta.finalproject.TupApp.attractionSearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mta.finalproject.TupApp.javaClasses.Attraction;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class SearchAttractionsFragment extends Fragment {



    private SearchView searchViewAttr;
    private RecyclerView attractionsRecView;
    private Button btnFinish;
    private AttractionsSearchRecAdapter adapterToDetailsAttr;
    private AddingAttrToMustVisitAttrAdapter adapterToMustVisitAttr;
    private String callingActivity;
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private int scrollPosition;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_attractions, container, false);
        initView(view);
        //callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);
        layoutManager = new GridLayoutManager(getContext(), 2);
        scrollPosition = 0;
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
    public void onResume() {
        super.onResume();
        setRecView();
    }


    /*
    @Override
    public void onStop() {
        super.onStop();
        Log.e("SearchAttrFrag==>", "SAVING and Deleting Favs Attractions");
        if(Utility.getInstance(getContext()).getFavAttractionsToAdd().size() > 0)
        {
            ServerConnection.getInstance(getContext()).sendFavAttractionsToAdd();
        }
        if(Utility.getInstance(getContext()).getFavAttractionsToDelete().size() > 0)
        {
            ServerConnection.getInstance(getContext()).sendFavAttractionsToDelete();
        }
    }
     */



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
            adapterToMustVisitAttr.setMustVisitAttractions(filteredList);
        }
        else
        {
            if(!filteredList.isEmpty())
            {
                adapterToDetailsAttr.setAttractions(filteredList);
            }
        }
    }


    private void setRecView()
    {

        callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);

        if(callingActivity.equals(CreateNewTripActivity.class.getName())){
            btnFinish.setVisibility(View.VISIBLE);
            adapterToMustVisitAttr = new AddingAttrToMustVisitAttrAdapter(getActivity());
            adapterToMustVisitAttr.setMustVisitAttractions(Utility.getInstance(getContext()).getAttractions());
            adapterToMustVisitAttr.setSelectedAttractions(Utility.getInstance(getContext()).getTripSelectedAttrations());
            attractionsRecView.setAdapter(adapterToMustVisitAttr);
            attractionsRecView.scrollToPosition(scrollPosition);
            attractionsRecView.setLayoutManager(layoutManager);
            attractionsRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    scrollPosition = layoutManager.findFirstVisibleItemPosition();
                }
            });


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
//                    Intent intent = new Intent(getContext(), NavigationDrawerActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            };
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
            adapterToDetailsAttr = new AttractionsSearchRecAdapter(getActivity());
            ArrayList<Attraction> array = Utility.getInstance(getContext()).getAttractions();
            adapterToDetailsAttr.setAttractions(array);
            //adapterToDetailsAttr.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT);
            attractionsRecView.setAdapter(adapterToDetailsAttr);
            attractionsRecView.scrollToPosition(scrollPosition);
            attractionsRecView.setLayoutManager(layoutManager);
            attractionsRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    scrollPosition = layoutManager.findFirstVisibleItemPosition();
                }
            });

            //.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
     }


    private void initView(View view) {
        attractionsRecView = view.findViewById(R.id.searchAttrRecView);
        btnFinish = view.findViewById(R.id.btnFinish);
        searchViewAttr = view.findViewById(R.id.attSearchView);
    }
}

