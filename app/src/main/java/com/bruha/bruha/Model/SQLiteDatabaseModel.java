package com.bruha.bruha.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteDatabaseModel extends SQLiteOpenHelper{

    public static final String TABLE_USER_INFO = "user_info";
    public static final String USER_INFO_ID = "_id";

    public static final String USER_INFO_USERNAME = "username";
    public static final String USER_INFO_NAME = "name";
    public static final String USER_INFO_BIRTHDATE = "birthdate";
    public static final String USER_INFO_GENDER = "gender";

    // Storing our local database name and version as strings

    private static final String DATABASE_NAME="BruhaUserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // SQL to create DB

    private static final String DATABASE_CREATE_USER_INFO = "create table "
            + TABLE_USER_INFO + "(" + USER_INFO_ID
            + " integer primary key autoincrement, "
            + USER_INFO_USERNAME + " text not null, "
            + USER_INFO_NAME + " text not null, "
            + USER_INFO_BIRTHDATE + " text not null, "
            + USER_INFO_GENDER + " text not null);";


    public SQLiteDatabaseModel(Context context) {

        // Creation of the DB

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        // Attempting to create a table named "user_info" with 5 columns

        db.execSQL(DATABASE_CREATE_USER_INFO);

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS user_info");

        onCreate(db);
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

        String selectQuery = "SELECT * FROM " + TABLE_USER_INFO;
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

        return cursor;
    }
}
