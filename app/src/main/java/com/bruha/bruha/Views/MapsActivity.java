package com.bruha.bruha.Views;

import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.SQLUtils;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ArrayList<Event> mEvents;       //The Array that will hold the Events that we will pass around(to Adapter,the List...)

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private UserCustomFilters mUserCustomFilters = new UserCustomFilters();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(this);

        // Map and Filter setup

        buildGoogleApiClient();

        setUpMapIfNeeded();
        setUpFilters();
        retrieveEvents();

        setEventMarkers();
        setUpperPanel();
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

    private void retrieveEvents(){

        // Create the local DB object

        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
    }

    private void setEventMarkers(){

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout_upper);

        for(int i=0;i< mEvents.size();i++){

            double eventLat = mEvents.get(i).getEventLatitude();
            double eventLng = mEvents.get(i).getEventLongitude();

            String eventName = mEvents.get(i).getEventName();

            LatLng eventLocation = new LatLng(eventLat, eventLng);

            Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventLocation).title(eventName).draggable(true));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
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

    private void setUpperPanel(){

        // Storing the sliding panel into mLayout

        LinearLayout mLayout = (LinearLayout)findViewById(R.id.dragViewUpper);

        Log.v("Status bar height", getStatusBarHeight() + "");

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

        // Calling the FilterView class to set the layout for the filters

        FilterView filterView = new FilterView(this);
        mUserCustomFilters = filterView.init();
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

        } else {
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
        Log.v("Big Filter Test", mUserCustomFilters.getCategoryFilter().keySet() + "");
        Log.v("Big Filter Test", mUserCustomFilters.getAdmissionPriceFilter() + "");

    }
}
