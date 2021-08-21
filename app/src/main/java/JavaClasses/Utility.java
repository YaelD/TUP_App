package JavaClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.TupApp.R;

import java.util.ArrayList;

public class Utility {


    private static Utility instance;
    private static RequestQueue queue;
    private static Context context;
    private static Traveler traveler;
    private static String travelerID;



    private static ArrayList<Attraction> tripSelectedAttractions = new ArrayList<>();
    private static ArrayList<Attraction> favoriteAttractions = new ArrayList<>();
    private static ArrayList<Attraction> attractions = new ArrayList<>();
    private static ArrayList<Attraction> hotels = new ArrayList<>();
    private static TripPlan lastCreatedTrip;
    private static ArrayList<TripPlan> allTrips = new ArrayList<>();

    private Traveler TestTraveler = new Traveler("Yael","Davidov","yaeldv@gmail.com", "1234");



    public boolean deleteTrip(int id)
    {
        for(TripPlan trip: allTrips)
        {
            if(trip.getId() == id)
            {
                return allTrips.remove(trip);
            }
        }
        return false;
    }

    public void addTrip(TripPlan trip)
    {
        allTrips.add(trip);
    }


    public String getTravelerID() {
        return travelerID;
    }

    public void setTravelerID(String travelerID) {
        this.travelerID = travelerID;
    }

    //----------------------------------------------------------------------------------------




    public ArrayList<TripPlan> getAllTrips() {
        return allTrips;
    }

    public void setAllTrips(ArrayList<TripPlan> allTrips) {
        Utility.allTrips = allTrips;
    }


    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }
//----------------------------------------------------------------------------------------


    public void setHotels(ArrayList<Attraction> hotels) {
        Utility.hotels = hotels;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        Utility.attractions = attractions;
    }


    //----------------------------------------------------------------------------------------

    public TripPlan getLastCreatedTrip() {
        return lastCreatedTrip;
    }

    public void setLastCreatedTrip(TripPlan lastCreatedTrip) {
        Utility.lastCreatedTrip = lastCreatedTrip;
    }

    //----------------------------------------------------------------------------------------

    public static void attTestFiller()
    {
        attractions.add(new Attraction("London eye", "Riverside Building, County Hall, London SE1 7PB, United Kingdom",
                "+44 20 7967 8021", "https://www.londoneye.com/", "1",
                "https://media.cntraveler.com/photos/55c8be0bd36458796e4ca38a/master/pass/london-eye-2-cr-getty.jpg"));
        attractions.get(0).setGeometry(new Geometry("0", "0"));
        attractions.add(new Attraction("Buckingham Palace", "London SW1A 1AA, United Kingdom",
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
        return instance;
    }

//----------------------------------------------------------------------------------------

    private Utility(Context context) {
        this.context = context;
        //SharedPreferencesReader();
        //Test function
        //attTestFiller();

    }
//----------------------------------------------------------------------------------------

    /*

    public void SharedPreferencesWriter()
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("emailAddress", traveler.getEmailAddress());
        editor.putString("password", traveler.getPassword());
        editor.putString("firstName", traveler.getFirstName());
        editor.putString("lastName", traveler.getLastName());
        editor.putString("travelerID", this.travelerID);
        editor.commit();

    }

    private void SharedPreferencesReader()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPreferences!= null)
        {
            this.setTravelerID(sharedPreferences.getString("travelerID", null));
            String firstName, lastName, password, email;
            firstName = sharedPreferences.getString("firstName", null);
            lastName = sharedPreferences.getString("lastName", null);
            password = sharedPreferences.getString("password", null);
            email = sharedPreferences.getString("emailAddress", null);
            instance.setTraveler(new Traveler(firstName, lastName, email, password));
        }

    }
     */


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
