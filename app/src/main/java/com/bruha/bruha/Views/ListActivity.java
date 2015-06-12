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
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.daimajia.swipe.util.Attributes;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends FragmentActivity {

    private UserCustomFilters mUserCustomFilters = new UserCustomFilters();

    ArrayList<Event> mEvents = new ArrayList<>();       //The Array that will hold the Events that we will pass around(to Adapter,the List...
    ArrayList<Event> newEvent = new ArrayList<>();
    ArrayList<Event> Backup= new ArrayList<>();         //Array Backup of the whole list,since mEvent changes when we update the adapter in filter save button.
    ListviewAdapter adapter;
    ArrayList<Event> MBACKUP = new ArrayList<>();

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    @InjectView(R.id.venueButton) Button VenueButton;
    @InjectView(R.id.artistButton) Button ArtistButton;
    @InjectView(R.id.orgButton) Button OrgButton;
    @InjectView(R.id.eventButton) Button EventButton;

    public static int Clicks=0; //The variable holding the number of times the user has tapped on a list item.



    private void setUpFilters(){

        // Calling the FilterView class to set the layout for the filters

        FilterView filterView = new FilterView(this);
        mUserCustomFilters = filterView.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.

        init();

        setUpFilters();

        //Creating an variable of type Listview Adapter to create the list view.
        adapter=new ListviewAdapter(this, mEvents); //Calling the adapter ListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        //Swipe stuff
        adapter.setMode(Attributes.Mode.Single);
    }

    private void init(){

        // Create the local DB object

        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);

        for(Event x:mEvents)
        {
            Backup.add(x);
        }

        for(Event x:mEvents)
        {
            MBACKUP.add(x);
        }



    }

    //VenueButton Implemented to switch the ListView to show List of Venues.
    @OnClick(R.id.venueButton)
    public void VenueButton(View view)
    {

        VenueButton.setTextColor(Color.BLUE);
        VenueButton.setTypeface(null, Typeface.BOLD);

        ArtistButton.setTypeface(null, Typeface.NORMAL);
        OrgButton.setTypeface(null, Typeface.NORMAL);
        EventButton.setTypeface(null, Typeface.NORMAL);


        EventButton.setTextColor(Color.BLACK);
        ArtistButton.setTextColor(Color.BLACK);
        OrgButton.setTextColor(Color.BLACK);

        VenueListViewAdapter venueAdapter;

        ArrayList<Venues> mVenue= new ArrayList<>();
        mVenue.add(new Venues());
        mVenue.add(new Venues());
        mVenue.add(new Venues());

        //Creating an variable of type Listview Adapter to create the list view.

        venueAdapter=new VenueListViewAdapter(this, mVenue); //Calling the adapter ListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);
    }

    //VenueButton Implemented to switch the ListView to show List of Venues.
    @OnClick(R.id.orgButton)
    public void organizationButton(View view)
    {
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
    public void eventButton(View view)
    {
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

    @OnClick(R.id.filterSaveButton)
    public void ImplementingButton(View view)  {

        ArrayList<String> Filters;

        //To make sure it is empty beforehand.
        newEvent.clear();


        //Obtaining all the filters that the user selected.
        List<String> Dates= mUserCustomFilters.getDateFilter();
        double price=(double)mUserCustomFilters.getAdmissionPriceFilter();
        Filters= mUserCustomFilters.getQuickieFilter();
        Map<String, ArrayList<String>> CategoryFilter = mUserCustomFilters.getCategoryFilter();

        if(Filters.size() == 0){
            Filters.add("No Filters Test");
        }


        //If "All Events" chosen from the filter layout,then load all events from the backup.
        if(Filters.get(0).equals("All Events"))
        {
            //Filtering out the Items,remember to compare with price.
            for(Event event:Backup)
            {
                if(event.getEventPrice() <= price)
                    newEvent.add(event);
            }
        }


        //If "Today" Chosen from the filter layout,then load all events in that day.
        else if(Filters.get(0).equals("Today"))
        {
            //Getting today's date and formatting it to the type that can be compared with the Event Date's type.
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            Log.v("Date:",formattedDate);

            //Filtering the Quickie,remember to compare with price.
            for(Event Ev:Backup)
            {
                if(Ev.getEventDate().equals(formattedDate) && Ev.getEventPrice() <= price )
                {
                    newEvent.add(Ev);
                }
            }
        }

        //If 'This Weekend' is selected in the Quickie Filter.
        else if(Filters.get(0).equals("This Weekend"))
        {
            //Getting the date of Saturday for the week.
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            c.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            //Getting the date of Sunday for the week.
            Calendar d=Calendar.getInstance();
            d.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            d.getTime();
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDatee = dff.format(d.getTime());

            //Filtering out the items.,remember to compare with price.
            for(Event Eve:Backup)
            {
                if((Eve.getEventDate().equals(formattedDate) || Eve.getEventDate().equals(formattedDatee))&& Eve.getEventPrice() <= price)
                {
                    newEvent.add(Eve);
                }
            }
        }

        //If none of the quickie fields are selected,we check the calender dates.

        //Getting the dates from the filter, filtering events out accordingly and setting the price along with it.
        for (String x : Dates) {
            int i = 0;
            while (i < Backup.size()) {
                if (x.equals(Backup.get(i).getEventDate()) && Backup.get(i).getEventPrice() <= price) {
                    newEvent.add(Backup.get(i));
                }

                i++;
            }
        }


        for(Event x:newEvent)
        { Log.v("NewEvent:",x.getEventName()); }


        for(Event y:Backup) {
            Log.v("Backup:",y.getEventName());
        }


        Filters.clear();

        adapter.getData().clear();
        adapter.getData().addAll(newEvent);
        mListView.setAdapter(adapter);
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