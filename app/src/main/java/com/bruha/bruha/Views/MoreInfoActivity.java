package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.RetrieveMyPHP;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MoreInfoActivity extends ActionBarActivity {

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
        setContentView(R.layout.activity_more_info);

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

        panelSetup();


    }

    private void setButtons() {

         dudeButton = (ImageView) findViewById(R.id.moreinfoDudeButton);
         listButton = (ImageView) findViewById(R.id.moreinfoListView);

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

    //OnClickListener for "Explore" that leads to the ListActivity.
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

    private void panelSetup() {

        // Retrieving the device API level to determine if the modification to the sliding panel need
        //to be made (click instead of swiping)

        int device_sdk = android.os.Build.VERSION.SDK_INT;

        Log.v("DEVICE API", device_sdk + "");

        // Android functions to determine the screen dimensions

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        // Taking the status bar height into account for height calculations

        int workingHeight = height - getStatusBarHeight();

        // Storing the sliding panel into mLayout

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)findViewById(R.id.more_info_sliding_layout);

        // Creating buttons for all the buttons on the sliding panel handle


        // Finding the handle layout

        LinearLayout handleLayout = (LinearLayout)findViewById(R.id.moreInfoHandle);

        //TODO Make the setPanelHeight a dynamic setting

        mLayout.setDragView(handleLayout);

        // Setting an anchor point at the halfway point

        mLayout.setAnchorPoint(.5f);

        //mLayout.

        // Storing the sliding panel (lin layout) into a linear layout variable

        LinearLayout dragLayout = (LinearLayout)findViewById(R.id.moreInfoSlideLayout);

        ViewGroup.LayoutParams handleParams = handleLayout.getLayoutParams();
        //handleParams.height = (int)Math.round(workingHeight*.33);
        mLayout.setPanelHeight((int)Math.round(workingHeight*.46));

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .65 the max screen height (status bar included)

        params.height =  (int)Math.round(workingHeight);

        initializePanel();
    }

    //Method to Format the Date that will be displayed.
    public String dateFormat(String Date) {
        String year=Date.substring(0,4);
        String Month=Date.substring(5,7);
        String Dates=Date.substring(8,10);
        String Displayed=getMonth(Month)+ " " + Dates + ", " + year;
        return Displayed;
    }

    //Part of the Implementation of the dateFormat Method.
    public String getMonth(String Month) {
        if(Month.equals("01"))
            Month="January";
        if(Month.equals("02"))
            Month="Febuary";
        if(Month.equals("03"))
            Month="March";
        if(Month.equals("04"))
            Month="April";
        if(Month.equals("05"))
            Month="May";
        if(Month.equals("06"))
            Month="June";
        if(Month.equals("07"))
            Month="July";
        if(Month.equals("08"))
            Month="August";
        if(Month.equals("09"))
            Month="September";
        if(Month.equals("10"))
            Month="October";
        if(Month.equals("11"))
            Month="November";
        if(Month.equals("12"))
            Month="December";
        return Month;
    }

    public void initializePanel() {
        Typeface opensansregfnt = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

        ImageView filterimage = (ImageView) findViewById(R.id.eventcategory);
        TextView eventName = (TextView) findViewById(R.id.nonfilterEventName);
        eventName.setTypeface(opensansregfnt);
        TextView venueName = (TextView) findViewById(R.id.nonfilterVenueName);
        venueName.setTypeface(opensansregfnt);
        TextView venueSt = (TextView) findViewById(R.id.nonfilterVenueStreet);
        venueSt.setTypeface(opensansregfnt);
        TextView venuecountry = (TextView) findViewById(R.id.nonfilterVenueCountry);
        venuecountry.setTypeface(opensansregfnt);
        TextView eventprice = (TextView) findViewById(R.id.eventprice);
        eventprice.setTypeface(opensansregfnt);
        TextView eventdate = (TextView) findViewById(R.id.eventdate);
        eventdate.setTypeface(opensansregfnt);
        TextView eventdesc = (TextView) findViewById(R.id.moreinfoeventdescription);
        eventdesc.setTypeface(opensansregfnt);
        ImageView eventPic = (ImageView) findViewById(R.id.moreinfoPicture);
        ImageView addictionImage = (ImageView) findViewById(R.id.addictionImage);
        final LinearLayout likeText = (LinearLayout) findViewById(R.id.moreInfoAddiction);
        final TextView addict1 = (TextView) findViewById(R.id.textView5);
        addict1.setTypeface(opensansregfnt);
        final TextView addict2 = (TextView) findViewById(R.id.textView6);
        addict2.setTypeface(opensansregfnt);

        addictionImage.setImageBitmap(svgToBitmap(getResources(),R.raw.myaddictions,60));

        if(type.equals("Event")) {
            //Setting the background image of the event.
            Picasso.with(getApplicationContext()).load(event.getEventPicture()).fit().into(eventPic);
            eventName.setText(event.getEventName());
            venueName.setText(event.getEventLocName());
            venueSt.setText(event.getEventLocSt());
            venuecountry.setText(event.getEventLocAdd());
            eventprice.setText("$"+event.getEventPrice());
            eventdate.setText(dateFormat(event.getEventDate()));
            eventdesc.setText(event.getEventDescription());
            Bitmap x = setEventIcon(event);
            filterimage.setImageBitmap(x);

            for(int i=0; i<mEventAdd.size();i++)
            {
                for(int j = 0; j<mEvents.size();j++)
                { if(mEventAdd.get(i).equals(mEvents.get(j)))
                    {
                        addict1.setText("You are");
                        addict2.setText("addicted");
                        likeText.setBackgroundResource(android.R.color.holo_orange_dark);
                    }

                }
            }

            boolean addict = false ;

            if(addict==false) {
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retrieveMyPHP.eventAddiction(MyApplication.userName, event.getEventid());
                        Toast.makeText(getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                        addict1.setText("You are");
                        addict2.setText("addicted");
                        likeText.setBackgroundResource(android.R.color.holo_orange_dark);

                        dbHelper.insertEventAddiction(dbHelper.getWritableDatabase(), event.getEventid());
                    }

                });
            }

            else{
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retrieveMyPHP.deleteVenueAddiction(MyApplication.userName, venue.getVenueId());
                        Toast.makeText(getApplicationContext(), "You are unaddicted", Toast.LENGTH_SHORT).show();
                        addict1.setText("Get");
                        addict2.setText("addicted");
                        likeText.setBackgroundColor(Color.parseColor("#ff54cdd6"));

                        dbHelper.deleteVenueAddiction(dbHelper.getWritableDatabase(), venue.getVenueId());
                    }

                });
            }

        }

        else if(type.equals("Venue")) {
            //Setting the background image of the event.
            Picasso.with(getApplicationContext()).load(venue.getVenuePicture()).fit().into(eventPic);
            eventName.setText(venue.getVenueName());
            venueName.setText(venue.getVenueName());
            venueSt.setText(venue.getVenueSt());
            venuecountry.setText(venue.getVenueLocation());
            eventprice.setText("-");
            eventdate.setText("-");
            eventdesc.setText(venue.getVenueDescription());
            Bitmap x = setVenueIcon(venue);
            filterimage.setImageBitmap(x);

            boolean addict = false;

            for(int i=0; i<mVenueAdd.size();i++)
            {
                for(int j = 0; j<mVenue.size();j++)
                { if(mVenueAdd.get(i).equals(mVenue.get(j).getVenueId()))
                {
                    addict1.setText("You are");
                    addict2.setText("addicted");
                    likeText.setBackgroundResource(android.R.color.holo_orange_dark);
                    addict = true;
                }}}

            if(addict==false)
            {
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retrieveMyPHP.venueAddiction(MyApplication.userName, venue.getVenueId());
                        Toast.makeText(getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                        addict1.setText("You are");
                        addict2.setText("addicted");
                        likeText.setBackgroundResource(android.R.color.holo_orange_dark);

                        dbHelper.insertVenueAddiction(dbHelper.getWritableDatabase(), venue.getVenueId());
                    }

                });
            }

            else{
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retrieveMyPHP.deleteVenueAddiction(MyApplication.userName, venue.getVenueId());
                        Toast.makeText(getApplicationContext(), "You are unaddicted", Toast.LENGTH_SHORT).show();
                        addict1.setText("Get");
                        addict2.setText("addicted");
                        likeText.setBackgroundColor(Color.parseColor("#ff54cdd6"));

                        dbHelper.deleteVenueAddiction(dbHelper.getWritableDatabase(), venue.getVenueId());
                    }

                });

            }

        }

        else if(type.equals("Artist")) {

        }

        else {
            //Setting the background image of the event.
            Picasso.with(getApplicationContext()).load(org.getOrgPicture()).fit().into(eventPic);
            eventName.setText(org.getOrgName());
            venueName.setText(org.getOrgName());
            venueSt.setText(org.getOrgSt());
            venuecountry.setText(org.getOrgLocation());
            eventprice.setText("-");
            eventdate.setText("-");
            eventdesc.setText(org.getOrgDescription());
            Bitmap x = setOrgIcon(org);
            filterimage.setImageBitmap(x);

            likeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retrieveMyPHP.organizationAddiction(MyApplication.userName, org.getOrgId());
                    Toast.makeText(getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                    addict1.setText("You are");
                    addict2.setText("Addicted");

                    dbHelper.insertOrgAddiction(dbHelper.getWritableDatabase(),org.getOrgId());
                }

            });

        }

        }

    //Method to set the icon of the event.
    public Bitmap setEventIcon(Event event) {
        if(event.getEventPrimaryCategory().contains("Club"))
        {return svgToBitmap(getResources(), R.raw.club, 30);}

        else if(event.getEventPrimaryCategory().contains("Performing"))
        {return svgToBitmap(getResources(), R.raw.performing, 30);}

        else if(event.getEventPrimaryCategory().contains("Business"))
        {return svgToBitmap(getResources(), R.raw.business, 30);}

        else if(event.getEventPrimaryCategory().contains("Ceremony"))
        {return svgToBitmap(getResources(), R.raw.ceremony, 30);}

        else if(event.getEventPrimaryCategory().contains("Tech"))
        {return svgToBitmap(getResources(), R.raw.tech, 30);}

        else if(event.getEventPrimaryCategory().contains("Comedy"))
        {return svgToBitmap(getResources(), R.raw.comedy, 30);}

        else if(event.getEventPrimaryCategory().contains("Fashion"))
        {return svgToBitmap(getResources(), R.raw.fashion, 30);}

        else if(event.getEventPrimaryCategory().contains("Festivals"))
        {return svgToBitmap(getResources(), R.raw.festivals, 30);}

        else if(event.getEventPrimaryCategory().contains("Film"))
        {return svgToBitmap(getResources(), R.raw.film, 30);}

        else if(event.getEventPrimaryCategory().contains("Food"))
        {return svgToBitmap(getResources(), R.raw.food, 30);}

        else if(event.getEventPrimaryCategory().contains("Party"))
        {return svgToBitmap(getResources(), R.raw.party, 30);}

        else if(event.getEventPrimaryCategory().contains("Music"))
        {return svgToBitmap(getResources(), R.raw.music, 30);}

        else if(event.getEventPrimaryCategory().contains("Political"))
        {return svgToBitmap(getResources(), R.raw.political, 30);}

        else if(event.getEventPrimaryCategory().contains("School"))
        {return svgToBitmap(getResources(), R.raw.school, 30);}

        else if(event.getEventPrimaryCategory().contains("Sports"))
        {return svgToBitmap(getResources(), R.raw.sports, 30);}

        else if(event.getEventPrimaryCategory().contains("Tour"))
        {return svgToBitmap(getResources(), R.raw.tour, 30);}

        else if(event.getEventPrimaryCategory().contains("Arts"))
        {return svgToBitmap(getResources(), R.raw.arts, 30);}

        else if(event.getEventPrimaryCategory().contains("Social"))
        {return svgToBitmap(getResources(), R.raw.social, 30);}

        return null;
    }

    //Method to set the icon of the venue
    public Bitmap setVenueIcon(Venue venue) {
        if(venue.getVenuePrimaryCategory().contains("Amphitheatre"))
        {return svgToBitmap(getResources(), R.raw.venamphiteather, 30);}

        if(venue.getVenuePrimaryCategory().contains("Arena"))
        {return svgToBitmap(getResources(), R.raw.venarena, 30);}

        else if(venue.getVenuePrimaryCategory().contains("Bar/Pub"))
        {return svgToBitmap(getResources(), R.raw.venbars, 30);}

        else if(venue.getVenuePrimaryCategory().contains("Casino"))
        {return svgToBitmap(getResources(), R.raw.vencasino, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Church"))
        {return svgToBitmap(getResources(), R.raw.venchurch, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Cinema"))
        {return svgToBitmap(getResources(), R.raw.vencinema, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Club"))
        {return svgToBitmap(getResources(), R.raw.venclubs, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Coffee"))
        {return svgToBitmap(getResources(), R.raw.vencoffee, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Comedy"))
        {return svgToBitmap(getResources(), R.raw.vencomedy, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Community"))
        {return svgToBitmap(getResources(), R.raw.vencommunity, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Fairgrounds"))
        {return svgToBitmap(getResources(), R.raw.venfairground, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Gallery"))
        {return svgToBitmap(getResources(), R.raw.vengallery, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Park"))
        {return svgToBitmap(getResources(), R.raw.venparks, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Restaurant"))
        {return svgToBitmap(getResources(), R.raw.venrestauratns, 30);}

        else if (venue.getVenuePrimaryCategory().contains("House/Residence"))
        {return svgToBitmap(getResources(), R.raw.venhouse, 30);}

        else if (venue.getVenuePrimaryCategory().contains("School"))
        {return svgToBitmap(getResources(), R.raw.venschool, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Sports/Arena"))
        {return svgToBitmap(getResources(), R.raw.venarena, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Store"))
        {return svgToBitmap(getResources(), R.raw.venstore, 30);}

        else if (venue.getVenuePrimaryCategory().contains("Theatre"))
        {return svgToBitmap(getResources(), R.raw.ventheater, 30);}
        return null;
    }

    //Method to set the icon of the event.
    public Bitmap setOrgIcon(Organizations org) {
        if(org.getOrgPrimaryCategory().contains("Academic"))
        {return svgToBitmap(getResources(), R.raw.orgacademic, 30);}

        else if(org.getOrgPrimaryCategory().contains("Business"))
        {return svgToBitmap(getResources(), R.raw.orgbusiness, 30);}

        else if(org.getOrgPrimaryCategory().contains("Charity"))
        {return svgToBitmap(getResources(), R.raw.orgcharity, 30);}

        else if (org.getOrgPrimaryCategory().contains("Fashion"))
        {return svgToBitmap(getResources(), R.raw.orgfashion, 30);}

        else if (org.getOrgPrimaryCategory().contains("Promoter"))
        {return svgToBitmap(getResources(), R.raw.orgpromoter, 30);}

        else if (org.getOrgPrimaryCategory().contains("Fraternity"))
        {return svgToBitmap(getResources(), R.raw.orgfraternity, 30);}

        else if (org.getOrgPrimaryCategory().contains("Not-for-profit"))
        {return svgToBitmap(getResources(), R.raw.orgnonprofit, 30);}

        else if (org.getOrgPrimaryCategory().contains("Sports"))
        {return svgToBitmap(getResources(), R.raw.orgsports, 30);}

        else if (org.getOrgPrimaryCategory().contains("Student"))
        {return svgToBitmap(getResources(), R.raw.orgstudent, 30);}

        else if (org.getOrgPrimaryCategory().contains("Religion"))
        {return svgToBitmap(getResources(), R.raw.orgreligon, 30);}

        return null;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //SVG Conversion method.
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
