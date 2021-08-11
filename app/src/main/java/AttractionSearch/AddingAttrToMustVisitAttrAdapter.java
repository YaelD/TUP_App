package AttractionSearch;

import android.annotation.SuppressLint;
import android.content.Context;
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

import JavaClasses.Attraction;

public class AddingAttrToMustVisitAttrAdapter extends RecyclerView.Adapter<AddingAttrToMustVisitAttrAdapter.ViewHolder>{

    private static final String TAG = "AddingAttrToMustVisitAttrAdapter";

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private Context mContext;

    public AddingAttrToMustVisitAttrAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pick_must_visit_attraction, parent, false);
        return new AddingAttrToMustVisitAttrAdapter.ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AddingAttrToMustVisitAttrAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtNameAttr.setText(attractions.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(attractions.get(position).getImageUrl())
                .into(holder.imgAttraction);
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public void setMustVisitAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
