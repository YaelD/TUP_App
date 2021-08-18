package TripCreation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import AttractionDetails.AttractionDetailsFragment;
import JavaClasses.Attraction;

public class MustVisitAttrRecViewAdapter extends RecyclerView.Adapter<MustVisitAttrRecViewAdapter.ViewHolder>{
    private static final String TAG = "MustVisitAttrRecViewAdapter";

    private ArrayList<Attraction> mustVisitAttractions = new ArrayList<>();
    private Context mContext;

    public MustVisitAttrRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }




    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attractions, parent, false);
        return new MustVisitAttrRecViewAdapter.ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MustVisitAttrRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.txtNameAttr.setText(mustVisitAttractions.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(mustVisitAttractions.get(position).getImageUrl())
                .into(holder.imgAttraction);


    }

    @Override
    public int getItemCount() {
        return mustVisitAttractions.size();
    }

    public void setMustVisitAttractions(ArrayList<Attraction> attractions) {
        this.mustVisitAttractions = attractions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgAttraction;
        private TextView txtNameAttr;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgAttraction = itemView.findViewById(R.id.imgAttraction);
            txtNameAttr = itemView.findViewById(R.id.txtAttractionName);
        }
    }
}
