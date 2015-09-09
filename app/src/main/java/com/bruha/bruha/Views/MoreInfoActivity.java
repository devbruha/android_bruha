package com.bruha.bruha.Views;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoreInfoActivity extends ActionBarActivity {

    String type;
    ArrayList<Event> mEvents = new ArrayList<>();
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

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

        panelSetup();


    }

    private void init() {
        //The method that calls the local database and sets all the variables accordingly.
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        //Assigns the array containing the list of events.
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
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

        // Storing the sliding panel (lin layout) into a linear layout variable

        LinearLayout dragLayout = (LinearLayout)findViewById(R.id.moreInfoSlideLayout);

        ViewGroup.LayoutParams handleParams = handleLayout.getLayoutParams();
        //handleParams.height = (int)Math.round(workingHeight*.33);
        mLayout.setPanelHeight((int)Math.round(workingHeight*.40));

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .65 the max screen height (status bar included)

        params.height =  (int)Math.round(workingHeight);

        initializePanel();
    }

    //Method to Format the Date that will be displayed.
    public String dateFormat(String Date)
    {
        String year=Date.substring(0,4);
        String Month=Date.substring(5,7);
        String Dates=Date.substring(8,10);
        String Displayed=getMonth(Month)+ " " + Dates + "," + year;
        return Displayed;
    }

    //Part of the Implementation of the dateFormat Method.
    public String getMonth(String Month)
    {
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
        ImageView filterimage = (ImageView) findViewById(R.id.eventcategory);
        TextView eventName = (TextView) findViewById(R.id.nonfilterEventName);
        TextView venueName = (TextView) findViewById(R.id.nonfilterVenueName);
        TextView venueSt = (TextView) findViewById(R.id.nonfilterVenueStreet);
        TextView venuecountry = (TextView) findViewById(R.id.nonfilterVenueCountry);
        TextView eventprice = (TextView) findViewById(R.id.eventprice);
        TextView eventdate = (TextView) findViewById(R.id.eventdate);
        TextView eventdesc = (TextView) findViewById(R.id.moreinfoeventdescription);
        ImageView eventPic = (ImageView) findViewById(R.id.moreinfoPicture);
        ImageView addictionImage = (ImageView) findViewById(R.id.addictionImage);

        addictionImage.setImageBitmap(svgToBitmap(getResources(),R.raw.myaddictions,60));

        //Setting the background image of the event.
        Picasso.with(getApplicationContext()).load(event.getEventPicture()).fit().into(eventPic);


        eventName.setText(event.getEventName());
        venueName.setText(event.getEventLocName());
        venueSt.setText(event.getEventLocSt());
        venuecountry.setText(event.getEventLocAdd());
        eventprice.setText(event.getEventPrice() + "");
        eventdate.setText(dateFormat(event.getEventDate()));
        eventdesc.setText(event.getEventDescription());
       Bitmap x = setEventIcon(event);
     filterimage.setImageBitmap(x);
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


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
