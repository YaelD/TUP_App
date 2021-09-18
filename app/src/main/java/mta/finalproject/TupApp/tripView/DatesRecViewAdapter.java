package mta.finalproject.TupApp.tripView;

import static mta.finalproject.TupApp.mapActivity.MapsActivity.DAY_PLAN_JSON;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import mta.finalproject.TupApp.R;

import java.util.ArrayList;

import mta.finalproject.TupApp.javaClasses.DayPlan;
import mta.finalproject.TupApp.mapActivity.MapsActivity;

public class DatesRecViewAdapter extends RecyclerView.Adapter<DatesRecViewAdapter.ViewHolder>{

    ArrayList<DayPlan> plans = new ArrayList<>();
    Context mContext;


    @SuppressLint("NotifyDataSetChanged")
    public void setPlans(ArrayList<DayPlan> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    //====================================================================================//

    public DatesRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //====================================================================================//

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //====================================================================================//

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtDate.setText(plans.get(position).getDate().toString());
        holder.txtHotelName.setText((plans.get(position).getHotel().getName()));
        holder.txtEndHour.setText(plans.get(position).getFinishTime().toString());
        AttractionsRecViewAdapter adapter = new AttractionsRecViewAdapter(mContext);
        adapter.setOnePlans(plans.get(position).getDaySchedule());
        holder.recViewAttractions.setAdapter(adapter);
        holder.recViewAttractions.setLayoutManager(new LinearLayoutManager(mContext));
        holder.imgRouteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: delete this log
                Log.e("DatesAdapter==>", "In On Click Listener");
                Intent intent = new Intent(mContext, MapsActivity.class);
                String dayPlanJson = new Gson().toJson(plans.get(position));
                intent.putExtra(DAY_PLAN_JSON, dayPlanJson);
                mContext.startActivity(intent);
            }
        });
    }

    //====================================================================================//

    @Override
    public int getItemCount() {
        return plans.size();
    }

    //====================================================================================//

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDate, txtHotelName, txtEndHour;
        RecyclerView recViewAttractions;
        ImageView imgRouteMap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            recViewAttractions = itemView.findViewById(R.id.attractionsRecView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtEndHour = itemView.findViewById(R.id.txtEndHour);
            imgRouteMap = itemView.findViewById(R.id.imgRouteMap);
        }
    }
}
