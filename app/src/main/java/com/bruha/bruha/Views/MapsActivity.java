// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bruha.bruha.Adapters.MapListViewAdapter;
import com.bruha.bruha.Adapters.MapOrganizationListViewAdapter;
import com.bruha.bruha.Adapters.MapVenListViewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.InjectView;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //The arrays that are used to store the information of mEvents/Artist/Venue/Outfits
    ArrayList<Event> mEvents;
    ArrayList<Venue> mVenues;
    ArrayList<Organizations> mOrganizations;
    ArrayList<String> eventaddictID;
    ArrayList<String> venueaddictID;
    ArrayList<String> orgaddictID;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    @InjectView(android.R.id.list) ListView mListView;
    MapListViewAdapter adapter;
    //The filter things to change everytime
    LinearLayout linearCalendar ;
    TextView admission;
    TextView mPrice;
    SeekBar prce;
    //Initualiing the Filter Obects to hide and display everytime Venue,Artist,Event and Outfit Filters are applied.
    LinearLayout mEventCategoryListView;
    LinearLayout mVenueCategoryListView;
    LinearLayout mArtistCategoryListView;
    LinearLayout mOrganizationCategoryListView;
    //Filter stuff:
    //The Linear layout to be set OnCLickListener to and background changing.
    @InjectView(R.id.venueButton) LinearLayout venueButton;
    @InjectView(R.id.artistButton) LinearLayout artistButton;
    @InjectView(R.id.eventButton) LinearLayout eventButton;
    @InjectView(R.id.outfitButton) LinearLayout orgButton;
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
    //Icon Buttons
    @InjectView(R.id.listButton) ImageView listButton;
    @InjectView(R.id.dudeButton) ImageView dudeButton;
    //private UserCustomFilters mUserCustomFilters;
    protected static final String TAG = "basic-location-sample";
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected String mLatitudeText;
    protected String mLongitudeText;
    HashMap<String, Marker> markerMap = new HashMap<>();
    // Variables for the marker clicks one for lat/lng storing and one for the applicable events
    LatLng venueLocation;
    //The arrays that are used to store the selected mEvents/Artist/Venue/Outfits when clicked on a certain marker.
    ArrayList<Event> selectedEvents = new ArrayList<>();
    ArrayList<Venue> selectedVenues = new ArrayList<>();
    ArrayList<Organizations> selectedOrg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(this);

        setButtons();    //Resizes the List and Dashboard navigation buttons.
        // Map and Filter setup
        buildGoogleApiClient();
        setUpMapIfNeeded();
        setUpFilters();
        retrieveEvents();
        setEventMarkers();
        setUpperPanel();
        setVariables(); //Sets the variables for the filter layout to be accessed and changed later.

        //Checking which filter was selecred before opening the activity and opening that one
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
            orgButton(null);
        }

        else{
            eventButton(null);
        }

        setImages();  //Sets the images for the buttons.
    }

    private void setVariables()
    {
        //Setting the filter stuff:
        linearCalendar = (LinearLayout) findViewById(R.id.calendarView);
        prce = (SeekBar) findViewById(R.id.priceBar);
        mPrice = (TextView) findViewById(R.id.priceDisplay);
        admission = (TextView) findViewById(R.id.admissionTextView);
        mEventCategoryListView = (LinearLayout) findViewById(R.id.event_category_listview);
        mVenueCategoryListView = (LinearLayout) findViewById(R.id.venue_category_listview);
        mArtistCategoryListView = (LinearLayout) findViewById(R.id.artist_category_listview);
        mOrganizationCategoryListView = (LinearLayout) findViewById(R.id.organization_category_listview);
    }

    private void setButtons()
    {
        //Resizing the Image Icons:
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height =  (int)Math.round(height * .07);
        dudeButtonLayoutParams.width =  (int)Math.round(height * .07);

        ViewGroup.LayoutParams mapButtonLayoutParams = listButton.getLayoutParams();
        mapButtonLayoutParams.height =  (int)Math.round(height * .07);
        mapButtonLayoutParams.width =  (int)Math.round(height * .07);

    }

    private void setImages()
    {
        //Sets the images for the buttons in the activity.

        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));
        listButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.listicon, 30));
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
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(listButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        listButton.setAlpha(1f);
                        startListViewActivity(v);
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    // Function to ensure a map has not already been instantiated
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    private void setUpMap() {
        // Setting default location to mcmaster, eventually will be set to user location
        if(mLastLocation != null){

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 14.0f));
        }
        else{

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.2500, -79.919501), 14.0f));
        }
    }

    //The method that gets the information of Event/Artist/Venue/Outfits from the local database and stores them to the arrays.
    private void retrieveEvents(){
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        //Gets the information and sets them to the defined arrays.
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenues = sqLiteUtils.getVenuesInfo(dbHelper);
        mOrganizations = sqLiteUtils.getOrganizationsInfo(dbHelper);
        eventaddictID = sqLiteUtils.getEventAddictions(dbHelper);
        venueaddictID = sqLiteUtils.getVenueAddictions(dbHelper);
        orgaddictID = sqLiteUtils.getOrgAddictions(dbHelper);
    }

    //Sets the Markers for the Outfits and calls the Adapter to set the mListView to the OutfitListView Adapter.
    private void setOrgMarkers(){
        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_upper);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        final SlidingUpPanelLayout mLowerLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_lower);

        for(int i=0;i< mOrganizations.size();i++){

            double eventLat = mOrganizations.get(i).getLat();
            double eventLng = mOrganizations.get(i).getLng();

            String eventName = mOrganizations.get(i).getOrgName();

            LatLng eventLocation = new LatLng(eventLat, eventLng);

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(eventName));
            Bitmap bitmapIcon = setOrgIcon(mOrganizations.get(i));
            BitmapDescriptor bitmapDescriptorIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
            eventMarker.setIcon(bitmapDescriptorIcon);

            if(MyApplication.sourceOrganizationsID.contains(mOrganizations.get(i).getOrgId())) {

                eventMarker.setVisible(false);
            }
            else if(MyApplication.sourceOrganizationsID.contains("All")){

                eventMarker.setVisible(true);
            }
            else{

                eventMarker.setVisible(true);
            }

            markerMap.put(mOrganizations.get(i).getOrgId()+"",eventMarker);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    selectedOrg.clear();
                    selectedEvents.clear();
                    selectedVenues.clear();

                    venueLocation = marker.getPosition();

                    double venueLat = venueLocation.latitude;
                    double venueLon = venueLocation.longitude;

                    for (int i = 0; i < mOrganizations.size(); i++) {

                        if (mOrganizations.get(i).getLat() == venueLat &&
                                mOrganizations.get(i).getLng() == venueLon) {

                            if (!MyApplication.sourceEventsID.contains(mOrganizations.get(i).getOrgId())) {

                                selectedOrg.add(mOrganizations.get(i));
                            }
                        }
                    }

                    setOrgAdapter();

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    mLowerLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return false;
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            });

        }
    }

    //Sets the Markers for the Venue and Calls the Adapter to set the VenueListView Adapter
    private void setVenueMarkers(){

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_upper);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        final SlidingUpPanelLayout mLowerLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_lower);

        for(int i=0;i< mVenues.size();i++){

            double eventLat = mVenues.get(i).getLat();
            double eventLng = mVenues.get(i).getLng();

            String eventName = mVenues.get(i).getVenueName();

            LatLng eventLocation = new LatLng(eventLat, eventLng);


            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(eventName));
            Bitmap bitmapIcon = setVenueIcon(mVenues.get(i));
            BitmapDescriptor bitmapDescriptorIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
            eventMarker.setIcon(bitmapDescriptorIcon);


            if(MyApplication.sourceVenuesID.contains(mVenues.get(i).getVenueId())) {

                eventMarker.setVisible(false);
            }
            else if(MyApplication.sourceVenuesID.contains("All")){

                eventMarker.setVisible(true);
            }
            else{

                eventMarker.setVisible(true);
            }

            markerMap.put(mVenues.get(i).getVenueId()+"",eventMarker);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    selectedVenues.clear();
                    selectedEvents.clear();
                    selectedOrg.clear();

                    venueLocation = marker.getPosition();

                    double venueLat = venueLocation.latitude;
                    double venueLon = venueLocation.longitude;

                    for (int i = 0; i < mVenues.size(); i++) {

                        if (mVenues.get(i).getLat() == venueLat &&
                                mVenues.get(i).getLng() == venueLon) {

                            if (!MyApplication.sourceVenuesID.contains(mVenues.get(i).getVenueId())) {

                                selectedVenues.add(mVenues.get(i));
                            }
                        }
                    }

                    setVenAdapter(); //Sets the Adapter of the mListView to the respective Venue's Adapter.

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    mLowerLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return false;
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            });

        }
    }

    //Sets the Adapter of the Outfit mListView.
    private void setOrgAdapter(){
        MapOrganizationListViewAdapter orgAdapter=new MapOrganizationListViewAdapter(this, selectedOrg,orgaddictID); //Calling the eventAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(orgAdapter);


    }

    //Sets the Adapter of the Venue mListView.
    private void setVenAdapter(){
        MapVenListViewAdapter mapAdapter=new MapVenListViewAdapter(this, selectedVenues,venueaddictID); //Calling the eventAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(mapAdapter);
    }

    //Method to set the icon of the event.
    public Bitmap setEventIcon(Event event) {
        if(event.getEventPrimaryCategory().contains("Club"))
        {return svgToBitmap(getResources(), R.raw.club, 30);}

        else if(event.getEventPrimaryCategory().contains("Performing"))
        {return svgToBitmap(getResources(), R.raw.performing, 30);}

        else if(event.getEventPrimaryCategory().contains("Business"))
        {return svgToBitmap(getResources(), R.raw.business, 30);}

        else if(event.getEventPrimaryCategory().contains("Ceremony"))
        {return svgToBitmap(getResources(), R.raw.ceremony, 30);}

        else if(event.getEventPrimaryCategory().contains("Tech"))
        {return svgToBitmap(getResources(), R.raw.tech, 30);}

        else if(event.getEventPrimaryCategory().contains("Comedy"))
        {return svgToBitmap(getResources(), R.raw.comedy, 30);}

        else if(event.getEventPrimaryCategory().contains("Fashion"))
        {return svgToBitmap(getResources(), R.raw.fashion, 30);}

        else if(event.getEventPrimaryCategory().contains("Festivals"))
        {return svgToBitmap(getResources(), R.raw.festivals, 30);}

        else if(event.getEventPrimaryCategory().contains("Film"))
        {return svgToBitmap(getResources(), R.raw.film, 30);}

        else if(event.getEventPrimaryCategory().contains("Food"))
        {return svgToBitmap(getResources(), R.raw.food, 30);}

        else if(event.getEventPrimaryCategory().contains("Party"))
        {return svgToBitmap(getResources(), R.raw.party, 30);}

        else if(event.getEventPrimaryCategory().contains("Music"))
        {return svgToBitmap(getResources(), R.raw.music, 30);}

        else if(event.getEventPrimaryCategory().contains("Political"))
        {return svgToBitmap(getResources(), R.raw.political, 30);}

        else if(event.getEventPrimaryCategory().contains("School"))
        {return svgToBitmap(getResources(), R.raw.school, 30);}

        else if(event.getEventPrimaryCategory().contains("Sports"))
        {return svgToBitmap(getResources(), R.raw.sports, 30);}

        else if(event.getEventPrimaryCategory().contains("Tour"))
        {return svgToBitmap(getResources(), R.raw.tour, 30);}

        else if(event.getEventPrimaryCategory().contains("Arts"))
        {return svgToBitmap(getResources(), R.raw.arts, 30);}

        else if(event.getEventPrimaryCategory().contains("Social"))
        {return svgToBitmap(getResources(), R.raw.social, 30);}

        return null;
    }

    //Method to set the icon of the venue
    public Bitmap setVenueIcon(Venue venue) {
        if(venue.getVenuePrimaryCategory().contains("Amphitheatre"))
        {return svgToBitmap(getResources(), R.raw.venamphiteather, 30);}

        if(venue.getVenuePrimaryCategory().contains("Arena"))
        {return svgToBitmap(getResources(), R.raw.venarena, 30);}

        else if(venue.getVenuePrimaryCategory().contains("Bar/Pub"))
        {return svgToBitmap(getResources(), R.raw.venbars, 30);}

        else if(venue.getVenuePrimaryCategory().contains("Casino"))
        {return svgToBitmap(getResources(), R.raw.vencasino, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Church"))
        {return svgToBitmap(getResources(), R.raw.venchurch, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Cinema"))
        {return svgToBitmap(getResources(), R.raw.vencinema, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Club"))
        {return svgToBitmap(getResources(), R.raw.venclubs, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Coffee"))
        {return svgToBitmap(getResources(), R.raw.vencoffee, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Comedy"))
        {return svgToBitmap(getResources(), R.raw.vencomedy, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Community"))
        {return svgToBitmap(getResources(), R.raw.vencommunity, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Fairgrounds"))
        {return svgToBitmap(getResources(), R.raw.venfairground, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Gallery"))
        {return svgToBitmap(getResources(), R.raw.vengallery, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Park"))
        {return svgToBitmap(getResources(), R.raw.venparks, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Restaurant"))
        {return svgToBitmap(getResources(), R.raw.venrestauratns, 30);}

        else if (venue.getVenuePrimaryCategory().contains("House/Residence"))
        {return svgToBitmap(getResources(), R.raw.venhouse, 30);}

        else if (venue.getVenuePrimaryCategory().contains("School"))
        {return svgToBitmap(getResources(), R.raw.venschool, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Sports/Arena"))
        {return svgToBitmap(getResources(), R.raw.venarena, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Store"))
        {return svgToBitmap(getResources(), R.raw.venstore, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Theatre"))
        {return svgToBitmap(getResources(), R.raw.ventheater, 30);}

        return svgToBitmap(getResources(), R.raw.vencommunity, 30);
    }

    //Method to set the icon of the event.
    public Bitmap setOrgIcon(Organizations org) {
        if(org.getOrgPrimaryCategory().contains("Academic"))
        {return svgToBitmap(getResources(), R.raw.orgacademic, 30);}

        else if(org.getOrgPrimaryCategory().contains("Business"))
        {return svgToBitmap(getResources(), R.raw.orgbusiness, 30);}

        else if(org.getOrgPrimaryCategory().contains("Charity"))
        {return svgToBitmap(getResources(), R.raw.orgcharity, 30);}

        else if (org.getOrgPrimaryCategory().contains("Fashion"))
        {return svgToBitmap(getResources(), R.raw.orgfashion, 30);}

        else if (org.getOrgPrimaryCategory().contains("Promoter"))
        {return svgToBitmap(getResources(), R.raw.orgpromoter, 30);}

        else if (org.getOrgPrimaryCategory().contains("Fraternity"))
        {return svgToBitmap(getResources(), R.raw.orgfraternity, 30);}

        else if (org.getOrgPrimaryCategory().contains("Not-for-profit"))
        {return svgToBitmap(getResources(), R.raw.orgnonprofit, 30);}

        else if (org.getOrgPrimaryCategory().contains("Sports"))
        {return svgToBitmap(getResources(), R.raw.orgsports, 30);}

        else if (org.getOrgPrimaryCategory().contains("Student"))
        {return svgToBitmap(getResources(), R.raw.orgstudent, 30);}

        else if (org.getOrgPrimaryCategory().contains("Religion"))
        {return svgToBitmap(getResources(), R.raw.orgreligon, 30);}

        return svgToBitmap(getResources(), R.raw.orgpromoter, 30);
    }

    //Sets the Markers for the mEvents and Calls the Adapter to set the EventListView Adapter.
    private void setEventMarkers(){

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_upper);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        final SlidingUpPanelLayout mLowerLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_lower);

        for(int i=0;i< mEvents.size();i++){

            double eventLat = mEvents.get(i).getEventLatitude();
            double eventLng = mEvents.get(i).getEventLongitude();

            String eventName = mEvents.get(i).getEventName();

            LatLng eventLocation = new LatLng(eventLat, eventLng);

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(eventName));
            Bitmap bitmapIcon = setEventIcon(mEvents.get(i));
            BitmapDescriptor bitmapDescriptorIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
            eventMarker.setIcon(bitmapDescriptorIcon);

            if(MyApplication.sourceEventsID.contains(mEvents.get(i).getEventid())) {

                eventMarker.setVisible(false);
            }
            else if(MyApplication.sourceEventsID.contains("All")){

                eventMarker.setVisible(true);
            }
            else{

                eventMarker.setVisible(true);
            }

            markerMap.put(mEvents.get(i).getEventid(),eventMarker);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    selectedEvents.clear();
                    selectedOrg.clear();
                    selectedVenues.clear();

                    marker.showInfoWindow();

                    venueLocation = marker.getPosition();

                    double venueLat = venueLocation.latitude;
                    double venueLon = venueLocation.longitude;

                    for (int i = 0; i < mEvents.size(); i++) {

                        if (mEvents.get(i).getEventLatitude() == venueLat &&
                                mEvents.get(i).getEventLongitude() == venueLon) {

                            if (!MyApplication.sourceEventsID.contains(mEvents.get(i).getEventid())) {

                                selectedEvents.add(mEvents.get(i));
                            }
                        }
                    }

                    setEventAdapter(); //Calling the funtion to set the Adapter for the List.

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    mLowerLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return false;
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            });

        }
    }

    //Sets the Adapter of the Event mListView.
    private void setEventAdapter() {
        adapter=new MapListViewAdapter(this, selectedEvents,eventaddictID); //Calling the eventAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);
    }

    private void setUpperPanel(){
        // Storing the sliding panel into mLayout
        LinearLayout mLayout = (LinearLayout)findViewById(R.id.dragViewUpper);
        SlidingUpPanelLayout mSlidingPanel = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_upper);
        mSlidingPanel.setTouchEnabled(false);

       /// Log.v("Status bar height", getStatusBarHeight() + "");

        // Android functions to determine the screen dimensions

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        // Taking the status bar height into account for height calculations
        int workingHeight = height - getStatusBarHeight();

        // Retrieves the current parameters of the layout and storing them in variable params
        ViewGroup.LayoutParams params = mLayout.getLayoutParams();

        // Re-setting the height parameter to .35 the max screen height (status bar included)
        params.height =  (int)Math.round(workingHeight*.35);
    }

    // A function to determine the height of the status bar present in android devices
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void setUpFilters(){
        // Calling the FilterView class to set the layout for the filter.
        FilterView filterView = new FilterView(this, null,null,null,null, markerMap);
        filterView.init();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText =String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());

        }
        else {

            Toast.makeText(this,"No location detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    public void startDashboardActivity(View view){

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }


    public void startListViewActivity(View view){

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.outfitButton)
    public void orgButton(View view)
    {
        MyApplication.filterTracker = "Outfit"; //Checking which filter was open last time.
        mMap.clear();
        setOrgMarkers();

        //Changing Filters:
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitorange, 50));
        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mVenueCategoryListView.setVisibility(view.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.GONE);
        mOrganizationCategoryListView.setVisibility(view.VISIBLE);
        linearCalendar.setVisibility(view.GONE);
        outfitText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
    }

    @OnClick(R.id.eventButton)
    public void eventButton(View view)
    {
        MyApplication.filterTracker = "Event";  //Checking which filter was open last time and populating that one.
        mMap.clear();
        setEventMarkers();

        //Changing shit:
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
    }

    @OnClick(R.id.venueButton)
    public void venueButton(View view)
    {
        MyApplication.filterTracker = "Venue";  //Checking which filter was open last time and populating that one.
        mMap.clear();
        setVenueMarkers();

        //Changing Filters:
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
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
    }

    @OnClick(R.id.artistButton)
    public void artistButton(View view)
    {
        Toast.makeText(getApplicationContext(),"Still under development,sorry!",Toast.LENGTH_LONG).show();
    }

    //SVG Conversion:
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

    public Bitmap svgToBitmap(Resources res, int resource, int size) {
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp;
            bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);


            return bmp;
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
