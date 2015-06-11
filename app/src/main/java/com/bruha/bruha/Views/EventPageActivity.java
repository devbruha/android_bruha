package com.bruha.bruha.Views;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

public class EventPageActivity extends ActionBarActivity {

    ArrayList<Event> mEvents;
    Event mEve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        init();


        Intent intent = getIntent();
        String id= intent.getStringExtra("EventId");

        for(Event x:mEvents)
        {
            if(x.getEventid().equals(id))
            {
                mEve = x;
            }
        }




        //The EventPage dimensions being set to fit all types of screen:

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;

        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ImageView EventCoverPage = (ImageView) findViewById(R.id.PageEventCoverPicture);
        ViewGroup.LayoutParams EventCoverPageParams = EventCoverPage.getLayoutParams();
        EventCoverPageParams.height =  (int)Math.round(height*.20);

        //Assigning the PageEventPicture to a variable to alter its dimensions after with.
        ImageView EventPicture = (ImageView) findViewById(R.id.PageEventPicture);
        ViewGroup.LayoutParams EventPictureParams = EventPicture.getLayoutParams();
        EventPictureParams.height =  (int)Math.round(height*.15);
        EventPictureParams.width  =  (int)Math.round(height*.15);



        //The PageEventName being Formatted.
        TextView EventName = (TextView) findViewById(R.id.PageEventName);
        int x1= (int)Math.round(height*.038);
        EventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
        EventName.setText(mEve.getEventName());

        //The PageEventAge being Formatted.
        TextView EventAge= (TextView) findViewById(R.id.PageEventAge);
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
        TextView PageStartDateandTime = (TextView) findViewById(R.id.PageStartDateandTime);
        TextView PageEndDateandTime = (TextView) findViewById(R.id.PageEndDateandTime);
        TextView PageAddressLoc = (TextView) findViewById(R.id.PageAddressLocation);
        PageStartDateandTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        PageEndDateandTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        PageAddressLoc.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        PageStartDateandTime.setText(mEve.getEventDate() + " At " + mEve.getEventStartTime());
        PageEndDateandTime.setText(mEve.getEventEndDate() + " At " + mEve.getEventEndTime());
        PageAddressLoc.setText(mEve.getEventLocName()+ ", "+mEve.getEventLocSt()+", "+mEve.getEventLocAdd());



        //The About text being resized.
        TextView PageAbout = (TextView) findViewById(R.id.VenueHourText);
        int x4= (int)Math.round(height * .020);
        PageAbout.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);

        //The PageEventDescription's size being defined. TextSize is being defined too.
        TextView PageEventDescription = (TextView) findViewById(R.id.PageEventDescription);
        ViewGroup.LayoutParams PageEventDescriptionParams = PageEventDescription.getLayoutParams();
        PageEventDescriptionParams.height= (int)Math.round(height * .14);
        int x5= (int)Math.round(height * .019);
        PageEventDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
        PageEventDescription.setText(mEve.getEventDescription());

        //Just spacing out for the map.
        TextView PageMapSpace = (TextView) findViewById(R.id.PageMapSpace);
        ViewGroup.LayoutParams PageMapSpaceParams = PageMapSpace.getLayoutParams();
        PageMapSpaceParams.height= (int)Math.round(height * .2);
        int x6= (int)Math.round(height * .10);
        PageMapSpace.setTextSize(TypedValue.COMPLEX_UNIT_PX, x6);




    }


    private void init(){

        // Create the local DB object

        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);



    }






}