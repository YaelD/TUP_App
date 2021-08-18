package JavaClasses;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;






public class DayPlan {
    double durationDesireByUser;
    ArrayList<OnePlan> daySchedule = new ArrayList<>();
    LocalDate date;
    Attraction hotel;
    LocalTime startTime;
    LocalTime finishTime;
    int durationDay = 0;
    //TODO: delete dateStr when finish testing
    String dateStr;

    public DayPlan(double durationDesireByUser, ArrayList<OnePlan> daySchedule, LocalDate date,
                   Attraction hotel, LocalTime startTime, LocalTime finishTime, int durationDay) {
        this.durationDesireByUser = durationDesireByUser;
        this.daySchedule = daySchedule;
        this.date = date;
        this.hotel = hotel;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.durationDay = durationDay;
    }

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

    //---------------------------------------------------------------------
    public DayPlan(ArrayList<OnePlan> daySchedule, String dateStr) {
        this.daySchedule = daySchedule;
        this.dateStr = dateStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
