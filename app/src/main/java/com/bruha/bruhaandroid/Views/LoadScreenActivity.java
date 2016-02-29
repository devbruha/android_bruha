// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.Processing.RetrieveMyPHP;
import com.bruha.bruhaandroid.Processing.RetrievePHP;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadScreenActivity extends Activity {
    //Initializing the class that contains the calls to the PHP Database.
    RetrievePHP retrievedInfo;
    RetrieveMyPHP retrieveMyPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        //In the case that there is no internet connection.
        if(!isNetworkAvailable())
        {

            //Initializing the local database.
            SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
            SQLiteUtils sqLiteUtils = new SQLiteUtils();
            ArrayList<String> userinfo = sqLiteUtils.getUserInfo(dbHelper);

            //Checks to see if the user is already logged in, if not,go to splash screen. Else navigate to Dashboard.
            if (userinfo.size() == 0) {
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
            }
            else {
                MyApplication.loginCheck = true;
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
        }

        else {
            // Initializing the PHP classes.
            retrievedInfo = new RetrievePHP();
            retrieveMyPHP = new RetrieveMyPHP();

            //Initializing the local database.
            SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
            //Calling the method that removes selected tables off the database and creates new ones.
            dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 1);

            //Retrieving a few info. from the local database to be used later.
            initialCategoryRetrieval(dbHelper);
            initialEventRetrieval(dbHelper);

            SQLiteUtils sqLiteUtils = new SQLiteUtils();
            ArrayList<String> userinfo = sqLiteUtils.getUserInfo(dbHelper);

            //Checks to see if the user is already logged in, if not,go to splash screen. Else navigate to Dashboard.
            if (userinfo.size() == 0) {
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
            } else {
                MyApplication.userName = userinfo.get(0);   //Letting the app know who the user is.

                //Retrieving the List of Events/Venues/Artists/Organizations.
                ArrayList<Event> userEvents = retrieveMyPHP.getUserEventList(userinfo.get(0));
                ArrayList<Venue> userVenues = retrieveMyPHP.getUserVenueList(userinfo.get(0));
                ArrayList<Artist> userArtist = retrieveMyPHP.getUserArtistList(userinfo.get(0));
                ArrayList<Organizations> userOrg = retrieveMyPHP.getUserOrgList(userinfo.get(0));
                sqLiteUtils.insertUserEvents(dbHelper, userEvents);
                sqLiteUtils.insertUserVenues(dbHelper, userVenues);
                sqLiteUtils.insertUserArtist(dbHelper, userArtist);
                sqLiteUtils.insertUserOrganization(dbHelper, userOrg);

                //Retrieving the Addictions info.
                ArrayList<String> addictedEvents = retrieveMyPHP.getAddictedList(userinfo.get(0));
                ArrayList<String> addictedVenues = retrieveMyPHP.getAddictedVenueList(userinfo.get(0));
                ArrayList<String> addictedArtists = retrieveMyPHP.getAddictedArtistList(userinfo.get(0));
                ArrayList<String> addictedOrganizations = retrieveMyPHP.getAddictedOrgList(userinfo.get(0));
                sqLiteUtils.insertEventAddictions(dbHelper, addictedEvents);
                sqLiteUtils.insertVenueAddictions(dbHelper, addictedVenues);
                sqLiteUtils.insertArtistAddictions(dbHelper, addictedArtists);
                sqLiteUtils.insertOrgAddictions(dbHelper,addictedOrganizations);

                //Navigating to dashboard and letting the app know we are logged in.
                MyApplication.loginCheck = true;
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
        }
    }

    private void initialCategoryRetrieval(SQLiteDatabaseModel dbHelper){
        //Retrieving the category list.
        HashMap<String,ArrayList<ArrayList<String>>> eventArrayList = new HashMap<>();
        ArrayList<String> venueArrayList = new ArrayList<>();
        ArrayList<String> artistArrayList = new ArrayList<>();
        ArrayList<String> organizationArrayList = new ArrayList<>();

        eventArrayList = retrievedInfo.getEventCategoryList();
        venueArrayList = retrievedInfo.getVenueCategoryList();
        artistArrayList = retrievedInfo.getArtistCategoryList();
        organizationArrayList = retrievedInfo.getOrganizationCategoryList();

        //Setting the local database.
        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        sqLiteUtils.insertEventCategories(dbHelper, eventArrayList);
        sqLiteUtils.insertVenueCategories(dbHelper, venueArrayList);
        sqLiteUtils.insertArtistCategories(dbHelper, artistArrayList);
        sqLiteUtils.insertOrganizationCategories(dbHelper, organizationArrayList);
    }

    private void initialEventRetrieval(SQLiteDatabaseModel dbHelper) {

        // Create the local DB object

        //The call to get the list of mEvents.
        ArrayList<Event> events = new ArrayList<>() ;
        try {
            events = retrievedInfo.getEventList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //The call to get the list of Venue.
        ArrayList<Venue> venues= new ArrayList<>() ;
        try {
            venues = retrievedInfo.getVenueList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //The call to get the list of Organizations.
        ArrayList<Organizations> outfits= new ArrayList<>() ;
        try {
            outfits = retrievedInfo.getOrgList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //The call to get the list of Artist.
        ArrayList<Artist> artists= new ArrayList<>() ;
        try {
            artists = retrievedInfo.getArtistList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // BubbleSorting events by starting dates.
        Event tempevent = new Event() ;
        for( int i = events.size() - 1 ; i > 0 ; --i ){
            for ( int j = 0 ; j < i ;++j ) {
                int retval = events.get(j+1).getEventDate().compareTo(events.get(j).getEventDate());
                if ( retval < 0 ){
                    tempevent = events.get(j);
                    events.set(j,events.get(j+1));
                    events.set(j+1,tempevent);
                }

            }
        }

        //Setting the local database with the information retrieved.
        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        sqLiteUtils.insertEvents(dbHelper, events);
        sqLiteUtils.insertVenues(dbHelper, venues);
        sqLiteUtils.insertOrganizations(dbHelper, outfits);
        sqLiteUtils.insertArtist(dbHelper, artists);
    }

    private boolean isNetworkAvailable() {
        // A function used to check whether users are connected to the internet
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // boolean variable initialized to false, set true if there is a connection

        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){

            isAvailable = true;
        }
        return isAvailable;
    }
}