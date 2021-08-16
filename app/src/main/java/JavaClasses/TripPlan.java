package JavaClasses;

import java.util.ArrayList;

public class TripPlan {
    private String name;
    private ArrayList<DayPlan> plans = new ArrayList<>();

    public TripPlan(String name, ArrayList<DayPlan> plans) {
        this.name = name;
        this.plans = plans;
    }


}
