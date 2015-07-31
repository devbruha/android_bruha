package com.bruha.bruha.Processing;

import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteUtils {

    //-------------------------------------- EXPLORE ITEMS------------------------------------------

    public void insertEvents(SQLiteDatabaseModel dbHelper, ArrayList<Event> events){

        dbHelper.addEvents(events);
    }

    public ArrayList<Event> getEventInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveEventInfo();
    }

    public void insertVenues(SQLiteDatabaseModel dbHelper, ArrayList<Venue> ven){

        dbHelper.addVenues(ven);
    }

    public ArrayList<Venue> getVenuesInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveVenuesInfo();
    }

    public void insertArtist(SQLiteDatabaseModel dbHelper, ArrayList<Artist> artist){

        dbHelper.addArtists(artist);

    }

    public ArrayList<Artist> getArtistInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveArtistInfo();
    }

    public void insertOrganizations(SQLiteDatabaseModel dbHelper, ArrayList<Organizations> org){

        dbHelper.addOrganizations(org);
    }

    public ArrayList<Organizations> getOrganizationsInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveOrganizationInfo();
    }

    //--------------------------------------USER MANAGEMENT-----------------------------------------

    public void insertNewUser( SQLiteDatabaseModel dbHelper, List<String> user_info){
        // Attempting to insert these values into the local DB
        dbHelper.addUser(user_info.get(0), user_info.get(1), user_info.get(2), user_info.get(3), user_info.get(4), user_info.get(5));
    }

    public ArrayList<String> getUserInfo(SQLiteDatabaseModel dbHelper){

        return dbHelper.retrieveUserInfo();
    }

    //-------------------------------------ADDICTION ITEMS------------------------------------------

    public void insertEventAddictions(SQLiteDatabaseModel dbHelper, ArrayList<String> events){

        dbHelper.addEventAddiction(events);
    }

    public ArrayList<String> getEventAddictions(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveEventsAddictedInfo();
    }

    public void insertVenueAddictions(SQLiteDatabaseModel dbHelper, ArrayList<String> venueId){

        dbHelper.addVenueAddiction(venueId);
    }

    public ArrayList<String> getVenueAddictions(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveVenuesAddictedInfo();
    }

    public void insertArtistAddictions(SQLiteDatabaseModel dbHelper, ArrayList<String> artists){

        dbHelper.addArtistAddiction(artists);
    }

    public ArrayList<String> getArtistAddictions(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveArtistAddictedInfo();
    }

    public void insertOrgAddictions(SQLiteDatabaseModel dbHelper, ArrayList<String> org){

        dbHelper.addOrgAddiction(org);
    }

    public ArrayList<String> getOrgAddictions(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveOrgsAddictedInfo();
    }

    //------------------------------------USER UPLOAD ITEMS-----------------------------------------

    public void insertUserEvents(SQLiteDatabaseModel dbHelper, ArrayList<Event> events){

        dbHelper.addUserEvents(events);
    }

    public ArrayList<Event> getUserEventInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveUserEventInfo();
    }

}
