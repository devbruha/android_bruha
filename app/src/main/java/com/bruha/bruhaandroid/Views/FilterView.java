// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Views;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bruha.bruhaandroid.Adapters.ArtistsListViewAdapter;
import com.bruha.bruhaandroid.Adapters.EventCategoryAdapter;
import com.bruha.bruhaandroid.Adapters.EventListviewAdapter;
import com.bruha.bruhaandroid.Adapters.OrganizationCategoryAdapter;
import com.bruha.bruhaandroid.Adapters.OrganizationListViewAdapter;
import com.bruha.bruhaandroid.Adapters.VenueCategoryAdapter;
import com.bruha.bruhaandroid.Adapters.VenueListViewAdapter;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.UserCustomFilters;
import com.bruha.bruhaandroid.Processing.FilterOut;
import com.bruha.bruhaandroid.R;
import com.google.android.gms.maps.model.Marker;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Thomas on 5/25/2015.
 */
public class FilterView {

    FilterOut filtering;

    Button filterClearButton;

    // Creating a UserCustomFilters object to store user filters

    UserCustomFilters userCustomFilters = MyApplication.userFilters;

    // Creating a CaldroidFragment object
    private CaldroidFragment caldroidFragment;

    ArrayList<String> calendarSelected;
    ArrayList<Date> datesSaved;
    ArrayList<String> quickieSaved;

    // Casting the passed activity as a Fragment activity
    private FragmentActivity mActivity;

    private EventListviewAdapter mEventAdapter;
    private VenueListViewAdapter mVenueAdapter;
    private ArtistsListViewAdapter mArtistAdapter;
    private OrganizationListViewAdapter mOrganizationAdapter;

    HashMap<String, Marker> markerMap = new HashMap<>();

    EventCategoryAdapter eventEventCategoryAdapter;
    VenueCategoryAdapter venueCategoryAdapter;
    OrganizationCategoryAdapter organizationCategoryAdapter;

    public FilterView(FragmentActivity activity,
                      EventListviewAdapter eventAdapter,
                      VenueListViewAdapter venueAdapter,
                      ArtistsListViewAdapter artistAdapter,
                      OrganizationListViewAdapter organizationAdapter,
                      HashMap markerHashMap){

        mActivity = activity;

        mEventAdapter = eventAdapter;
        mVenueAdapter = venueAdapter;
        mArtistAdapter = artistAdapter;
        mOrganizationAdapter = organizationAdapter;

        markerMap = markerHashMap;

        filtering = new FilterOut(activity);

        // Linking the local variables with their global counterparts

        calendarSelected = new ArrayList<>(userCustomFilters.getDateFilter());
        datesSaved = ((MyApplication) mActivity.getApplicationContext()).getSavedDates();

        quickieSaved = ((MyApplication) mActivity.getApplicationContext()).getSavedQuickie();
    }

