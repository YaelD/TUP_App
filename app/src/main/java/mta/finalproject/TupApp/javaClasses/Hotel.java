package mta.finalproject.TupApp.javaClasses;


import android.os.Parcel;
import android.os.Parcelable;

public class Hotel {

    private String name;
    private String placeID;
    private Geometry geometry;


    public Hotel(String name, String placeID) {
        this.name = name;
        this.placeID = placeID;
    }


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    protected Hotel(Parcel in) {
        name = in.readString();
        placeID = in.readString();
    }

}
