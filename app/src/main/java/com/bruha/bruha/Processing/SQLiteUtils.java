package com.bruha.bruha.Processing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteUtils {

    private String DB_DEBUGGING = "Local Database Test";

    public void insertNewUser( SQLiteDatabaseModel dbHelper, List<String> user_info){

        // Attempting to insert these values into the local DB

        dbHelper.addUser(user_info.get(0), user_info.get(1), user_info.get(2), user_info.get(3));
    }

    public void insertEvents(SQLiteDatabaseModel dbHelper, ArrayList<Event> events){

        dbHelper.addEvents(events);

    }

    public void insertUserEvents(SQLiteDatabaseModel dbHelper, ArrayList<Event> events){

        dbHelper.addUserEvents(events);

    }

    public void getUserInfo(SQLiteDatabaseModel dbHelper){

        dbHelper.retrieveUserInfo();
    }

    public ArrayList<Event> getEventInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveEventInfo();
    }


    public ArrayList<Event> getUserEventInfo(SQLiteDatabaseModel dbhelper){

        return dbhelper.retrieveUserEventInfo();
    }

}
