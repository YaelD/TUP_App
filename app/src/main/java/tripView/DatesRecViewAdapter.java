package tripView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import java.util.ArrayList;

import javaClasses.DayPlan;

public class DatesRecViewAdapter extends RecyclerView.Adapter<DatesRecViewAdapter.ViewHolder>{

    ArrayList<DayPlan> plans = new ArrayList<>();
    Context mContext;


    public void setPlans(ArrayList<DayPlan> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    public DatesRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtDate.setText(plans.get(position).getDate().toString());
        holder.txtHotelName.setText((plans.get(position).getHotel().getName()));
        //holder.txtDate.setText(plans.get(position).getDateStr());
        AttractionsRecViewAdapter adapter = new AttractionsRecViewAdapter(mContext);
        adapter.setOnePlans(plans.get(position).getDaySchedule());
        holder.recViewAttractions.setAdapter(adapter);
        holder.recViewAttractions.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDate, txtHotelName;
        RecyclerView recViewAttractions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            recViewAttractions = itemView.findViewById(R.id.attractionsRecView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
        }
    }
}
