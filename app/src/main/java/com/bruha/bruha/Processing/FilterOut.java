package com.bruha.bruha.Processing;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.widget.ListView;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.R;

import java.util.ArrayList;
import java.util.logging.Filter;

/**
 * Created by Thomas on 6/12/2015.
 */

// A class that shall be used to apply the filters to the list/map dynamically
public class FilterOut {

    Activity mActivity;

    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Event> filteredEvents= new ArrayList<>();

    public FilterOut(Activity activity){

        mActivity = activity;
    }

    public void filterDate(ArrayList<String> dates, ListviewAdapter adapter){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = ((MyApplication) mActivity.getApplicationContext()).getBackupEventList();
        filteredEvents = ((MyApplication) mActivity.getApplicationContext()).getSelectedEventList();

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        // clears the filtered events then re fills it

        filteredEvents.clear();

        for(int i = 0; i < events.size(); i++){

            if(dates.contains(events.get(i).getEventDate())){

                filteredEvents.add(events.get(i));
            }
        }

        // If there are no filters then fill the adapter with all events (events variable) otherwise
        // fill the adapter with the filtered events

        if(filteredEvents.size() == 0){

            adapter.getData().clear();
            adapter.getData().addAll(events);
            listView.setAdapter(adapter);
        }

        else{

            adapter.getData().clear();
            adapter.getData().addAll(filteredEvents);
            listView.setAdapter(adapter);
        }


    }
}
