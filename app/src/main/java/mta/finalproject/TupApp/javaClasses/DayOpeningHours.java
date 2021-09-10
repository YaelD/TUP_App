package mta.finalproject.TupApp.javaClasses;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class DayOpeningHours {

    private boolean isAllDayLongOpened;
    private boolean isOpen;
    private DayOfWeek day;
    private ArrayList<String> openingHours = new ArrayList<>();
    private ArrayList<String> closingHours = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayOpeningHours(boolean isOpen, int day, String openingHours, String closingHours) {
        this.isOpen = isOpen;
        this.day = DayOfWeek.of(day);
        this.addOpening(openingHours);
        this.addClosing(closingHours);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayOpeningHours(boolean isOpen, int day) {
        this.isOpen = isOpen;
        this.day = DayOfWeek.of(day);
    }

    public DayOfWeek getDay() {
        return day;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public ArrayList<String> getOpeningHours() {return openingHours;}


    public ArrayList<String> getClosingHours() {return closingHours;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayOpeningHours(int day) {
        //set the day(0=Sunday....6= Saturday]

        this.day = DayOfWeek.of(day);
        this.isOpen = false;
    }

    public void addOpening(String openingHour) {
        this.isOpen = true;
        this.openingHours.add(openingHour);
    }

    public void addClosing(String closingHour) {
        this.isOpen = true;
        this.closingHours.add(closingHour);
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.day +": ");
        if(isAllDayLongOpened) {
            stringBuilder.append("Open all day\n");
        }
        else {
            if(this.isOpen)
            {
                for (int i = 0; i < this.openingHours.size(); ++i)
                {
                    stringBuilder.append(openingHours.get(i) + "-" + closingHours.get(i) + "\n");
                }
            }
            else
            {
                stringBuilder.append("Closed\n");
            }
        }
        return stringBuilder.toString();
    }
}

