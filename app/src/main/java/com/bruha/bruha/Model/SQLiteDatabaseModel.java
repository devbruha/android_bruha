package com.bruha.bruha.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteDatabaseModel extends SQLiteOpenHelper{
    //Category declarations:
    // Event Category Lists
    public static final String TABLE_EVENT_PRIMARY = "event_primary_categories";
    public static final String TABLE_EVENT_CATEGORIES = "event_categories";
    public static final String RELATION_ID = "_id";
    public static final String PRIMARY_CATEGORY_NAME = "primary_category_name";
    public static final String SUB_CATEGORY_NAME = "sub_category_name";
    public static final String SUB_CATEGORY_ID = "sub_category_id";

    // Venue Category Lists
    public static final String TABLE_VENUE_CATEGORIES = "venue_categories";

    // Artist Category Lists
    public static final String TABLE_ARTIST_CATEGORIES = "artist_categories";

    // Organization Category Lists
    public static final String TABLE_ORGANIZATION_CATEGORIES = "organization_categories";

    // User Info Table Stuff
    public static final String TABLE_USER_INFO = "user_info";
    public static final String USER_INFO_ID = "_id";
    public static final String USER_INFO_USERNAME = "username";
    public static final String USER_INFO_NAME = "name";
    public static final String USER_INFO_BIRTHDATE = "birthdate";
    public static final String USER_INFO_GENDER = "gender";
    public static final String USER_INFO_EMAIL = "email";
    public static final String USER_INFO_LOCATION = "location";

    //Addictions:

    //Addictions Event
    public static final String TABLE_ADDICTIONS = "addictions";
    public static final String ADDICTIONS_ID = "_id";
    public static final String ADDICTIONS_EVENTID = "eventID";

    //Addictions Venue
    public static final String TABLE_ADDICTIONS_VENUES = "addictionsVenue";
    public static final String ADDICTIONS_VENUES_ID = "_id";
    public static final String ADDICTIONS_VENUES_VENUEID = "venueID";

    //Addictions Artist
    public static final String TABLE_ADDICTIONS_ARTISTS = "addictionsArtist";
    public static final String ADDICTIONS_ARTISTS_ID = "_id";
    public static final String ADDICTIONS_ARTISTS_ARTISTID = "artistID";

    //Addictions Org
    public static final String TABLE_ADDICTIONS_ORG = "addictionsOrg";
    public static final String ADDICTIONS_ORG_ID = "_id";
    public static final String ADDICTIONS_ORG_ORGID = "orgID";

    // Events Info Table Stuff
    public static final String TABLE_USER_EVENT_INFO = "user_event_info";
    public static final String TABLE_EVENT_INFO = "event_info";
    public static final String EVENT_LOCAL_ID = "_id";
    public static final String EVENT_REMOTE_ID = "eventID";
    public static final String EVENT_VENUE_ID = "venueID";
    public static final String EVENT_DESCRIPTION = "eventDescription";
    public static final String EVENT_PRIMARY_CATEGORY = "eventPrimaryCategory";
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_DATE = "eventDate";
    public static final String EVENT_PRICE = "eventPrice";
    public static final String EVENT_LATITUDE = "eventLatitude";
    public static final String EVENT_LONGITUDE = "eventLongitude";
    public static final String EVENT_PICTURE = "eventPicture";
    public static final String EVENT_LOCATION_NAME = "eventLocName";
    public static final String EVENT_LOCATION_STREET = "eventLocStreet";
    public static final String EVENT_LOCATION_ADDRESS = "eventLocAddress";
    public static final String EVENT_START_TIME = "eventStartTime";
    public static final String EVENT_END_TIME = "eventEndTime";
    public static final String EVENT_END_DATE = "eventEndDate";

    // Event SubCategory Table
    public static final String TABLE_EVENT_SUB_CATEGORY = "event_sub_categories";
    public static final String EVENT_SUB_CATEGORY = "eventSubCategory";
    public static final String EVENT_SUB_CATEGORY_ID = "eventSubCategoryID";

    // User Event SubCategory Table
    public static final String TABLE_USER_EVENT_SUB_CATEGORY = "user_event_sub_categories";

    // VENUES Info Table Stuff
    public static final String TABLE_USER_VENUES_INFO = "user_venue_info";
    public static final String TABLE_VENUES_INFO = "venue_info";
    public static final String VENUES_LOCAL_ID = "_id";
    public static final String VENUES_REMOTE_ID = "venueID";
    public static final String VENUE_STREET = "venueSt";
    public static final String VENUE_DESCRIPTION = "venueDescription";
    public static final String VENUE_PRIMARY_CATEGORY = "venuePrimaryCategory";
    public static final String VENUE_NAME = "venueName";
    public static final String VENUE_LATITUDE = "venueLatitude";
    public static final String VENUE_LONGITUDE = "venueLongitude";
    public static final String VENUE_LOCATION_NAME = "venueLocName";
    public static final String VENUE_PICTURE = "venuePicture";

    //OUTFITS INTO LOCAL DATABASE:
    public static final String TABLE_USER_OUTFIT_INFO = "user_outfit_info";
    public static final String TABLE_OUTFIT_INFO = "outfit_info";
    public static final String OUTFIT_LOCAL_ID = "_id";
    public static final String OUTFIT_REMOTE_ID = "outfitID";
    public static final String OUTFIT_DESCRIPTION = "outfitDescription";
    public static final String OUTFIT_PRIMARY_CATEGORY = "outfitPrimaryCategory";
    public static final String OUTFIT_NAME = "outfitName";
    public static final String OUTFIT_LATITUDE = "outfitLatitude";
    public static final String OUTFIT_LONGITUDE = "outfitLongitude";
    public static final String OUTFIT_LOCATION_NAME = "outfitLocName";
    public static final String OUTFIT_STREET = "outfitSt";
    public static final String OUTFIT_PICTURE = "outfitPicture";

    //ARTIST INTO LOCAL DATABASE:
    public static final String TABLE_USER_ARTIST_INFO = "user_artist_info";
    public static final String TABLE_ARTIST_INFO = "artist_info";
    public static final String ARTIST_LOCAL_ID = "_id";
    public static final String ARTIST_REMOTE_ID = "artistID";
    public static final String ARTIST_DESCRIPTION = "artistDescription";
    public static final String ARTIST_PRIMARY_CATEGORY = "artistPrimaryCategory";
    public static final String ARTIST_NAME = "artistName";
    public static final String ARTIST_PICTURE = "artistPicture";

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

    // SQL to create DB

    //Category list shit

    private static final String DATABASE_CREATE_EVENT_PRIMARY = "create table "
            + TABLE_EVENT_PRIMARY + "(" + RELATION_ID
            + " integer primary key autoincrement, "
            + PRIMARY_CATEGORY_NAME + " text not null);";

    private static final String DATABASE_CREATE_EVENT_CATEGORIES = "create table "
            + TABLE_EVENT_CATEGORIES + "(" + RELATION_ID
            + " integer primary key autoincrement, "
            + PRIMARY_CATEGORY_NAME + " text not null, "
            + SUB_CATEGORY_NAME + " text not null, "
            + SUB_CATEGORY_ID + " text not null);";

    private static final String DATABASE_CREATE_VENUE_CATEGORIES = "create table "
            + TABLE_VENUE_CATEGORIES + "(" + RELATION_ID
            + " integer primary key autoincrement, "
            + PRIMARY_CATEGORY_NAME + " text not null);";

    private static final String DATABASE_CREATE_ARTIST_CATEGORIES = "create table "
            + TABLE_ARTIST_CATEGORIES + "(" + RELATION_ID
            + " integer primary key autoincrement, "
            + PRIMARY_CATEGORY_NAME + " text not null);";

    private static final String DATABASE_CREATE_ORGANIZATION_CATEGORIES = "create table "
            + TABLE_ORGANIZATION_CATEGORIES + "(" + RELATION_ID
            + " integer primary key autoincrement, "
            + PRIMARY_CATEGORY_NAME + " text not null);";

    //Addictions shit
    private static final String DATABASE_CREATE_ADDICTIONS = "create table "
            + TABLE_ADDICTIONS + "(" + ADDICTIONS_ID
            + " integer primary key autoincrement, "
            + ADDICTIONS_EVENTID + " text not null);";

    private static final String DATABASE_CREATE_ADDICTIONS_VENUES = "create table "
            + TABLE_ADDICTIONS_VENUES + "(" + ADDICTIONS_VENUES_ID
            + " integer primary key autoincrement, "
            + ADDICTIONS_VENUES_VENUEID + " text not null);";

    private static final String DATABASE_CREATE_ADDICTIONS_ARTISTS = "create table "
            + TABLE_ADDICTIONS_ARTISTS + "(" + ADDICTIONS_ARTISTS_ID
            + " integer primary key autoincrement, "
            + ADDICTIONS_ARTISTS_ARTISTID + " text not null);";

    private static final String DATABASE_CREATE_ADDICTIONS_ORG = "create table "
            + TABLE_ADDICTIONS_ORG + "(" + ADDICTIONS_ORG_ID
            + " integer primary key autoincrement, "
            + ADDICTIONS_ORG_ORGID + " text not null);";

    private static final String DATABASE_CREATE_EVENT_INFO = "create table "
            + TABLE_EVENT_INFO + "(" + EVENT_LOCAL_ID
            + " integer primary key autoincrement, "
            + EVENT_REMOTE_ID + " text not null, "
            + EVENT_VENUE_ID + " text not null, "
            + EVENT_DESCRIPTION + " text not null, "
            + EVENT_NAME + " text not null, "
            + EVENT_PRIMARY_CATEGORY + " text not null, "
            + EVENT_DATE + " text not null, "
            + EVENT_PRICE + " text not null, "
            + EVENT_LATITUDE + " text not null, "
            + EVENT_LONGITUDE + " text not null, "
            + EVENT_LOCATION_NAME + " text not null, "
            + EVENT_LOCATION_STREET + " text not null, "
            + EVENT_LOCATION_ADDRESS + " text not null, "
            + EVENT_START_TIME + " text not null, "
            + EVENT_PICTURE + " text not null, "
            + EVENT_END_TIME + " text not null, "
            + EVENT_END_DATE + " text not null);";

    private static final String DATABASE_CREATE_EVENT_SUB_CATEGORY = "create table "
            + TABLE_EVENT_SUB_CATEGORY + "(" + EVENT_LOCAL_ID
            + " integer primary key autoincrement, "
            + EVENT_REMOTE_ID + " text not null, "
            + EVENT_SUB_CATEGORY_ID + " text not null, "
            + EVENT_SUB_CATEGORY + " text not null);";

    private static final String DATABASE_CREATE_USER_EVENT_INFO = "create table "
            + TABLE_USER_EVENT_INFO + "(" + EVENT_LOCAL_ID
            + " integer primary key autoincrement, "
            + EVENT_REMOTE_ID + " text not null, "
            + EVENT_VENUE_ID + " text not null, "
            + EVENT_DESCRIPTION + " text not null, "
            + EVENT_NAME + " text not null, "
            + EVENT_PRIMARY_CATEGORY + " text not null, "
            + EVENT_DATE + " text not null, "
            + EVENT_PRICE + " text not null, "
            + EVENT_LATITUDE + " text not null, "
            + EVENT_LONGITUDE + " text not null, "
            + EVENT_LOCATION_NAME + " text not null, "
            + EVENT_LOCATION_STREET + " text not null, "
            + EVENT_LOCATION_ADDRESS + " text not null, "
            + EVENT_START_TIME + " text not null, "
            + EVENT_END_TIME + " text not null, "
            + EVENT_PICTURE + " text not null, "
            + EVENT_END_DATE + " text not null);";

    private static final String DATABASE_CREATE_USER_EVENT_SUB_CATEGORY = "create table "
            + TABLE_USER_EVENT_SUB_CATEGORY + "(" + EVENT_LOCAL_ID
            + " integer primary key autoincrement, "
            + EVENT_REMOTE_ID + " text not null, "
            + EVENT_SUB_CATEGORY_ID + " text not null, "
            + EVENT_SUB_CATEGORY + " text not null);";

    private static final String DATABASE_CREATE_VENUE_INFO = "create table "
            + TABLE_VENUES_INFO + "(" + VENUES_LOCAL_ID
            + " integer primary key autoincrement, "
            + VENUES_REMOTE_ID + " text not null, "
            + VENUE_DESCRIPTION + " text not null, "
            + VENUE_PRIMARY_CATEGORY + " text not null, "
            + VENUE_NAME + " text not null, "
            + VENUE_LATITUDE + " text not null, "
            + VENUE_PICTURE + " text not null, "
            + VENUE_STREET + " text not null, "
            + VENUE_LONGITUDE + " text not null, "
            + VENUE_LOCATION_NAME + " text not null); ";

    private static final String DATABASE_CREATE_USER_VENUE_INFO = "create table "
            + TABLE_USER_VENUES_INFO + "(" + VENUES_LOCAL_ID
            + " integer primary key autoincrement, "
            + VENUES_REMOTE_ID + " text not null, "
            + VENUE_DESCRIPTION + " text not null, "
            + VENUE_PRIMARY_CATEGORY + " text not null, "
            + VENUE_NAME + " text not null, "
            + VENUE_LATITUDE + " text not null, "
            + VENUE_PICTURE + " text not null, "
            + VENUE_STREET + " text not null, "
            + VENUE_LONGITUDE + " text not null, "
            + VENUE_LOCATION_NAME + " text not null); ";

    private static final String DATABASE_CREATE_USER_OUTFIT_INFO = "create table "
            + TABLE_USER_OUTFIT_INFO + "(" + OUTFIT_LOCAL_ID
            + " integer primary key autoincrement, "
            + OUTFIT_REMOTE_ID + " text not null, "
            + OUTFIT_DESCRIPTION + " text not null, "
            + OUTFIT_PRIMARY_CATEGORY + " text not null, "
            + OUTFIT_NAME + " text not null, "
            + OUTFIT_LATITUDE + " text not null, "
            + OUTFIT_LONGITUDE + " text not null, "
            + OUTFIT_STREET + " text not null, "
            + OUTFIT_PICTURE + " text not null, "
            + OUTFIT_LOCATION_NAME + " text not null); ";

    private static final String DATABASE_CREATE_OUTFIT_INFO = "create table "
            + TABLE_OUTFIT_INFO + "(" + OUTFIT_LOCAL_ID
            + " integer primary key autoincrement, "
            + OUTFIT_REMOTE_ID + " text not null, "
            + OUTFIT_DESCRIPTION + " text not null, "
            + OUTFIT_PRIMARY_CATEGORY + " text not null, "
            + OUTFIT_NAME + " text not null, "
            + OUTFIT_LATITUDE + " text not null, "
            + OUTFIT_LONGITUDE + " text not null, "
            + OUTFIT_STREET + " text not null, "
            + OUTFIT_PICTURE + " text not null, "
            + OUTFIT_LOCATION_NAME + " text not null); ";

    private static final String DATABASE_CREATE_ARTIST_INFO = "create table "
            + TABLE_ARTIST_INFO + "(" + ARTIST_LOCAL_ID
            + " integer primary key autoincrement, "
            + ARTIST_REMOTE_ID + " text not null, "
            + ARTIST_DESCRIPTION + " text not null, "
            + ARTIST_PRIMARY_CATEGORY + " text not null, "
            + ARTIST_PICTURE + " text not null, "
            + ARTIST_NAME + " text not null); ";

    private static final String DATABASE_CREATE_USER_ARTIST_INFO = "create table "
            + TABLE_USER_ARTIST_INFO + "(" + ARTIST_LOCAL_ID
            + " integer primary key autoincrement, "
            + ARTIST_REMOTE_ID + " text not null, "
            + ARTIST_DESCRIPTION + " text not null, "
            + ARTIST_PRIMARY_CATEGORY + " text not null, "
            + ARTIST_PICTURE + " text not null, "
            + ARTIST_NAME + " text not null); ";

    //Creating the Database.
    public SQLiteDatabaseModel(Context context) {
        // Creation of the DB
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database setup stuff

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        //EXCECUTING THE COMMAND TO CREATE THE TABLES.
        db.execSQL(DATABASE_CREATE_EVENT_PRIMARY);
        db.execSQL(DATABASE_CREATE_EVENT_CATEGORIES);
        db.execSQL(DATABASE_CREATE_VENUE_CATEGORIES);
        db.execSQL(DATABASE_CREATE_ARTIST_CATEGORIES);
        db.execSQL(DATABASE_CREATE_ORGANIZATION_CATEGORIES);
        db.execSQL(DATABASE_CREATE_USER_INFO);
        db.execSQL(DATABASE_CREATE_EVENT_INFO);
        db.execSQL(DATABASE_CREATE_EVENT_SUB_CATEGORY);
        db.execSQL(DATABASE_CREATE_USER_EVENT_INFO);
        db.execSQL(DATABASE_CREATE_USER_VENUE_INFO);
        db.execSQL(DATABASE_CREATE_USER_ARTIST_INFO);
        db.execSQL(DATABASE_CREATE_USER_OUTFIT_INFO);
        db.execSQL(DATABASE_CREATE_USER_EVENT_SUB_CATEGORY);
        db.execSQL(DATABASE_CREATE_ADDICTIONS);
        db.execSQL(DATABASE_CREATE_ADDICTIONS_ARTISTS);
        db.execSQL(DATABASE_CREATE_ADDICTIONS_ORG);
        db.execSQL(DATABASE_CREATE_ADDICTIONS_VENUES);
        db.execSQL(DATABASE_CREATE_VENUE_INFO);
        db.execSQL(DATABASE_CREATE_OUTFIT_INFO);
        db.execSQL(DATABASE_CREATE_ARTIST_INFO);
    }

    //Deleting Addictions
    public void deleteEventAddiction(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_ADDICTIONS+" where eventID='"+id+"'");
    }

    public void deleteVenueAddiction(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_ADDICTIONS_VENUES+" where venueID='"+id+"'");
    }

    public void deleteArtistAddiction(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_ADDICTIONS_ARTISTS+" where artistID='"+id+"'");
    }

    public void deleteOrgAddiction(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_ADDICTIONS_ORG+" where orgID='"+id+"'");
    }

    //Deleting Event/Org/Venue/Artist

    public void deleteUserEvent(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_USER_EVENT_INFO+" where eventID='"+id+"'");
        db.execSQL("delete from "+TABLE_EVENT_INFO+" where eventID='"+id+"'");
    }

    public void deleteUserVenue(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_USER_VENUES_INFO+" where venueID='"+id+"'");
        db.execSQL("delete from "+TABLE_VENUES_INFO+" where venueID='"+id+"'");
    }

    public void deleteUserArtist(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from "+TABLE_USER_ARTIST_INFO+" where artistID='"+id+"'");
        db.execSQL("delete from "+TABLE_ARTIST_INFO+" where artistID='"+id+"'");
    }

    public void deleteUserOrg(android.database.sqlite.SQLiteDatabase db,String id) {
        db.execSQL("delete from " + TABLE_USER_OUTFIT_INFO + " where outfitID='" + id + "'");
        db.execSQL("delete from " + TABLE_OUTFIT_INFO + " where outfitID='" + id + "'");
    }

    //Inserting Addiction:

    public void insertEventAddiction(android.database.sqlite.SQLiteDatabase db,String id)
    {
                ContentValues values = new ContentValues();

            values.put("eventID", id);
            db.insert(TABLE_ADDICTIONS, null, values);


    }

    public void insertVenueAddiction(android.database.sqlite.SQLiteDatabase db,String id)
    {
        ContentValues values = new ContentValues();

        values.put("venueID", id);
        db.insert(TABLE_ADDICTIONS_VENUES, null, values);


    }

    public void insertArtistAddiction(android.database.sqlite.SQLiteDatabase db,String id)
    {
        ContentValues values = new ContentValues();

        values.put("artistID", id);
        db.insert(TABLE_ADDICTIONS_ARTISTS, null, values);


    }

    public void insertOrgAddiction(android.database.sqlite.SQLiteDatabase db,String id)
    {
        ContentValues values = new ContentValues();
        values.put("orgID", id);
        db.insert(TABLE_ADDICTIONS_ORG, null, values);
    }

    //MANUALLY DELETING SELECTED TABLES WHEN APP IS OPENED.
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

      //  db.execSQL("DROP TABLE IF EXISTS user_info");
        db.execSQL("DROP TABLE IF EXISTS event_primary_categories");
        db.execSQL(DATABASE_CREATE_EVENT_PRIMARY);
        db.execSQL("DROP TABLE IF EXISTS event_categories");
        db.execSQL(DATABASE_CREATE_EVENT_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS venue_categories");
        db.execSQL(DATABASE_CREATE_VENUE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS artist_categories");
        db.execSQL(DATABASE_CREATE_ARTIST_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS organization_categories");
        db.execSQL(DATABASE_CREATE_ORGANIZATION_CATEGORIES);

        db.execSQL("DROP TABLE IF EXISTS event_info");
        db.execSQL(DATABASE_CREATE_EVENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS event_sub_categories");
        db.execSQL(DATABASE_CREATE_EVENT_SUB_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS venue_info");
        db.execSQL(DATABASE_CREATE_VENUE_INFO);
        db.execSQL("DROP TABLE IF EXISTS outfit_info");
        db.execSQL(DATABASE_CREATE_OUTFIT_INFO);
        db.execSQL("DROP TABLE IF EXISTS artist_info");
        db.execSQL(DATABASE_CREATE_ARTIST_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_event_info");
        db.execSQL(DATABASE_CREATE_USER_EVENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_venue_info");
        db.execSQL(DATABASE_CREATE_USER_VENUE_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_artist_info");
        db.execSQL(DATABASE_CREATE_USER_ARTIST_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_outfit_info");
        db.execSQL(DATABASE_CREATE_USER_OUTFIT_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_event_sub_categories");
        db.execSQL(DATABASE_CREATE_USER_EVENT_SUB_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS addictions");
        db.execSQL(DATABASE_CREATE_ADDICTIONS);
        db.execSQL("DROP TABLE IF EXISTS addictionsVenue");
        db.execSQL(DATABASE_CREATE_ADDICTIONS_VENUES);
        db.execSQL("DROP TABLE IF EXISTS addictionsOrg");
        db.execSQL(DATABASE_CREATE_ADDICTIONS_ORG);
        db.execSQL("DROP TABLE IF EXISTS addictionsArtist");
        db.execSQL(DATABASE_CREATE_ADDICTIONS_ARTISTS);
    }

    //DROPING THE INFORMATION CONTANED ABOUT THE USER WHEN LOGGED IN.
    public void onLogout(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_info");
        db.execSQL(DATABASE_CREATE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_event_info");
        db.execSQL(DATABASE_CREATE_USER_EVENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_venue_info");
        db.execSQL(DATABASE_CREATE_USER_VENUE_INFO);
        db.execSQL("DROP TABLE IF EXISTS user_artist_info");
        db.execSQL(DATABASE_CREATE_USER_ARTIST_INFO);
        db.execSQL("DROP TABLE IF EXISTS outfit_info");
        db.execSQL(DATABASE_CREATE_OUTFIT_INFO);
        db.execSQL("DROP TABLE IF EXISTS addictions");
        db.execSQL(DATABASE_CREATE_ADDICTIONS);
        db.execSQL("DROP TABLE IF EXISTS addictionsVenue");
        db.execSQL(DATABASE_CREATE_ADDICTIONS_VENUES);
        db.execSQL("DROP TABLE IF EXISTS addictionsOrg");
        db.execSQL(DATABASE_CREATE_ADDICTIONS_ORG);
        db.execSQL("DROP TABLE IF EXISTS addictionsArtist");
        db.execSQL(DATABASE_CREATE_ADDICTIONS_ARTISTS);
    }

    //--------------------------------------CATEGORY LISTS-----------------------------------------

    public void addEventPrimaryCategories( HashMap<String,ArrayList<ArrayList<String>>> event_primary_categories){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(String key: event_primary_categories.keySet()){

            values.put("primary_category_name", key);

            db.insert(TABLE_EVENT_PRIMARY, null, values);
        }
    }

    public void addEventCategories( HashMap<String,ArrayList<ArrayList<String>>> event_categories){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(String key : event_categories.keySet()){

            for(int i = 0; i<event_categories.get(key).get(0).size(); i++){

                values.put("primary_category_name", key);
                values.put("sub_category_name", event_categories.get(key).get(1).get(i));
                values.put("sub_category_id", event_categories.get(key).get(0).get(i));

                db.insert(TABLE_EVENT_CATEGORIES, null, values);
            }
        }
    }

    public ArrayList<ArrayList<Items>> getEventCategories(){

        ArrayList<ArrayList<Items>> mainList = new ArrayList<>();

        ArrayList<Items>eventMainList = new ArrayList<>();
        ArrayList<Items>eventMainListID = new ArrayList<>();

        ArrayList<Items.SubCategory>eventArrayList = new ArrayList<>();
        ArrayList<Items.SubCategory>eventArrayListID = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy = "primary_category_name";

        Cursor cursor = db.query(TABLE_EVENT_PRIMARY, null, null, null, null, null, orderBy);

        eventMainList.add(new Items("Event Categories", eventArrayList));
        eventMainListID.add(new Items("Event Categories", eventArrayListID));

        if(cursor != null) {

            while(cursor.moveToNext()){

                ArrayList<Items.SubCategory.ItemList> itemSub = new ArrayList<>();
                ArrayList<Items.SubCategory.ItemList> itemSubID = new ArrayList<>();

                String whereClause = "primary_category_name = '"+cursor.getString(cursor.getColumnIndex("primary_category_name"))+"'";
                Cursor subCursor = db.query(TABLE_EVENT_CATEGORIES,null,whereClause,null,null,null,null);

                if(subCursor != null){

                    while(subCursor.moveToNext()){

                        itemSubID.add(new Items.SubCategory.ItemList(subCursor.getString(subCursor.getColumnIndex("sub_category_id"))));
                        itemSub.add(new Items.SubCategory.ItemList(subCursor.getString(subCursor.getColumnIndex("sub_category_name"))));
                    }
                }

                subCursor.close();

                eventArrayList.add(new Items.SubCategory(cursor.getString(cursor.getColumnIndex("primary_category_name")), new ArrayList<>(itemSub)));
                eventArrayListID.add(new Items.SubCategory(cursor.getString(cursor.getColumnIndex("primary_category_name")), new ArrayList<>(itemSubID)));
            }

            mainList.add(eventMainListID);
            mainList.add(eventMainList);
        }

        cursor.close();

        return mainList;
    }

    public void addVenueCategories( ArrayList<String> venue_categories){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i <venue_categories.size(); i++){

            values.put("primary_category_name", venue_categories.get(i));

            db.insert(TABLE_VENUE_CATEGORIES, null, values);
        }
    }

    public ArrayList<Items> getVenueCategories(){

        ArrayList<Items>venueMainList = new ArrayList<Items>();

        ArrayList<Items.SubCategory>venueArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VENUE_CATEGORIES, null, null, null, null, null, null);

        venueMainList.add(new Items("Venue Categories", venueArrayList));

        if(cursor != null) {

            while(cursor.moveToNext()){

                venueArrayList.add(new Items.SubCategory(cursor.getString(cursor.getColumnIndex("primary_category_name")), null));
            }
        }

        cursor.close();

        return venueMainList;
    }

    public void addArtistCategories( ArrayList<String> artist_categories){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i <artist_categories.size(); i++){

            values.put("primary_category_name", artist_categories.get(i));

            db.insert(TABLE_ARTIST_CATEGORIES, null, values);
        }
    }

    public ArrayList<Items> getArtistCategories(){

        ArrayList<Items>artistMainList = new ArrayList<Items>();

        ArrayList<Items.SubCategory>artistArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARTIST_CATEGORIES, null, null, null, null, null, null);

        artistMainList.add(new Items("Artist Categories", artistArrayList));

        if(cursor != null) {

            while(cursor.moveToNext()){

                artistArrayList.add(new Items.SubCategory(cursor.getString(cursor.getColumnIndex("primary_category_name")), null));
            }
        }

        cursor.close();

        return artistMainList;
    }

    public void addOrganizationCategories( ArrayList<String> organization_categories){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i <organization_categories.size(); i++){

            values.put("primary_category_name", organization_categories.get(i));

            db.insert(TABLE_ORGANIZATION_CATEGORIES, null, values);
        }
    }

    public ArrayList<Items> getOrganizationCategories(){

        ArrayList<Items>organizationMainList = new ArrayList<Items>();

        ArrayList<Items.SubCategory>organizationArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORGANIZATION_CATEGORIES, null, null, null, null, null, null);

        organizationMainList.add(new Items("Organization Categories", organizationArrayList));

        if(cursor != null) {

            while(cursor.moveToNext()){

                organizationArrayList.add(new Items.SubCategory(cursor.getString(cursor.getColumnIndex("primary_category_name")), null));
            }
        }

        cursor.close();

        return organizationMainList;
    }

    //-------------------------------------- EXPLORE ITEMS-----------------------------------------

    //ADDING THE LIST OF EVENTS INTO THE LOCAL DATABASE TABLE.
    public void addEvents( ArrayList<Event> events){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues subCatValues = new ContentValues();

        for(int i =0; i < events.size();i++){
            values.put("eventID", events.get(i).getEventid());
            values.put("venueID", events.get(i).getVenueid());
            values.put("eventDescription", events.get(i).getEventDescription());
            values.put("eventName", events.get(i).getEventName());
            values.put("eventPrimaryCategory", events.get(i).getEventPrimaryCategory());

            for(int j= 0; j< events.get(i).getEventSubCategories().size(); j++){

                subCatValues.put("eventID",events.get(i).getEventid());
                subCatValues.put("eventSubCategoryID", events.get(i).getEventSubCategoriesID().get(j));
                subCatValues.put("eventSubCategory", events.get(i).getEventSubCategories().get(j));

                db.insert(TABLE_EVENT_SUB_CATEGORY, null, subCatValues);
            }

            values.put("eventDate", events.get(i).getEventDate());
            values.put("eventPrice", events.get(i).getEventPrice());
            values.put("eventLatitude", events.get(i).getEventLatitude());
            values.put("eventLongitude", events.get(i).getEventLongitude());
            values.put("eventLocName", events.get(i).getEventLocName());
            values.put("eventLocStreet", events.get(i).getEventLocSt());
            values.put("eventLocAddress", events.get(i).getEventLocAdd());
            values.put("eventStartTime", events.get(i).getEventStartTime());
            values.put("eventEndTime", events.get(i).getEventEndTime());
            values.put("eventEndDate", events.get(i).getEventEndDate());
            values.put("eventPicture", events.get(i).getEventPicture());

            db.insert(TABLE_EVENT_INFO, null, values);
        }
    }

    //RETRIEVED THE LIST OF EVENTS FROM THE LOCAL DATABASE TABLE.
    public ArrayList<Event> retrieveEventInfo(){

        ArrayList<Event> mEvents = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_INFO, null, null, null, null, null, null);

        if(cursor != null) {

            while(cursor.moveToNext()){

                Event newEvent = new Event();

                ArrayList<String> eventSubCatID = new ArrayList<>();
                ArrayList<String> eventSubCat = new ArrayList<>();

                String whereClause = "eventID = '"+cursor.getString(cursor.getColumnIndex("eventID"))+"'";

                Cursor subCursor = db.query(TABLE_EVENT_SUB_CATEGORY,null,whereClause,null,null,null,null);

                if(subCursor != null){

                    while(subCursor.moveToNext()){

                        eventSubCatID.add(subCursor.getString(subCursor.getColumnIndex("eventSubCategoryID")));
                        eventSubCat.add(subCursor.getString(subCursor.getColumnIndex("eventSubCategory")));
                    }
                }

                newEvent.setEventid(cursor.getString(cursor.getColumnIndex("eventID")));
                newEvent.setVenueid(cursor.getString(cursor.getColumnIndex("venueID")));
                newEvent.setEventDescription(cursor.getString(cursor.getColumnIndex("eventDescription")));
                newEvent.setEventName(cursor.getString(cursor.getColumnIndex("eventName")));
                newEvent.setEventPrimaryCategory(cursor.getString(cursor.getColumnIndex("eventPrimaryCategory")));
                newEvent.setEventSubCategoriesID(eventSubCatID);
                newEvent.setEventSubCategories(eventSubCat);
                newEvent.setEventDate(cursor.getString(cursor.getColumnIndex("eventDate")));
                newEvent.setEventPrice(cursor.getDouble(cursor.getColumnIndex("eventPrice")));
                newEvent.setEventLatitude(cursor.getDouble(cursor.getColumnIndex("eventLatitude")));
                newEvent.setEventLongitude(cursor.getDouble(cursor.getColumnIndex("eventLongitude")));
                newEvent.setEventPicture(cursor.getString(cursor.getColumnIndex("eventPicture")));
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

    //ADDING THE LIST OF VENUES INTO THE LOCAL DATABASE TABLE.
    public void addVenues( ArrayList<Venue> venue){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< venue.size();i++){
            values.put("venueID", venue.get(i).getVenueId());
            values.put("venueDescription", venue.get(i).getVenueDescription());
            values.put("venuePrimaryCategory", venue.get(i).getVenuePrimaryCategory());
            values.put("venueName", venue.get(i).getVenueName());
            values.put("venueLatitude", venue.get(i).getLat());
            values.put("venueLongitude", venue.get(i).getLng());
            values.put("venueLocName", venue.get(i).getVenueLocation());
            values.put("venuePicture", venue.get(i).getVenuePicture());
            values.put("venueSt", venue.get(i).getVenueSt());

            db.insert(TABLE_VENUES_INFO, null, values);
        }
    }

    //RETRIEVING THE LIST OF VENUES INTO THE LOCAL DATABASE TABLE.
    public ArrayList<Venue> retrieveVenuesInfo(){

        ArrayList<Venue> mVenues = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VENUES_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){
                Venue newVen = new Venue();

                //int id = cursor.getInt(cursor.getColumnIndex("_id"));
                newVen.setVenueId(cursor.getString(cursor.getColumnIndex("venueID")));
                newVen.setVenueDescription(cursor.getString(cursor.getColumnIndex("venueDescription")));
                newVen.setVenuePrimaryCategory(cursor.getString(cursor.getColumnIndex("venuePrimaryCategory")));
                newVen.setVenueName(cursor.getString(cursor.getColumnIndex("venueName")));
                newVen.setLat(cursor.getDouble(cursor.getColumnIndex("venueLatitude")));
                newVen.setLng(cursor.getDouble(cursor.getColumnIndex("venueLongitude")));
                newVen.setVenueLocation(cursor.getString(cursor.getColumnIndex("venueLocName")));
                newVen.setVenuePicture(cursor.getString(cursor.getColumnIndex("venuePicture")));
                newVen.setVenueSt(cursor.getString(cursor.getColumnIndex("venueSt")));

                mVenues.add(newVen);
            }
        }

        cursor.close();

        return mVenues;
    }

    //ADDING THE LIST OF ARTISTS INTO THE LOCAL DATABASE TABLE.
    public void addArtists( ArrayList<Artist> artist){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< artist.size();i++){
            values.put("artistID", artist.get(i).getArtistId());
            values.put("artistDescription", artist.get(i).getArtistDescription());
            values.put("artistPrimaryCategory", artist.get(i).getArtistPrimaryCategory());
            values.put("artistName", artist.get(i).getArtistName());
            values.put("artistPicture", artist.get(i).getArtistPicture());

            db.insert(TABLE_ARTIST_INFO, null, values);
        }
    }

    //RETRIEVING THE LIST OF ARTISTS FROM THE LOCAL DATABASE TABLE.
    public ArrayList<Artist> retrieveArtistInfo(){

        ArrayList<Artist> mArtist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ARTIST_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){
                Artist artist = new Artist();

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                artist.setArtistId(cursor.getString(cursor.getColumnIndex("artistID")));
                artist.setArtistDescription(cursor.getString(cursor.getColumnIndex("artistDescription")));
                artist.setArtistPrimaryCategory(cursor.getString(cursor.getColumnIndex("artistPrimaryCategory")));
                artist.setArtistName(cursor.getString(cursor.getColumnIndex("artistName")));
                artist.setArtistPicture(cursor.getString(cursor.getColumnIndex("artistPicture")));

                mArtist.add(artist);
            }
        }
        cursor.close();
        return mArtist;
    }

    //ADDING THE LIST OF OUTFITS INTO THE LOCAL DATABASE TABLE.
    public void addOrganizations(ArrayList<Organizations> Org){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< Org.size();i++){
            values.put("outfitID", Org.get(i).getOrgId());
            values.put("outfitDescription", Org.get(i).getOrgDescription());
            values.put("outfitPrimaryCategory", Org.get(i).getOrgPrimaryCategory());
            values.put("outfitName", Org.get(i).getOrgName());
            values.put("outfitLatitude", Org.get(i).getLat());
            values.put("outfitLongitude", Org.get(i).getLng());
            values.put("outfitLocName", Org.get(i).getOrgLocation());
            values.put("outfitPicture", Org.get(i).getOrgPicture());
            values.put("outfitSt", Org.get(i).getOrgSt());

            db.insert(TABLE_OUTFIT_INFO, null, values);
        }
    }

    //RETRIEVING THE LIST OF OUTFITS FROM THE LOCAL DATABASE TABLE.
    public ArrayList<Organizations> retrieveOrganizationInfo(){

        ArrayList<Organizations> mOutfit = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OUTFIT_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Organizations Org = new Organizations();

                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                Org.setOrgId(cursor.getString(cursor.getColumnIndex("outfitID")));
                Org.setOrgDescription(cursor.getString(cursor.getColumnIndex("outfitDescription")));
                Org.setOrgPrimaryCategory(cursor.getString(cursor.getColumnIndex("outfitPrimaryCategory")));
                Org.setOrgName(cursor.getString(cursor.getColumnIndex("outfitName")));
                Org.setLat(cursor.getDouble(cursor.getColumnIndex("outfitLatitude")));
                Org.setLng(cursor.getDouble(cursor.getColumnIndex("outfitLongitude")));
                Org.setOrgLocation(cursor.getString(cursor.getColumnIndex("outfitLocName")));
                Org.setOrgPicture(cursor.getString(cursor.getColumnIndex("outfitPicture")));
                Org.setOrgSt(cursor.getString(cursor.getColumnIndex("outfitSt")));

                mOutfit.add(Org);
            }
        }

        cursor.close();

        return mOutfit;
    }

    //--------------------------------------USER MANAGEMENT-----------------------------------------

    //ADDING THE INFO OF THE USER INTO THE LOCAL DATABASE TABLE.
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

    //GETTING THE INFO OF THE USER FROM THE LOCAL DATABASE TABLE.
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

                //ADDING THE RETIEVED INFO INTO THE ARRAY TO BE RETURNED.
                User.add(username);
                User.add(name);
                User.add(DOB);
                User.add(gender);
                User.add(email);
                User.add(location);
            }
        }

        cursor.close();

        return User;
    }

    //-------------------------------------ADDICTION ITEMS-----------------------------------------

    //GETTING THE INFO OF THE USER FROM THE LOCAL DATABASE TABLE.
    public ArrayList<String> retrieveEventsAddictedInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> eventsAddiction=new ArrayList<>();

        Cursor cursor = db.query(TABLE_ADDICTIONS, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){


                String eventID = cursor.getString(cursor.getColumnIndex("eventID"));

                //ADDING THE RETIEVED INFO INTO THE ARRAY TO BE RETURNED.
                eventsAddiction.add(eventID);

            }
        }

        cursor.close();

        return eventsAddiction;
    }

    //GETTING THE INFO OF THE USER FROM THE LOCAL DATABASE TABLE.
    public ArrayList<String> retrieveVenuesAddictedInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> venuesAddiction=new ArrayList<>();

        Cursor cursor = db.query(TABLE_ADDICTIONS_VENUES, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){


                String eventID = cursor.getString(cursor.getColumnIndex("venueID"));


                //ADDING THE RETIEVED INFO INTO THE ARRAY TO BE RETURNED.
                venuesAddiction.add(eventID);

            }
        }

        cursor.close();

        return venuesAddiction;
    }

    //GETTING THE INFO OF THE USER FROM THE LOCAL DATABASE TABLE.
    public ArrayList<String> retrieveArtistAddictedInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> eventsArtistAddiction=new ArrayList<>();

        Cursor cursor = db.query(TABLE_ADDICTIONS_ARTISTS, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){


                String artistID = cursor.getString(cursor.getColumnIndex("artistID"));


                //ADDING THE RETIEVED INFO INTO THE ARRAY TO BE RETURNED.
                eventsArtistAddiction.add(artistID);

            }
        }

        cursor.close();

        return eventsArtistAddiction;
    }

    //GETTING THE INFO OF THE USER FROM THE LOCAL DATABASE TABLE.
    public ArrayList<String> retrieveOrgsAddictedInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> eventsOrgAddiction=new ArrayList<>();

        Cursor cursor = db.query(TABLE_ADDICTIONS_ORG, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){


                String orgID = cursor.getString(cursor.getColumnIndex("orgID"));


                //ADDING THE RETIEVED INFO INTO THE ARRAY TO BE RETURNED.
                eventsOrgAddiction.add(orgID);

            }
        }

        cursor.close();

        return eventsOrgAddiction;
    }

    //ADDING THE INFO OF THE USER INTO THE LOCAL DATABASE TABLE.
    public void addEventAddiction(ArrayList<String> eventID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< eventID.size();i++) {
            values.put("eventID", eventID.get(i));
            db.insert(TABLE_ADDICTIONS, null, values);
        }
    }

    //ADDING THE INFO OF THE USER INTO THE LOCAL DATABASE TABLE.
    public void addVenueAddiction( ArrayList<String> venueID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< venueID.size();i++) {
            values.put("venueID", venueID.get(i));
            db.insert(TABLE_ADDICTIONS_VENUES, null, values);
        }
    }

    //ADDING THE INFO OF THE USER INTO THE LOCAL DATABASE TABLE.
    public void addArtistAddiction( ArrayList<String> artistID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< artistID.size();i++) {
            values.put("artistID", artistID.get(i));
            db.insert(TABLE_ADDICTIONS_ARTISTS, null, values);
        }
    }

    //ADDING THE INFO OF THE USER INTO THE LOCAL DATABASE TABLE.
    public void addOrgAddiction( ArrayList<String> orgID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< orgID.size();i++) {
            values.put("orgID", orgID.get(i));
            db.insert(TABLE_ADDICTIONS_ORG, null, values);
        }
    }

    //------------------------------------USER UPLOAD ITEMS----------------------------------------

    //ADDING THE LIST OF EVENTS UPLOADED BY THE USER INTO THE LOCAL DATABASE TABLE.
    public void addUserEvents( ArrayList<Event> events){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues subCatValues = new ContentValues();

        for(int i =0; i< events.size();i++){
            values.put("eventID", events.get(i).getEventid());
            values.put("venueID", events.get(i).getVenueid());
            values.put("eventDescription", events.get(i).getEventDescription());
            values.put("eventName", events.get(i).getEventName());
            values.put("eventPrimaryCategory", events.get(i).getEventPrimaryCategory());

            //Log.v("LocalTest", events.get(i).getEventSubCategories() + "");
            //Log.v("LocalTest", events.get(i).getEventSubCategoriesID() + "");

            for(int j= 0; j< events.get(i).getEventSubCategories().size(); j++){

                subCatValues.put("eventID",events.get(i).getEventid());
                subCatValues.put("eventSubCategory", events.get(i).getEventSubCategories().get(j));
                subCatValues.put("eventSubCategoryID", events.get(i).getEventSubCategoriesID().get(j));

                db.insert(TABLE_USER_EVENT_SUB_CATEGORY, null, subCatValues);
            }

            values.put("eventDate", events.get(i).getEventDate());
            values.put("eventPrice", events.get(i).getEventPrice());
            values.put("eventLatitude", events.get(i).getEventLatitude());
            values.put("eventLongitude", events.get(i).getEventLongitude());
            values.put("eventLocName", events.get(i).getEventLocName());
            values.put("eventLocStreet", events.get(i).getEventLocSt());
            values.put("eventLocAddress", events.get(i).getEventLocAdd());
            values.put("eventStartTime", events.get(i).getEventStartTime());
            values.put("eventEndTime", events.get(i).getEventEndTime());
            values.put("eventEndDate", events.get(i).getEventEndDate());
            values.put("eventPicture", events.get(i).getEventPicture());

            db.insert(TABLE_USER_EVENT_INFO, null, values);
        }
    }

    //RETIEVING THE LIST OF EVENTS UPLOADED BY THE USER INTO THE LOCAL DATABASE TABLE.
    public ArrayList<Event> retrieveUserEventInfo(){

        ArrayList<Event> mEvents = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_EVENT_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Event newEvent = new Event();

                String whereClause = "eventID = '"+cursor.getString(cursor.getColumnIndex("eventID"))+"'";

                Cursor subCursor = db.query(TABLE_EVENT_SUB_CATEGORY,null,whereClause,null,null,null,null);

                ArrayList<String> eventSubCatID = new ArrayList<>();
                ArrayList<String> eventSubCat = new ArrayList<>();

                if(subCursor != null){

                    while(subCursor.moveToNext()){

                        eventSubCatID.add(subCursor.getString(subCursor.getColumnIndex("eventSubCategoryID")));
                        eventSubCat.add(subCursor.getString(subCursor.getColumnIndex("eventSubCategory")));
                    }
                }

                newEvent.setEventid(cursor.getString(cursor.getColumnIndex("eventID")));
                newEvent.setVenueid(cursor.getString(cursor.getColumnIndex("venueID")));
                newEvent.setEventDescription(cursor.getString(cursor.getColumnIndex("eventDescription")));
                newEvent.setEventName(cursor.getString(cursor.getColumnIndex("eventName")));
                newEvent.setEventPrimaryCategory(cursor.getString(cursor.getColumnIndex("eventPrimaryCategory")));
                newEvent.setEventSubCategoriesID(eventSubCatID);
                newEvent.setEventSubCategories(eventSubCat);
                newEvent.setEventDate(cursor.getString(cursor.getColumnIndex("eventDate")));
                newEvent.setEventPrice(cursor.getDouble(cursor.getColumnIndex("eventPrice")));
                newEvent.setEventLatitude(cursor.getDouble(cursor.getColumnIndex("eventLatitude")));
                newEvent.setEventLongitude(cursor.getDouble(cursor.getColumnIndex("eventLongitude")));
                newEvent.setEventLocName(cursor.getString(cursor.getColumnIndex("eventLocName")));
                newEvent.setEventLocSt(cursor.getString(cursor.getColumnIndex("eventLocStreet")));
                newEvent.setEventLocAdd(cursor.getString(cursor.getColumnIndex("eventLocAddress")));
                newEvent.setEventStartTime(cursor.getString(cursor.getColumnIndex("eventStartTime")));
                newEvent.setEventEndTime(cursor.getString(cursor.getColumnIndex("eventEndTime")));
                newEvent.setEventEndDate(cursor.getString(cursor.getColumnIndex("eventEndDate")));
                newEvent.setEventPicture(cursor.getString(cursor.getColumnIndex("eventPicture")));

                mEvents.add(newEvent);
            }
        }

        cursor.close();
        return mEvents;
    }

    //ADDING THE LIST OF USER VENUES INTO THE LOCAL DATABASE TABLE.
    public void addUserVenues( ArrayList<Venue> venue){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< venue.size();i++){
            values.put("venueID", venue.get(i).getVenueId());
            values.put("venueDescription", venue.get(i).getVenueDescription());
            values.put("venuePrimaryCategory", venue.get(i).getVenuePrimaryCategory());
            values.put("venueName", venue.get(i).getVenueName());
            values.put("venueLatitude", venue.get(i).getLat());
            values.put("venueLongitude", venue.get(i).getLng());
            values.put("venueLocName", venue.get(i).getVenueLocation());
            values.put("venuePicture", venue.get(i).getVenuePicture());
            values.put("venueSt", venue.get(i).getVenueSt());


            db.insert(TABLE_USER_VENUES_INFO, null, values);
        }
    }

    //RETRIEVING THE LIST OF USER VENUES INTO THE LOCAL DATABASE TABLE.
    public ArrayList<Venue> retrieveUserVenuesInfo(){

        ArrayList<Venue> mVenues = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_VENUES_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){
                Venue newVen = new Venue();

                //int id = cursor.getInt(cursor.getColumnIndex("_id"));
                newVen.setVenueId(cursor.getString(cursor.getColumnIndex("venueID")));
                newVen.setVenueDescription(cursor.getString(cursor.getColumnIndex("venueDescription")));
                newVen.setVenuePrimaryCategory(cursor.getString(cursor.getColumnIndex("venuePrimaryCategory")));
                newVen.setVenueName(cursor.getString(cursor.getColumnIndex("venueName")));
                newVen.setLat(cursor.getDouble(cursor.getColumnIndex("venueLatitude")));
                newVen.setLng(cursor.getDouble(cursor.getColumnIndex("venueLongitude")));
                newVen.setVenueLocation(cursor.getString(cursor.getColumnIndex("venueLocName")));
                newVen.setVenuePicture(cursor.getString(cursor.getColumnIndex("venuePicture")));
                newVen.setVenueSt(cursor.getString(cursor.getColumnIndex("venueSt")));

                mVenues.add(newVen);
            }
        }

        cursor.close();

        return mVenues;
    }

    //ADDING THE LIST OF ARTISTS INTO THE LOCAL DATABASE TABLE.
    public void addUserArtists( ArrayList<Artist> artist){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< artist.size();i++){
            values.put("artistID", artist.get(i).getArtistId());
            values.put("artistDescription", artist.get(i).getArtistDescription());
            values.put("artistPrimaryCategory", artist.get(i).getArtistPrimaryCategory());
            values.put("artistName", artist.get(i).getArtistName());
            values.put("artistPicture", artist.get(i).getArtistPicture());

            db.insert(TABLE_USER_ARTIST_INFO, null, values);
        }
    }

    //RETRIEVING THE LIST OF ARTISTS FROM THE LOCAL DATABASE TABLE.
    public ArrayList<Artist> retrieveUserArtistInfo(){

        ArrayList<Artist> mArtist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_ARTIST_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){
                Artist artist = new Artist();

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                artist.setArtistId(cursor.getString(cursor.getColumnIndex("artistID")));
                artist.setArtistDescription(cursor.getString(cursor.getColumnIndex("artistDescription")));
                artist.setArtistPrimaryCategory(cursor.getString(cursor.getColumnIndex("artistPrimaryCategory")));
                artist.setArtistName(cursor.getString(cursor.getColumnIndex("artistName")));
                artist.setArtistPicture(cursor.getString(cursor.getColumnIndex("artistPicture")));

                mArtist.add(artist);
            }
        }
        cursor.close();
        return mArtist;
    }

    //ADDING THE LIST OF OUTFITS INTO THE LOCAL DATABASE TABLE.
    public void addUserOrganizations(ArrayList<Organizations> Org){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i =0; i< Org.size();i++){
            values.put("outfitID", Org.get(i).getOrgId());
            values.put("outfitDescription", Org.get(i).getOrgDescription());
            values.put("outfitPrimaryCategory", Org.get(i).getOrgPrimaryCategory());
            values.put("outfitName", Org.get(i).getOrgName());
            values.put("outfitLatitude", Org.get(i).getLat());
            values.put("outfitLongitude", Org.get(i).getLng());
            values.put("outfitLocName", Org.get(i).getOrgLocation());
            values.put("outfitPicture", Org.get(i).getOrgPicture());
            values.put("outfitSt", Org.get(i).getOrgSt());

            db.insert(TABLE_USER_OUTFIT_INFO, null, values);
        }
    }

    //RETRIEVING THE LIST OF OUTFITS FROM THE LOCAL DATABASE TABLE.
    public ArrayList<Organizations> retrieveUserOrganizationInfo(){

        ArrayList<Organizations> mOutfit = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_OUTFIT_INFO, null, null, null, null, null, null);

        if(cursor != null)
        {
            while(cursor.moveToNext()){

                Organizations Org = new Organizations();

                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                Org.setOrgId(cursor.getString(cursor.getColumnIndex("outfitID")));
                Org.setOrgDescription(cursor.getString(cursor.getColumnIndex("outfitDescription")));
                Org.setOrgPrimaryCategory(cursor.getString(cursor.getColumnIndex("outfitPrimaryCategory")));
                Org.setOrgName(cursor.getString(cursor.getColumnIndex("outfitName")));
                Org.setLat(cursor.getDouble(cursor.getColumnIndex("outfitLatitude")));
                Org.setLng(cursor.getDouble(cursor.getColumnIndex("outfitLongitude")));
                Org.setOrgLocation(cursor.getString(cursor.getColumnIndex("outfitLocName")));
                Org.setOrgPicture(cursor.getString(cursor.getColumnIndex("outfitPicture")));
                Org.setOrgSt(cursor.getString(cursor.getColumnIndex("outfitSt")));

                mOutfit.add(Org);
            }
        }

        cursor.close();

        return mOutfit;
    }

}
