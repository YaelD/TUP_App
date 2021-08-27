package mta.finalproject.TupApp.javaClasses;


import android.os.Parcel;
import android.os.Parcelable;

public class Hotel implements Parcelable {

    private String name;
    private String placeID;


    public Hotel(String name, String placeID) {
        this.name = name;
        this.placeID = placeID;
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

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(placeID);
    }
}
