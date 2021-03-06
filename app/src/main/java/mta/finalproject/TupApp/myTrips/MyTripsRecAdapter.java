package mta.finalproject.TupApp.myTrips;

import static mta.finalproject.TupApp.mainScreen.MainScreenFragment.CALLING_ACTIVITY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import mta.finalproject.TupApp.R;

import java.util.ArrayList;

import mta.finalproject.TupApp.javaClasses.Utility;
import mta.finalproject.TupApp.javaClasses.TripPlan;
import mta.finalproject.TupApp.tripView.TripViewActivity;

public class MyTripsRecAdapter extends RecyclerView.Adapter<MyTripsRecAdapter.ViewHolder>{

    private ArrayList<TripPlan> trips = new ArrayList<>();
    private Context mContext;
    private TextView emptyListTxt;

    public MyTripsRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //====================================================================================//

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trip, parent, false);
        return new MyTripsRecAdapter.ViewHolder(view);
    }

    //====================================================================================//

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTripName.setText(trips.get(position).getTripName());

        holder.imgDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = trips.get(position).getTripName();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete " + trips.get(position).getTripName() + " trip?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Utility.getInstance(mContext).deleteTrip(trips.get(position).getTripId())) {
                            Toast.makeText(mContext, tripName + " was deleted successfully", Toast.LENGTH_SHORT).show();
                            if(getItemCount() == 0)
                            {
                                emptyListTxt.setVisibility(View.VISIBLE);
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TripViewActivity.class);
                intent.putExtra(CALLING_ACTIVITY, mContext.getClass().getName());
                Utility.getInstance(mContext).setLastCreatedTrip(trips.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    //====================================================================================//

    @Override
    public int getItemCount() {
        return trips.size();
    }

    //====================================================================================//

    @SuppressLint("NotifyDataSetChanged")
    public void setTrips(ArrayList<TripPlan> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    //====================================================================================//

    public void setEmptyListTxt(TextView emptyListTxt) {
        this.emptyListTxt = emptyListTxt;
    }

    //====================================================================================//

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
