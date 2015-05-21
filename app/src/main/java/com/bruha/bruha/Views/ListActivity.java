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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends ActionBarActivity {


    //Default Constructor for the class ListActivity
    public ListActivity()
    {}


    Event[] mEvents = new Event[10]; //Creating an array of Events to help Test our Application.

    //Injecting Buttons using ButterKnife Library
    @InjectView(R.id.MapButton) Button mMapButton;
    @InjectView(R.id.DashboardButton) Button mDashboardButton;
    @InjectView(android.R.id.list) ListView mListView;
   // @InjectView(R.id.example_swipe_lv_list) SwipeListView swipe;

    public static int Clicks=0; //The variable holding the number of times the user has tapped on a list item.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.




        //A test array for Events
        for(int i=0; i<10;i++) {

            Event event = new Event();
           // mEvents[i].setEventIcon(R.drawable.testround);
           // mEvents[i].setEventPicture(R.drawable.testround);
            event.setEventName("Meow");
            event.setEventDate("May 25,2015");
            event.setEventDistance(5.0);
            event.setEventPrice(10.0);

            mEvents[i] = event;
        }


        //Creating an variable of type Listview Adapter to create the list view.
        ListviewAdapter adapter=new ListviewAdapter(this,mEvents); //Calling the adapter ListView to help set the List


/*

//Swipe List View Interface being Implemented

        swipe.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));

                swipe.openAnimate(position); //when you touch front view it will open

            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));

                swipe.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }

        });

        //Setting what each swipe does

        swipe.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
        swipe.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
        swipe.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        swipe.setOffsetLeft(convertDpToPixel(260f)); // left side offset
        swipe.setOffsetRight(convertDpToPixel(0f)); // right side offset
        swipe.setAnimationTime(50); // animarion time
        swipe.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress


        */

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);



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




 //Used for the ListSwipe
    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }




    //The method that sets the height of the layout to half the screen.
    public void setPanelHeight(){





        // Storing the sliding panel (lin layout) into a linear layout variable

      RelativeLayout mLayouttoChange=(RelativeLayout) findViewById(R.id.LayoutToChange);




        // Android functions to determine the screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);





        // Storing the screen height into an int variable
           int height = size.y;




        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = mLayouttoChange.getLayoutParams();

        // Re-setting the height parameter to .50 the max screen height


        params.height =  (int)Math.round(height*.50);

        Log.v("height test", params.height + "");

    }




}
