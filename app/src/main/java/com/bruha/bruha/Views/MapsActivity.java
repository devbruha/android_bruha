package com.bruha.bruha.Views;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private UserCustomFilters mUserCustomFilters = new UserCustomFilters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(this);

        // Map and Filter setup

        setUpMapIfNeeded();
        setUpFilters();
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

    private void setUpFilters(){

        // Calling the FilterView class to set the layout for the filters

        FilterView filterView = new FilterView(this);
        mUserCustomFilters = filterView.init();
    }

    @OnClick(R.id.dudeButton)
    public void startDashboardActivity(View view){

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.listButton)
    public void startListViewActivity(View view){

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.filterSaveButton)
    public void saveFilters(View view){

        // Testing if the filters are being saved

        Log.v("Big Filter Test", mUserCustomFilters.getQuickieFilter()+"");
        Log.v("Big Filter Test", mUserCustomFilters.getDateFilter()+"");
        Log.v("Big Filter Test", mUserCustomFilters.getCategoryFilter().keySet()+"");
        Log.v("Big Filter Test", mUserCustomFilters.getAdmissionPriceFilter()+"");

    }
}
