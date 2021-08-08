package com.example.tupapp;

public class DesiredHoursInDay {

    private String date;
    private String startTime;
    private String endTime;

    public DesiredHoursInDay(String date) {
        this.date = date;
        this.endTime = "20:00";
        this.startTime = "10:00";
    }


    @Override
    public String toString() {
        return "DesiredHoursInDay{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
