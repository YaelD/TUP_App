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

    public TripDetails() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public ArrayList<String> getMustSeenAttractionsID() {
        return mustSeenAttractionsID;
    }

    public void setMustSeenAttractionsID(ArrayList<String> mustSeenAttractionsID) {
        this.mustSeenAttractionsID = mustSeenAttractionsID;
    }

    public ArrayList<DesiredHoursInDay> getHoursEveryDay() {
        return hoursEveryDay;
    }

    public void setHoursEveryDay(ArrayList<DesiredHoursInDay> hoursEveryDay) {
        this.hoursEveryDay = hoursEveryDay;
    }

    public static TripDetails staticTrip()
    {
        TripDetails trip = new TripDetails();

        trip.destination = "london";
        trip.hotelID = "ChIJGeIsmrgEdkgRlsvY9Maa0A0";
        trip.mustSeenAttractionsID = new ArrayList<>();
        trip.mustSeenAttractionsID.add("ChIJ_R7u-6QcdkgR_TvWQJZsm3k");
        trip.mustSeenAttractionsID.add("ChIJ2_19mdYEdkgRadLE5rfxLPU");
        trip.mustSeenAttractionsID.add("ChIJ7bDgv2IadkgRkIbzf3FdF5M");
        trip.mustSeenAttractionsID.add("ChIJG1YB2m4RdkgRsetv9D40NGY");
        trip.mustSeenAttractionsID.add("ChIJVbSVrt0EdkgRQH_FO4ZkHc0");
        trip.hoursEveryDay = new ArrayList<>();
        trip.hoursEveryDay.add(new DesiredHoursInDay("2021-08-11"));
        trip.hoursEveryDay.get(0).setStartTime("10:00");
        trip.hoursEveryDay.get(0).setEndTime("20:00");
        trip.hoursEveryDay.add(new DesiredHoursInDay("2021-08-12"));
        trip.hoursEveryDay.get(1).setStartTime("10:00");
        trip.hoursEveryDay.get(1).setEndTime("18:00");
        trip.hoursEveryDay.add(new DesiredHoursInDay("2021-08-13"));
        trip.hoursEveryDay.get(2).setStartTime("15:00");
        trip.hoursEveryDay.get(2).setEndTime("20:00");

        return trip;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static TripDetails getTrip2()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new ServerConnection.LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class,new ServerConnection.LocalDateAdapter()).create();
        String str = "{\"destination\":\"london\",\"hotelID\":\"ChIJGeIsmrgEdkgRlsvY9Maa0A0\",\"mustSeenAttractionsID\":[\"ChIJ_R7u-6QcdkgR_TvWQJZsm3k\",\"ChIJ2_19mdYEdkgRadLE5rfxLPU\",\"ChIJ7bDgv2IadkgRkIbzf3FdF5M\",\"ChIJG1YB2m4RdkgRsetv9D40NGY\",\"ChIJVbSVrt0EdkgRQH_FO4ZkHc0\"],\"hoursEveryDay\":[{\"date\":\"2021-08-11\",\"startTime\":\"10:00\",\"endTime\":\"20:00\"},{\"date\":\"2021-08-12\",\"startTime\":\"10:00\",\"endTime\":\"18:00\"},{\"date\":\"2021-08-13\",\"startTime\":\"15:00\",\"endTime\":\"20:00\"}]}";
        return gson.fromJson(str, TripDetails.class);
    }


    @Override
    public String toString() {
        return "TripDetails{" +
                "destination='" + destination + '\'' +
                ", hotelID='" + hotelID + '\'' +
                ", mustSeenAttractionsID=" + mustSeenAttractionsID +
                ", hoursEveryDay=" + hoursEveryDay +
                '}';
    }
}
