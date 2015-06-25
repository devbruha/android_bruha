package com.bruha.bruha.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Adapters.ArtistsListViewAdapter;
import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Adapters.OrganizationListViewAdapter;
import com.bruha.bruha.Adapters.VenueListViewAdapter;
import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.daimajia.swipe.util.Attributes;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends FragmentActivity {

    ArrayList<Event> mEvents = new ArrayList<>();       //The Array that will hold the Events that we will pass around(to Adapter,the List...

    ArrayList<Venues> mVenues = new ArrayList<>();

    ArrayList<Event> Backup;         //Array Backup of the whole list,since mEvent changes when we update the adapter in filter save button.

    ListviewAdapter adapter;

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    @InjectView(R.id.venueButton) Button VenueButton;
    @InjectView(R.id.artistButton) Button ArtistButton;
    @InjectView(R.id.orgButton) Button OrgButton;
    @InjectView(R.id.eventButton) Button EventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.

        init();

        if(MyApplication.sourceEvents.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            adapter = new ListviewAdapter(this, mEvents); //Calling the adapter ListView to help set the List
        }
        else{
            adapter = new ListviewAdapter(this, MyApplication.sourceEvents);
        }

        setUpFilters();

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        //Swipe stuff
        adapter.setMode(Attributes.Mode.Single);
    }

    private void init(){

        Backup = ((MyApplication) getApplicationContext()).getBackupEventList();
        Backup.clear();

        // Create the local DB object

        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenues= sqLiteUtils.getVenuesInfo(dbHelper);

        for(Event x:mEvents)
        {
            Backup.add(x);
        }
    }

    private void setUpFilters(){

        // Calling the FilterView class to set the layout for the filters

        FilterView filterView = new FilterView(this, adapter, null);
        filterView.init();
    }

    //VenueButton Implemented to switch the ListView to show List of Venues.
    @OnClick(R.id.venueButton)
    public void VenueButton(View view) {

        VenueButton.setTextColor(Color.BLUE);
        VenueButton.setTypeface(null, Typeface.BOLD);

        ArtistButton.setTypeface(null, Typeface.NORMAL);
        OrgButton.setTypeface(null, Typeface.NORMAL);
        EventButton.setTypeface(null, Typeface.NORMAL);


        Log.v("Venues",mVenues.size()+"");

        EventButton.setTextColor(Color.BLACK);
        ArtistButton.setTextColor(Color.BLACK);
        OrgButton.setTextColor(Color.BLACK);

        VenueListViewAdapter venueAdapter;



        //Creating an variable of type Listview Adapter to create the list view.

        venueAdapter=new VenueListViewAdapter(this, mVenues); //Calling the adapter ListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);
    }

    //VenueButton Implemented to switch the ListView to show List of Venues.
    @OnClick(R.id.orgButton)
    public void organizationButton(View view) {

        OrgButton.setTextColor(Color.BLUE);
        OrgButton.setTypeface(null, Typeface.BOLD);

        VenueButton.setTypeface(null, Typeface.NORMAL);
        ArtistButton.setTypeface(null, Typeface.NORMAL);
        EventButton.setTypeface(null, Typeface.NORMAL);


        VenueButton.setTextColor(Color.BLACK);
        ArtistButton.setTextColor(Color.BLACK);
        EventButton.setTextColor(Color.BLACK);

        OrganizationListViewAdapter OrgAdapter;

        ArrayList<Organizations> mOrganizations= new ArrayList<>();
        mOrganizations.add(new Organizations());
        mOrganizations.add(new Organizations());
        mOrganizations.add(new Organizations());

        //Creating an variable of type Listview Adapter to create the list view.

        OrgAdapter=new OrganizationListViewAdapter(this, mOrganizations); //Calling the adapter ListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(OrgAdapter);
    }

    @OnClick(R.id.eventButton)
    public void eventButton(View view) {

        mListView.setAdapter(adapter);

        EventButton.setTextColor(Color.BLUE);
        EventButton.setTypeface(null, Typeface.BOLD);

        VenueButton.setTypeface(null, Typeface.NORMAL);
        OrgButton.setTypeface(null, Typeface.NORMAL);
        ArtistButton.setTypeface(null, Typeface.NORMAL);

        VenueButton.setTextColor(Color.BLACK);
        ArtistButton.setTextColor(Color.BLACK);
        OrgButton.setTextColor(Color.BLACK);

    }

    @OnClick(R.id.artistButton)
    public void artistButton(View view) {

        ArtistButton.setTextColor(Color.BLUE);
        ArtistButton.setTypeface(null, Typeface.BOLD);

        VenueButton.setTypeface(null, Typeface.NORMAL);
        OrgButton.setTypeface(null, Typeface.NORMAL);
        EventButton.setTypeface(null, Typeface.NORMAL);


        VenueButton.setTextColor(Color.BLACK);
        EventButton.setTextColor(Color.BLACK);
        OrgButton.setTextColor(Color.BLACK);


        ArtistsListViewAdapter Adapter;

        ArrayList<Artists> mArtists= new ArrayList<>();
        mArtists.add(new Artists());
        mArtists.add(new Artists());
        mArtists.add(new Artists());

        //Creating an variable of type Listview Adapter to create the list view.

        Adapter=new ArtistsListViewAdapter(this, mArtists); //Calling the adapter ListView to help set the List

        //Sets the Adapter from the class Listview Adapter.
        mListView.setAdapter(Adapter);
    }

    //Button Implementation for navigating to the Map from ListView.
    @OnClick(R.id.MapButton)
    public void StartMapActivity(View view)
    {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
        finish();
    }

    //Button Implementation for navigating to the Dashboard from ListView.
    @OnClick(R.id.DashboardButton)
    public void StartDashboardActivity(View view)
    {
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}