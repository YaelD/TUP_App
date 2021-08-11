package JavaClasses;

import java.util.ArrayList;

public class TripDetails {
    String destination;
    String hotelID;
    ArrayList<String> mustSeenAttractionsID = new ArrayList<>();
    ArrayList<DesiredHoursInDay> hoursEveryDay = new ArrayList<>();


    public static TripDetails staticTrip()
    {
        TripDetails trip = new TripDetails();

        trip.destination = "london";
        trip.hotelID = "ChIJGeIsmrgEdkgRlsvY9Maa0A0";
        trip.mustSeenAttractionsID.add("ChIJ_R7u-6QcdkgR_TvWQJZsm3k");
        trip.mustSeenAttractionsID.add("ChIJ2_19mdYEdkgRadLE5rfxLPU");
        trip.mustSeenAttractionsID.add("ChIJ7bDgv2IadkgRkIbzf3FdF5M");
        trip.mustSeenAttractionsID.add("ChIJG1YB2m4RdkgRsetv9D40NGY");
        trip.mustSeenAttractionsID.add("ChIJVbSVrt0EdkgRQH_FO4ZkHc0");
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


}
