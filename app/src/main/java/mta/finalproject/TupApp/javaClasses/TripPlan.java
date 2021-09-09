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

}
