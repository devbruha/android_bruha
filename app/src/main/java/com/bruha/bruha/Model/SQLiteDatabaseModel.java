package com.bruha.bruha.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteDatabaseModel extends SQLiteOpenHelper{

    // User Info Table Stuff

    public static final String TABLE_USER_INFO = "user_info";
    public static final String USER_INFO_ID = "_id";

    public static final String USER_INFO_USERNAME = "username";
    public static final String USER_INFO_NAME = "name";
    public static final String USER_INFO_BIRTHDATE = "birthdate";
    public static final String USER_INFO_GENDER = "gender";

    // Events Info Table Stuff

    public static final String TABLE_USER_EVENT_INFO = "user_event_info";

    public static final String TABLE_EVENT_INFO = "event_info";
    public static final String EVENT_LOCAL_ID = "_id";
    public static final String EVENT_REMOTE_ID = "eventID";
    public static final String EVENT_VENUE_ID = "venueID";
    public static final String EVENT_ORGANIZATION_ID = "orgID";
    public static final String EVENT_LOCATION_ID = "locID";
    public static final String EVENT_DESCRIPTION = "eventDescription";

    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_ICON = "eventIcon";
    public static final String EVENT_DATE = "eventDate";
    public static final String EVENT_PRICE = "eventPrice";
    public static final String EVENT_DISTANCE = "eventDistance";

    public static final String EVENT_LATITUDE = "eventLatitude";
    public static final String EVENT_LONGITUDE = "eventLongitude";

    public static final String EVENT_LOCATION_NAME = "eventLocName";
    public static final String EVENT_LOCATION_STREET = "eventLocStreet";
    public static final String EVENT_LOCATION_ADDRESS = "eventLocAddress";
    public static final String EVENT_START_TIME = "eventStartTime";
    public static final String EVENT_END_TIME = "eventEndTime";
    public static final String EVENT_END_DATE = "eventEndDate";



    // VENUES Info Table Stuff

   // public static final String TABLE_USER_VENUES_INFO = "user_venue_info";

    public static final String TABLE_VENUES_INFO = "venue_info";
    public static final String VENUES_LOCAL_ID = "_id";
    public static final String VENUES_REMOTE_ID = "venueID";
    public static final String VENUE_LOCATION_ID = "locID";
    public static final String VENUE_DESCRIPTION = "venueDescription";
    public static final String VENUE_NAME = "venueName";
    public static final String VENUE_LATITUDE = "venueLatitude";
    public static final String VENUE_LONGITUDE = "venueLongitude";
    public static final String VENUE_LOCATION_NAME = "venueLocName";
   // public static final String VENUE_LOCATION_STREET = "venueLocStreet";
    // public static final String VENUE_LOCATION_ADDRESS = "venueLocAddress";
    //public static final String VENUE_START_TIME = "eventStartTime";
   // public static final String VENUE_END_TIME = "eventEndTime";
   // public static final String VENUE_END_DATE = "eventEndDate";
    //  public static final String VENUE_ICON = "venueIcon";
    // public static final String VENUE_DATE = "eventDate";
    // public static final String VENUE_PRICE = "eventPrice";
    // public static final String VENUE_DISTANCE = "eventDistance"







    // Storing our local database name and version as strings

    private static final String DATABASE_NAME="BruhaDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // SQL to create DB

    private static final String DATABASE_CREATE_USER_INFO = "create table "
            + TABLE_USER_INFO + "(" + USER_INFO_ID
            + " integer primary key autoincrement, "
            + USER_INFO_USERNAME + " text not null, "
            + USER_INFO_NAME + " text not null, "
            + USER_INFO_BIRTHDATE + " text not null, "
            + USER_INFO_GENDER + " text not null);";

    private static final String DATABASE_CREATE_EVENT_INFO = "create table "
            + TABLE_EVENT_INFO + "(" + EVENT_LOCAL_ID
            + " integer primary key autoincrement, "
            + EVENT_REMOTE_ID + " text not null, "
            + EVENT_VENUE_ID + " text not null, "
            //+ EVENT_ORGANIZATION_ID + " text not null, "
            + EVENT_LOCATION_ID + " text not null, "
            + EVENT_DESCRIPTION + " text not null, "
            + EVENT_NAME + " text not null, "
            + EVENT_ICON + " text not null, "
            + EVENT_DATE + " text not null, "
            + EVENT_PRICE + " text not null, "
            + EVENT_DISTANCE + " text not null, "
            + EVENT_LATITUDE + " text not null, "
            + EVENT_LONGITUDE + " text not null, "
            + EVENT_LOCATION_NAME + " text not null, "
            + EVENT_LOCATION_STREET + " text not null, "
            + EVENT_LOCATION_ADDRESS + " text not null, "
            + EVENT_START_TIME + " text not null, "
            + EVENT_END_TIME + " text not null, "
            + EVENT_END_DATE + " text not null);";

    private static final String DATABASE_CREATE_USER_EVENT_INFO = "create table "
            + TABLE_USER_EVENT_INFO + "(" + EVENT_LOCAL_ID
            + " integer primary key autoincrement, "
            + EVENT_REMOTE_ID + " text not null, "
            + EVENT_VENUE_ID + " text not null, "
            //+ EVENT_ORGANIZATION_ID + " text not null, "
            + EVENT_LOCATION_ID + " text not null, "
            + EVENT_DESCRIPTION + " text not null, "
            + EVENT_NAME + " text not null, "
            + EVENT_ICON + " text not null, "
            + EVENT_DATE + " text not null, "
            + EVENT_PRICE + " text not null, "
            + EVENT_DISTANCE + " text not null, "
            + EVENT_LATITUDE + " text not null, "
            + EVENT_LONGITUDE + " text not null, "
            + EVENT_LOCATION_NAME + " text not null, "
            + EVENT_LOCATION_STREET + " text not null, "
            + EVENT_LOCATION_ADDRESS + " text not null, "
            + EVENT_START_TIME + " text not null, "
            + EVENT_END_TIME + " text not null, "
            + EVENT_END_DATE + " text not null);";


    private static final String DATABASE_CREATE_VENUE_INFO = "create table "
            + TABLE_VENUES_INFO + "(" + VENUES_LOCAL_ID
            + " integer primary key autoincrement, "
            + VENUES_REMOTE_ID + " text not null, "
            + VENUE_DESCRIPTION + " text not null, "
            + VENUE_NAME + " text not null, "
            + VENUE_LATITUDE + " text not null, "
            + VENUE_LONGITUDE + " text not null, "
            + VENUE_LOCATION_NAME + " text not null); ";





    //VENUES LOCAL DATABASE SHIT

    public void addVenues( ArrayList<Venues> venue){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< venue.size();i++){

            values.put("venueID", venue.get(i).getVenueId());
            values.put("venueDescription", venue.get(i).getVenueDescription());
            values.put("venueName", venue.get(i).getVenueName());
            values.put("venueLatitude", venue.get(i).getLat());
            values.put("venueLongitude", venue.get(i).getLng());
            values.put("venueLocName", venue.get(i).getVenueLocation());
            //values.put("eventIcon", venue.get(i).getEventIcon());
            // values.put("eventDate", venue.get(i).getEventDate());
            //values.put("eventPrice", venue.get(i).getEventPrice());
            //values.put("eventDistance", venue.get(i).getEventDistance());
            // values.put("locID", venue.get(i).getLocationID());
            //values.put("eventLocStreet", venue.get(i).getEventLocSt());
            //values.put("eventLocAddress", venue.get(i).getEventLocAdd());
            //values.put("eventStartTime", venue.get(i).getEventStartTime());
            //values.put("eventEndTime", venue.get(i).getEventEndTime());
            //values.put("eventEndDate", venue.get(i).getEventEndDate());

            db.insert(TABLE_VENUES_INFO, null, values);
        }
    }



    public ArrayList<Venues> retrieveVenuesInfo(){

        ArrayList<Venues> mVenues = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VENUES_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Venues newVen = new Venues();

                //int id = cursor.getInt(cursor.getColumnIndex("_id"));

                newVen.setVenueId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("venueID"))));
                newVen.setVenueDescription(cursor.getString(cursor.getColumnIndex("venueDescription")));
                newVen.setVenueName(cursor.getString(cursor.getColumnIndex("venueName")));
                newVen.setLat(cursor.getDouble(cursor.getColumnIndex("venueLatitude")));
                newVen.setLng(cursor.getDouble(cursor.getColumnIndex("venueLongitude")));
                newVen.setVenueLocation(cursor.getString(cursor.getColumnIndex("venueLocName")));

                mVenues.add(newVen);
            }
        }

        cursor.close();

        return mVenues;
    }











    //MyUpload, User's Event Info Pages.

    public void addUserEvents( ArrayList<Event> events){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< events.size();i++){

            values.put("eventID", events.get(i).getEventid());
            values.put("venueID", events.get(i).getVenueid());
            //values.put("orgID", events.get(i).getOrganizationid());
            values.put("locID", events.get(i).getLocationID());
            values.put("eventDescription", events.get(i).getEventDescription());

            values.put("eventName", events.get(i).getEventName());
            values.put("eventIcon", events.get(i).getEventIcon());
            values.put("eventDate", events.get(i).getEventDate());
            values.put("eventPrice", events.get(i).getEventPrice());
            values.put("eventDistance", events.get(i).getEventDistance());

            values.put("eventLatitude", events.get(i).getEventLatitude());
            values.put("eventLongitude", events.get(i).getEventLongitude());

            values.put("eventLocName", events.get(i).getEventLocName());
            values.put("eventLocStreet", events.get(i).getEventLocSt());
            values.put("eventLocAddress", events.get(i).getEventLocAdd());
            values.put("eventStartTime", events.get(i).getEventStartTime());
            values.put("eventEndTime", events.get(i).getEventEndTime());
            values.put("eventEndDate", events.get(i).getEventEndDate());

            db.insert(TABLE_USER_EVENT_INFO, null, values);
        }
    }


    public ArrayList<Event> retrieveUserEventInfo(){

        ArrayList<Event> mEvents = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_EVENT_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Event newEvent = new Event();

                //int id = cursor.getInt(cursor.getColumnIndex("_id"));

                newEvent.setEventid(cursor.getString(cursor.getColumnIndex("eventID")));
                newEvent.setVenueid(cursor.getString(cursor.getColumnIndex("venueID")));
                //String orgID = cursor.getString(cursor.getColumnIndex("orgID"));
                newEvent.setLocationID(cursor.getString(cursor.getColumnIndex("locID")));
                newEvent.setEventDescription(cursor.getString(cursor.getColumnIndex("eventDescription")));

                newEvent.setEventName(cursor.getString(cursor.getColumnIndex("eventName")));
                newEvent.setEventid(cursor.getString(cursor.getColumnIndex("eventName")));
                newEvent.setEventDate(cursor.getString(cursor.getColumnIndex("eventDate")));
                newEvent.setEventPrice(cursor.getDouble(cursor.getColumnIndex("eventPrice")));
                newEvent.setEventDistance(cursor.getDouble(cursor.getColumnIndex("eventDistance")));

                newEvent.setEventLatitude(cursor.getDouble(cursor.getColumnIndex("eventLatitude")));
                newEvent.setEventLongitude(cursor.getDouble(cursor.getColumnIndex("eventLongitude")));

                newEvent.setEventLocName(cursor.getString(cursor.getColumnIndex("eventLocName")));
                newEvent.setEventLocSt(cursor.getString(cursor.getColumnIndex("eventLocStreet")));
                newEvent.setEventLocAdd(cursor.getString(cursor.getColumnIndex("eventLocAddress")));
                newEvent.setEventStartTime(cursor.getString(cursor.getColumnIndex("eventStartTime")));
                newEvent.setEventEndTime(cursor.getString(cursor.getColumnIndex("eventEndTime")));
                newEvent.setEventEndDate(cursor.getString(cursor.getColumnIndex("eventEndDate")));

                mEvents.add(newEvent);
            }
        }

        cursor.close();

        return mEvents;
    }




    public SQLiteDatabaseModel(Context context) {

        // Creation of the DB

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        // Attempting to create a table named "user_info" and event info with 5 columns

        db.execSQL(DATABASE_CREATE_USER_INFO);
        db.execSQL(DATABASE_CREATE_EVENT_INFO);
        db.execSQL(DATABASE_CREATE_USER_EVENT_INFO);
        db.execSQL(DATABASE_CREATE_VENUE_INFO);

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS user_info");
        db.execSQL("DROP TABLE IF EXISTS event_info");

        onCreate(db);
    }


    //Event List being Implemented.

    public void addEvents( ArrayList<Event> events){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< events.size();i++){

            values.put("eventID", events.get(i).getEventid());
            values.put("venueID", events.get(i).getVenueid());
            //values.put("orgID", events.get(i).getOrganizationid());
            values.put("locID", events.get(i).getLocationID());
            values.put("eventDescription", events.get(i).getEventDescription());

            values.put("eventName", events.get(i).getEventName());
            values.put("eventIcon", events.get(i).getEventIcon());
            values.put("eventDate", events.get(i).getEventDate());
            values.put("eventPrice", events.get(i).getEventPrice());
            values.put("eventDistance", events.get(i).getEventDistance());

            values.put("eventLatitude", events.get(i).getEventLatitude());
            values.put("eventLongitude", events.get(i).getEventLongitude());

            values.put("eventLocName", events.get(i).getEventLocName());
            values.put("eventLocStreet", events.get(i).getEventLocSt());
            values.put("eventLocAddress", events.get(i).getEventLocAdd());
            values.put("eventStartTime", events.get(i).getEventStartTime());
            values.put("eventEndTime", events.get(i).getEventEndTime());
            values.put("eventEndDate", events.get(i).getEventEndDate());

            db.insert(TABLE_EVENT_INFO, null, values);
        }
    }

    public ArrayList<Event> retrieveEventInfo(){

        ArrayList<Event> mEvents = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Event newEvent = new Event();

                //int id = cursor.getInt(cursor.getColumnIndex("_id"));

                newEvent.setEventid(cursor.getString(cursor.getColumnIndex("eventID")));
                newEvent.setVenueid(cursor.getString(cursor.getColumnIndex("venueID")));
                //String orgID = cursor.getString(cursor.getColumnIndex("orgID"));
                newEvent.setLocationID(cursor.getString(cursor.getColumnIndex("locID")));
                newEvent.setEventDescription(cursor.getString(cursor.getColumnIndex("eventDescription")));

                newEvent.setEventName(cursor.getString(cursor.getColumnIndex("eventName")));
                newEvent.setEventid(cursor.getString(cursor.getColumnIndex("eventName")));
                newEvent.setEventDate(cursor.getString(cursor.getColumnIndex("eventDate")));
                newEvent.setEventPrice(cursor.getDouble(cursor.getColumnIndex("eventPrice")));
                newEvent.setEventDistance(cursor.getDouble(cursor.getColumnIndex("eventDistance")));

                newEvent.setEventLatitude(cursor.getDouble(cursor.getColumnIndex("eventLatitude")));
                newEvent.setEventLongitude(cursor.getDouble(cursor.getColumnIndex("eventLongitude")));

                newEvent.setEventLocName(cursor.getString(cursor.getColumnIndex("eventLocName")));
                newEvent.setEventLocSt(cursor.getString(cursor.getColumnIndex("eventLocStreet")));
                newEvent.setEventLocAdd(cursor.getString(cursor.getColumnIndex("eventLocAddress")));
                newEvent.setEventStartTime(cursor.getString(cursor.getColumnIndex("eventStartTime")));
                newEvent.setEventEndTime(cursor.getString(cursor.getColumnIndex("eventEndTime")));
                newEvent.setEventEndDate(cursor.getString(cursor.getColumnIndex("eventEndDate")));

                mEvents.add(newEvent);
            }
        }

        cursor.close();

        return mEvents;
    }

    public void addUser( String username, String name, String birthdate, String gender){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("name",name);
        values.put("birthdate", birthdate);
        values.put("gender", gender);

        db.insert(TABLE_USER_INFO, null, values);
    }

    public Cursor retrieveUserInfo(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String DOB = cursor.getString(cursor.getColumnIndex("birthdate"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));

                Log.v("Local DB TEST", id+"");
                Log.v("Local DB TEST", username);
                Log.v("Local DB TEST", name);
                Log.v("Local DB TEST", DOB);
                Log.v("Local DB TEST", gender);


                // use these strings as you want
            }
        }

        cursor.close();

        return cursor;
    }


}
