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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import AttractionSearch.AddingAttrToMustVisitAttrAdapter;
import AttractionSearch.AttractionsSearchRecAdapter;
import JavaClasses.ServerUtility;
import MainScreen.MainScreenActivity;
import TripCreation.MustVisitAttrRecViewAdapter;

import static MainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class FavoriteAttractionsFragment extends Fragment {
    private static final String TAG = "FavoriteAttractionsFrag";

    private RecyclerView recViewFavoriteAttractions;
    private Button btnFinishSelectFavAttr;
    private TextView txtEmptyFavoriteList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_attractions, container, false);
        initViews(view);
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
        if(ServerUtility.getInstance(getContext()).getFavoriteAttractions().size() != 0)
            txtEmptyFavoriteList.setVisibility(View.GONE);
        else
            txtEmptyFavoriteList.setVisibility(View.VISIBLE);

        if(callingActivity.equals(MainScreenActivity.class.getName()))
        {
            btnFinishSelectFavAttr.setVisibility(View.GONE);
            AttractionsSearchRecAdapter adapter = new AttractionsSearchRecAdapter(getActivity());
            adapter.setAttractions(ServerUtility.getInstance(getContext()).getFavoriteAttractions());
            recViewFavoriteAttractions.setAdapter(adapter);
            recViewFavoriteAttractions.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else
        {
            if(ServerUtility.getInstance(getContext()).getFavoriteAttractions().size() != 0)
                btnFinishSelectFavAttr.setVisibility(View.VISIBLE);
            else
                btnFinishSelectFavAttr.setVisibility(View.GONE);
            AddingAttrToMustVisitAttrAdapter adapter = new AddingAttrToMustVisitAttrAdapter(getActivity());
            adapter.setMustVisitAttractions(ServerUtility.getInstance(getContext()).getFavoriteAttractions());
            adapter.setSelectedAttractions(ServerUtility.getInstance(getContext()).getTripSelectedAttrations());
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
