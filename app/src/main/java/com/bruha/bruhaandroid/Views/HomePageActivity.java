package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bruha.bruhaandroid.Fragments.CalendarFragment;
import com.bruha.bruhaandroid.Fragments.ExplorerFragment;
import com.bruha.bruhaandroid.Fragments.ProfileFragment;
import com.bruha.bruhaandroid.Fragments.TicketsFragment;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HomePageActivity extends FragmentActivity {

    ArrayList<Event> mEvents = new ArrayList<>();
   // ObservableListView listView;

    // Buttons to be implemented
    // top images
    ImageView filterButton;
    ImageView mapIcon;
    LinearLayout mapButton;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.inject(this);
        assigningImages();

        // Setting Design; Images, Texts, etc
        importingData();
        parseData();

        // Setting onClicks
        filterBarOnClicks(); // the top bar; filterIcon, categoryName, and mapIcon
        navigationBarOnClicks(); // the bottom bar; home(explorer)Icon, calendarIcon, discoverableIcon, ticketsIcon, and profileIcon

        //Activity profileAct = new ProfileActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerFragment()).commit();


    }

    private void assigningImages() {
        // top images
        filterButton = (ImageView) findViewById(R.id.filterIcon);
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


    }

    private void importingData() {

        settingImages();

        // setting edit text

    }

    private void settingImages() {
        int buttonResolution = 50;
        // top images
        //filterButton.setImageDrawable(;
        mapIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.mapicon, buttonResolution));
        // bottom images

        homeButton.setBackgroundColor(Color.parseColor("#24163f"));
        calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
        ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
        profileButton.setBackgroundColor(Color.parseColor("#402c67"));


        homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.explore, buttonResolution));
        calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
        discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
        ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
        profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));
    }

    private void parseData() {
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        mEvents = sqLiteUtils.getEventInfo(dbHelper);
    }

    private void filterBarOnClicks() {
        final int buttonResolution = 50;
        final int animationDuration = 150;
        // filterIcon

        // categoryName

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
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
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

                homeButton.setBackgroundColor(Color.parseColor("#24163f"));
                calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
                ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
                profileButton.setBackgroundColor(Color.parseColor("#402c67"));

                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.explore, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));
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

                homeButton.setBackgroundColor(Color.parseColor("#402c67"));
                calenderButton.setBackgroundColor(Color.parseColor("#24163f"));
                ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
                profileButton.setBackgroundColor(Color.parseColor("#402c67"));

                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendar, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));
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

                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhalogoicon, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));
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

                homeButton.setBackgroundColor(Color.parseColor("#402c67"));
                calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
                ticketsButton.setBackgroundColor(Color.parseColor("#24163f"));
                profileButton.setBackgroundColor(Color.parseColor("#402c67"));

                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profileselectedicon, buttonResolution));
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

                homeButton.setBackgroundColor(Color.parseColor("#402c67"));
                calenderButton.setBackgroundColor(Color.parseColor("#402c67"));
                ticketsButton.setBackgroundColor(Color.parseColor("#402c67"));
                profileButton.setBackgroundColor(Color.parseColor("#24163f"));

                clickAnimator.start();
                homeIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.exploreselectedicon, buttonResolution));
                calenderIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendarselectedicon, buttonResolution));
                discoverableIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
                ticketsIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ticketsselectedicon, buttonResolution));
                profileIcon.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profile, buttonResolution));
            }
        });
    }

    private void startHomePageActivity(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerFragment()).commit();

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
}




