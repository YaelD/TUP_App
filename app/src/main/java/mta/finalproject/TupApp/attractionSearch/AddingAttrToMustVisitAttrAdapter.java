package mta.finalproject.TupApp.attractionSearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mta.finalproject.TupApp.javaClasses.Attraction;
import mta.finalproject.TupApp.javaClasses.Utility;

public class AddingAttrToMustVisitAttrAdapter extends RecyclerView.Adapter<AddingAttrToMustVisitAttrAdapter.ViewHolder>{

    private static final String TAG = "AddingAttrToMustVisitAttrAdapter";

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Attraction> selectedAttractions = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull @NotNull AddingAttrToMustVisitAttrAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtNameAttr.setText(attractions.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(attractions.get(position).getImageUrl())
                .into(holder.imgAttraction);

        holder.checkBox.setOnCheckedChangeListener(null);

        boolean isInSelected = Utility.isAttractionInArr(selectedAttractions, attractions.get(position));

        if(isInSelected)
        {
            holder.checkBox.setChecked(true);
        }
        else
        {
            holder.checkBox.setChecked(false);
        }

        /*
        if(selectedAttractions.contains(attractions.get(position)))
        {
            Log.e("AddingAttToAdapter==>", "The attraction  " +attractions.get(position).getName() + " is not in the adapter");
            holder.checkBox.setChecked(true);
        }
        else
        {
            holder.checkBox.setChecked(false);
        }


         */






        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    selectedAttractions.add(attractions.get(position));
                }
                else
                {
                    selectedAttractions.remove(attractions.get(position));
                }
                /*
                    if(isChecked)
                    {
                        if(!selectedAttractions.contains(attractions.get(position)))
                        {
                            selectedAttractions.add(attractions.get(position));
                            Log.e("AddToMustVisitAdap==>", "added to selected " + attractions.get(position).getName());
                            //Log.e("AddToMustVisitAdap==>", "selected Attractions=" +selectedAttractions.toString());
                        }
                    }
                    else
                    {
                        if(selectedAttractions.contains(attractions.get(position)))
                        {
                            selectedAttractions.remove(attractions.get(position));
                            Log.e("AddToMustVisitAdap==>", "removed from selected " + attractions.get(position).getName());
                            //Log.e("AddToMustVisitAdap==>", "selected Attractions=" +selectedAttractions.toString());

                        }
                    }

                 */

            }
        });

    }




    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public void setMustVisitAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
        notifyDataSetChanged();
    }

    public void setSelectedAttractions(ArrayList<Attraction> selectedAttractions) {
        this.selectedAttractions = selectedAttractions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgAttraction;
        private TextView txtNameAttr;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgAttraction = itemView.findViewById(R.id.imgAttraction);
            txtNameAttr = itemView.findViewById(R.id.txtAttractionName);
            checkBox = itemView.findViewById(R.id.checkBoxPickAttr);
        }
    }
}
