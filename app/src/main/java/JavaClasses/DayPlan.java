package JavaClasses;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class DayPlan {
    double durationDesireByUser;
    ArrayList<OnePlan> daySchedule = new ArrayList<>();
    LocalDate date;
    Attraction hotel;
    LocalTime startTime;
    LocalTime finishTime;
    int durationDay = 0;
}
