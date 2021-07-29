package com.example.tupapp;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tupapp.Geometry;
import com.example.tupapp.DayOpeningHours;
import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Attraction {

    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private Geometry geometry;
    private String placeID;
    private String imageUrl;
//    private ArrayList<AttractionType> types = new ArrayList<>();
    private ArrayList<DayOpeningHours> OpeningHoursArr = new ArrayList<>();//
//    private int duration = 0;

    public Attraction(String name, String address, String phoneNumber, String website, /*Geometry geometry,*/ String placeID,
                      String imageUrl/*, ArrayList<AttractionType> types, ArrayList<DayOpeningHours> openingHoursArr*/) {
        this.setName(name);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setWebsite(website);
//        this.setPlaceID(placeID);
//        this.setTypes(types);
//        this.setDuration(types);

        //this.setGeometry(geometry);
        //this.setOpeningHoursArr(openingHoursArr);
        this.setImageUrl(imageUrl);
    }

    public Attraction(Attraction other)
    {
        this.setName(other.name);
        this.setAddress(other.address);
        this.setPhoneNumber(other.phoneNumber);
        this.setWebsite(other.website);
//        this.setPlaceID(other.placeID);
//        this.setDuration(other.getTypes());

        this.setGeometry(other.geometry);
//        this.setTypes(other.types);
        this.setOpeningHoursArr(other.OpeningHoursArr);
        this.setImageUrl(other.imageUrl);
    }


/*    public double calcDistanceBetweenAttractions(Attraction other)
    {
        double xLat = Double.parseDouble(this.geometry.getLat());
        double xLng = Double.parseDouble(this.geometry.getLng());
        double yLat = Double.parseDouble(other.geometry.getLat());
        double yLng = Double.parseDouble(other.geometry.getLng());

        xLat = Math.toRadians(xLat);
        xLng = Math.toRadians(xLng);
        yLat = Math.toRadians(yLat);
        yLng = Math.toRadians(yLng);

        double dlon = yLng - xLng;
        double dlat = yLat - xLat;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(xLat) * Math.cos(yLat)
                * Math.pow(Math.sin(dlon / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));
        // Radius of earth in kilometers
        double r = 6371;
        return (c*r);
    }*/

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Geometry getGeometry() {
        return geometry;
    }
 /*   public String getPlaceID() {
        return placeID;
    }*/
    public String getWebsite() {return website;}


    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }

    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {this.address = address;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setWebsite(String website) {this.website = website;}
    public void setGeometry(Geometry geometry) {this.geometry = geometry;}
//    public void setPlaceID(String placeID) {this.placeID = placeID;}
//    public void setTypes(ArrayList<AttractionType> types) {this.types = types;}
    public void setOpeningHoursArr(ArrayList<DayOpeningHours> openingHoursArr) {OpeningHoursArr = openingHoursArr;}


    public ArrayList<DayOpeningHours> getOpeningHoursArr() {
        return OpeningHoursArr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayOpeningHours getOpeningHoursByDay(DayOfWeek day){
        return OpeningHoursArr.get(day.getValue());
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", geometry=" + geometry +
                ", imageUrl='" + imageUrl + '\'' +
                ", OpeningHoursArr=" + OpeningHoursArr +
                '}';
    }

    /*    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", geometry=" + geometry +
                ", placeID='" + placeID + '\'' +
                ", types=" + types +
                ", OpeningHoursArr=" + OpeningHoursArr +
                ", duration=" + duration +
                '}';
    }*/
}
