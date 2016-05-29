package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bruha.bruhaandroid.Adapters.CustomArrayAdapter;
import com.bruha.bruhaandroid.Fragments.CalendarFragment;
import com.bruha.bruhaandroid.Fragments.ExplorerFragment;
import com.bruha.bruhaandroid.Fragments.ExplorerOrganizationFragment;
import com.bruha.bruhaandroid.Fragments.ExplorerVenueFragment;
import com.bruha.bruhaandroid.Fragments.MapFragment;
import com.bruha.bruhaandroid.Fragments.ProfileFragment;
import com.bruha.bruhaandroid.Fragments.ShowOnMapFragment;
import com.bruha.bruhaandroid.Fragments.TicketsFragment;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomePageActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener{

    // Array lists of data
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Venue> mVenues = new ArrayList<>();
    ArrayList<Organizations> mOrganizations = new ArrayList<>();

    // Buttons to be implemented
    LinearLayout homePageView;
    // top images
    ImageView filterIcon;
    LinearLayout filterButton;
    ImageView mapIcon;
    LinearLayout mapButton;
    Spinner spinner; // drop down menu
    EditText searchBar;
    TextView activityTitle;

    // bottom images
    ImageView homeIcon;
    LinearLayout homeButton;
    ImageView calenderIcon;
    LinearLayout calenderButton;
    ImageView discoverableIcon;
    LinearLayout discoverableButton;
    ImageView ticketsIcon;
    LinearLayout ticketsButton;
    ImageView profileIcon;
    LinearLayout profileButton;
    RelativeLayout CategoriesLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.inject(this);
        assigningImages();

        // Setting Design; Images, Texts, etc
        importingData();
        //parseData();

        // Setting onClicks
        filterBarOnClicks(); // the top bar; filterIcon, categoryName, and mapIcon
        navigationBarOnClicks(); // the bottom bar; home(explorer)Icon, calendarIcon, discoverableIcon, ticketsIcon, and profileIcon

        //Activity profileAct = new ProfileActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerFragment()).commit();

    }

    private void assigningImages() {
        // top images
        filterIcon = (ImageView) findViewById(R.id.filterIcon);
        filterButton = (LinearLayout) findViewById(R.id.filterButton);
        mapIcon = (ImageView) findViewById(R.id.mapIcon);
        mapButton = (LinearLayout) findViewById(R.id.mapButton);
        // bottom images
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        homeButton = (LinearLayout) findViewById(R.id.homeButton);
        calenderIcon = (ImageView) findViewById(R.id.calendarIcon);
        calenderButton = (LinearLayout) findViewById(R.id.calendarButton);
        discoverableIcon = (ImageView) findViewById(R.id.discoverableIcon);
        discoverableButton = (LinearLayout) findViewById(R.id.discoverableButton);
        ticketsIcon = (ImageView) findViewById(R.id.ticketsIcon);
        ticketsButton = (LinearLayout) findViewById(R.id.ticketsButton);
        profileIcon = (ImageView) findViewById(R.id.profileIcon);
        profileButton = (LinearLayout) findViewById(R.id.profileButton);
        CategoriesLayout= (RelativeLayout) findViewById(R.id.CategoriesLayout);


    }

    private void importingData() {

        // Setting Images
        settingImages();

        // Setting Top Bar
        spinner = (Spinner) findViewById(R.id.spinner);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        // For Search bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        searchBar = (EditText) findViewById(R.id.searchBar);
        homePageView = (LinearLayout) findViewById(R.id.filterButton);
        homePageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                searchBar.clearFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });


    }


    private void settingImages() {
        int buttonResolution = 50;
        // top images
        //filterButton.setImageDrawable(;
        mapIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.mapicon, buttonResolution));
        // bottom images

        // initial set up with homeButton selected
        homeButton.setBackgroundColor(Color.parseColor("#24163f"));
        calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
        ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
        profileButton.setBackgroundColor(Color.parseColor("#402c67"));

        // initial set up with homeIcon shown as selected
        homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.explore, buttonResolution));
        calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
        discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
        ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
        profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));
    }

    private void filterBarOnClicks() {
        final int buttonResolution = 50;
        final int animationDuration = 150;
        // filterIcon

        // categoryName

        // Setting spinner -- initially at Events
        List<String> objects = new ArrayList<>();
        objects.add("Events");
        objects.add("Venues");
        objects.add("Organizations");

        ArrayAdapter<String> myAdapter = new CustomArrayAdapter(this, R.layout.simple_spinner_item, objects);
        //CustomArrayAdapter<String> adapter = new CustomArrayAdapter<String>(this,arraySpinner);
        //adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);


        // mapsIcon
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(mapIcon, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(animationDuration);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mapIcon.setAlpha(1f);
                        startMapActivity(view);
                    }
                });
                clickAnimator.start();
            }
        });
    }

    private void startMapActivity(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MapFragment()).commit();
    }

    private void navigationBarOnClicks() {
        final int buttonResolution = 50;
        final int animationDuration = 0;


        // home(explorer)Icon
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(homeIcon, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(animationDuration);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        homeIcon.setAlpha(1f);
                        startHomePageActivity(view);
                    }
                });
                clickAnimator.start();

                // Setting activity title
                activityTitle.setVisibility(View.VISIBLE);

                // Set up with homeButton selected
                homeButton.setBackgroundColor(Color.parseColor("#24163f"));
                //homeButton.setBackgroundColor(Color.parseColor("#ffffff"));
                calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
                ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
                profileButton.setBackgroundColor(Color.parseColor("#402c67"));

                // Set up with homeIcon shown as selected
                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.explore, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));

                // Set Spinner VISIBILE or GONE
                spinner.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.VISIBLE);
                filterIcon.setVisibility(View.VISIBLE);
                filterButton.setEnabled(true);
                mapIcon.setVisibility(View.VISIBLE);
                mapButton.setEnabled(true);
            }
        });

        // calendarIcon
        calenderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(calenderIcon, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(animationDuration);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        calenderIcon.setAlpha(1f);
                        startCalendarActivity(view);
                    }
                });
                clickAnimator.start();

                // Setting activity title
                activityTitle.setText("Calender");

                // Set up with calenderButton selected
                homeButton.setBackgroundColor(Color.parseColor("#402c67"));
                calenderButton.setBackgroundColor(Color.parseColor("#24163f"));
                ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
                profileButton.setBackgroundColor(Color.parseColor("#402c67"));

                // Set up with calenderIcon shown as selected
                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendar, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));

                // Setting Top Bar
                spinner.setVisibility(View.GONE);
                searchBar.setVisibility(View.GONE);
                filterIcon.setVisibility(View.INVISIBLE);
                filterButton.setEnabled(false);
                mapIcon.setVisibility(View.INVISIBLE);
                mapButton.setEnabled(false);
            }
        });

        // discoverableIcon
        discoverableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(discoverableIcon, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(animationDuration);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        discoverableIcon.setAlpha(1f);
                        startDiscoverable(view);
                    }
                });
                clickAnimator.start();
