package FavoriteAttractions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import AttractionSearch.AddingAttrToMustVisitAttrAdapter;
import AttractionSearch.AttractionsSearchRecAdapter;
import JavaClasses.Utility;
import TripCreation.CreateNewTripActivity;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

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

    private void setAdapters()
    {
        String callingActivity = getActivity().getIntent().getStringExtra(CALLING_ACTIVITY);
        if(Utility.getInstance(getContext()).getFavoriteAttractions().size() != 0)
        {
            txtEmptyFavoriteList.setVisibility(View.GONE);
        }
        else
        {
            txtEmptyFavoriteList.setVisibility(View.VISIBLE);
        }
        if(callingActivity.equals(CreateNewTripActivity.class.getName()))
        {
            if(Utility.getInstance(getContext()).getFavoriteAttractions().size() != 0)
            {
                btnFinishSelectFavAttr.setVisibility(View.VISIBLE);
            }
            else
            {
                btnFinishSelectFavAttr.setVisibility(View.GONE);
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
            AttractionsSearchRecAdapter adapter = new AttractionsSearchRecAdapter(getActivity());
            adapter.setAttractions(Utility.getInstance(getContext()).getFavoriteAttractions());
            recViewFavoriteAttractions.setAdapter(adapter);
            recViewFavoriteAttractions.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");
        recViewFavoriteAttractions = view.findViewById(R.id.recViewFavoriteAttractions);
        btnFinishSelectFavAttr = view.findViewById(R.id.btnFinishSelectFavAttr);
        txtEmptyFavoriteList = view.findViewById(R.id.txtEmptyFavoriteList);
    }
}
