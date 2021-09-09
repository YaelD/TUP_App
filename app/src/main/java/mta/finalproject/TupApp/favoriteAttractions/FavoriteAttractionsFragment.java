package mta.finalproject.TupApp.favoriteAttractions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import mta.finalproject.TupApp.attractionSearch.AddingAttrToMustVisitAttrAdapter;
import mta.finalproject.TupApp.attractionSearch.AttractionsSearchRecAdapter;
import mta.finalproject.TupApp.javaClasses.ServerConnection;
import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;
import mta.finalproject.TupApp.tripCreation.CreateNewTripActivity;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import java.util.ArrayList;

public class FavoriteAttractionsFragment extends Fragment {
    private static final String TAG = "FavoriteAttractionsFrag";

    private RecyclerView recViewFavoriteAttractions;
    private Button btnFinishSelectFavAttr;
    private TextView txtEmptyFavoriteList;




    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_attractions, container, false);
        initViews(view);
        btnFinishSelectFavAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapters();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    private void setAdapters()
    {
        String callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);
        if(callingActivity != null  && callingActivity.equals(CreateNewTripActivity.class.getName()))
        {
            if(Utility.getInstance(getContext()).getFavoriteAttractions().size() != 0)
            {
                btnFinishSelectFavAttr.setVisibility(View.VISIBLE);
                txtEmptyFavoriteList.setVisibility(View.GONE);
            }
            else
            {
                btnFinishSelectFavAttr.setVisibility(View.GONE);
                txtEmptyFavoriteList.setVisibility(View.VISIBLE);
            }
            AddingAttrToMustVisitAttrAdapter adapter = new AddingAttrToMustVisitAttrAdapter(getActivity());
            adapter.setMustVisitAttractions(Utility.getInstance(getContext()).getFavoriteAttractions());
            adapter.setSelectedAttractions(Utility.getInstance(getContext()).getTripSelectedAttrations());
            recViewFavoriteAttractions.setAdapter(adapter);
            recViewFavoriteAttractions.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else
        {
            btnFinishSelectFavAttr.setVisibility(View.GONE);

            Log.e("FavAttFrag==>", "The size of fav attractions is" +
                    Utility.getInstance(getContext()).getFavoriteAttractions().size());
            if(Utility.getInstance(getContext()).getFavoriteAttractions().size() != 0)
            {
                Log.e("FavAttFrag==>","Setting Adapter!!");
                txtEmptyFavoriteList.setVisibility(View.GONE);
                AttractionsSearchRecAdapter adapter = new AttractionsSearchRecAdapter(getActivity());
                adapter.setAttractions(Utility.getInstance(getContext()).getFavoriteAttractions());
                recViewFavoriteAttractions.setAdapter(adapter);
                recViewFavoriteAttractions.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }
            else
            {
                txtEmptyFavoriteList.setVisibility(View.VISIBLE);
            }

            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    getActivity().finish();
                }
            };
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //TODO: Matan added this
        recViewFavoriteAttractions.setAdapter(null);
        /*

        Log.e("FavAttractionsFrag==>", "SAVING and Deleting Favs Attractions");
        if(Utility.getInstance(getContext()).getFavAttractionsToAdd().size() > 0)
        {
            ServerConnection.getInstance(getContext()).sendFavAttractionsToAdd();
        }
        if(Utility.getInstance(getContext()).getFavAttractionsToDelete().size() > 0)
        {
            ServerConnection.getInstance(getContext()).sendFavAttractionsToDelete();
        }

         */
    }




    private void initViews(View view) {
        recViewFavoriteAttractions = view.findViewById(R.id.recViewFavoriteAttractions);
        btnFinishSelectFavAttr = view.findViewById(R.id.btnFinishSelectFavAttr);
        txtEmptyFavoriteList = view.findViewById(R.id.txtEmptyFavoriteList);
    }
}
