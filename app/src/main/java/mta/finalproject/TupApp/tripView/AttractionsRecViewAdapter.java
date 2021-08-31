package mta.finalproject.TupApp.tripView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import mta.finalproject.TupApp.R;

import java.util.ArrayList;

import mta.finalproject.TupApp.attractionDetails.AttractionDetailsActivity;
import mta.finalproject.TupApp.javaClasses.OnePlan;

public class AttractionsRecViewAdapter extends RecyclerView.Adapter<AttractionsRecViewAdapter.ViewHolder> {

    public static final String ATTRACTION_KEY = "attraction";

    private Context mContext;
    private ArrayList<OnePlan> onePlans = new ArrayList<>();

    public void setOnePlans(ArrayList<OnePlan> onePlans) {
        this.onePlans = onePlans;
        notifyDataSetChanged();
    }

    public AttractionsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attraction_layout, parent, false);
        AttractionsRecViewAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.txtHours.setText(onePlans.get(position).getStartTime().toString());
        holder.txtHours.setText(onePlans.get(position).getStartTime().toString());
        holder.txtAttractionName.setText(onePlans.get(position).getAttraction().getName());
        if(onePlans.get(position).getFavoriteAttraction())
        {
            //holder.attractionCardView.setCardBackgroundColor(mContext.getColor(R.color.bright_red));
            holder.imgSelectedAttraction.setVisibility(View.VISIBLE);
        }
        else
            holder.imgSelectedAttraction.setVisibility(View.GONE);

        holder.attractionCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AttractionDetailsActivity.class);
                intent.putExtra(ATTRACTION_KEY, onePlans.get(position).getAttraction().getPlaceID());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return onePlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView txtHours;
        TextView txtAttractionName;
        CardView attractionCardView;
        ImageView imgSelectedAttraction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHours = itemView.findViewById(R.id.txtHour);
            txtAttractionName = itemView.findViewById(R.id.txtNameOfAttr);
            attractionCardView = itemView.findViewById(R.id.attractionCardView);
            imgSelectedAttraction = itemView.findViewById(R.id.imgSelectedAttraction);
        }
    }
}

