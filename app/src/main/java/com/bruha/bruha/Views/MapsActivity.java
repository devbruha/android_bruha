package com.bruha.bruha.Views;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bruha.bruha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MapsActivity extends FragmentActivity {

    private SlidingUpPanelLayout mLayout;
    private LinearLayout dragLayout;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        dragLayout = (LinearLayout) findViewById(R.id.dragView);

        mLayout.setAnchorPoint(.9f);
        mLayout.setAnchorPoint(.5f);


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
}
