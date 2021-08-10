package TripCreation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import JavaClasses.Attraction;

public class MustVisitAttractionsAdapter extends RecyclerView.Adapter<MustVisitAttractionsAdapter.ViewHolder>{
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



public class AttractionsSearchRecAdapter extends RecyclerView.Adapter<AttractionSearch.AttractionsSearchRecAdapter.ViewHolder> {
    private static final String TAG = "AttrSearchRecAdapter";

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private Context mContext;

    public AttractionsSearchRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public AttractionSearch.AttractionsSearchRecAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attractions, parent, false);
        return new AttractionSearch.AttractionsSearchRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AttractionSearch.AttractionsSearchRecAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtNameAttr.setText(attractions.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(attractions.get(position).getImageUrl())
                .into(holder.imgAttraction);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, attractions.get(position).getName() + "selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
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
