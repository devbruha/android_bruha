package com.bruha.bruha.Views;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.PHP.RetrievePHP;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

public class LoadScreenActivity extends Activity {

    //The arrays that are used to store the information of mEvents/Venues/Outfits/Artists.
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Organizations> mOutfits = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();

    RetrievePHP reteievedInfo; //Initialzing the class that contains the calls to the PHP Database.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);



        reteievedInfo = new RetrievePHP(); // Initializing the class.

       // this.deleteDatabase("BruhaDatabase.db");

        //Initializing the local database.
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        //Calling the method that removes selected tables off the database and creates new ones.
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,1);

        initialEventRetrieval(dbHelper);

       // ArrayList<Event> userEvents=retrievePHP.getUserEventList(username);
     //   ArrayList<String> userInfo = dbHelper.getUserInfo(username);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        ArrayList<String> userinfo= sqLiteUtils.getUserInfo(dbHelper);
        Log.v("Size",userinfo.size()+"");

        //Checks to see if the user is already logged in, if not,go to splash screen. Else navigate to Dashboard.
        if(userinfo.size()==0) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
        else{
            ArrayList<Event> userEvents= reteievedInfo.getUserEventList(userinfo.get(0));
            sqLiteUtils.insertUserEvents(dbHelper,userEvents);
            MyApplication.loginCheck = true;
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    private void initialEventRetrieval(SQLiteDatabaseModel dbHelper) {
        // Create the local DB object
        //SQLiteDatabaseModel dbHelper = ((MyApplication) getApplicationContext()).getDbHelper();
        //SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        //The call to get the list of mEvents.
        ArrayList<Event> events = new ArrayList<>() ;
        try {
            events = reteievedInfo.getEventList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("HeyThisSize",events.size()+"");

        //The call to get the list of Venues.
        ArrayList<Venues> venues= new ArrayList<>() ;
        try {
            venues = reteievedInfo.getVenueList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //The call to get the list of Outfits.
        ArrayList<Organizations> outfits= new ArrayList<>() ;
        try {
            outfits = reteievedInfo.getOrgList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //The call to get the list of Artists.
        ArrayList<Artists> artists= new ArrayList<>() ;
        try {
            artists = reteievedInfo.getArtistList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Setting the arrays to hold the retrieved arrays, can remove this later on.
        mArtists= artists;
        mOutfits = outfits;
        mVenues = venues;
        mEvents = events;

        //Setting the local database.
        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        sqLiteUtils.insertEvents(dbHelper, mEvents);
        sqLiteUtils.insertVenues(dbHelper, mVenues);
        sqLiteUtils.insertOutfits(dbHelper, mOutfits);
        sqLiteUtils.insertArtist(dbHelper, mArtists);

    }
}
