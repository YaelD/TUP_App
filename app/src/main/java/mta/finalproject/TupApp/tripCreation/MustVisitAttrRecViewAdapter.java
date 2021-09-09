package mta.finalproject.TupApp.tripCreation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mta.finalproject.TupApp.javaClasses.Attraction;
import mta.finalproject.TupApp.navigationDrawer.NavigationDrawerActivity;

public class MustVisitAttrRecViewAdapter extends RecyclerView.Adapter<MustVisitAttrRecViewAdapter.ViewHolder>{
    private static final String TAG = "MustVisitAttrRecViewAdapter";

    private ArrayList<Attraction> mustVisitAttractions = new ArrayList<>();
    private Context mContext;

    public MustVisitAttrRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }




    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attractions_create_trip, parent, false);
        return new MustVisitAttrRecViewAdapter.ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MustVisitAttrRecViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.txtNameAttr.setText(mustVisitAttractions.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(mustVisitAttractions.get(position).getImageUrl())
                .into(holder.imgAttraction);

        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Remove Attraction");
                alertDialog.setMessage("Are you sure you want to remove this attraction?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mustVisitAttractions.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

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
        private AppCompatImageView img_cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgAttraction = itemView.findViewById(R.id.imgAttraction);
            txtNameAttr = itemView.findViewById(R.id.txtAttractionName);
            img_cancel = itemView.findViewById(R.id.img_cancel);
        }
    }
}
