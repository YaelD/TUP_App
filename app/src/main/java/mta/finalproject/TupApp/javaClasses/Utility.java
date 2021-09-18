package mta.finalproject.TupApp.javaClasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class Utility {


    private final static String SHARED_PREF_FILE_NAME = "TupPref";
    public final static String TRAVELER = "traveler";
    public final static String TRAVELER_ID = "travelerID";

    private static Utility instance;
    private Context context;
    private Traveler traveler;
    private String travelerID;
    private SharedPreferences sharedPreferences;
    private ArrayList<Activity> oldActivities = new ArrayList<>();
    private ArrayList<String> destinations = new ArrayList<>();
    private ArrayList<Integer> tripsToDelete = new ArrayList<>();
    private ArrayList<Attraction> tripSelectedAttractions = new ArrayList<>();
    private ArrayList<Attraction> favoriteAttractions = new ArrayList<>();
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Hotel> hotels = new ArrayList<>();
    private TripPlan lastCreatedTrip;
    private ArrayList<TripPlan> allTrips = new ArrayList<>();
    private ArrayList<String> favAttractionsToDelete = new ArrayList<>();
    private ArrayList<String> favAttractionsToAdd = new ArrayList<>();





    //====================================================================================//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDataFromServer() {
        ServerConnection.getInstance(context).getDestinationsFromServer();
        ServerConnection.getInstance(context).getAttractionsFromServer("london");
        ServerConnection.getInstance(context).getHotelsFromServer("london");
        ServerConnection.getInstance(context).getFavoritesFromServer();
        ServerConnection.getInstance(context).getMyTripsFromServer();

    }
    //====================================================================================//

    public void addActivity(Activity activity)
    {
        this.oldActivities.add(activity);
    }
    //====================================================================================//

    public void finishAllActivities() {
        for(Activity activity: oldActivities)
        {
            activity.finish();
        }
        oldActivities.clear();
    }
    //====================================================================================//

    public ArrayList<String> getDestinations() {
        return destinations;
    }
    //====================================================================================//

    public Hotel FindHotelByName(String hotelName) {
        for(Hotel hotel: this.hotels)
        {
            if(hotel.getName().equals(hotelName))
            {
                return hotel;
            }
        }
        return null;
    }
    //====================================================================================//

    public ArrayList<Integer> getTripsToDelete() {
        return tripsToDelete;
    }
    //====================================================================================//

    public void setTripsToDelete(ArrayList<Integer> tripsToDelete) {
        this.tripsToDelete = tripsToDelete;
    }
    //====================================================================================//

    public ArrayList<String> getFavAttractionsToDelete() {
        return favAttractionsToDelete;
    }
    //====================================================================================//

    public ArrayList<String> getFavAttractionsToAdd() {
        return favAttractionsToAdd;
    }
    //====================================================================================//

    public void writeToSharedPreferences() {
        clearSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TRAVELER_ID, travelerID);
        String travelerJson = new Gson().toJson(this.getTraveler());
        Log.e("HERE==>", "Traveler is about to write:" + travelerJson);
        editor.putString(TRAVELER, travelerJson);
        editor.commit();
    }
    //====================================================================================//

    public void clearSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    //====================================================================================//

    public static void sendDataToServer() {
        if(instance.getTripsToDelete() != null && !instance.getTripsToDelete().isEmpty())
        {
            ServerConnection.getInstance(instance.context).deleteTripFromServer();
        }
        if(instance.getFavAttractionsToDelete() != null && !instance.getFavAttractionsToDelete().isEmpty())
        {
            ServerConnection.getInstance(instance.context).sendFavAttractionsToDelete();
        }
        if(instance.getFavAttractionsToAdd() != null &&!instance.getFavAttractionsToAdd().isEmpty())
        {
            ServerConnection.getInstance(instance.context).sendFavAttractionsToAdd();
        }

    }
    //====================================================================================//

    public static void logOut() {
        sendDataToServer();
        instance.clearSharedPreferences();
        instance = null;
    }
    //====================================================================================//

    public boolean deleteTrip(int id) {
        for(TripPlan trip: allTrips)
        {
            if(trip.getTripId() == id)
            {
                tripsToDelete.add(id);
                return allTrips.remove(trip);
            }
        }
        return false;
    }
    //====================================================================================//

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
    //====================================================================================//

    public String getTravelerID() {
        return travelerID;
    }
    //====================================================================================//

    public void setTravelerID(String travelerID) {
        this.travelerID = travelerID;
    }
    //====================================================================================//

    public ArrayList<TripPlan> getAllTrips() {
        return allTrips;
    }
    //====================================================================================//

    public void setAllTrips(ArrayList<TripPlan> allTrips) {
        instance.allTrips = allTrips;
    }
    //====================================================================================//

    public Traveler getTraveler() {
        return traveler;
    }
    //====================================================================================//

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }
    //====================================================================================//

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }
    //====================================================================================//

    public TripPlan getLastCreatedTrip() {
        return lastCreatedTrip;
    }
    //====================================================================================//

    public void setLastCreatedTrip(TripPlan lastCreatedTrip) {
        this.lastCreatedTrip = lastCreatedTrip;
    }
    //====================================================================================//

    public Attraction getAttractionByID(String id) {
        Attraction selectedAttraction = null;
        for(Attraction currentAttraction: attractions)
        {
            if(currentAttraction.getPlaceID().equals(id))
            {
                selectedAttraction = currentAttraction;
            }
        }
        return selectedAttraction;
    }
    //====================================================================================//

    public boolean isAttractionIsInFavorites(String id){
        for(Attraction attraction: favoriteAttractions)
        {
            if(attraction.getPlaceID().equals(id))
            {
                return true;
            }
        }
        return false;
    }
    //====================================================================================//

    public ArrayList<Attraction> getFavoriteAttractions() {
        return favoriteAttractions;
    }
    //====================================================================================//

    public void setFavoriteAttractions(ArrayList<Attraction> favoriteAttractions) {
        this.favoriteAttractions = favoriteAttractions;
    }
    //====================================================================================//

    public boolean addAttractionToFavoriteList(Attraction attraction){
        if(favoriteAttractions.contains(attraction))
        {
            return false;
        }
        else
        {
            if(!favAttractionsToAdd.contains(attraction.getPlaceID()) &&
                    favAttractionsToDelete.contains(attraction.getPlaceID()))
            {
                favAttractionsToDelete.remove(attraction.getPlaceID());
            }
            else if(!favAttractionsToAdd.contains(attraction.getPlaceID()))
            {
                favAttractionsToAdd.add(attraction.getPlaceID());
            }
            favoriteAttractions.add(attraction);
            return true;
        }

    }
    //====================================================================================//

    public boolean removeAttractionFromFavoriteList(Attraction attraction){
        for(Attraction currAttraction: favoriteAttractions)
        {
            if(currAttraction.getPlaceID().equals(attraction.getPlaceID()))
            {
                //the attraction is from the server
                if(!favAttractionsToDelete.contains(currAttraction.getPlaceID()) &&
                    favAttractionsToAdd.contains(currAttraction.getPlaceID()))
                {
                    favAttractionsToAdd.remove(currAttraction.getPlaceID());
                }
                else if(!favAttractionsToDelete.contains(currAttraction.getPlaceID()))
                {
                    favAttractionsToDelete.add(currAttraction.getPlaceID());
                }
                favoriteAttractions.remove(currAttraction);
                return true;
            }
        }

        return false;
    }
    //====================================================================================//

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }
    //====================================================================================//

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }
    //====================================================================================//

    public ArrayList<Attraction> getTripSelectedAttractions() {
        return tripSelectedAttractions;
    }
    //====================================================================================//

    static public boolean isAttractionInArr(ArrayList<Attraction> attractionArr, Attraction attraction) {
        boolean res = false;
        for(Attraction currentAttraction: attractionArr)
        {
            if(currentAttraction.getPlaceID().equals(attraction.getPlaceID()))
            {
                res = true;
            }
        }
        return res;
    }
    //====================================================================================//

    public static synchronized Utility getInstance(Context context) {
        if(null == instance)
        {
            instance = new Utility(context);
        }
        return instance;
    }
    //====================================================================================//

    private Utility(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
    }
    //====================================================================================//

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
    //====================================================================================//

}