/*
                // Setting activity title
                activityTitle.setText("Discoverable");

                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhalogoicon, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));

                // Set Spinner VISIBILE or GONE
                spinner.setVisibility(View.GONE);
                searchBar.setVisibility(View.GONE);
                filterIcon.setVisibility(View.INVISIBLE);
                filterButton.setEnabled(false);
                mapIcon.setVisibility(View.INVISIBLE);
                mapButton.setEnabled(false);
*/
            }
        });

        // ticketsIcon
        ticketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(ticketsIcon, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(animationDuration);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ticketsIcon.setAlpha(1f);
                        startTicketsActivity(view);
                    }
                });
                clickAnimator.start();

                // Setting activity title
                activityTitle.setText("My Tickets");

                // Set up with ticketsButton selected
                homeButton.setBackgroundColor(Color.parseColor("#402c67"));
                calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
                ticketsButton.setBackgroundColor(Color.parseColor("#24163f"));
                profileButton.setBackgroundColor(Color.parseColor("#402c67"));

                // Set up with ticketsIcon shown as selected
                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));

                // Set Spinner VISIBILE or GONE
                spinner.setVisibility(View.GONE);
                searchBar.setVisibility(View.VISIBLE);
                searchBar.setHint("Search Tickets");
                filterIcon.setVisibility(View.VISIBLE);
                filterButton.setEnabled(true);
                mapIcon.setVisibility(View.VISIBLE);
                mapButton.setEnabled(true);
            }
        });

        // profileIcon
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(profileIcon, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(animationDuration);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        profileIcon.setAlpha(1f);
                        startProfileActivity(view);
                    }
                });
                clickAnimator.start();

                // Setting activity title
                activityTitle.setText("My Profile");

                // Set up with profileButton selected
                homeButton.setBackgroundColor(Color.parseColor("#402c67"));
                calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
                ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
                profileButton.setBackgroundColor(Color.parseColor("#24163f"));

                // Set up with profileIcon shown as selected
                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profile, buttonResolution));

                // Set Spinner VISIBILE or GONE
                spinner.setVisibility(View.GONE);
                searchBar.setVisibility(View.GONE);
                filterIcon.setVisibility(View.INVISIBLE);
                filterButton.setEnabled(false);
                mapIcon.setVisibility(View.INVISIBLE);
                mapButton.setEnabled(false);
            }
        });
    }

    private void startHomePageActivity(View view) {
        String text = spinner.getSelectedItem().toString();

        if(text == "Venues"){
            // To go to Venues
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerVenueFragment()).commit();
            searchBar.setHint("Search Venues");
        }
        else if(text == "Organizations"){
            // To go to Organizations
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerOrganizationFragment()).commit();
            searchBar.setHint("Search Organizations");
        }
        else{
            // To go to Events
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerFragment()).commit();
            searchBar.setHint("Search Events");
        }

    }

    private void startCalendarActivity(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CalendarFragment()).commit();

    }

    private void startDiscoverable(View view) {
        showDialog();
    }

    private void startTicketsActivity(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TicketsFragment()).commit();
    }

    private void startProfileActivity(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
    }

    public void showDialog() {   //Alert dialog to let the user know discoverable is coming soon..
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("Discoverable Coming Soon!");
        builder.setCancelable(true);
        builder.setPositiveButton("Close.. For Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size) {
        try {
            size = (int) (size * res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Methods for Spinners
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner s = (Spinner) findViewById(R.id.spinner);
        String text = s.getSelectedItem().toString();

        if(text == "Venues"){
            // To go to Venues
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerVenueFragment()).commit();
            searchBar.setHint("Search Venues");
        }
        else if(text == "Organizations"){
            // To go to Organizations
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerOrganizationFragment()).commit();
            searchBar.setHint("Search Organizations");
        }
        else{
            // To go to Events
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerFragment()).commit();
            searchBar.setHint("Search Events");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}