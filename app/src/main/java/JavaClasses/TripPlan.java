package JavaClasses;

import com.example.TupApp.MainActivity;

import java.util.ArrayList;

public class TripPlan {
    private int id;
    private String name;
    private ArrayList<DayPlan> plans = new ArrayList<>();

    public TripPlan(int id, String name, ArrayList<DayPlan> plans) {
        this.id = id;
        this.name = name;
        this.plans = plans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<DayPlan> getPlans() {
        return plans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TripPlan getStaticTripPlan()
    {
        ArrayList<DayPlan> plans = new ArrayList<>();

        ArrayList<OnePlan> onePlans = new ArrayList<>();
        onePlans.add(new OnePlan(new Attraction("London Eye"), "09:00", "11:00"));
        onePlans.add(new OnePlan(new Attraction("Big Ben"), "11:00", "12:00"));
        plans.add(new DayPlan(onePlans, "4.6.21"));
        ArrayList<OnePlan> twoPlans = new ArrayList<>();
        twoPlans.add(new OnePlan(new Attraction("Oxford Street"), "10:00", "13:00"));
        twoPlans.add(new OnePlan(new Attraction("Piccadilly Street"), "14:00", "16:00"));
        plans.add(new DayPlan(twoPlans, "5.6.21"));
        TripPlan trip = new TripPlan(48, "London Trip", plans);
        return trip;
    }




}
