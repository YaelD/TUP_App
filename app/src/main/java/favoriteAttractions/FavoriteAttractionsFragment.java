package favoriteAttractions;

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

import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import attractionSearch.AddingAttrToMustVisitAttrAdapter;
import attractionSearch.AttractionsSearchRecAdapter;
import javaClasses.ServerConnection;
import javaClasses.Utility;
import navigationDrawer.NavigationDrawerActivity;
import tripCreation.CreateNewTripActivity;

import static mainScreen.MainScreenFragment.CALLING_ACTIVITY;

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
            AttractionsSearchRecAdapter adapter = new AttractionsSearchRecAdapter(getActivity());
            adapter.setAttractions(Utility.getInstance(getContext()).getFavoriteAttractions());
            recViewFavoriteAttractions.setAdapter(adapter);
            recViewFavoriteAttractions.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ServerConnection.getInstance(getContext()).sendFavAttractions();
    }

    private void initViews(View view) {
        recViewFavoriteAttractions = view.findViewById(R.id.recViewFavoriteAttractions);
        btnFinishSelectFavAttr = view.findViewById(R.id.btnFinishSelectFavAttr);
        txtEmptyFavoriteList = view.findViewById(R.id.txtEmptyFavoriteList);
    }
}
