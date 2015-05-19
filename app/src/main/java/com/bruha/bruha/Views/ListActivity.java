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


    public ListActivity()
    {}

    Event[] mEvents = new Event[10];

    //Injecting Buttons using ButterKnife Library
    @InjectView(R.id.MapButton) Button mMapButton;
    @InjectView(R.id.DashboardButton) Button mDashboardButton;
    @InjectView(android.R.id.list) ListView mListView;
   // @InjectView(R.id.example_swipe_lv_list) SwipeListView swipe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);




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


        ListviewAdapter adapter=new ListviewAdapter(this,mEvents); //Calling the adapter ListView to help set the List


/*
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

        swipe.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
        swipe.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
        swipe.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        swipe.setOffsetLeft(convertDpToPixel(260f)); // left side offset
        swipe.setOffsetRight(convertDpToPixel(0f)); // right side offset
        swipe.setAnimationTime(50); // animarion time
        swipe.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
*/

        mListView.setAdapter(adapter);

        //Setting an OnClickListener everytime a item of the list is tapped.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                setPanelHeight();

                ImageView mBubble= (ImageView) view.findViewById(R.id.EventImageBubble);
                mBubble.setVisibility(View.INVISIBLE);

               // view.setAlpha((float) 0.25);



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


/*
    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

*/


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

        // Re-setting the height parameter to .75 the max screen height


        params.height =  (int)Math.round(height*.50);

        Log.v("height test", params.height + "");

    }




}
