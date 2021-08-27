package mta.finalproject.TupApp.javaClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Utility {


    private final static String SHARED_PREF_FILE_NAME = "TupPref";

    public final static String TRAVELER = "traveler";
    public final static String TRAVELER_ID = "travelerID";



    private static Utility instance;
    private Context context;
    private Traveler traveler;
    private String travelerID;
    private SharedPreferences sharedPreferences;


    private ArrayList<Integer> tripsToDelete = new ArrayList<>();
    private ArrayList<Attraction> tripSelectedAttractions = new ArrayList<>();
    private ArrayList<Attraction> favoriteAttractions = new ArrayList<>();
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Hotel> hotels = new ArrayList<>();
    private ArrayList<Hotel> testHotels = new ArrayList<>();
    private TripPlan lastCreatedTrip;
    private ArrayList<TripPlan> allTrips = new ArrayList<>();

    //private Traveler TestTraveler = new Traveler("Yael","Davidov","yaeldv@gmail.com", "1234");

    public void initTestHotels(){
        Hotel hotel1 = new Hotel("Matan", "1");
        Hotel hotel2 = new Hotel("Yael", "2");
        Hotel hotel3 = new Hotel("Maya", "3");
        testHotels.add(hotel1);
        testHotels.add(hotel2);
        testHotels.add(hotel3);
    }

    public ArrayList<Hotel> getTestHotels(){
        initTestHotels();
        return testHotels;
    }

    public void writeToSharedPreferences()
    {
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


    public void testAttractions()
    {
        String att1 = "{\"name\":\"The Mall\",\"address\":\"The Mall, London, UK\",\"phoneNumber\":\"N\\\\A\",\"website\":\"N\\\\A\",\"geometry\":{\"lat\":\"51.5045039\",\"lng\":\"-0.1342032\"},\"placeID\":\"ChIJ1SnFptAEdkgRCQABqtg50Cw\",\"types\":[\"route\"],\"OpeningHoursArr\":[{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"SUNDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]},{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"MONDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]},{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"TUESDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]},{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"WEDNESDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]},{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"THURSDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]},{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"FRIDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]},{\"isAllDayLongOpened\":true,\"isOpen\":true,\"day\":\"SATURDAY\",\"openingHours\":[\"01:00\"],\"closingHours\":[\"23:59\"]}],\"imageUrl\":\"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/commons\\/thumb\\/0\\/07\\/The_Coldstream_Guards_Troop_Their_Colour_MOD_45165212.jpg\\/300px-The_Coldstream_Guards_Troop_Their_Colour_MOD_45165212.jpg\",\"description\":\"The Mall () is a road in the City of Westminster, central London, between Buckingham Palace at its western end and Trafalgar Square via Admiralty Arch to the east.\\nNear the east end at Trafalgar Square and Whitehall it is met by Horse Guards Road and Spring Gardens where the Metropolitan Board of Works and London County Council were once based.\\nIt is closed to traffic on Saturdays, Sundays, public holidays and on ceremonial occasions.\\nHistory\\n\\nThe Mall began as a field for playing pall-mall.\\n\",\"duration\":3}";
        String att2 = "{\"name\":\"London Transport Museum\",\"address\":\"Covent Garden, London WC2E 7BB, UK\",\"phoneNumber\":\"N\\\\A\",\"website\":\"https:\\/\\/ltmuseum.co.uk\\/\",\"geometry\":{\"lat\":\"51.5119054\",\"lng\":\"-0.1215648\"},\"placeID\":\"ChIJ4bF21K8FdkgRDXc6FiSVAzE\",\"types\":[\"museum\",\"tourist_attraction\",\"cafe\",\"point_of_interest\",\"establishment\"],\"OpeningHoursArr\":[{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"SUNDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]},{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"MONDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]},{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"TUESDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]},{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"WEDNESDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]},{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"THURSDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]},{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"FRIDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]},{\"isAllDayLongOpened\":false,\"isOpen\":true,\"day\":\"SATURDAY\",\"openingHours\":[\"10:00\"],\"closingHours\":[\"18:00\"]}],\"imageUrl\":\"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/commons\\/thumb\\/d\\/d1\\/London_Transport_Museum_%2842206944281%29.jpg\\/300px-London_Transport_Museum_%2842206944281%29.jpg\",\"description\":\"The London Transport Museum (often abbreviated as the LTM) is a transport museum based in Covent Garden, London.\\nThe museum mainly hosts exhibits related to the heritage of London's transport, as well as  conserving and explaining the history of it.\\nThe majority of the museum's exhibits originated in the collections of London Transport, but, since the creation of Transport for London (TfL) in 2000, the remit of the museum has expanded to cover all aspects of transportation in the city.\\nThe museum operates from two sites within London.\\n\",\"duration\":3}";
        attractions.add(new Gson().fromJson(att1, Attraction.class));
        attractions.add(new Gson().fromJson(att2, Attraction.class));

    }



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
            return false;
        else
            favoriteAttractions.add(attraction);
        return true;
    }
//----------------------------------------------------------------------------------------

    public boolean removeAttractionFromFavoriteList(Attraction attraction){
        Log.e("HERE==>", "Check on Attraction " + attraction.getPlaceID());
        for(Attraction currAttraction: favoriteAttractions)
        {
            if(currAttraction.getPlaceID().equals(attraction.getPlaceID()))
            {

                favoriteAttractions.remove(currAttraction);
                Log.e("HERE==>", "Removed Attraction" + attraction.getPlaceID());
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
            //ServerConnection.getInstance(context).getAttractionsFromServer("london");
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
