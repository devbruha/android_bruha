// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bruha.bruha.Adapters.ArtistsListViewAdapter;
import com.bruha.bruha.Adapters.EventListviewAdapter;
import com.bruha.bruha.Adapters.OrganizationListViewAdapter;
import com.bruha.bruha.Adapters.VenueListViewAdapter;
import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListActivity extends FragmentActivity implements ObservableScrollViewCallbacks {
    //The Arrays that will contain the information retrieved from the local database.
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Organizations> mOrganizations = new ArrayList<>();
    ArrayList<Venue> mVenues = new ArrayList<>();
    ArrayList<Artist> mArtists = new ArrayList<>();
    ArrayList<String> addictEventId = new ArrayList<>();
    ArrayList<String> addictVenueId = new ArrayList<>();
    ArrayList<String> addictArtistId = new ArrayList<>();
    ArrayList<String> addictOutfitId = new ArrayList<>();
    public static ListActivity instance = null;   //Some variable.
    //Initualiing the Filter Obects to hide and display everytime Venue,Artist,Event and Outfit Filters are applied.
    LinearLayout mEventCategoryListView;
    LinearLayout mVenueCategoryListView;
    LinearLayout mArtistCategoryListView;
    LinearLayout mOrganizationCategoryListView;
    //Filters stuff that will hide when changed filter.
    FilterView filterView;
    LinearLayout linearCalendar ;
    TextView admission;
    TextView mPrice;
    SeekBar prce;
    //Backup lists so the filters can work.
    ArrayList<Event> backupEventList;         //Array backupEventList of the whole list,since mEvent changes when we update the eventAdapter in filter save button.
    ArrayList<Venue> backupVenueList;         //Array backupEventList of the whole list,since mEvent changes when we update the eventAdapter in filter save button.
    ArrayList<Artist> backupArtistList;         //Array backupEventList of the whole list,since mEvent changes when we update the eventAdapter in filter save button.
    ArrayList<Organizations> backupOrganizationList;         //Array backupEventList of the whole list,since mEvent changes when we update the eventAdapter in filter save button.
    //The adapters to be used.
    EventListviewAdapter eventAdapter;
    VenueListViewAdapter venueAdapter;
    OrganizationListViewAdapter organizationAdapter;
    ArtistsListViewAdapter artistAdapter;
    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ObservableListView mListView;
    //The Linear layout to be set OnCLickListener to and background changing.
    @InjectView(R.id.venueButton) LinearLayout venueButton;
    @InjectView(R.id.artistButton) LinearLayout artistButton;
    @InjectView(R.id.outfitButton) LinearLayout orgButton;
    @InjectView(R.id.eventButton) LinearLayout eventButton;
    //The Image Views to set and Change for the Filters
    @InjectView(R.id.eventButtonImage) ImageView eventImage;
    @InjectView(R.id.venueButtonImage) ImageView venueImage;
    @InjectView(R.id.artistButtonImage) ImageView artistImage;
    @InjectView(R.id.outfitButtonImage) ImageView outfitImage;
    //The TextViews to be RESIZED(HELLO,RESIZE EM!!) and Changed.
    @InjectView(R.id.filtereventtext) TextView eventText;
    @InjectView(R.id.filtervenuetext) TextView venueText;
    @InjectView(R.id.filterartisttext) TextView artistText;
    @InjectView(R.id.filteroutfittext) TextView outfitText;
    //Buttons
    ImageView mapButton;
    ImageView dudeButton;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.

        mListView.setScrollViewCallbacks(this);

        actionbar();

        init();     //Initialize the variables from local database.

        //Filter sttuff.
        if(MyApplication.sourceEvents.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            eventAdapter = new EventListviewAdapter(this, mEvents,addictEventId); //Calling the eventAdapter mListView to help set the List
        }
        else{
            eventAdapter = new EventListviewAdapter(this, MyApplication.sourceEvents,addictEventId);
        }

        if(MyApplication.sourceVenues.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            venueAdapter=new VenueListViewAdapter(this, mVenues,addictVenueId); //Calling the eventAdapter mListView to help set the List
        }
        else{
            //Creating an variable of type Listview Adapter to create the list view.
            venueAdapter=new VenueListViewAdapter(this, MyApplication.sourceVenues,addictVenueId); //Calling the eventAdapter mListView to help set the List
        }

        if(MyApplication.sourceArtists.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            artistAdapter =new ArtistsListViewAdapter(this, mArtists,addictArtistId); //Calling the eventAdapter mListView to help set the List
        }
        else{
            //Creating an variable of type Listview Adapter to create the list view.
            artistAdapter =new ArtistsListViewAdapter(this, MyApplication.sourceArtists,addictArtistId); //Calling the eventAdapter mListView to help set the List
        }

        if(MyApplication.sourceOrganizations.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            organizationAdapter =new OrganizationListViewAdapter(this, mOrganizations,addictOutfitId); //Calling the eventAdapter mListView to help set the List
        }
        else{
            //Creating an variable of type Listview Adapter to create the list view.
            organizationAdapter =new OrganizationListViewAdapter(this, MyApplication.sourceOrganizations,addictOutfitId); //Calling the eventAdapter mListView to help set the List
        }

        setUpFilters(); //Method to set up the filters in the view.

        //Setting the Filters to the Filter Item once they have been declared and set in the view,later to be altered with
        mEventCategoryListView = (LinearLayout) findViewById(R.id.event_category_listview);
        mVenueCategoryListView = (LinearLayout) findViewById(R.id.venue_category_listview);
        mArtistCategoryListView = (LinearLayout) findViewById(R.id.artist_category_listview);
        mOrganizationCategoryListView = (LinearLayout) findViewById(R.id.organization_category_listview);
        //assigning the global variables the respective views.
        linearCalendar = (LinearLayout) findViewById(R.id.calendarView);
        prce = (SeekBar) findViewById(R.id.priceBar);
        mPrice = (TextView) findViewById(R.id.priceDisplay);
        admission = (TextView) findViewById(R.id.admissionTextView);
        ImageView swipeup = (ImageView) findViewById(R.id.swipeup);
        swipeup.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.swipeup, 20));
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(eventAdapter);

        //Checking which filter was chosen before opening the activity.
        if(MyApplication.filterTracker.equals("Event"))
        {
            eventButton(null);
        }

       else if(MyApplication.filterTracker.equals("Venue"))
        {
            venueButton(null);
        }

        else if(MyApplication.filterTracker.equals("Outfit"))
        {
            organizationButton(null);
        }

        else{
            artistButton(null);
        }

    }

    private void actionbar()
    {
        /*
        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        */
        dudeButton =(ImageView) findViewById(R.id.DashboardButton);
        mapButton = (ImageView) findViewById(R.id.MapButton);
        setsize(); //Method to set the size of the buttons in the view.
       // mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setsize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        // Storing the screen height into an int variable..
        int height = size.y;
        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height =  (int)Math.round(height*.07);
        dudeButtonLayoutParams.width =  (int)Math.round(height*.07);
        ViewGroup.LayoutParams mapButtonLayoutParams = mapButton.getLayoutParams();
        mapButtonLayoutParams.height =  (int)Math.round(height*.07);
        mapButtonLayoutParams.width =  (int)Math.round(height*.07);

        setButtons();  //Method to create and set the Dashboard/Map Button.
    }

    private void setButtons() {
        //Sets the image and onclicklisteners for the Buttons for dashboard/map navigation.
        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));
        mapButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.mapicon, 30));
        dudeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(dudeButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        dudeButton.setAlpha(1f);
                        startDashboardActivity(v);
                    }
                });
                animator.start();
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mapButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        mapButton.setAlpha(1f);
                        startMapActivity(v);
                    }
                });
                animator.start();
            }
        });
    }

    private void init() {

        backupEventList = MyApplication.backupEventList;
        backupEventList.clear();

        backupVenueList = MyApplication.backupVenueList;
        backupVenueList.clear();

        backupArtistList = MyApplication.backupArtistList;
        backupArtistList.clear();

        backupOrganizationList = MyApplication.backupOrganizationList;
        backupOrganizationList.clear();

        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenues = sqLiteUtils.getVenuesInfo(dbHelper);
        mOrganizations = sqLiteUtils.getOrganizationsInfo(dbHelper);
        mArtists = sqLiteUtils.getArtistInfo(dbHelper);

        addictEventId = sqLiteUtils.getEventAddictions(dbHelper);
        addictVenueId = sqLiteUtils.getVenueAddictions(dbHelper);
        addictOutfitId = sqLiteUtils.getOrgAddictions(dbHelper);
        addictArtistId = sqLiteUtils.getArtistAddictions(dbHelper);

        for(Event x:mEvents){

            backupEventList.add(x);
        }

        for(Venue x:mVenues){

            backupVenueList.add(x);
        }

        for(Artist x:mArtists){

            backupArtistList.add(x);
        }
        for(Organizations x:mOrganizations){

            backupOrganizationList.add(x);
        }
    }

    private void setUpFilters(){
        // Calling the FilterView class to set the layout for the filters
        filterView = new FilterView(this, eventAdapter,venueAdapter, artistAdapter, organizationAdapter, null);
        filterView.init();
    }

    //venueButton Implemented to switch the mListView to show List of Venue.
    @OnClick(R.id.venueButton)
    public void venueButton(View view) {

        MyApplication.filterTracker = "Venue";  //Letting App know which filter was open last.
        //Changing Filters
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venueorange, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        mVenueCategoryListView.setVisibility(view.VISIBLE);
        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.GONE);
        mOrganizationCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);
        venueText.setTextColor(Color.parseColor("#FFFFBB33"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);
    }

    //outfitButton Implemented to switch the mListView to show List of Outfits.
    @OnClick(R.id.outfitButton)
    public void organizationButton(View view) {

        MyApplication.filterTracker = "Outfit"; //Letting App know which filter was open last.

        //Changing shit:
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitorange, 50));
        outfitText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
        mOrganizationCategoryListView.setVisibility(view.VISIBLE);
        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mVenueCategoryListView.setVisibility(view.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(organizationAdapter);
    }

    @OnClick(R.id.eventButton)
    public void eventButton(View view) {

        MyApplication.filterTracker = "Event";  //Letting the app know which filter was open last.
        //Changing Filter:
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventorange, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        eventText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
        TextView Admission = (TextView) findViewById(R.id.admissionTextView);
        TextView Price = (TextView) findViewById(R.id.priceDisplay);
        SeekBar prce = (SeekBar) findViewById(R.id.priceBar);
        Admission.setVisibility(View.VISIBLE);
        Price.setVisibility(View.VISIBLE);
        prce.setVisibility(View.VISIBLE);
        mVenueCategoryListView.setVisibility(view.GONE);
        mEventCategoryListView.setVisibility(view.VISIBLE);
        mArtistCategoryListView.setVisibility(view.GONE);
        mOrganizationCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.VISIBLE);
        //Setting the adapter.
        mListView.setAdapter(eventAdapter);
    }

    @OnClick(R.id.artistButton)
    public void artistButton(View view) {

        MyApplication.filterTracker = "Artist";
        //Changing Filters:
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistorange, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.VISIBLE);
        mOrganizationCategoryListView.setVisibility(view.GONE);
        mVenueCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);
        artistText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        //Sets the Adapter from the class Listview Adapter.
        mListView.setAdapter(artistAdapter);
    }

    //Button Implementation for navigating to the Map from mListView.
    public void startMapActivity(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
        finish();
    }

    //Button Implementation for navigating to the Dashboard from mListView.
    public void startDashboardActivity(View view) {
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    //SVG Conversion.
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        }catch(SVGParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (mActionBar == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (mActionBar.isShowing()) {
                mActionBar.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!mActionBar.isShowing()) {
                mActionBar.show();
            }
        }

    }
}