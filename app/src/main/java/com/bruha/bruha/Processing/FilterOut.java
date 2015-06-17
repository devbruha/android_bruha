package com.bruha.bruha.Processing;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.widget.ListView;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.R;

import java.util.ArrayList;
import java.util.logging.Filter;

/**
 * Created by Thomas on 6/12/2015.
 */

// A class that shall be used to apply the filters to the list/map dynamically
public class FilterOut {

    Activity mActivity;

    UserCustomFilters customFilters;

    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Event> filteredEvents= new ArrayList<>();
    ArrayList<Event> tempList = new ArrayList<>();

    public FilterOut(Activity activity){

        mActivity = activity;

        customFilters = ((MyApplication) mActivity.getApplicationContext()).getUserCustomFilters();
    }

    public void filterAll(ListviewAdapter adapter){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = ((MyApplication) mActivity.getApplicationContext()).getBackupEventList();

        tempList.clear();

        tempList = new ArrayList<>(events);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        // If the date filter is non null, filter off all events that dont have date...

        //Log.v("date size", customFilters.getDateFilter().size()+"");

        if(customFilters.getDateFilter().size() != 0) {

            for (int i = tempList.size(); i > 0; i--) {

                if (!customFilters.getDateFilter().contains(tempList.get(i - 1).getEventDate())) {

                    tempList.remove(i - 1);
                }
            }
        }

        // If event price > admission filter, filter event off


        Log.v("price value check", customFilters.getAdmissionPriceFilter()+"");

        if(customFilters.getAdmissionPriceFilter() != -1) {

            for (int i = tempList.size(); i > 0; i--) {

                if (tempList.get(i - 1).getEventPrice() > customFilters.getAdmissionPriceFilter()) {

                    tempList.remove(i - 1);
                }
            }
        }

        if(customFilters.getDateFilter().size() == 0 &&
                customFilters.getAdmissionPriceFilter() == -1) {

            tempList = new ArrayList<>(events);
        }

        MyApplication.sourceEvents = new ArrayList<>(tempList);

        adapter.getData().clear();
        adapter.getData().addAll(tempList);
        listView.setAdapter(adapter);
    }
}
