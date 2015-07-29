package com.bruha.bruha.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ShowOnMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    ArrayList<Event> mEvents= new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();
    ArrayList<Organizations> mOutfit = new ArrayList<>();
    Event event;
    Venues venue;
    Artists artist;
    Organizations outfit;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_on_map);


        init(); //Calling to initialized Array selectedDateEvents to loop through to find the Event to be displayed.

        //Getting the ID of the event that will need to be displayed
        Intent intent = getIntent();
        String id= intent.getStringExtra("Id");
         type = intent.getStringExtra("Type");

        if(type.equals("Event")) {
            //Finding out and storing the event that is to be displayed.
            for (Event x : mEvents) {
                if (x.getEventid().equals(id)) {
                    event = x;
                }
            }
        }

        else if(type.equals("Venue"))
        {
            //Finding out and storing the event that is to be displayed.
            for (Venues x : mVenues) {
                if (x.getVenueId().equals(id)) {
                    venue = x;
                }
            }
        }

        else if(type.equals("Outfit"))
        {
            //Finding out and storing the event that is to be displayed.
            for (Organizations x : mOutfit) {
                if (x.getOrgId().equals(id)) {
                    outfit = x;
                }
            }
        }

        else
        {
            //Finding out and storing the event that is to be displayed.
            for (Artists x : mArtists) {
                if (x.getArtistId().equals(id)) {
                    artist = x;
                }
            }
        }


        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {



        if(type.equals("Event")) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.getEventLatitude(), event.getEventLongitude()), 14.0f));
            LatLng eventLocation = new LatLng(event.getEventLatitude(), event.getEventLongitude());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(event.getEventName()));
        }

       else if(type.equals("Outfit"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(outfit.getLat(), outfit.getLng()), 14.0f));
            LatLng eventLocation = new LatLng(outfit.getLat(), outfit.getLng());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(outfit.getOrgName()));
        }

       else if(type.equals("Venue"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(venue.getLat(), venue.getLng()), 14.0f));
            LatLng eventLocation = new LatLng(venue.getLat(), venue.getLng());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(venue.getVenueName()));

        }

        else {
           /* mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(artist.get, event.getEventLongitude()), 14.0f));
            LatLng eventLocation = new LatLng(event.getEventLatitude(), event.getEventLongitude());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(event.getEventName()));
            */

        }

    }



private void init()
{
    // Create the local DB object
    SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
    SQLiteUtils sqLiteUtils = new SQLiteUtils();

    //Assigns the array containing the list of events.
    mEvents = sqLiteUtils.getEventInfo(dbHelper);
    mVenues= sqLiteUtils.getVenuesInfo(dbHelper);
    mOutfit= sqLiteUtils.getOutfitsInfo(dbHelper);
    mArtists= sqLiteUtils.getArtistInfo(dbHelper);
}


}