package FavoriteAttractions;

import static AttractionDetails.AttractionDetailsFragment.ATTRACTION_KEY;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.TupApp.R;

import java.util.ArrayList;

import AttractionDetails.AttractionDetailsActivity;
import AttractionSearch.AttractionsSearchRecAdapter;
import JavaClasses.Attraction;

public class FavoriteAttractionsRecAdapter extends RecyclerView.Adapter<FavoriteAttractionsRecAdapter.ViewHolder> {

    private static final String TAG = "AttrSearchRecAdapter";
    private ArrayList<Attraction> attractionsArr = new ArrayList<>();
    private Context mContext;

    public FavoriteAttractionsRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attractions, parent, false);
        return new FavoriteAttractionsRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtNameAttr.setText(attractionsArr.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(attractionsArr.get(position).getImageUrl())
                .into(holder.imgAttraction);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AttractionDetailsActivity.class);
                intent.putExtra(ATTRACTION_KEY, attractionsArr.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractionsArr.size();
    }

    public void setFavoriteAttractions(ArrayList<Attraction> attractions) {
        this.attractionsArr = attractions;
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
