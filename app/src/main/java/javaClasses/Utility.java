package javaClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.example.TupApp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Utility {


    private static Utility instance;
    private Context context;
    private Traveler traveler;
    private String travelerID;
    private SharedPreferences sharedPreferences;


    private ArrayList<Integer> tripsToDelete = new ArrayList<>();
    private ArrayList<Attraction> tripSelectedAttractions = new ArrayList<>();
    private ArrayList<Attraction> favoriteAttractions = new ArrayList<>();
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Attraction> hotels = new ArrayList<>();
    private TripPlan lastCreatedTrip;
    private ArrayList<TripPlan> allTrips = new ArrayList<>();

    private Traveler TestTraveler = new Traveler("Yael","Davidov","yaeldv@gmail.com", "1234");


//----------------------------------------------------------------------------------------

    public boolean deleteTrip(int id)
    {
        for(TripPlan trip: allTrips)
        {
            if(trip.getTripID() == id)
            {
                tripsToDelete.add(id);
                return allTrips.remove(trip);
            }
        }
        return false;
    }
//----------------------------------------------------------------------------------------

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


    public void setHotels(ArrayList<Attraction> hotels) {
        instance.hotels = hotels;
    }

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

    public static void attTestFiller()
    {
        instance.attractions.add(new Attraction("London eye", "Riverside Building, County Hall, London SE1 7PB, United Kingdom",
                "+44 20 7967 8021", "https://www.londoneye.com/", "1",
                "https://media.cntraveler.com/photos/55c8be0bd36458796e4ca38a/master/pass/london-eye-2-cr-getty.jpg"));
        instance.attractions.get(0).setGeometry(new Geometry("0", "0"));
        instance.attractions.add(new Attraction("Buckingham Palace", "London SW1A 1AA, United Kingdom",
                "+44 303 123 7300", "https://www.royal.uk/royal-residences-buckingham-palace", "2",
                "https://zamanturkmenistan.com.tm/wp-content/uploads/2021/04/buckingham-palace-london.jpg"));
    }

//----------------------------------------------------------------------------------------

    //REMEMBER TO CHANGE TO attractions ArrayList;
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
            return false;
        else
            favoriteAttractions.add(attraction);
        return true;
    }
//----------------------------------------------------------------------------------------

    public boolean removeAttractionFromFavoriteList(Attraction attraction){
        if(favoriteAttractions.contains(attraction))
            favoriteAttractions.remove(attraction);
        else
            return false;
        return true;
    }

//----------------------------------------------------------------------------------------

    public ArrayList<Attraction> getHotelsByDestination(String destination) {
        ServerConnection.getInstance(context).getHotelsFromServer(destination.toLowerCase().trim()+"_hotels");
        return hotels;
    }
//----------------------------------------------------------------------------------------

    public ArrayList<Attraction> getAttractions() {
        if(instance.attractions.size() == 0)
        {
            ServerConnection.getInstance(context).getAttractionsFromServer("london");
        }
        return instance.attractions;
    }
//----------------------------------------------------------------------------------------

    public ArrayList<Attraction> getHotels() {
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
        instance.setTraveler(instance.TestTraveler);
        return instance;
    }

//----------------------------------------------------------------------------------------

    private Utility(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        initData();
    }

    public void saveData()
    {
        ServerConnection.getInstance(context).sendFavAttractions();
        ServerConnection.getInstance(context).updateUser(instance.getTraveler());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("travelerID", instance.getTravelerID());
        editor.putString("traveler", new Gson().toJson(instance.getTraveler()));
        editor.commit();
    }

    private void initData() {
        if(sharedPreferences.contains("travelerID"))
        {
            instance.setTravelerID(sharedPreferences.getString("travelerID", ""));
            String travelerJson = sharedPreferences.getString("traveler", "");
            Traveler traveler = new Gson().fromJson(travelerJson, Traveler.class);
            Utility.instance.setTraveler(traveler);
            ServerConnection.getInstance(context).getFavoritesFromServer();
            ServerConnection.getInstance(context).getAttractionsFromServer("london");
            ServerConnection.getInstance(context).getHotelsFromServer("london");
        }
    }
//----------------------------------------------------------------------------------------



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








}
