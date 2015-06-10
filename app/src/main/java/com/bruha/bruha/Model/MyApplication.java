package com.bruha.bruha.Model;

import android.app.Application;

/**
 * Created by Thomas on 6/10/2015.
 */
public class MyApplication extends Application{

    SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

    public SQLiteDatabaseModel getDbHelper(){

        return dbHelper;
    }

}
