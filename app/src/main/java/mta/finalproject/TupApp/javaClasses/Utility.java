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

    private Activity oldActivity = null;

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


    //private Traveler TestTraveler = new Traveler("Yael","Davidov","yaeldv@gmail.com", "1234");



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDataFromServer()
    {
        ServerConnection.getInstance(context).getDestinationsFromServer();
        ServerConnection.getInstance(context).getAttractionsFromServer("london");
        ServerConnection.getInstance(context).getHotelsFromServer("london");
        ServerConnection.getInstance(context).getFavoritesFromServer();
        ServerConnection.getInstance(context).getMyTripsFromServer();

    }

    public ArrayList<String> getDestinations() {
        return destinations;
    }

    public void finishOldActivity() {
        if(oldActivity != null)
        {
            oldActivity.finish();
        }
        oldActivity = null;
    }


    public void setOldActivity(Activity oldActivity) {
        this.oldActivity = oldActivity;
    }


    public Hotel FindHotelByName(String hotelName)
    {
        for(Hotel hotel: this.hotels)
        {
            if(hotel.getName().equals(hotelName))
            {
                return hotel;
            }
        }
        return null;
    }

    public ArrayList<Integer> getTripsToDelete() {
        return tripsToDelete;
    }

    public void setTripsToDelete(ArrayList<Integer> tripsToDelete) {
        this.tripsToDelete = tripsToDelete;
    }

    public void addAttractionToDelete(String placeID)
    {
        if(!favAttractionsToDelete.contains(placeID))
        {
            favAttractionsToDelete.add(placeID);
            if(favAttractionsToAdd.contains(placeID))
            {
                favAttractionsToAdd.remove(placeID);
            }
        }
    }

    public void addAttractionToAdd(String placeID)
    {
        if(!favAttractionsToAdd.contains(placeID))
        {
            favAttractionsToAdd.add(placeID);
            if(favAttractionsToDelete.contains(placeID))
            {
                favAttractionsToDelete.remove(placeID);
            }
        }
    }

    public ArrayList<String> getFavAttractionsToDelete() {
        return favAttractionsToDelete;
    }

    public ArrayList<String> getFavAttractionsToAdd() {
        return favAttractionsToAdd;
    }


    public void writeToSharedPreferences()
    {
        clearSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TRAVELER_ID, travelerID);
        String travelerJson = new Gson().toJson(this.getTraveler());
        Log.e("HERE==>", "Traveler is about to write:" + travelerJson);
        editor.putString(TRAVELER, travelerJson);
        editor.commit();
    }


    public void clearSharedPreferences()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void logOut()
    {
        instance.clearSharedPreferences();
        instance = null;
    }



//----------------------------------------------------------------------------------------

    public boolean deleteTrip(int id)
    {
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
//----------------------------------------------------------------------------------------


    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public void addTrip(TripPlan trip)
    {
        allTrips.add(trip);
    }

//----------------------------------------------------------------------------------------

    public String getTravelerID() {
        return travelerID;
    }

//----------------------------------------------------------------------------------------

    public void setTravelerID(String travelerID) {
        this.travelerID = travelerID;
    }

//----------------------------------------------------------------------------------------

    public ArrayList<TripPlan> getAllTrips() {
        return allTrips;
    }

//----------------------------------------------------------------------------------------

    public void setAllTrips(ArrayList<TripPlan> allTrips) {
        instance.allTrips = allTrips;
    }

//----------------------------------------------------------------------------------------


    public Traveler getTraveler() {
        return traveler;
    }

//----------------------------------------------------------------------------------------

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }
//----------------------------------------------------------------------------------------

    public void setAttractions(ArrayList<Attraction> attractions) {
        instance.attractions = attractions;
    }


    //----------------------------------------------------------------------------------------

    public TripPlan getLastCreatedTrip() {
        return lastCreatedTrip;
    }

    public void setLastCreatedTrip(TripPlan lastCreatedTrip) {
        instance.lastCreatedTrip = lastCreatedTrip;
    }

