package com.bruha.bruha.Views;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.SQLUtils;
import com.bruha.bruha.R;
import com.daimajia.swipe.util.Attributes;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends FragmentActivity {

    private UserCustomFilters mUserCustomFilters = new UserCustomFilters();

    // Our database hostname and the credentials for our showdom_android account
    String url = "jdbc:mysql://66.147.244.109:3306/showdomc_web2"; //
    String user = "showdomc_android";
    String pass = "show12345!";

    SQLUtils sqlu ; //The SQLUtil object type that will be initialized later depending on the credentials given above.
    ArrayList<Event> mEvents = new ArrayList<>();       //The Array that will hold the Events that we will pass around(to Adapter,the List...
    ArrayList<Event> newEvent = new ArrayList<>();
    ArrayList<Event> Backup= new ArrayList<>();         //Array Backup of the whole list,since mEvent changes when we update the adapter in filtersavebutton.
    ListviewAdapter adapter;

    //Default Constructor for the class ListActivity
    public ListActivity()
    {
        sqlu = new SQLUtils(url, user, pass); //Creating Object type SQLUtils using credentials needed
        mEvents = sqlu.Events();

        for(Event x:mEvents)
        {
            Backup.add(x);
        }
    }

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    public static int Clicks=0; //The variable holding the number of times the user has tapped on a list item.

    private void setUpFilters(){

        // Calling the FilterView class to set the layout for the filters

        FilterView filterView = new FilterView(this);
        mUserCustomFilters = filterView.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.

        setUpFilters();

        //Creating an variable of type Listview Adapter to create the list view.
        adapter=new ListviewAdapter(this, mEvents); //Calling the adapter ListView to help set the List

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
               //Just used for testing,ignore if not delete String name = mEvents[0].getEventName();
               //Just used for testing,ignore if not delete  String nsize= mEvents.length + "" ;
                Toast.makeText(view.getContext(), "OnItemLongClickListener" , Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.filterSaveButton)
    public void ImplementingButton(View view)  {

        newEvent.clear();

        //Filtering the date.
        List<String> Dates= mUserCustomFilters.getDateFilter();
        double price=(double)mUserCustomFilters.getAdmissionPriceFilter();

       ArrayList<String> Filters= mUserCustomFilters.getQuickieFilter();


        //Last Checkpoint,remember to come here and see what the Log values shows and implement the filters accordingly.
        for(String y:Filters)
        {
            Log.v("The Tags:",y);
        }

        for (String x : Dates) {
            int i = 0;
            while (i < Backup.size()) {
                if (x.equals(Backup.get(i).getEventDate()) && Backup.get(i).getEventPrice()<=price ) {
                    newEvent.add(Backup.get(i));
                }
                i++;
            }
        }

        adapter.getData().clear();

        adapter.getData().addAll(newEvent);

        mListView.setAdapter(adapter);

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
}
