package com.bruha.bruha.Views;

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

import com.bruha.bruha.Adapters.CategoryAdapter;
import com.bruha.bruha.Adapters.QuickieAdapter;
import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.FilterGen;
import com.bruha.bruha.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Thomas on 5/25/2015.
 */
public class FilterView {

    // Creating a UserCustomFilters object to store user filters

    UserCustomFilters userCustomFilters = new UserCustomFilters();

    // Creating a CaldroidFragment object
    private CaldroidFragment caldroidFragment;

    ArrayList<String> calendarSelected = new ArrayList<>();

    // Casting the passed activity as a Fragment activity
    private FragmentActivity mActivity;

    public FilterView(FragmentActivity activity){

        mActivity = activity;
    }

    public UserCustomFilters init(){

        setPanel();

        // Setting and storing the quickie filters.

        userCustomFilters.setQuickieFilter(setQuickieList());

        // Simultaneously setting calendar and updating the user custom filters

        userCustomFilters.setDateFilter(setCalendar());

        // Simultaneously setting the category lists and updating the user custom filters

        userCustomFilters.setCategoryFilter(setCategoryList());

        //Admission price is added to userCustomFilters within its function

        setAdmissionPrice();

        // Returning the userCustomFilters to parent activity

        return userCustomFilters;
    }

    private void setPanel(){

        // Retrieving the device API level to determine if the modification to the sliding panel need
        //to be made (click instead of swiping)

        int device_sdk = android.os.Build.VERSION.SDK_INT;

        Log.v("DEVICE API", device_sdk + "");

        // Storing the sliding panel into mLayout

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout);

        // Creating buttons for all the buttons on the sliding panel handle

        Button eventButton = (Button)mActivity.findViewById(R.id.eventButton);
        Button venueButton = (Button)mActivity.findViewById(R.id.venueButton);
        Button artistButton = (Button)mActivity.findViewById(R.id.artistButton);
        Button orgButton = (Button)mActivity.findViewById(R.id.orgButton);

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

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .75 the max screen height

        params.height =  (int)Math.round(height*.75);

        // If device is API level 15 then we disable the drag while the sliding panel is expanded

        if(device_sdk == 15) {

            mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View view, float v) {

                }

                @Override
                public void onPanelCollapsed(View view) {

                }

                @Override
                public void onPanelExpanded(View view) {

                    mLayout.setTouchEnabled(false);
                }

                @Override
                public void onPanelAnchored(View view) {

                }

                @Override
                public void onPanelHidden(View view) {

                }
            });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {

                } else {

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mLayout.setTouchEnabled(true);
                }
            }
        });
        venueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){

                }
                else{

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mLayout.setTouchEnabled(true);
                }
            }
        });

        artistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){

                }
                else{

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mLayout.setTouchEnabled(true);
                }
            }
        });

        orgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){

                }
                else{

                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mLayout.setTouchEnabled(true);
                }
            }
        });

        }
    }

    private ArrayList<String> setQuickieList(){

        // Storing the quickie layout into mQuickieListview

        LinearLayout mQuickieListView = (LinearLayout)mActivity.findViewById(R.id.quickie_listview);

        // Calling the FilterGen class to set the populate the list of the filters

        FilterGen quickieGen = new FilterGen();
        ArrayList<Items> mainList = quickieGen.initRecommended();

        // calling and setting the "adapter" to set the list items

        QuickieAdapter adapter = new QuickieAdapter(mActivity, mQuickieListView, mainList);
        return adapter.set();
    }

    private ArrayList<String> setCalendar(){

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

        caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_blue_light, currentDate);
        calendarSelected.add(formatter.format(currentDate));


        // Setup listener for date onClick
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                // if the selected date has already been selected, set background to black and remove from
                // date array list, other wise set background to light blue and add to array list

                if(!calendarSelected.contains(date)){

                    caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_blue_light, date);
                    calendarSelected.add(formatter.format(date));
                }
                else{

                    caldroidFragment.setBackgroundResourceForDate(android.R.color.background_dark, date);
                    calendarSelected.remove(date);
                }

                caldroidFragment.refreshView();
            }
        };

        // Setting the listener to the calendar
        caldroidFragment.setCaldroidListener(listener);

        return calendarSelected;
    }

    private Map<String, ArrayList<String>> setCategoryList(){

        // Storing the quickie layout into mCategoryListview

        LinearLayout mCategoryListView = (LinearLayout)mActivity.findViewById(R.id.category_listview);

        // Calling the FilterGen class to set the populate the list of the filters

        FilterGen catGen = new FilterGen();
        ArrayList<Items> mainList = catGen.initCategory();

        // calling and setting the "adapter" to set the list items

        CategoryAdapter adapter = new CategoryAdapter(mActivity, mCategoryListView, mainList);

        return adapter.set();
    }

    private void setAdmissionPrice(){

        // Storing both the seek bar and the price textview display

        SeekBar mSeekBar = (SeekBar) mActivity.findViewById(R.id.priceBar);
        final TextView seekBarValue = (TextView)mActivity.findViewById(R.id.priceDisplay);

        // Setting a listener for the seekbar changing values

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // If the progress bar is 0 set the text to free, otherwise to progress value

                if (progress == 0) {
                    seekBarValue.setText("Free Events");
                } else {
                    seekBarValue.setText(String.valueOf(progress) + " $");
                }

                // As the seekBar is changed we shall update the userCustomFilters aswell

                userCustomFilters.setAdmissionPriceFilter(progress);
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
