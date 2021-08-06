package com.example.tupapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.MaterialDatePicker;
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
    //private int currentHour, currentMinutes;
    //private FragmentActivity context;
    private Context context;

    public DesiredHoursRecViewAdapter(FragmentActivity context) {
        this.context = context;
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

        //holder.txtTimeFrom.setInputType(InputType.TYPE_NULL);
        holder.btnTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.btnTimeFrom.setText(hourOfDay + ":" + minute);
                    }
                }, currentHour, currentMinutes, true);

                timePicker.show();

                /*Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setHour(10)
                        .setMinute(0)
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .build();


                materialTimePicker.show(context.getSupportFragmentManager(), "TAG");
                //materialTimePicker.show(materialTimePicker.getParentFragmentManager(), "fragment_tag");
                //materialTimePicker.show(materialTimePicker.getActivity().getSupportFragmentManager(), "fragment_tag");

               /* materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
                    int newHour = materialTimePicker.getHour();
                    int newMinute = materialTimePicker.getMinute();
                    String time = String.format(Locale.getDefault(), "%02d:%02d", newHour, newMinute);
                    holder.txtTimeFrom.setText(time);

                });*/
            }
        });

        //holder.txtTimeTo.setInputType(InputType.TYPE_NULL);
        holder.btnTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.btnTimeTo.setText(hourOfDay + ":" + minute);
                    }
                }, currentHour, currentMinutes, true);

                timePicker.show();
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

        private TextView txtDate, txtHyphen;
        private Button btnTimeTo, btnTimeFrom;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtHyphen = itemView.findViewById(R.id.txtHyphen);
            btnTimeFrom = itemView.findViewById(R.id.btnTimeFrom);
            btnTimeTo = itemView.findViewById(R.id.btnTimeTo);
            //txtEror = itemView.findViewById(R.id.textView6);


        }
    }
}
