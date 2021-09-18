package mta.finalproject.TupApp.javaClasses;

public class Hotel {

    private String name;
    private String placeID;
    private Geometry geometry;

    //====================================================================================//

    public Hotel(String name, String placeID, Geometry geometry) {
        this.name = name;
        this.placeID = placeID;
        this.geometry = geometry;
    }
    //====================================================================================//


    public Geometry getGeometry() {
        return geometry;
    }
    //====================================================================================//

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    //====================================================================================//

    public String getName() {
        return name;
    }
    //====================================================================================//

    public void setName(String name) {
        this.name = name;
    }
    //====================================================================================//

    public String getPlaceID() {
        return placeID;
    }
    //====================================================================================//

}
