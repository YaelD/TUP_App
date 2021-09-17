package mta.finalproject.TupApp.tripCreation;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import mta.finalproject.TupApp.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mta.finalproject.TupApp.javaClasses.DesiredHoursInDay;

public class DesiredHoursRecViewAdapter extends RecyclerView.Adapter<DesiredHoursRecViewAdapter.ViewHolder> {

    private ArrayList<DesiredHoursInDay> desiredHours = new ArrayList<>();
    private Context context;

    public DesiredHoursRecViewAdapter(FragmentActivity context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_desired_hours, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull DesiredHoursRecViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.startTime = LocalTime.parse(desiredHours.get(position).getStartTime());
        holder.endTime = LocalTime.parse(desiredHours.get(position).getEndTime());
        holder.txtDate.setText(desiredHours.get(position).getDate() + ":");
        //Check if the Date is today, start from the current time
        if(desiredHours.get(position).getDate().toString().equals(LocalDate.now().toString()))
        {
            holder.startTime = LocalTime.of(LocalTime.now().getHour()+1, LocalTime.now().getMinute());
        }
        holder.btnTimeFrom.setText(holder.startTime.toString());
        holder.btnTimeTo.setText(holder.endTime.toString());
        holder.btnTimeFrom.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePicker = new TimePickerDialog(context, R.style.TimePickerDialogStyle,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.startTime = LocalTime.of(hourOfDay, minute);
                        if(LocalDate.now().equals(desiredHours.get(position).getDate()))
                        {
                            if(holder.startTime.isBefore(LocalTime.now()))
                            {
                                holder.startTime = LocalTime.of(LocalTime.now().getHour()+1, LocalTime.now().getMinute());
                            }
                        }
                        if(holder.startTime.equals(holder.endTime))
                        {
                            if(holder.startTime.equals(LocalTime.of(0,0)))
                            {
                                holder.endTime = LocalTime.of(holder.endTime.getHour()+1, holder.endTime.getMinute());
                            }
                            else
                            {
                                holder.startTime = LocalTime.of(holder.startTime.getHour()-1, holder.startTime.getMinute());
                            }
                        }
                        if(holder.startTime.isAfter(holder.endTime))
                        {
                            if(holder.startTime.getHour() == 23)
                            {
                                holder.endTime = LocalTime.of(23,59);
                            }
                            else
                            {
                                holder.endTime = LocalTime.of(holder.startTime.getHour()+1, holder.startTime.getMinute());
                            }
                        }

                        desiredHours.get(position).setStartTime(holder.startTime.toString());
                        desiredHours.get(position).setEndTime(holder.endTime.toString());
                        holder.btnTimeFrom.setText(holder.startTime.toString());
                        holder.btnTimeTo.setText(holder.endTime.toString());
                        Log.e("CreateTripHours==>", "Date:" + desiredHours.get(position).getDate() +
                                " Start=" + desiredHours.get(position).getStartTime() + " End=" +
                                desiredHours.get(position).getEndTime());
                    }
                }, holder.startTime.getHour(), holder.startTime.getMinute(), true);
                timePicker.show();
            }
        });

        holder.btnTimeTo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePicker = new TimePickerDialog(context, R.style.TimePickerDialogStyle,
                        new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.endTime = LocalTime.of(hourOfDay, minute);
                        if(holder.startTime.equals(holder.endTime))
                        {
                            if(holder.startTime.equals(LocalTime.of(0,0)))
                            {
                                holder.endTime = LocalTime.of(holder.startTime.getHour()+1,holder.startTime.getMinute());
                            }
                            else
                            {
                                holder.startTime = LocalTime.of(holder.startTime.getHour()-1, holder.startTime.getMinute());
                            }
                        }
                        if(holder.endTime.withNano(0).withSecond(0).equals(LocalTime.of(0,0,0,0)))
                        {
                            holder.endTime = LocalTime.of(23, 59);
                        }
                        if(holder.startTime.isAfter(holder.endTime))
                        {
                            holder.startTime = LocalTime.of(holder.endTime.getHour()-1,holder.endTime.getMinute());
                        }

                        desiredHours.get(position).setStartTime(holder.startTime.toString());
                        desiredHours.get(position).setEndTime(holder.endTime.toString());
                        holder.btnTimeFrom.setText(holder.startTime.toString());
                        holder.btnTimeTo.setText(holder.endTime.toString());
                        Log.e("CreateTripHours==>", "Date:" + desiredHours.get(position).getDate() +
                                " Start=" + desiredHours.get(position).getStartTime() + " End=" +
                                desiredHours.get(position).getEndTime());

                    }
                },
                        holder.endTime.getHour(), holder.endTime.getMinute(), true);
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


        private LocalTime startTime, endTime;

        @RequiresApi(api = Build.VERSION_CODES.O)
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtHyphen = itemView.findViewById(R.id.txtHyphen);
            btnTimeFrom = itemView.findViewById(R.id.btnTimeFrom);
            btnTimeTo = itemView.findViewById(R.id.btnTimeTo);

            startTime = LocalTime.of(10,0);
            endTime = LocalTime.of(20,0);

        }
    }



}
