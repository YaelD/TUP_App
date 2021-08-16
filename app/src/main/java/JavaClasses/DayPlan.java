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

    public double getDurationDesireByUser() {
        return durationDesireByUser;
    }

    public void setDurationDesireByUser(double durationDesireByUser) {
        this.durationDesireByUser = durationDesireByUser;
    }

    public ArrayList<OnePlan> getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(ArrayList<OnePlan> daySchedule) {
        this.daySchedule = daySchedule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Attraction getHotel() {
        return hotel;
    }

    public void setHotel(Attraction hotel) {
        this.hotel = hotel;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public int getDurationDay() {
        return durationDay;
    }

    public void setDurationDay(int durationDay) {
        this.durationDay = durationDay;
    }


    public static ArrayList<DayPlan> getStaticDayPlan()
    {
        ArrayList<DayPlan> dayPlans = new ArrayList<>();
        return dayPlans;
    }



}
