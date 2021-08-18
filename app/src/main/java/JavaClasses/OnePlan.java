package JavaClasses;


import java.time.LocalTime;

//change the class name

public class OnePlan {
    Attraction attraction;
    LocalTime startTime;
    LocalTime finishTime;

    //TODO: delete startTimeStr, finishTimeStr when finish testing
    String startTimeStr;
    String finishTimeStr;


    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
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


    //----------------------------------------------------------------------------------

    public OnePlan(Attraction attraction, String startTimeStr, String finishTimeStr) {
        this.attraction = attraction;
        this.startTimeStr = startTimeStr;
        this.finishTimeStr = finishTimeStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public String getFinishTimeStr() {
        return finishTimeStr;
    }
}
