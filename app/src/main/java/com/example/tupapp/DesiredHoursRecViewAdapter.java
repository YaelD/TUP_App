package com.example.tupapp;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
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

    private ArrayList<DesiredHoursInDay> desiredHours = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull @NotNull DesiredHoursRecViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtDate.setText(desiredHours.get(position).getDate() + ":");

        holder.btnTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.btnTimeFrom.setText(String.format("%02d:%02d", hourOfDay, minute));
                        //selectedHours.add(String.format("%02d:%02d", hourOfDay, minute));
                        desiredHours.get(position).setStartTime(String.format("%02d:%02d", hourOfDay, minute));
                        Log.e("HERE STARTTIME======>>>", desiredHours.toString());

                    }
                }, 10, 0, true);
                timePicker.show();
            }
        });

        holder.btnTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.btnTimeTo.setText(String.format("%02d:%02d", hourOfDay, minute));
                        //selectedHours.add(String.format("%02d:%02d", hourOfDay, minute));
                        desiredHours.get(position).setEndTime(String.format("%02d:%02d", hourOfDay, minute));
                        Log.e("HERE END TIME======>>>", desiredHours.toString());

                    }
                }, 20, 0, true);
                timePicker.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return desiredHours.size();
    }

    public void setDesiredHours(ArrayList<DesiredHoursInDay> desiredHours){
        this.desiredHours = desiredHours;
        notifyDataSetChanged();
    }
    public ArrayList<DesiredHoursInDay> getDesiredHours(){
        return this.desiredHours;
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
        }
    }
}
