package com.example.tupapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DesiredHoursRecViewAdapter extends RecyclerView.Adapter<DesiredHoursRecViewAdapter.ViewHolder> {

    private List<LocalDate> desiredHours = new ArrayList<>();

    public DesiredHoursRecViewAdapter() {
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_desired_hours, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DesiredHoursRecViewAdapter.ViewHolder holder, int position) {
        holder.txtDate.setText(desiredHours.get(position).toString() + ":");

        holder.txtTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(hour)
                        .setMinute(minute)
                        .build();

                materialTimePicker.show(materialTimePicker.getActivity().getSupportFragmentManager(), "fragment_tag");

               /* materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
                    int newHour = materialTimePicker.getHour();
                    int newMinute = materialTimePicker.getMinute();
                    String time = String.format(Locale.getDefault(), "%02d:%02d", newHour, newMinute);
                    holder.txtTimeFrom.setText(time);

                });*/
            }
        });

        /*holder.txtTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(hour)
                        .setMinute(minute)
                        .build();

                materialTimePicker.show(materialTimePicker.getParentFragmentManager(), "fragment_tag");

                materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
                    int newHour = materialTimePicker.getHour();
                    int newMinute = materialTimePicker.getMinute();
                    String time = String.format(Locale.getDefault(), "%02d:%02d", newHour, newMinute);
                    holder.txtTimeTo.setText(time);
                });
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return desiredHours.size();
    }

    public void setDesiredHours(List<LocalDate> desiredHours){
        this.desiredHours = desiredHours;
        notifyDataSetChanged();
    }


    //מקשר בין קובץ הXML של רשימת השעות (כאן ניתן לקשר את האובייקטים שנמצאים בקובץ למחלקה)
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtDate, txtTimeFrom, txtHyphen, txtTimeTo; //txtEror;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtHyphen = itemView.findViewById(R.id.txtHyphen);
            txtTimeFrom = itemView.findViewById(R.id.txtTimeFrom);
            txtTimeTo = itemView.findViewById(R.id.txtTimeTo);
            //txtEror = itemView.findViewById(R.id.textView6);


        }
    }
}
