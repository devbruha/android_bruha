package com.bruha.bruha.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;

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

        mListView.setAdapter(adapter);



        //Setting an OnClickListener everytime a item of the list is tapped.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               setPanelHeight();
                String name = mEvents[position].getEventName();
                String date = mEvents[position].getEventDate();
                String price = mEvents[position].getEventPrice() + "";
                String distance= mEvents[position].getEventDistance()+ "";
                String message = String.format("%s will be playing on %s and it will be %s and is at a distance of %s from you",
                        name,
                        date,
                        price,
                        distance);
                Toast.makeText(ListActivity.this, message, Toast.LENGTH_LONG).show();
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
