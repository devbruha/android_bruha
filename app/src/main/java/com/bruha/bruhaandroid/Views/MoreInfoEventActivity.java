package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.Processing.RetrieveMyPHP;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.util.ArrayList;

public class MoreInfoEventActivity extends AppCompatActivity {

    String type;
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Venue> mVenue = new ArrayList();
    ArrayList<Organizations> mOrg = new ArrayList<>();
    ArrayList<Artist> mArtist = new ArrayList<>();
    ArrayList<String> mEventAdd = new ArrayList<>();
    ArrayList<String> mVenueAdd = new ArrayList<>();
    ArrayList<String> mOrgAdd = new ArrayList<>();
    Event event;
    Venue venue;
    Organizations org;
    Artist artist;
    RetrieveMyPHP retrieveMyPHP;
    SQLiteDatabaseModel dbHelper;

    ImageView dudeButton;
    ImageView listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_event);
        setButtons();

        retrieveMyPHP = new RetrieveMyPHP();
        dbHelper = new SQLiteDatabaseModel(this);

        init(); //Calling to initialized Array selectedDateEvents to loop through to find the Event to be displayed.

        //Getting the ID of the event that will need to be displayed
        Intent intent = getIntent();
        String id= intent.getStringExtra("Id");
        type = intent.getStringExtra("Type");

        if(type.equals("Event")) {
            //Finding out and storing the event that is to be displayed.
            for (Event x : mEvents) {
                if (x.getEventid().equals(id)) {
                    event = x;
                }
            }
        }

        else if(type.equals("Venue")) {
            //Finding out and storing the event that is to be displayed.
            for (Venue x : mVenue) {
                if (x.getVenueId().equals(id)) {
                    venue = x;
                }
            }
        }

        else if(type.equals("Artist")) {
            //Finding out and storing the event that is to be displayed.
            for (Artist x : mArtist) {
                if (x.getArtistId().equals(id)) {
                    artist = x;
                }
            }
        }

        else  {
            //Finding out and storing the event that is to be displayed.
            for (Organizations x : mOrg) {
                if (x.getOrgId().equals(id)) {
                    org = x;
                }
            }
        }

        //panelSetup();





    }


    public void startListActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    //OnClickListener for "Explore" that leads to the ListActivity.
    public void startDashboardActivity(View view){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
    private void init() {
        //The method that calls the local database and sets all the variables accordingly.
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        //Assigns the array containing the list of events.
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenue = sqLiteUtils.getVenuesInfo(dbHelper);
        mArtist = sqLiteUtils.getArtistInfo(dbHelper);
        mOrg = sqLiteUtils.getOrganizationsInfo(dbHelper);

        mEventAdd = sqLiteUtils.getEventAddictions(dbHelper);
        mVenueAdd = sqLiteUtils.getVenueAddictions(dbHelper);
        mOrgAdd = sqLiteUtils.getOrgAddictions(dbHelper);

    }

    private void setButtons() {

        dudeButton = (ImageView) findViewById(R.id.MIEbacktoDashBoardimageView);
        listButton = (ImageView) findViewById(R.id.MIElistImageview);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        // Storing the screen height into an int variable..
        int height = size.y;
        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height =  (int)Math.round(height*.07);
        dudeButtonLayoutParams.width =  (int)Math.round(height*.07);
        ViewGroup.LayoutParams listButtonLayoutParams = listButton.getLayoutParams();
        listButtonLayoutParams.height =  (int)Math.round(height*.07);
        listButtonLayoutParams.width =  (int)Math.round(height*.07);


        //Sets the image and onclicklisteners for the Buttons for dashboard/map navigation.
        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));
        listButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.listicon, 30));
        dudeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(dudeButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        dudeButton.setAlpha(1f);
                        startDashboardActivity(v);
                    }
                });
                animator.start();
            }
        });
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(listButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        listButton.setAlpha(1f);
                        startListActivity(v);
                    }
                });
                animator.start();
            }
        });
    }

    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        }catch(SVGParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap svgToBitmap(Resources res, int resource, int size) {
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp;
            bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);


            return bmp;
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
