package com.bruha.bruha.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bruha.bruha.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
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
    public static final String USER_INFO_EMAIL = "email";
    public static final String USER_INFO_LOCATION = "location";

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

   // static Bitmap icon = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.car);
    public static final String EVENT_PICTUREE = "eventPicturee" ;

    public static final String EVENT_LATITUDE = "eventLatitude";
    public static final String EVENT_LONGITUDE = "eventLongitude";
    public static final String EVENT_PICTURE = "eventPicture";
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






    //OUTFITS INTO LOCAL DATABASE:
    public static final String TABLE_OUTFIT_INFO = "outfit_info";
    public static final String OUTFIT_LOCAL_ID = "_id";
    public static final String OUTFIT_REMOTE_ID = "outfitID";
    public static final String OUTFIT_DESCRIPTION = "outfitDescription";
    public static final String OUTFIT_NAME = "outfitName";
    public static final String OUTFIT_LATITUDE = "outfitLatitude";
    public static final String OUTFIT_LONGITUDE = "outfitLongitude";
    public static final String OUTFIT_LOCATION_NAME = "outfitLocName";


    //ARTIST INTO LOCAL DATABASE:
    public static final String TABLE_ARTIST_INFO = "artist_info";
    public static final String ARTIST_LOCAL_ID = "_id";
    public static final String ARTIST_REMOTE_ID = "artistID";
    public static final String ARTIST_DESCRIPTION = "artistDescription";
    public static final String ARTIST_NAME = "artistName";

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
            + USER_INFO_GENDER + " text not null, "
            + USER_INFO_EMAIL + " text not null, "
            + USER_INFO_LOCATION + " text not null);";

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
            + EVENT_PICTURE + " text not null, "
            + EVENT_PICTUREE + " text not null, "
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

    private static final String DATABASE_CREATE_OUTFIT_INFO = "create table "
            + TABLE_OUTFIT_INFO + "(" + OUTFIT_LOCAL_ID
            + " integer primary key autoincrement, "
            + OUTFIT_REMOTE_ID + " text not null, "
            + OUTFIT_DESCRIPTION + " text not null, "
            + OUTFIT_NAME + " text not null, "
            + OUTFIT_LATITUDE + " text not null, "
            + OUTFIT_LONGITUDE + " text not null, "
            + OUTFIT_LOCATION_NAME + " text not null); ";

    private static final String DATABASE_CREATE_ARTIST_INFO = "create table "
            + TABLE_ARTIST_INFO + "(" + ARTIST_LOCAL_ID
            + " integer primary key autoincrement, "
            + ARTIST_REMOTE_ID + " text not null, "
            + ARTIST_DESCRIPTION + " text not null, "
            + ARTIST_NAME + " text not null); ";





    //ARTIST LOCAL DATABASE SHIT

    public void addArtists( ArrayList<Artists> artist){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< artist.size();i++){

            values.put("artistID", artist.get(i).getArtistId());
            values.put("artistDescription", artist.get(i).getArtistDescription());
            values.put("artistName", artist.get(i).getArtistName());

            db.insert(TABLE_ARTIST_INFO, null, values);
        }
    }



    public ArrayList<Artists> retrieveArtistInfo(){

        ArrayList<Artists> mArtist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARTIST_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Artists artist = new Artists();

                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                artist.setArtistId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("artistID"))));
                artist.setArtistDescription(cursor.getString(cursor.getColumnIndex("artistDescription")));
                artist.setArtistName(cursor.getString(cursor.getColumnIndex("artistName")));

                mArtist.add(artist);
            }
        }

        cursor.close();

        return mArtist;
    }







    //OUTFIT LOCAL DATABASE SHIT

    public void addOutfits( ArrayList<Organizations> Org){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< Org.size();i++){

            values.put("outfitID", Org.get(i).getOrgId());
            values.put("outfitDescription", Org.get(i).getOrgDescription());
            values.put("outfitName", Org.get(i).getOrgName());
            values.put("outfitLatitude", Org.get(i).getLat());
            values.put("outfitLongitude", Org.get(i).getLng());
            values.put("outfitLocName", Org.get(i).getOrgLocation());

            db.insert(TABLE_OUTFIT_INFO, null, values);
        }
    }



    public ArrayList<Organizations> retrieveOutfitInfo(){

        ArrayList<Organizations> mOutfit = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OUTFIT_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Organizations Org = new Organizations();

                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                Org.setOrgId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("outfitID"))));
                Org.setOrgDescription(cursor.getString(cursor.getColumnIndex("outfitDescription")));
                Org.setOrgName(cursor.getString(cursor.getColumnIndex("outfitName")));
                Org.setLat(cursor.getDouble(cursor.getColumnIndex("outfitLatitude")));
                Org.setLng(cursor.getDouble(cursor.getColumnIndex("outfitLongitude")));
                Org.setOrgLocation(cursor.getString(cursor.getColumnIndex("outfitLocName")));

                mOutfit.add(Org);
            }
        }

        cursor.close();

        return mOutfit;
    }





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
        db.execSQL(DATABASE_CREATE_OUTFIT_INFO);
        db.execSQL(DATABASE_CREATE_ARTIST_INFO);

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
            values.put("eventPicture", events.get(i).getEventPicture());
            values.put("eventPicturee", getBytes(events.get(i).getEventPicturee()));

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

                newEvent.setEventPicture(cursor.getString(cursor.getColumnIndex("eventPicture")));
                newEvent.setEventLocName(cursor.getString(cursor.getColumnIndex("eventLocName")));
                newEvent.setEventLocSt(cursor.getString(cursor.getColumnIndex("eventLocStreet")));
                newEvent.setEventLocAdd(cursor.getString(cursor.getColumnIndex("eventLocAddress")));
                newEvent.setEventStartTime(cursor.getString(cursor.getColumnIndex("eventStartTime")));
                newEvent.setEventEndTime(cursor.getString(cursor.getColumnIndex("eventEndTime")));
                newEvent.setEventEndDate(cursor.getString(cursor.getColumnIndex("eventEndDate")));
                newEvent.setEventPicturee(getImage(cursor.getBlob(cursor.getColumnIndex("eventPicturee"))));

                mEvents.add(newEvent);
            }
        }

        cursor.close();

        return mEvents;
    }

    public void addUser( String username, String name, String birthdate, String gender,String email,String loc){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("name",name);
        values.put("birthdate", birthdate);
        values.put("gender", gender);
        values.put("email",email);
        values.put("location",loc);

        db.insert(TABLE_USER_INFO, null, values);
    }

    public ArrayList<String> retrieveUserInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> User=new ArrayList<>();

        Cursor cursor = db.query(TABLE_USER_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String DOB = cursor.getString(cursor.getColumnIndex("birthdate"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String location = cursor.getString(cursor.getColumnIndex("location"));


                User.add(username);
                User.add(name);
                User.add(DOB);
                User.add(gender);
                User.add(email);
                User.add(location);


                // use these strings as you want

            }
        }

        cursor.close();

        return User;
    }




        // convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }



}
