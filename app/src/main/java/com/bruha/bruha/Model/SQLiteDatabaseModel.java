package com.bruha.bruha.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteDatabaseModel extends SQLiteOpenHelper{

    // Storing our local database name as string

    private static final String DATABASE_NAME="BruhaUserDatabase";

    public SQLiteDatabaseModel(Context context) {

        // Creation of the DB

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        // Attempting to create a table named "user_info" with 5 columns

        db.execSQL("CREATE TABLE user_info (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT" +
                "name TEXT, " +
                "birthdate DATE, " +
                "gender TEXT);");

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS user_info");

        onCreate(db);
    }

    public void addUser( String username, String name, String birthdate, String gender){

        ContentValues values = new ContentValues(4);

        values.put("username", username);
        values.put("name",name);
        values.put("birthdate", birthdate);
        values.put("gender", gender);

        getWritableDatabase().insert("user_info", "username", values);
        getWritableDatabase().insert("user_info", "name", values);
        getWritableDatabase().insert("user_info", "birthdate", values);
        getWritableDatabase().insert("user_info", "gender", values);

    }
}
