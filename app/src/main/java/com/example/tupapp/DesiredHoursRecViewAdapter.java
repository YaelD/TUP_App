package com.example.tupapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        private TextView txtDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
