package com.bruha.bruha.Views;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.PHP.RetrieveEvents;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

public class LoadScreenActivity extends Activity {

    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Organizations> mOutfits = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();
    RetrieveEvents EList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        EList = new RetrieveEvents();

       // this.deleteDatabase("BruhaDatabase.db");


        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,1);

        initialEventRetrieval(dbHelper);

       // ArrayList<Event> userEvents=Call.GetUserEventList(username);
     //   ArrayList<String> userInfo = dbHelper.GetUserInfo(username);


        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        ArrayList<String> userinfo= sqLiteUtils.getUserInfo(dbHelper);
        Log.v("Size",userinfo.size()+"");

        if(userinfo.size()==0) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }

        else{
            ArrayList<Event> userEvents=EList.GetUserEventList(userinfo.get(0));
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

        RetrieveEvents EList = new RetrieveEvents();
        ArrayList<Event> x= new ArrayList<>() ;
        try {
            x = EList.GetEventList();
        } catch (Exception e) {
            e.printStackTrace();
        }




        ArrayList<Venues> Ven= new ArrayList<>() ;
        try {
            Ven = EList.GetVenueList();
        } catch (Exception e) {
            e.printStackTrace();
        }





        ArrayList<Organizations> org= new ArrayList<>() ;
        try {
            org = EList.GetOrgList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Artists> artist= new ArrayList<>() ;
        try {
            artist = EList.GetArtistList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mArtists= artist;
        mOutfits = org;
        mVenues = Ven;
        mEvents = x;

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        sqLiteUtils.insertEvents(dbHelper, mEvents);
        sqLiteUtils.insertVenues(dbHelper, mVenues);
        sqLiteUtils.insertOutfits(dbHelper, mOutfits);
        sqLiteUtils.insertArtist(dbHelper, mArtists);

    }
}