    public void init(){

        filterClearButton = (Button)mActivity.findViewById(R.id.clearFilterButton);

        setPanel();

        // Setting and storing the quickie filters.

      //  setQuickieList();              //NO QUICKIE LIST FOR THE BETA VERSION!!

        // Simultaneously setting calendar and updating the user custom filters

        setCalendar();

        // Simultaneously setting the category lists and updating the user custom filters

        setEventCategoryList();

        setVenueCategoryList();

        setOrganizationCategoryList();

        //admission price is added to userFilters within its function

        setAdmissionPrice();

        filterClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SeekBar mSeekBar = (SeekBar) mActivity.findViewById(R.id.priceBar);
                mSeekBar.setProgress(-1);

                if(!userCustomFilters.getNonFormattedDateFilter().isEmpty()){

                    for(int i = 0; i<userCustomFilters.getNonFormattedDateFilter().size();i++){
                        caldroidFragment.setBackgroundResourceForDate(android.R.color.black, userCustomFilters.getNonFormattedDateFilter().get(i));
                    }
                }

                MyApplication.userFilters.setDateFilter(new ArrayList<String>());
                MyApplication.userFilters.setNonFormattedDateFilter(new ArrayList<Date>());

                caldroidFragment.clearSelectedDates();
                caldroidFragment.refreshView();

                MyApplication.userFilters.setCategoryFilter(new HashMap<String, ArrayList<String>>());

                LinearLayout mCategoryListView = (LinearLayout)mActivity.findViewById(R.id.event_category_listview);
                mCategoryListView.removeAllViews();

                setEventCategoryList();

                if(mEventAdapter != null) {

                    filtering.filterEventList(mEventAdapter);
                }

                if(markerMap!= null){
                    filtering.filterEventMap(markerMap);
                }
            }
        });
    }

    private void setPanel(){

        // Retrieving the device API level to determine if the modification to the sliding panel need
        //to be made (click instead of swiping)

        int device_sdk = android.os.Build.VERSION.SDK_INT;

        Log.v("DEVICE API", device_sdk + "");

        // Storing the sliding panel into mLayout

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout_lower);

        // Finding the handle layout

        LinearLayout handleLayout = (LinearLayout)mActivity.findViewById(R.id.handleLayout);

        mLayout.setDragView(handleLayout);

        // Setting an anchor point at the halfway point

        mLayout.setAnchorPoint(.5f);

        // Storing the sliding panel (lin layout) into a linear layout variable

        LinearLayout dragLayout = (LinearLayout)mActivity.findViewById(R.id.mapFilterLayout);

        // Android functions to determine the screen dimensions

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        // Taking the status bar height into account for height calculations

        int workingHeight = height - getStatusBarHeight();

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .65 the max screen height (status bar included)

        params.height =  (int)Math.round(workingHeight*.65);
    }

    // A function to determine the height of the status bar in all android devices

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mActivity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setEventCategoryList(){

        // Storing the quickie layout into mCategoryListview

        LinearLayout mCategoryListView = (LinearLayout)mActivity.findViewById(R.id.event_category_listview);

        // calling and setting the "eventAdapter" to set the list items

        eventEventCategoryAdapter = new EventCategoryAdapter(mActivity, mCategoryListView, MyApplication.mainList.get(1), mEventAdapter,markerMap);

        eventEventCategoryAdapter.set();

    }

    private void setVenueCategoryList(){

        // Storing the quickie layout into mQuickieListview

        LinearLayout mVenueListView = (LinearLayout)mActivity.findViewById(R.id.venue_category_listview);

        // calling and setting the "eventAdapter" to set the list items

        venueCategoryAdapter = new VenueCategoryAdapter(mActivity, mVenueListView, MyApplication.mainList.get(2), mVenueAdapter, markerMap);
        venueCategoryAdapter.set();
    }

    private void setOrganizationCategoryList(){

        // Storing the quickie layout into mQuickieListview

        LinearLayout mOrganizationListView = (LinearLayout)mActivity.findViewById(R.id.organization_category_listview);

        // calling and setting the "eventAdapter" to set the list items

        organizationCategoryAdapter =  new OrganizationCategoryAdapter(mActivity, mOrganizationListView, MyApplication.mainList.get(4),mOrganizationAdapter,markerMap);
        organizationCategoryAdapter.set();
    }

    private void setCalendar(){

        // Dynamically changing the calendar height / width due to the bug while it is within a
        // scrollview

        LinearLayout linearCalendar = (LinearLayout)mActivity.findViewById(R.id.calendarView);

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height and width into int variables
        int height = size.y;
        int width = size.x;

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = linearCalendar.getLayoutParams();

        // Re-setting the height parameter to .75 the max screen height

        params.height =  (int)Math.round(height*.55);
        params.width = (int)Math.round(width*.80);

        // Creating a date formatter for the calendar

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // Setup caldroid fragment

        caldroidFragment = new CaldroidFragment();

        // Setup arguments for the CalDroid

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();

        // Setting month / year
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));

        // Swipe nav between months
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);

        // Enable 6 weeks in calendar
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

        // Dark Theme
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

        // Applying the arguments

        caldroidFragment.setArguments(args);

        // Attach to the activity
        FragmentTransaction t = mActivity.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, caldroidFragment);
        t.commit();

        // Getting current date and setting background and adding to arraylist

        cal.add(Calendar.DATE, 0);
        Date currentDate = cal.getTime();

        // Overriding the default background for current date

        caldroidFragment.setBackgroundResourceForDate(android.R.color.black, currentDate);

        // If there are dates from the previous activity, they are looped through and added visually

        if(!userCustomFilters.getNonFormattedDateFilter().isEmpty()){

            for(int i = 0; i<userCustomFilters.getNonFormattedDateFilter().size();i++){
                caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_blue_light, userCustomFilters.getNonFormattedDateFilter().get(i));
            }
        }

        // Setup listener for date onClick
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                // if the selected date has already been selected, set background to black and remove from
                // date array list(spinner), other wise set background to light blue and add to array list
                // we have two arraylists to store the dates as date variables as well as formatted string
                // variables

                Log.v("calendar check", userCustomFilters.getDateFilter().size()+"");

                if (!userCustomFilters.getDateFilter().contains(formatter.format(date))){

                    caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_blue_light, date);
                    datesSaved.add(date);
                    calendarSelected.add(formatter.format(date));
                }
                else{

                    caldroidFragment.setBackgroundResourceForDate(android.R.color.background_dark, date);
                    datesSaved.remove(date);
                    calendarSelected.remove(formatter.format(date));
                }

                userCustomFilters.setDateFilter(calendarSelected);

                userCustomFilters.setNonFormattedDateFilter(datesSaved);

                // Checking to ensure eventAdapter is non null, i.e if this is being used in the list activity
                // going to have to pass in a map type object to apply changes to map activity

                if(mEventAdapter != null) {

                    filtering.filterEventList(mEventAdapter);
                }

                if(markerMap!= null){
                    filtering.filterEventMap(markerMap);
                }

                caldroidFragment.refreshView();
            }
        };

        // Setting the listener to the calendar
        caldroidFragment.setCaldroidListener(listener);
    }

    private void setAdmissionPrice(){

        // Storing both the seek bar and the price textView display

        SeekBar mSeekBar = (SeekBar) mActivity.findViewById(R.id.priceBar);
        final TextView seekBarValue = (TextView)mActivity.findViewById(R.id.priceDisplay);

        if(userCustomFilters.getAdmissionPriceFilter() > 0){

            mSeekBar.setProgress(userCustomFilters.getAdmissionPriceFilter()-1);
            seekBarValue.setText(String.valueOf(userCustomFilters.getAdmissionPriceFilter()-1) + " $");
        }
        else if(userCustomFilters.getAdmissionPriceFilter() == 0){

            mSeekBar.setProgress(userCustomFilters.getAdmissionPriceFilter()-1);
            seekBarValue.setText("Free mEvents");
        }
        else {
            seekBarValue.setText("No mPrice Filter");
        }

        // Setting a listener for the seek bar changing values

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // If the progress bar is 0 set the text to free, otherwise to progress value

                if (progress == 0) {
                    seekBarValue.setText("No mPrice Filter");
                }
                else if(progress == 1){
                    seekBarValue.setText("Free mEvents");
                }
                else {
                    seekBarValue.setText(String.valueOf(progress-1) + " $");
                }

                // As the seekBar is changed we shall update the userFilters aswell

                Log.v("progress test", progress+"");

                userCustomFilters.setAdmissionPriceFilter(progress - 1);

                if(mEventAdapter != null) {

                    filtering.filterEventList(mEventAdapter);
                }

                if(markerMap!= null){
                    filtering.filterEventMap(markerMap);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
