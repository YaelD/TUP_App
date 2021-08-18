package AttractionSearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import JavaClasses.Attraction;
import JavaClasses.Utility;
import NavigationDrawer.NavigationDrawerActivity;
import TripCreation.CreateNewTripActivity;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class SearchAttractionsFragment extends Fragment {

    public static final String SELECTED_ATTRACTIONS = "selected attractions";

    private EditText txtSearchAttr;
    private RecyclerView attractionsRecView;
    private Button btnFinish;
    private ImageView imgSearch;
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

            txtSearchAttr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    initSearch();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
     }

    private void initSearch() {
        if(!txtSearchAttr.getText().toString().equals("")){
            String name = txtSearchAttr.getText().toString();
            ArrayList<Attraction> searchedAttractions = Utility.getInstance(getContext()).searchAttractions(name);
            if(null != searchedAttractions){
                adapterToDetailesAttr.setAttractions(searchedAttractions);
            }
        }
    }

    private void initView(View view) {
        txtSearchAttr = view.findViewById(R.id.txtSearchAttr);
        attractionsRecView = view.findViewById(R.id.searchAttrRecView);
        btnFinish = view.findViewById(R.id.btnFinish);
        imgSearch = view.findViewById(R.id.imgSearch);
    }
}

