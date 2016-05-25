package com.bruha.bruhaandroid.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by ArhamRazaMac on 16-05-24.
 */
public class ShowOnMapFragment extends Fragment{
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    //Initializing the Variables that will be used to populate our page.
    Event event;
    Venue venue;
    Organizations organization;

    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_show_on_map, container, false);

        grabInfo();
        setUpMapIfNeeded(); //Sets the map up.

        return view;
    }

    private void grabInfo() {
        // find type
        type = getArguments().getString("type");

        // event info
        if(type == "Event" ) {
            event = new Event();
            event.setEventid(getArguments().getString("id"));
            event.setEventName(getArguments().getString("name"));
            event.setEventLatitude(getArguments().getDouble("latitude"));
            event.setEventLongitude(getArguments().getDouble("longitude"));
            event.setEventPrimaryCategory(getArguments().getString("category"));
        }

        // Venue info
        if(type == "Venue" ) {
            venue = new Venue();
            venue.setVenueId(getArguments().getString("id"));
            venue.setVenueName(getArguments().getString("name"));
            venue.setLat(getArguments().getDouble("latitude"));
            venue.setLng(getArguments().getDouble("longitude"));
            venue.setVenuePrimaryCategory(getArguments().getString("category"));
        }

        // Organization info
        if(type == "Organization" ) {
            organization = new Organizations();
            organization.setOrgId(getArguments().getString("id"));
            organization.setOrgName(getArguments().getString("name"));
            organization.setLat(getArguments().getDouble("latitude"));
            organization.setLng(getArguments().getDouble("longitude"));
            organization.setOrgPrimaryCategory(getArguments().getString("category"));
        }

    }

    @Override
    public void onResume() {
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
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
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
        //Set the map up according to what type is selected and what Event
        if(type.equals("Event")) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.getEventLatitude(), event.getEventLongitude()), 14.0f));
            LatLng eventLocation = new LatLng(event.getEventLatitude(), event.getEventLongitude());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(event.getEventName()));

            Bitmap bitmapIcon = setEventIcon(event);
            BitmapDescriptor bitmapDescriptorIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
            eventMarker.setIcon(bitmapDescriptorIcon);
        }

        else if(type.equals("Organization"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(organization.getLat(), organization.getLng()), 14.0f));
            LatLng eventLocation = new LatLng(organization.getLat(), organization.getLng());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(organization.getOrgName()));
            Bitmap bitmapIcon = setOrgIcon(organization);
            BitmapDescriptor bitmapDescriptorIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
            eventMarker.setIcon(bitmapDescriptorIcon);
        }

        else if(type.equals("Venue"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(venue.getLat(), venue.getLng()), 14.0f));
            LatLng eventLocation = new LatLng(venue.getLat(), venue.getLng());

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(venue.getVenueName()));
            Bitmap bitmapIcon = setVenueIcon(venue);
            BitmapDescriptor bitmapDescriptorIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
            eventMarker.setIcon(bitmapDescriptorIcon);
        }


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

    //SVG Conversion:
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(this.getActivity().getApplicationContext(), resource);

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
            SVG svg = SVG.getFromResource(this.getActivity().getApplicationContext(), resource);

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
