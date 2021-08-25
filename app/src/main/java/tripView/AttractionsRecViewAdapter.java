package tripView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import java.util.ArrayList;

import attractionDetails.AttractionDetailsActivity;
import javaClasses.OnePlan;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.txtHours.setText(onePlans.get(position).getStartTime().toString());
        Log.e("AttRecAddapter===>", onePlans.get(position).getAttraction().toString());
        holder.txtHours.setText(onePlans.get(position).getStartTime().toString());
        holder.txtAttractionName.setText(onePlans.get(position).getAttraction().getName());

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHours = itemView.findViewById(R.id.txtHour);
            txtAttractionName = itemView.findViewById(R.id.txtNameOfAttr);
            attractionCardView = itemView.findViewById(R.id.attractionCardView);
        }
    }
}

