package com.bruha.bruha.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Processing.SQLUtils;
import com.bruha.bruha.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends ActionBarActivity {

    // Our database hostname and the credentials for our showdom_android account
    String url = "jdbc:mysql://66.147.244.109:3306/showdomc_web2"; //
    String user = "showdomc_android";
    String pass = "show12345!";
    SQLUtils sqlu ;
    List<String> events;
    Event[] nmEvents;

    //Default Constructor for the class ListActivity
    public ListActivity()
    {
        sqlu = new SQLUtils(url, user, pass); //Creating Object type SQLUtils using credentials needed
        events = sqlu.Events();

        int size = events.size();
        size=size/3;
        nmEvents=  new Event[size];
        int j=0;
        for(int i=0;i<nmEvents.length;i++)
        {
            Event eventsi=new Event();
            eventsi.setEventName(events.get(j));
            j++;
            eventsi.setEventDate(events.get(j));
            j++;
            eventsi.setEventLocName(events.get(j));
            j++;


            nmEvents[i]=eventsi;
        }

    }

  //  Event[] mEvents = new Event[10]; //Creating an array of Events to help Test our Application.

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    public static int Clicks=0; //The variable holding the number of times the user has tapped on a list item.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.


        /*
        //A test array for Events
        for(int i=0; i<10;i++) {

            //Creating variables type event used for testing the app
            Event event = new Event();
           // mEvents[i].setEventIcon(R.drawable.testround);
           // mEvents[i].setEventPicture(R.drawable.testround);
            event.setEventName("Meow");
            event.setEventDate("May 25,2015");
            event.setEventDistance(5.0);
            event.setEventPrice(10.0);

            mEvents[i] = event;
        }
*/

        //Creating an variable of type Listview Adapter to create the list view.
        ListviewAdapter adapter=new ListviewAdapter(this,nmEvents); //Calling the adapter ListView to help set the List


        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        //Swipe stuff
        adapter.setMode(Attributes.Mode.Single);

        //OnClick listener when Item on the list is tapped
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Assigning the Relative Layout that contains the detailed description.
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.DescriptionLayout);

                //Assigning the summary desciption stuff that will hide and reappear depending on the clicks.
                ImageView Bubble = (ImageView) view.findViewById(R.id.EventImageBubble);
                TextView EventName = (TextView) view.findViewById(R.id.TextEventName);
                TextView EventDate = (TextView) view.findViewById(R.id.TextEventDate);
                TextView EventPrice = (TextView) view.findViewById(R.id.TextEventPrice);
                TextView EventDistance = (TextView) view.findViewById(R.id.TextEventDistance);


                if(Clicks%2==0) {
                    //Popping the detailed description into view.
                    layout.setVisibility(View.VISIBLE);

                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.INVISIBLE);
                    EventName.setVisibility(View.INVISIBLE);
                    EventDate.setVisibility(View.INVISIBLE);
                    EventPrice.setVisibility(View.INVISIBLE);
                    EventDistance.setVisibility(View.INVISIBLE);
                }

                else{
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);

                    //Displaying the summary description back upon the 2nd click.
                    Bubble.setVisibility(View.VISIBLE);
                    EventName.setVisibility(View.VISIBLE);
                    EventDate.setVisibility(View.VISIBLE);
                    EventPrice.setVisibility(View.VISIBLE);
                    EventDistance.setVisibility(View.VISIBLE);
                }

                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });

        //Swipe Interface being Implemented

        //What happens upon touching the ListView.
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });

        //OnClick Listener when Item is tapped for a longer period of time
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name = nmEvents[0].getEventName();
                String nsize= nmEvents.length + "" ;
                Toast.makeText(view.getContext(), nsize , Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });

    }


/*
         //The Old OnClick Listener
        //Setting an OnClickListener everytime a item of the list is tapped.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                setPanelHeight(); //Setting the layout to cover half the page


                //Assigning the Relative Layout that contains the detailed description.
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.DescriptionLayout);

                //Assigning the summary desciption stuff that will hide and reappear depending on the clicks.
                ImageView Bubble = (ImageView) view.findViewById(R.id.EventImageBubble);
                TextView EventName = (TextView) view.findViewById(R.id.TextEventName);
                TextView EventDate = (TextView) view.findViewById(R.id.TextEventDate);
                TextView EventPrice = (TextView) view.findViewById(R.id.TextEventPrice);
                TextView EventDistance = (TextView) view.findViewById(R.id.TextEventDistance);


                if(Clicks%2==0) {
                    //Popping the detailed description into view.
                    layout.setVisibility(View.VISIBLE);

                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.INVISIBLE);
                    EventName.setVisibility(View.INVISIBLE);
                    EventDate.setVisibility(View.INVISIBLE);
                    EventPrice.setVisibility(View.INVISIBLE);
                    EventDistance.setVisibility(View.INVISIBLE);
                }

                else{
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);

                    //Displaying the summary description back upon the 2nd click.
                    Bubble.setVisibility(View.VISIBLE);
                    EventName.setVisibility(View.VISIBLE);
                    EventDate.setVisibility(View.VISIBLE);
                    EventPrice.setVisibility(View.VISIBLE);
                    EventDistance.setVisibility(View.VISIBLE);

                }




                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });
    }
    */



    //Button Implementation for navigating to the Map from ListView.
    @OnClick(R.id.MapButton)
    public void StartMapActivity(View view)
    {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    //Button Implementation for navigating to the Dashboard from ListView.
    @OnClick(R.id.DashboardButton)
    public void StartDashboardActivity(View view)
    {
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }


/*
    //The method that sets the height of the layout to half the screen.
    public void setPanel(){

        // Storing the Relative Layout in a Variable to alter its dimens with.
      LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.mLinear);

        // Android functions to determine the screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
           int width = size.x;

        // Retrieves the current parameters of the layout and storing them in variable params
        ViewGroup.LayoutParams params = mLinearLayout.getLayoutParams();

        // Re-setting the height parameter to .50 the max screen height
        params.width =  (int)Math.round(width*.50);

        Log.v("height test", params.height + "");
    }
    */

}
