package MyTrips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TupApp.R;

import java.util.ArrayList;

import JavaClasses.Utility;
import JavaClasses.TripPlan;
import TripView.TripViewActivity;

public class MyTripsRecAdapter extends RecyclerView.Adapter<MyTripsRecAdapter.ViewHolder>{

    private static final String TAG = "MyTripsRecAdapter";
    private ArrayList<TripPlan> trips = new ArrayList<>();
    private Context mContext;

    public MyTripsRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trip, parent, false);
        return new MyTripsRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTripName.setText(trips.get(position).getName());

        holder.imgDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance(mContext).deleteTrip(trips.get(position).getId());
                //TODO: check how to delete item from recview in run time
            }
        });

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TripViewActivity.class);
                Utility.getInstance(mContext).setLastCreatedTrip(trips.get(position));


            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public void setTrips(ArrayList<TripPlan> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private TextView txtTripName;
        private ImageView imgDeleteTrip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtTripName = itemView.findViewById(R.id.txtTripName);
            imgDeleteTrip = itemView.findViewById(R.id.imgDeleteTrip);

        }
    }
}
