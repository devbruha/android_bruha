package com.bruha.bruha.Views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLUtils;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

public class LoadScreenActivity extends Activity {

    // Our database hostname and the credentials for our showdom_android account
    String url = "jdbc:mysql://66.147.244.109:3306/showdomc_web2"; //
    String user = "showdomc_android";
    String pass = "show12345!";

    SQLUtils sqlu ; //The SQLUtil object type that will be initialized later depending on the credentials given above.
    ArrayList<Event> mEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        this.deleteDatabase("BruhaDatabase.db");

        initialEventRetrieval();

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }


    private void initialEventRetrieval() {

        // Create the local DB object

        //SQLiteDatabaseModel dbHelper = ((MyApplication) getApplicationContext()).getDbHelper();
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        sqlu = new SQLUtils(url, user, pass); //Creating Object type SQLUtils using credentials needed
        mEvents = sqlu.Events();

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        sqLiteUtils.insertEvents(dbHelper, mEvents);

    }
}
