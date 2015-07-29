package com.bruha.bruha.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class EventPageActivity extends ActionBarActivity {

    ArrayList<Event> mEvents= new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();
    ArrayList<Organizations> mOutfit = new ArrayList<>();
    Event event;
    Venues venue;
    Artists artist;
    Organizations outfit;
    String type;

    TextView EventName;
    TextView EventAge;
    TextView PageStartDateandTime;
    TextView PageEndDateandTime;
    TextView PageAddressLoc;
    TextView PageEventDescription;
    ImageView EventCoverPage;
    ImageView EventPicture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);


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

        else if(type.equals("Venue"))
        {
            //Finding out and storing the event that is to be displayed.
            for (Venues x : mVenues) {
                if (x.getVenueId().equals(id)) {
                    venue = x;
                }
            }
        }

        else if(type.equals("Outfit"))
        {
            //Finding out and storing the event that is to be displayed.
            for (Organizations x : mOutfit) {
                if (x.getOrgId().equals(id)) {
                    outfit = x;
                }
            }
        }

        else
        {
            //Finding out and storing the event that is to be displayed.
            for (Artists x : mArtists) {
                if (x.getArtistId().equals(id)) {
                    artist = x;
                }
            }
        }

        resize();

        settext();

    }




    private void resize()
    {
        //The EventPage dimensions being set to fit all types of screen, along with the text being assigned.

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        EventCoverPage = (ImageView) findViewById(R.id.PageEventCoverPicture);
        ViewGroup.LayoutParams EventCoverPageParams = EventCoverPage.getLayoutParams();
        EventCoverPageParams.height =  (int)Math.round(height*.20);


        //Assigning the PageEventPicture to a variable to alter its dimensions after with.
        EventPicture = (ImageView) findViewById(R.id.PageEventPicture);
        ViewGroup.LayoutParams EventPictureParams = EventPicture.getLayoutParams();
        EventPictureParams.height =  (int)Math.round(height*.15);
        EventPictureParams.width  =  (int)Math.round(height*.15);


        //The PageEventName being Formatted.
         EventName = (TextView) findViewById(R.id.PageEventName);
        int x1= (int)Math.round(height*.038);
        EventName.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);


        //The PageEventAge being Formatted.
        EventAge= (TextView) findViewById(R.id.PageEventAge);
        int x2= (int)Math.round(height * .02);
        EventAge.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);

        //The Start,End and Address Text being resized.
        TextView PageStart = (TextView) findViewById(R.id.VenueHourText);
        TextView PageEnd = (TextView) findViewById(R.id.PageEndText);
        TextView PageAddress = (TextView) findViewById(R.id.PageAddressText);
        int x3= (int)Math.round(height * .018);
        PageStart.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        PageEnd.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        PageAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);

        //Setting these values.
        PageStartDateandTime = (TextView) findViewById(R.id.PageStartDateandTime);
        PageEndDateandTime = (TextView) findViewById(R.id.PageEndDateandTime);
        PageAddressLoc = (TextView) findViewById(R.id.PageAddressLocation);
        PageStartDateandTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        PageEndDateandTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        PageAddressLoc.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);


        //The About text being resized.
        TextView PageAbout = (TextView) findViewById(R.id.VenueHourText);
        int x4= (int)Math.round(height * .020);
        PageAbout.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);

        //The PageEventDescription's size being defined. TextSize is being defined too.
        PageEventDescription = (TextView) findViewById(R.id.PageEventDescription);
        ViewGroup.LayoutParams PageEventDescriptionParams = PageEventDescription.getLayoutParams();
        PageEventDescriptionParams.height= (int)Math.round(height * .14);
        int x5= (int)Math.round(height * .019);
        PageEventDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, x5);


        //Just spacing out for the map.
        TextView PageMapSpace = (TextView) findViewById(R.id.PageMapSpace);
        ViewGroup.LayoutParams PageMapSpaceParams = PageMapSpace.getLayoutParams();
        PageMapSpaceParams.height= (int)Math.round(height * .2);
        int x6= (int)Math.round(height * .10);
        PageMapSpace.setTextSize(TypedValue.COMPLEX_UNIT_PX, x6);
    }

    private void settext()
    {


        if(type.equals("Event"))
        {

            EventName.setText(event.getEventName());
            PageEventDescription.setText(event.getEventDescription());
            PageStartDateandTime.setText(event.getEventDate() + " At " + event.getEventStartTime());
            PageEndDateandTime.setText(event.getEventEndDate() + " At " + event.getEventEndTime());
            PageAddressLoc.setText(event.getEventLocName()+ ", "+ event.getEventLocSt()+", "+ event.getEventLocAdd());
            Picasso.with(this.getApplicationContext()).load(event.getEventPicture()).fit().into(EventPicture);
            Picasso.with(this.getApplicationContext()).load(event.getEventPicture()).fit().into(EventCoverPage);

        }

        else if(type.equals("Outfit"))
        {
            Picasso.with(this.getApplicationContext()).load(outfit.getOrgPicture()).fit().into(EventPicture);
            Picasso.with(this.getApplicationContext()).load(outfit.getOrgPicture()).fit().into(EventCoverPage);
            EventName.setText(outfit.getOrgName());
            PageEventDescription.setText(outfit.getOrgDescription());
            // PageStartDateandTime.setText(event.getEventDate() + " At " + event.getEventStartTime());
            // PageEndDateandTime.setText(event.getEventEndDate() + " At " + event.getEventEndTime());
            PageAddressLoc.setText(outfit.getOrgLocation());

        }

        else if(type.equals("Venue"))
        {
            Picasso.with(this.getApplicationContext()).load(venue.getVenuePicture()).fit().into(EventPicture);
            Picasso.with(this.getApplicationContext()).load(venue.getVenuePicture()).fit().into(EventCoverPage);
            EventName.setText(venue.getVenueName());
            PageEventDescription.setText(venue.getVenueDescription());
           // PageStartDateandTime.setText(event.getEventDate() + " At " + event.getEventStartTime());
           // PageEndDateandTime.setText(event.getEventEndDate() + " At " + event.getEventEndTime());
            PageAddressLoc.setText(venue.getVenueLocation());
        }

        else {


        }

    }

    private void init(){
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        //Assigns the array containing the list of events.
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenues= sqLiteUtils.getVenuesInfo(dbHelper);
        mOutfit= sqLiteUtils.getOutfitsInfo(dbHelper);
        mArtists= sqLiteUtils.getArtistInfo(dbHelper);
    }
}