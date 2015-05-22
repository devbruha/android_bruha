package com.bruha.bruha.Views;

import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bruha.bruha.Adapters.CategoryAdapter;
import com.bruha.bruha.Adapters.QuickieAdapter;
import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Processing.FilterGen;
import com.bruha.bruha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private ArrayList<Items>mainList;

    private LinearLayout mLinearListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        setPanel();

        setQuickieList();

        setCategoryList();


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

    private void setQuickieList(){

        mLinearListView = (LinearLayout) findViewById(R.id.quickie_listview);

        FilterGen quickieGen = new FilterGen();
        mainList = quickieGen.initRecommended();

        QuickieAdapter adapter = new QuickieAdapter(this, mLinearListView, mainList);
        adapter.set();
    }

    private void setCategoryList(){

        mLinearListView = (LinearLayout) findViewById(R.id.category_listview);

        //Make array list one is for mainlist and other is for sublist

        FilterGen catGen = new FilterGen();
        mainList = catGen.initCategory();

        CategoryAdapter adapter = new CategoryAdapter(this, mLinearListView, mainList);
        adapter.set();
    }

    private void setAdmissionPrice(){

    }

}