//----------------------------------------------------------------------------------------

    public Attraction getAttractionByID(String id)
    {
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

    public boolean isAttractionIsInFavorites(String id)
    {
        for(Attraction attraction: favoriteAttractions)
        {
            if(attraction.getPlaceID().equals(id))
            {
                return true;
            }
        }
        return false;
    }

//----------------------------------------------------------------------------------------

    public ArrayList<Attraction> getFavoriteAttractions() {
        return favoriteAttractions;
    }
//----------------------------------------------------------------------------------------

    public void setFavoriteAttractions(ArrayList<Attraction> favoriteAttractions) {
        this.favoriteAttractions = favoriteAttractions;
    }
//----------------------------------------------------------------------------------------

    public boolean addAttractionToFavoriteList(Attraction attraction){
        if(favoriteAttractions.contains(attraction))
        {
            return false;
        }
        else
        {
            if(!favAttractionsToAdd.contains(attraction.getPlaceID()))
            {
                favAttractionsToAdd.add(attraction.getPlaceID());
            }
            if(favAttractionsToDelete.contains(attraction.getPlaceID()))
            {
                favAttractionsToDelete.remove(attraction.getPlaceID());
            }
            favoriteAttractions.add(attraction);
            return true;
        }

    }
//----------------------------------------------------------------------------------------

    public boolean removeAttractionFromFavoriteList(Attraction attraction){
        Log.e("HERE==>", "Check on Attraction " + attraction.getPlaceID());
        for(Attraction currAttraction: favoriteAttractions)
        {
            if(currAttraction.getPlaceID().equals(attraction.getPlaceID()))
            {
                if(!favAttractionsToDelete.contains(currAttraction.getPlaceID()))
                {
                    favAttractionsToDelete.add(currAttraction.getPlaceID());
                }
                if(favAttractionsToAdd.contains(currAttraction.getPlaceID()))
                {
                    favAttractionsToAdd.remove(currAttraction.getPlaceID());
                }
                favoriteAttractions.remove(currAttraction);
                return true;
            }
        }

        return false;
    }

//----------------------------------------------------------------------------------------

    public ArrayList<Hotel> getHotelsByDestination(String destination) {
        ServerConnection.getInstance(context).getHotelsFromServer(destination.toLowerCase().trim()+"_hotels");
        return hotels;
    }
//----------------------------------------------------------------------------------------

    public ArrayList<Attraction> getAttractions() {
        if(instance.attractions.size() == 0)
        {
            //ServerConnection.getInstance(context).getAttractionsFromServer("london");
        }
        return instance.attractions;
    }
//----------------------------------------------------------------------------------------

    public ArrayList<Hotel> getHotels() {
        if(hotels == null)
        {
            ServerConnection.getInstance(context).getHotelsFromServer("london_hotels");
        }
        return hotels;
    }
//----------------------------------------------------------------------------------------

    public ArrayList<Attraction> getTripSelectedAttrations() {
        return tripSelectedAttractions;
    }


    static public boolean isAttractionInArr(ArrayList<Attraction> attractionArr, Attraction attraction)
    {
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


     /*

        public void setTripSelectedAttrations(ArrayList<Attraction> tripSelectedAttrations) {
            this.tripSelectedAttractions = tripSelectedAttrations;
        }
      */



//----------------------------------------------------------------------------------------

    public static synchronized Utility getInstance(Context context)
    {
        if(null == instance)
        {
            instance = new Utility(context);
        }
        //instance.setTraveler(instance.TestTraveler);
        return instance;
    }

//----------------------------------------------------------------------------------------

    private Utility(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        //initData();
        //TODO: This is a test, delete it!
        //testAttractions();
    }




    //----------------------------------------------------------------------------------------

    /*


    public ArrayList<Attraction> searchAttractions (String text){
        if (null != attractions) {
            ArrayList<Attraction> searchedAttractions = new ArrayList<>();
            for (Attraction attraction: attractions) {
                if (attraction.getName().equalsIgnoreCase(text)) {
                    searchedAttractions.add(attraction);
                }

                String[] names = attraction.getName().split(" ");
                for (int i=0; i< names.length; i++) {
                    if(text.equalsIgnoreCase(names [i])){
                        boolean doesExist = false;

                        for (Attraction attr: searchedAttractions) {
                            if(attr.getPlaceID() == attraction.getPlaceID()) {
                                doesExist = true;
                            }
                        }
                        if(!doesExist) {
                            searchedAttractions.add(attraction);
                        }
                    }
                }
            }
            return searchedAttractions;
        }
        return null;
    }
     */


    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

}
