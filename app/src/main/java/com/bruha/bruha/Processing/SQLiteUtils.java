package com.bruha.bruha.Processing;

import android.util.Log;

import com.bruha.bruha.Model.SQLiteDatabaseModel;

import java.util.List;

/**
 * Created by Thomas on 5/8/2015.
 */
public class SQLiteUtils {

    private String DB_DEBUGGING = "Local Database Test";

    public void insertNewUser( SQLiteDatabaseModel dbHelper, List<String> user_info){

        // Testing to ensure that the values are correct

        Log.v(DB_DEBUGGING, user_info.get(0));
        Log.v(DB_DEBUGGING, user_info.get(1));
        Log.v(DB_DEBUGGING, user_info.get(2));
        Log.v(DB_DEBUGGING, user_info.get(3));

        // Attempting to insert these values into the local DB

        dbHelper.addUser(user_info.get(0), user_info.get(1), user_info.get(2), user_info.get(3));
    }
}
