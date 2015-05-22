package com.bruha.bruha.Views;

import android.content.res.Configuration;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.bruha.bruha.Adapters.FilterAdapter;
import com.bruha.bruha.Processing.ExpandableListDataProvider;
import com.bruha.bruha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        setPanel();

        setExpLists();
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
            }
        }
    }

    private void setUpMap() {

        // Setting default location to mcmaster, eventually will be set to user location

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.2500,-79.919501), 14.0f));
    }

    // A function that shall be used to dynamically set the max height of the sliding panel

    private void setPanel(){

        SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        mLayout.setAnchorPoint(.5f);

        // Storing the sliding panel (lin layout) into a linear layout variable

        LinearLayout dragLayout = (LinearLayout) findViewById(R.id.dragView);

        // Android functions to determine the screen dimensions

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .75 the max screen height

        params.height =  (int)Math.round(height*.75);
    }

    private void setExpLists(){

        // Getting an instance of the recommended expandable list

        ExpandableListView QuickieExpList = (ExpandableListView) findViewById(R.id.recommendedExpList);

        // Storing the list of fields into a hashmap with key "Quickie"

        HashMap<String, List<String>> QuickieFields = ExpandableListDataProvider.getQuickieInfo();

        // Retrieving the parent title (quickie) and storing into a list

        List<String> QuickieList = new ArrayList<String>(QuickieFields.keySet());

        // Passing the hashmap containing all quickie fields aswell as a list of keys for the
        // hash map in the adapter

        FilterAdapter adapter = new FilterAdapter(this, QuickieFields, QuickieList);

        QuickieExpList.setAdapter(adapter);

    }

}
