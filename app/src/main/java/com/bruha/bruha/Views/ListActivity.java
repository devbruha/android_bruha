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
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.SQLUtils;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.daimajia.swipe.util.Attributes;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends FragmentActivity {

    private UserCustomFilters mUserCustomFilters = new UserCustomFilters();

    ArrayList<Event> mEvents = new ArrayList<>();       //The Array that will hold the Events that we will pass around(to Adapter,the List...
    ArrayList<Event> newEvent = new ArrayList<>();
    ArrayList<Event> Backup= new ArrayList<>();         //Array Backup of the whole list,since mEvent changes when we update the adapter in filter save button.
    ListviewAdapter adapter;

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

        init();

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

    private void init(){

        // Create the local DB object

        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);

        for(Event x:mEvents)
        {
            Backup.add(x);
        }

    }

    @OnClick(R.id.filterSaveButton)
    public void ImplementingButton(View view)  {

        //To make sure it is empty beforehand.
        newEvent.clear();

        //Obtaining all the filters that the user selected.
        List<String> Dates= mUserCustomFilters.getDateFilter();
        double price=(double)mUserCustomFilters.getAdmissionPriceFilter();
        ArrayList<String> Filters= mUserCustomFilters.getQuickieFilter();
        Map<String, ArrayList<String>> CategoryFilter = mUserCustomFilters.getCategoryFilter();


        //If "All Events" chosen from the filter layout,then load all events from the backup.
        if(Filters.get(0).equals("All Events"))
        {
            //Filtering out the Items,remember to compare with price.
            for(Event event:Backup)
            {
                if(event.getEventPrice() <= price)
                newEvent.add(event);
            }
        }


        //If "Today" Chosen from the filter layout,then load all events in that day.
        else if(Filters.get(0).equals("Today"))
        {
            //Getting today's date and formatting it to the type that can be compared with the Event Date's type.
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            Log.v("Date:",formattedDate);

            //Filtering the Quickie,remember to compare with price.
            for(Event Ev:Backup)
            {
                if(Ev.getEventDate().equals(formattedDate) && Ev.getEventPrice() <= price )
                {
                    newEvent.add(Ev);
                }

            }
        }

        //If 'This Weekend' is selected in the Quickie Filter.
        else if(Filters.get(0).equals("This Weekend"))
        {
            //Getting the date of Saturday for the week.
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            c.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            //Getting the date of Sunday for the week.
            Calendar d=Calendar.getInstance();
            d.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            d.getTime();
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDatee = dff.format(d.getTime());

            //Filtering out the items.,remember to compare with price.
            for(Event Eve:Backup)
            {
                if((Eve.getEventDate().equals(formattedDate) || Eve.getEventDate().equals(formattedDatee))&& Eve.getEventPrice() <= price)
                {
                    newEvent.add(Eve);
                }
            }
        }


        //If none of the quickie fields are selected,we check the calender dates.
        else {

            //Getting the dates from the filter, filtering events out accordingly and setting the price along with it.
            for (String x : Dates) {
                int i = 0;
                while (i < Backup.size()) {
                    if (x.equals(Backup.get(i).getEventDate()) && Backup.get(i).getEventPrice() <= price) {
                        newEvent.add(Backup.get(i));
                    }

                    i++;
                }
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
