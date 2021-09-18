package mta.finalproject.TupApp.attractionSearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mta.finalproject.TupApp.attractionDetails.AttractionDetailsActivity;
import mta.finalproject.TupApp.javaClasses.Attraction;

import static mta.finalproject.TupApp.attractionDetails.AttractionDetailsFragment.ATTRACTION_KEY;
import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

public class AttractionsSearchRecAdapter extends RecyclerView.Adapter<AttractionsSearchRecAdapter.ViewHolder> {
    private static final String TAG = "AttrSearchRecAdapter";
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private Context mContext;
    //====================================================================================//

    public AttractionsSearchRecAdapter(Context mContext) {
        this.mContext = mContext;
    }
    //====================================================================================//

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attractions, parent, false);
        return new ViewHolder(view);
    }
    //====================================================================================//

    @Override
    public void onBindViewHolder(@NonNull @NotNull AttractionsSearchRecAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtNameAttr.setText(attractions.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(attractions.get(position).getImageUrl())
                .into(holder.imgAttraction);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AttractionDetailsActivity.class);
                intent.putExtra(ATTRACTION_KEY, attractions.get(position).getPlaceID());
                intent.putExtra(CALLING_ACTIVITY, mContext.getClass().getName());
                mContext.startActivity(intent);
            }
        });
    }
    //====================================================================================//

    @Override
    public int getItemCount() {
        return attractions.size();
    }
    //====================================================================================//

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
        notifyDataSetChanged();
    }
    //====================================================================================//

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgAttraction;
        private TextView txtNameAttr;
        //====================================================================================//

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgAttraction = itemView.findViewById(R.id.imgAttraction);
            txtNameAttr = itemView.findViewById(R.id.txtAttractionName);
        }
    }
}
