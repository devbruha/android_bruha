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
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bruha.bruhaandroid.Adapters.ListViewAdapter2;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomePageActivity extends FragmentActivity {

    ArrayList<Event> mEvents = new ArrayList<>();
   // ObservableListView listView;

    // Buttons to be implemented
    // top images
    ImageView filterButton;
    ImageView mapButton;
    // bottom images
    ImageView homeButton;
    ImageView calenderButton;
    ImageView discoverableButton;
    ImageView ticketsButton;
    ImageView profileButton;


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



       /* // Setting Home Page
        explorerFragment = new ExplorerFragment();
        fragmentTransaction.add(R.id.layoutFragment, explorerFragment);
        fragmentTransaction.commit();
        */

    }

    private void assigningImages() {
        // top images
        filterButton = (ImageView) findViewById(R.id.filterIcon);
        mapButton = (ImageView) findViewById(R.id.mapIcon);
        // bottom images
        homeButton = (ImageView) findViewById(R.id.homeIcon);
        calenderButton = (ImageView) findViewById(R.id.calendarIcon);
        discoverableButton = (ImageView) findViewById(R.id.discoverableIcon);
        ticketsButton = (ImageView) findViewById(R.id.ticketsIcon);
        profileButton = (ImageView) findViewById(R.id.profileIcon);
        //listView = (ObservableListView) findViewById(R.id.list);
    }

    private void importingData() {

        settingImages();

        // setting edit text

    }

    private void settingImages() {
        int buttonResolution = 50;
        // top images
        //filterButton.setImageDrawable(;
        mapButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.mapicon, buttonResolution));
        // bottom images
        homeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.explore, buttonResolution));
        calenderButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.calendar, buttonResolution));
        discoverableButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, buttonResolution));
        ticketsButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, buttonResolution));
        profileButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profile, buttonResolution));
    }

    private void parseData() {
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        //listView.setAdapter(new ListViewAdapter2(this, mEvents));
    }

    private void filterBarOnClicks() {
        // filterIcon

        // categoryName

        // mapsIcon
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(mapButton, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(500);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mapButton.setAlpha(1f);
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
        // home(explorer)Icon
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(homeButton, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(500);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        homeButton.setAlpha(1f);
                        startHomePageActivity(view);
                    }
                });
                clickAnimator.start();
            }
        });

        // calendarIcon
        calenderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(calenderButton, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(500);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        calenderButton.setAlpha(1f);
                        startCalendarActivity(view);
                    }
                });
                clickAnimator.start();
            }
        });

        // discoverableIcon
        discoverableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(discoverableButton, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(100);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        discoverableButton.setAlpha(1f);
                        startDiscoverable(view);
                    }
                });
                clickAnimator.start();
            }
        });

        // ticketsIcon
        ticketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(ticketsButton, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(500);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ticketsButton.setAlpha(1f);
                        startTicketsActivity(view);
                    }
                });
                clickAnimator.start();
            }
        });

        // profileIcon
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ObjectAnimator clickAnimator = ObjectAnimator.ofFloat(profileButton, "alpha", 1f, 0.5f);
                clickAnimator.setDuration(500);
                clickAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        profileButton.setAlpha(1f);
                        startProfileActivity(view);
                    }
                });
                clickAnimator.start();
            }
        });
    }

    private void startHomePageActivity(View view) {
        //Activity profileAct = new ProfileActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExplorerFragment()).commit();
        /*
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
        */
    }

    private void startCalendarActivity(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }

    private void startDiscoverable(View view) {
        showDialog();
    }

    private void startTicketsActivity(View view) {
        Intent intent = new Intent(this, MyTicketActivity.class);
        startActivity(intent);
        finish();
    }

    private void startProfileActivity(View view) {


        //Activity profileAct = new ProfileActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new profileFragment()).commit();
/*
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
        */
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




