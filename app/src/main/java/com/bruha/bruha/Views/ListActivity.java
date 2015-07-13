package com.bruha.bruha.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bruha.bruha.Adapters.ArtistsListViewAdapter;
import com.bruha.bruha.Adapters.EventListviewAdapter;
import com.bruha.bruha.Adapters.OrganizationListViewAdapter;
import com.bruha.bruha.Adapters.VenueListViewAdapter;
import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.daimajia.swipe.util.Attributes;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends FragmentActivity {
    //The Arrays that will contain the information retrieved from the local database.
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Organizations> mOutfit = new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();

    //Initualiing the Filter Obects to hide and display everytime Venue,Artist,Event and Outfit Filters are applied.
    LinearLayout mQuickieListView ;
    LinearLayout linearCalendar ;
    TextView admission;
    TextView mPrice;
    SeekBar prce;

    ArrayList<Event> backupEventList;         //Array backupEventList of the whole list,since mEvent changes when we update the adapter in filter save button.
    EventListviewAdapter adapter;

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    @InjectView(R.id.venueButton) Button venueButton;
    @InjectView(R.id.artistButton) Button artistButton;
    @InjectView(R.id.orgButton) Button orgButton;
    @InjectView(R.id.eventButton) Button eventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.

        init();

        if(MyApplication.sourceEvents.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            adapter = new EventListviewAdapter(this, mEvents); //Calling the adapter mListView to help set the List
        }
        else{
            adapter = new EventListviewAdapter(this, MyApplication.sourceEvents);
        }

        setUpFilters();

        //Setting the Filters to the Filter Item once they have been declared and set in the view,later to be altered with.
        mQuickieListView = (LinearLayout) findViewById(R.id.quickie_listview);
        linearCalendar = (LinearLayout) findViewById(R.id.calendarView);
        prce = (SeekBar) findViewById(R.id.priceBar);
        mPrice = (TextView) findViewById(R.id.priceDisplay);
        admission = (TextView) findViewById(R.id.admissionTextView);

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        //Swipe stuff
        adapter.setMode(Attributes.Mode.Single);
    }

    private void init(){

        backupEventList = ((MyApplication) getApplicationContext()).getBackupEventList();
        backupEventList.clear();

        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenues= sqLiteUtils.getVenuesInfo(dbHelper);
        mOutfit= sqLiteUtils.getOutfitsInfo(dbHelper);
        mArtists= sqLiteUtils.getArtistInfo(dbHelper);

        for(Event x:mEvents)
        {
            backupEventList.add(x);
        }
    }

    private void setUpFilters(){
        // Calling the FilterView class to set the layout for the filters
        FilterView filterView = new FilterView(this, adapter, null);
        filterView.init();
    }

    //venueButton Implemented to switch the mListView to show List of Venues.
    @OnClick(R.id.venueButton)
    public void venueButton(View view) {
        venueButton.setTextColor(Color.BLUE);
        venueButton.setTypeface(null, Typeface.BOLD);

        artistButton.setTypeface(null, Typeface.NORMAL);
        orgButton.setTypeface(null, Typeface.NORMAL);
        eventButton.setTypeface(null, Typeface.NORMAL);

        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mQuickieListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);

        eventButton.setTextColor(Color.BLACK);
        artistButton.setTextColor(Color.BLACK);
        orgButton.setTextColor(Color.BLACK);

        VenueListViewAdapter venueAdapter;

        //Creating an variable of type Listview Adapter to create the list view.
        venueAdapter=new VenueListViewAdapter(this, mVenues); //Calling the adapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);
    }

    //venueButton Implemented to switch the mListView to show List of Venues.
    @OnClick(R.id.orgButton)
    public void organizationButton(View view) {
        orgButton.setTextColor(Color.BLUE);
        orgButton.setTypeface(null, Typeface.BOLD);

        venueButton.setTypeface(null, Typeface.NORMAL);
        artistButton.setTypeface(null, Typeface.NORMAL);
        eventButton.setTypeface(null, Typeface.NORMAL);

        venueButton.setTextColor(Color.BLACK);
        artistButton.setTextColor(Color.BLACK);
        eventButton.setTextColor(Color.BLACK);

        OrganizationListViewAdapter OrgAdapter;

        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mQuickieListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);

        //Creating an variable of type Listview Adapter to create the list view.
        OrgAdapter=new OrganizationListViewAdapter(this, mOutfit); //Calling the adapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(OrgAdapter);
    }

    @OnClick(R.id.eventButton)
    public void eventButton(View view) {

        mListView.setAdapter(adapter);

        eventButton.setTextColor(Color.BLUE);
        eventButton.setTypeface(null, Typeface.BOLD);

        venueButton.setTypeface(null, Typeface.NORMAL);
        orgButton.setTypeface(null, Typeface.NORMAL);
        artistButton.setTypeface(null, Typeface.NORMAL);

        venueButton.setTextColor(Color.BLACK);
        artistButton.setTextColor(Color.BLACK);
        orgButton.setTextColor(Color.BLACK);

        TextView Admission = (TextView) findViewById(R.id.admissionTextView);
        TextView Price = (TextView) findViewById(R.id.priceDisplay);
        SeekBar prce = (SeekBar) findViewById(R.id.priceBar);

        Admission.setVisibility(View.VISIBLE);
        Price.setVisibility(View.VISIBLE);
        prce.setVisibility(View.VISIBLE);
        mQuickieListView.setVisibility(view.VISIBLE);
        linearCalendar.setVisibility(view.VISIBLE);
    }

    @OnClick(R.id.artistButton)
    public void artistButton(View view) {
        artistButton.setTextColor(Color.BLUE);
        artistButton.setTypeface(null, Typeface.BOLD);

        venueButton.setTypeface(null, Typeface.NORMAL);
        orgButton.setTypeface(null, Typeface.NORMAL);
        eventButton.setTypeface(null, Typeface.NORMAL);

        venueButton.setTextColor(Color.BLACK);
        eventButton.setTextColor(Color.BLACK);
        orgButton.setTextColor(Color.BLACK);

        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mQuickieListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);

        ArtistsListViewAdapter artistsListViewAdapter;

        //Creating an variable of type Listview Adapter to create the list view.
        artistsListViewAdapter=new ArtistsListViewAdapter(this, mArtists); //Calling the adapter mListView to help set the List



        //Sets the Adapter from the class Listview Adapter.
        mListView.setAdapter(artistsListViewAdapter);
    }

    //Button Implementation for navigating to the Map from mListView.
    @OnClick(R.id.MapButton)
    public void startMapActivity(View view)
    {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
        finish();
    }

    //Button Implementation for navigating to the Dashboard from mListView.
    @OnClick(R.id.DashboardButton)
    public void startDashboardActivity(View view)
    {
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}