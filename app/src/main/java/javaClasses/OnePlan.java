package javaClasses;


import java.time.LocalTime;

//change the class name

public class OnePlan {
    private Attraction attraction;
    private LocalTime startTime;
    private LocalTime finishTime;
    private Boolean isFavoriteAttraction = false;


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
