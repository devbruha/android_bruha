package com.bruha.bruha.Views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyUploadsActivity extends ActionBarActivity {

    ArrayList<Event> mEvents = new ArrayList<>();       //The Array that will hold the Events that we will pass around(to Adapter,the List...
    ListviewAdapter adapter;

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploads);
        ButterKnife.inject(this);

        init();

        //Creating an variable of type Listview Adapter to create the list view.
        adapter=new ListviewAdapter(this, mEvents); //Calling the adapter ListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);


    }



    private void init(){
        // Create the local DB object

        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getUserEventInfo(dbHelper);
    }



















}
