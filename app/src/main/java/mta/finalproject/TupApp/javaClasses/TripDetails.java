package mta.finalproject.TupApp.javaClasses;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TripDetails {
    String destination;
    String hotelID;
    ArrayList<String> mustSeenAttractionsID = new ArrayList<>();
    ArrayList<DesiredHoursInDay> hoursEveryDay = new ArrayList<>();
    //====================================================================================//

    public TripDetails() {
    }
    //====================================================================================//

    public String getDestination() {
        return destination;
    }
    //====================================================================================//

    public void setDestination(String destination) {
        this.destination = destination;
    }
    //====================================================================================//

    public String getHotelID() {
        return hotelID;
    }
    //====================================================================================//

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
    //====================================================================================//

    public ArrayList<String> getMustSeenAttractionsID() {
        return mustSeenAttractionsID;
    }
    //====================================================================================//

    public void setMustSeenAttractionsID(ArrayList<String> mustSeenAttractionsID) {
        this.mustSeenAttractionsID = mustSeenAttractionsID;
    }
    //====================================================================================//

    public ArrayList<DesiredHoursInDay> getHoursEveryDay() {
        return hoursEveryDay;
    }
    //====================================================================================//

    public void setHoursEveryDay(ArrayList<DesiredHoursInDay> hoursEveryDay) {
        this.hoursEveryDay = hoursEveryDay;
    }
    //====================================================================================//

    @Override
    public String toString() {
        return "TripDetails{" +
                "destination='" + destination + '\'' +
                ", hotelID='" + hotelID + '\'' +
                ", mustSeenAttractionsID=" + mustSeenAttractionsID +
                ", hoursEveryDay=" + hoursEveryDay +
                '}';
    }
    //====================================================================================//

}
