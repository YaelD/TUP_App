package mta.finalproject.TupApp.javaClasses;

import java.util.ArrayList;

public class TripPlan {
    private String tripName;
    private ArrayList<DayPlan> plans = new ArrayList<>();
    private int tripId;
    private String destination;


    public TripPlan(String tripName, ArrayList<DayPlan> plans) {
        this.tripName = tripName;
        this.plans = plans;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ArrayList<DayPlan> getPlans() {
        return plans;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setPlans(ArrayList<DayPlan> plans) {
        this.plans = plans;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public static TripPlan getStaticTripPlan()
    {
        ArrayList<DayPlan> plans = new ArrayList<>();

        ArrayList<OnePlan> onePlans = new ArrayList<>();
        Attraction att = new Attraction("London Eye");
        att.setPlaceID("1");
        //onePlans.add(new OnePlan(new Attraction("London Eye"), "09:00", "11:00"));
        //onePlans.add(new OnePlan(att, "09:00", "11:00"));
        //onePlans.add(new OnePlan(new Attraction("Big Ben"), "12:00", "14:00"));
        //plans.add(new DayPlan(onePlans, "10.10.21"));
        ArrayList<OnePlan> twoPlans = new ArrayList<>();
        //twoPlans.add(new OnePlan(new Attraction("Buckingham Palace"), "10:00", "13:00"));
        //twoPlans.add(new OnePlan(new Attraction("Piccadilly Street"), "14:00", "16:00"));
        //plans.add(new DayPlan(twoPlans, "11.10.21"));
        TripPlan trip = new TripPlan( "London Trip", plans);
        return trip;
    }




}
