// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Model.User;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.Processing.CredentialsPHP;
import com.bruha.bruhaandroid.Processing.RetrieveMyPHP;
import com.bruha.bruhaandroid.Processing.RetrievePHP;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadScreenActivity extends Activity {
    //Initializing the class that contains the calls to the PHP Database.
    //  RetrievePHP retrievedInfo;
    // RetrieveMyPHP retrieveMyPHP;

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
            //       retrievedInfo = new RetrievePHP();
            //     retrieveMyPHP = new RetrieveMyPHP();

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
                //    ArrayList<Event> userEvents = retrieveMyPHP.getUserEventList(userinfo.get(0));
                //    ArrayList<Venue> userVenues = retrieveMyPHP.getUserVenueList(userinfo.get(0));
                //    ArrayList<Artist> userArtist = retrieveMyPHP.getUserArtistList(userinfo.get(0));
                //    ArrayList<Organizations> userOrg = retrieveMyPHP.getUserOrgList(userinfo.get(0));
                ArrayList<Event> userEvents = new ArrayList<>();
                ArrayList<Venue> userVenues = new ArrayList<>();
                ArrayList<Artist> userArtist = new ArrayList<>();
                ArrayList<Organizations> userOrg = new ArrayList<>();

                sqLiteUtils.insertUserEvents(dbHelper, userEvents);
                sqLiteUtils.insertUserVenues(dbHelper, userVenues);
                sqLiteUtils.insertUserArtist(dbHelper, userArtist);
                sqLiteUtils.insertUserOrganization(dbHelper, userOrg);

                //Retrieving the Addictions info.
                //   ArrayList<String> addictedEvents = retrieveMyPHP.getAddictedList(userinfo.get(0));
                // ArrayList<String> addictedVenues = retrieveMyPHP.getAddictedVenueList(userinfo.get(0));
                //  ArrayList<String> addictedArtists = retrieveMyPHP.getAddictedArtistList(userinfo.get(0));
                //  ArrayList<String> addictedOrganizations = retrieveMyPHP.getAddictedOrgList(userinfo.get(0));
                ArrayList<String> addictedEvents =  new ArrayList<>();
                ArrayList<String> addictedVenues =  new ArrayList<>();
                ArrayList<String> addictedArtists = new ArrayList<>();
                ArrayList<String> addictedOrganizations =  new ArrayList<>();
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

        // eventArrayList = retrievedInfo.getEventCategoryList();
        // venueArrayList = retrievedInfo.getVenueCategoryList();
        // artistArrayList = retrievedInfo.getArtistCategoryList();
        // organizationArrayList = retrievedInfo.getOrganizationCategoryList();

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

            // events = retrievedInfo.getEventList();
            Event x = new Event();
            x.setEventName("Party Event");
            //x.setEventName("Chutiyaap");
            x.setEventid(0+"");
            x.setEventDate("2016-10-21");
            x.setEventEndDate("2016-10-21");
            x.setEventStartTime("08:00");
            x.setEventEndTime("11:30");
            x.setEventDescription("HAIIIIII");
            x.setEventPrimaryCategory("Sports");
            x.setEventLocAdd("Phiti");
            x.setEventLocName("Hai");
            x.setEventPrice(10.00);
            x.setEventPicture("https://thump-images.vice.com/images/articles/meta/2015/07/22/veld-festival-2015-is-coming-and-so-are-the-after-parties-1437601122.jpg");
            x.setEventLocSt("85 ward");
            x.setEventLatitude(43.254436);
            x.setEventLongitude(-79.9246225);
            x.setVenueid(0+"");
            x.setOrganizationid(new ArrayList<String>());
            events.add(x);

            Event x1 = new Event();
            x1.setEventName("Sporting Event");
            //x1.setEventName("tattiyan");
            x1.setEventid(1+"");
            x1.setEventDate("2016-10-21");
            x1.setEventEndDate("2016-10-21");
            x1.setEventStartTime("08:00");
            x1.setEventEndTime("11:30");
            x1.setEventDescription("HAIIIIII");
            x1.setEventPrimaryCategory("Sports");
            x1.setEventLocAdd("Phiti");
            x1.setEventLocName("Hai");
            x1.setEventPicture("http://assets.lfcimages.com/v2/uploads/media/default/0001/07/thumb_6249_default_news_size_5.jpeg");
            x1.setEventLocSt("85 ward");
            x1.setEventLatitude(43.2565857);
            x1.setEventLongitude(-79.9227579);
            x1.setEventPrice(20.0);
            x1.setVenueid(1+"");
            x1.setOrganizationid(new ArrayList<String>());
            events.add(x1);

            Event x2 = new Event();
            x2.setEventName("Formal Event");
            //x2.setEventName("scene awnnn");
            x2.setEventid(2+"");
            x2.setEventDate("2016-10-21");
            x2.setEventEndDate("2016-10-21");
            x2.setEventStartTime("08:00");
            x2.setEventEndTime("11:30");
            x2.setEventDescription("HAIIIIII");
            x2.setEventPrimaryCategory("Sports");
            x2.setEventLocAdd("Phiti");
            x2.setEventLocName("Hai");
            x2.setEventPicture("http://www.dreameurotrip.com/wp-content/uploads/2014/04/stockholm-party-Sturecompagniet.jpg");
            x2.setEventLocSt("85 ward");
            x2.setEventLatitude(43.2556535);
            x2.setEventLongitude(-79.9209809);
            x2.setVenueid(2+"");
            x2.setOrganizationid(new ArrayList<String>());
            events.add(x2);

            Event x3 = new Event();
            x3.setEventName("Concert Event");
            //x3.setEventName("ghand me mara");
            x3.setEventid(3+"");
            x3.setEventDate("2016-10-21");
            x3.setEventEndDate("2016-10-21");
            x3.setEventStartTime("08:00");
            x3.setEventEndTime("11:30");
            x3.setEventDescription("HAIIIIII");
            x3.setEventPrimaryCategory("Sports");
            x3.setEventLocAdd("Phiti");
            x3.setEventLocName("Hai");
            x3.setEventPrice(53.24);
            x3.setEventPicture("http://www.dreameurotrip.com/wp-content/uploads/2014/04/amsterdam-party-1024x488.jpg");
            x3.setEventLocSt("85 ward");
            x3.setEventLatitude(43.2594733);
            x3.setEventLongitude(-79.9266316);
            x3.setVenueid(3+"");
            x3.setOrganizationid(new ArrayList<String>());
            events.add(x3);

            Event x4 = new Event();
            x4.setEventName("Social Event");
            //x4.setEventName("Jalde se");
            x4.setEventid(4+"");
            x4.setEventDate("2016-10-21");
            x4.setEventEndDate("2016-10-21");
            x4.setEventStartTime("08:00");
            x4.setEventEndTime("11:30");
            x4.setEventDescription("HAIIIIII");
            x4.setEventPrimaryCategory("Sports");
            x4.setEventLocAdd("Phiti");
            x4.setEventLocName("Hai");
            x4.setEventPicture("http://www.dreameurotrip.com/wp-content/uploads/2014/04/hula-hula-hvar-beach-party-clubbing-croatia.jpg");
            x4.setEventLocSt("85 ward");
            x4.setEventLatitude(43.2589223);
            x4.setEventLongitude(-79.9297044);
            x4.setVenueid(4+"");
            x4.setOrganizationid(new ArrayList<String>());
            events.add(x4);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //The call to get the list of Venue.
        ArrayList<Venue> venues= new ArrayList<>() ;
        try {
            Venue x = new Venue();
            x.setVenueName("Bean bar");
            x.setVenueId(0+"");
            x.setVenueDescription("Small local artists");
            x.setVenuePrimaryCategory("Music");
            x.setVenueLocation("Westdale");
            x.setLat(102.3);
            x.setLng(73.2);
            x.setVenueSt("Haa");
            x.setVenuePicture("http://urbanicity.ca/wp-content/uploads/2014/05/bean-bar-front-entrance.jpg");
            venues.add(x);

            Venue x1 = new Venue();
            x1.setVenueName("Ron Joyce");
            x1.setVenueId(1+"");
            x1.setVenueDescription("Football Stadium");
            x1.setVenuePrimaryCategory("Sports");
            x1.setVenueLocation("McMaster University");
            x1.setLat(85.4);
            x1.setLat(82.7);
            x1.setVenueSt("Ya");
            x1.setVenuePicture("http://pnimg.net/w/articles/0/569/53906f2f36.jpg");
            venues.add(x1);

            Venue x2 = new Venue();
            x2.setVenueName("Formal Event");
            x2.setVenueId(2+"");
            x2.setVenueDescription("HAIIIIII");
            x2.setVenuePrimaryCategory("Sports");
            x2.setVenueLocation("Phiti");
            x2.setLng(80.2);
            x2.setVenueSt("Ye");
            x2.setLat(50.5);
            x2.setVenuePicture("http://www.dreameurotrip.com/wp-content/uploads/2014/04/stockholm-party-Sturecompagniet.jpg");
            venues.add(x2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //The call to get the list of Organizations.
        ArrayList<Organizations> outfits= new ArrayList<>() ;
        try {
            Organizations x = new Organizations();
            x.setOrgName("Pakistani Student Association");
            x.setOrgId(0+"");
            x.setOrgDescription("Small local artists");
            x.setOrgPrimaryCategory("Theatre");
            x.setLocId(1);
            x.setOrgLocation("Westdale");
            x.setLat(24.8666853);
            x.setOrgSt("ya");
            x.setLng(66.9772329);
            x.setOrgPicture("http://www.uscpsa.com/uploads/2/6/0/8/26083121/7983101_orig.png");
            outfits.add(x);

            Organizations x1 = new Organizations();
            x1.setOrgName("Ron Joyce");
            x1.setOrgId(1+"");
            x1.setOrgDescription("Football Stadium");
            x1.setOrgPrimaryCategory("Sports");
            x1.setOrgLocation("McMaster University");
            x1.setLocId(2);
            x1.setOrgSt("yeah");
            x1.setLat(85.4);
            x1.setLat(82.7);
            x1.setOrgPicture("http://pnimg.net/w/articles/0/569/53906f2f36.jpg");
            outfits.add(x1);

            Organizations x2 = new Organizations();
            x2.setOrgName("Formal Event");
            x2.setOrgId(2+"");
            x2.setOrgDescription("HAIIIIII");
            x2.setOrgPrimaryCategory("Sports");
            x2.setOrgLocation("Phiti");
            x2.setLng(80.2);
            x2.setLat(50.5);
            x2.setOrgSt("yee");
            x2.setLocId(3);
            x2.setOrgPicture("http://www.dreameurotrip.com/wp-content/uploads/2014/04/stockholm-party-Sturecompagniet.jpg");
            outfits.add(x2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //The call to get the list of Artist.
        ArrayList<Artist> artists= new ArrayList<>() ;
        try {
            //  artists = retrievedInfo.getArtistList();
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