package JavaClasses;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;

import java.util.ArrayList;

public class Attraction implements Parcelable {

    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private Geometry geometry;
    private String placeID;
    private String imageUrl;
    private String description;
    private ArrayList<DayOpeningHours> OpeningHoursArr = new ArrayList<>();//

    public Attraction(String name) {
        this.name = name;
    }

    public Attraction(String name, String address, String phoneNumber, String website, /*Geometry geometry,*/ String placeID,
                      String imageUrl/*, ArrayList<AttractionType> types, ArrayList<DayOpeningHours> openingHoursArr*//*, String description*/) {
        this.setName(name);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setWebsite(website);
        this.setPlaceID(placeID);
        //this.setDescription(description);
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
        this.setPlaceID(other.placeID);
        //this.setGeometry(other.geometry);
        this.setOpeningHoursArr(other.OpeningHoursArr);
        this.setImageUrl(other.imageUrl);
        this.setDescription(other.description);
    }


    protected Attraction(Parcel in) {
        name = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        website = in.readString();
        placeID = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel in) {
            return new Attraction(in);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };

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
    public String getPlaceID() {
        return placeID;
    }
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
    public void setPlaceID(String placeID) {this.placeID = placeID;}
    public void setOpeningHoursArr(ArrayList<DayOpeningHours> openingHoursArr) {OpeningHoursArr = openingHoursArr;}


    public ArrayList<DayOpeningHours> getOpeningHoursArr() {
        return OpeningHoursArr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayOpeningHours getOpeningHoursByDay(DayOfWeek day){
        return OpeningHoursArr.get(day.getValue());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                //", geometry=" + geometry +
                ", placeID='" + placeID + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", OpeningHoursArr=" + OpeningHoursArr +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(website);
        dest.writeString(placeID);
        dest.writeString(imageUrl);
    }
}
