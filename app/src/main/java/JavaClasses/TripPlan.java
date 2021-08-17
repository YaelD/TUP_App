package JavaClasses;

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
}
