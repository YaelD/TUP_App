package mta.finalproject.TupApp.javaClasses;


import java.time.LocalTime;

//change the class name

public class OnePlan {
    private Attraction attraction;
    private LocalTime startTime;
    private LocalTime finishTime;
    private Boolean isFavoriteAttraction = false;


    public OnePlan(Attraction attraction, LocalTime startTime, LocalTime finishTime) {
        this.attraction = attraction;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

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
}