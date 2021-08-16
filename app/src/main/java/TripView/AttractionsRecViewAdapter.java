package TripView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import java.util.ArrayList;

import JavaClasses.OnePlan;

public class AttractionsRecViewAdapter extends RecyclerView.Adapter<AttractionsRecViewAdapter.ViewHolder> {

    private ArrayList<OnePlan> onePlans = new ArrayList<>();

    public void setOnePlans(ArrayList<OnePlan> onePlans) {
        this.onePlans = onePlans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_item_layout, parent, false);
        AttractionsRecViewAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtHours.setText(onePlans.get(position).getStartTime().toString());
        holder.txtAttractionName.setText(onePlans.get(position).getAttraction().getName());
        Log.e("HERE==>", "Set attraction: " + onePlans.get(position).getAttraction().getName());


    }

    @Override
    public int getItemCount() {
        return onePlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtHours;
        TextView txtAttractionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHours = itemView.findViewById(R.id.hourTxt);
            txtAttractionName = itemView.findViewById(R.id.attractionNameTxt);

        }
    }
}

