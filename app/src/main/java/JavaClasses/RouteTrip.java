package JavaClasses;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class RouteTrip {
    ArrayList<DayPlan> planForDays;
    int tripDuration;
    Destinations destination;    //an Enum that saves destinations(include Paris, London)
    Attraction hotel;
    LocalDate arrivingDate;
    LocalDate leavingDate;
    ArrayList<Attraction> mustSeenAttractions;
    static int tripId = 0;

}
