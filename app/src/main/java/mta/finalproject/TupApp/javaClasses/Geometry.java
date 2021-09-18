package mta.finalproject.TupApp.javaClasses;

import com.google.android.gms.maps.model.LatLng;

public class Geometry {
    private String lat;
    private String lng;
    //====================================================================================//

    public Geometry(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
    //====================================================================================//

    public String getLat() {
        return lat;
    }
    //====================================================================================//

    public String getLng() {
        return lng;
    }
    //====================================================================================//

    public void setLat(String lat) {
        this.lat = lat;
    }
    //====================================================================================//

    public void setLng(String lng) {
        this.lng = lng;
    }
    //====================================================================================//

    @Override
    public String toString() {
        return lat + ',' + lng;
    }
    //====================================================================================//

    public LatLng geometryToLatLng() {
        Double Lat = Double.valueOf(this.lat);
        Double Lng = Double.valueOf(this.lng);

        return new LatLng(Lat, Lng);
    }
    //====================================================================================//

}

