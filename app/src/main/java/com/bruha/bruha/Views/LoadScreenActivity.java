package com.bruha.bruha.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.RetrieveMyPHP;
import com.bruha.bruha.Processing.RetrievePHP;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

public class LoadScreenActivity extends Activity {

    RetrievePHP retrievedInfo; //Initialzing the class that contains the calls to the PHP Database.
    RetrieveMyPHP retrieveMyPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        if(!isNetworkAvailable())
        {
            Dialog();
        }

        else {

            retrievedInfo = new RetrievePHP(); // Initializing the class.
            retrieveMyPHP = new RetrieveMyPHP();

            retrievedInfo.getCategoryList();

            // this.deleteDatabase("BruhaDatabase.db");

            //Initializing the local database.
            SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

            //Calling the method that removes selected tables off the database and creates new ones.
            dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 1);

            initialEventRetrieval(dbHelper);

            // ArrayList<Event> userEvents=retrievePHP.getUserEventList(username);
            //   ArrayList<String> userInfo = dbHelper.getUserInfo(username);

            SQLiteUtils sqLiteUtils = new SQLiteUtils();
            ArrayList<String> userinfo = sqLiteUtils.getUserInfo(dbHelper);
            //Log.v("Size", userinfo.size() + "");

            //Checks to see if the user is already logged in, if not,go to splash screen. Else navigate to Dashboard.
            if (userinfo.size() == 0) {
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
            } else {
                ArrayList<Event> userEvents = retrieveMyPHP.getUserEventList(userinfo.get(0));
                ArrayList<Venue> userVenues = retrieveMyPHP.getUserVenueList(userinfo.get(0));
                ArrayList<Artist> userArtist = retrieveMyPHP.getUserArtistList(userinfo.get(0));
                ArrayList<Organizations> userOrg = retrieveMyPHP.getUserOrgList(userinfo.get(0));
                sqLiteUtils.insertUserEvents(dbHelper, userEvents);
                sqLiteUtils.insertUserVenues(dbHelper, userVenues);
                sqLiteUtils.insertUserArtist(dbHelper, userArtist);
                sqLiteUtils.insertUserOrganization(dbHelper, userOrg);


                MyApplication.userName = userinfo.get(0);

                //Addiction stuff
                ArrayList<String> addictedEvents = retrieveMyPHP.getAddictedList(userinfo.get(0));
                ArrayList<String> addictedVenues = retrieveMyPHP.getAddictedVenueList(userinfo.get(0));
                ArrayList<String> addictedArtists = retrieveMyPHP.getAddictedArtistList(userinfo.get(0));
                ArrayList<String> addictedOrganizations = retrieveMyPHP.getAddictedOrgList(userinfo.get(0));
                sqLiteUtils.insertEventAddictions(dbHelper, addictedEvents);
                sqLiteUtils.insertVenueAddictions(dbHelper, addictedVenues);
                sqLiteUtils.insertArtistAddictions(dbHelper, addictedArtists);
                sqLiteUtils.insertOrgAddictions(dbHelper,addictedOrganizations);

                MyApplication.loginCheck = true;
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
        }
    }

    private void initialEventRetrieval(SQLiteDatabaseModel dbHelper) {
        // Create the local DB object
        //SQLiteDatabaseModel dbHelper = ((MyApplication) getApplicationContext()).getDbHelper();
        //SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        //The call to get the list of mEvents.
        ArrayList<Event> events = new ArrayList<>() ;
        try {
            events = retrievedInfo.getEventList();
        } catch (Exception e) {
            e.printStackTrace();
        }

       // Log.v("HeyThisSize",events.size()+"");

        //The call to get the list of Venue.
        ArrayList<Venue> venues= new ArrayList<>() ;
        try {
            venues = retrievedInfo.getVenueList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //The call to get the list of Outfits.
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





        //Setting the local database.
        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        sqLiteUtils.insertEvents(dbHelper, events);
        sqLiteUtils.insertVenues(dbHelper, venues);
        sqLiteUtils.insertOrganizations(dbHelper, outfits);
        sqLiteUtils.insertArtist(dbHelper, artists);

    }

    // A function used to check whether users are connected to the internet
    private boolean isNetworkAvailable() {
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

    private void Dialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("You need an internet connection for this app!");
        builder.setCancelable(true);
        builder.setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
