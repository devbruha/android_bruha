package com.bruha.bruha.Processing;

import android.app.Activity;
import android.widget.ListView;

import com.bruha.bruha.Adapters.EventListviewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.UserCustomFilters;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 6/12/2015.
 */

// A class that shall be used to apply the filters to the list/map dynamically
public class FilterOut {

    Activity mActivity;

    UserCustomFilters customFilters;

    ArrayList<Event> events = new ArrayList<>();

    ArrayList<Event> tempList = new ArrayList<>();
    ArrayList<String> tempListID = new ArrayList<>();

    public FilterOut(Activity activity){

        mActivity = activity;

        customFilters = ((MyApplication) mActivity.getApplicationContext()).getUserCustomFilters();
    }

    public void filterList(EventListviewAdapter adapter){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = ((MyApplication) mActivity.getApplicationContext()).getBackupEventList();

        tempList.clear();
        tempListID.clear();

        tempList = new ArrayList<>(events);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        // If the date filter is non null, filter off all events that dont have date...

        if(customFilters.getDateFilter().size() != 0) {

            for (int i = tempList.size(); i > 0; i--) {

                if (!customFilters.getDateFilter().contains(tempList.get(i - 1).getEventDate())) {

                    tempListID.add(tempList.get(i-1).getEventid());
                    tempList.remove(i - 1);
                }
            }
        }

        // If event price > admission filter, filter event off

        if(customFilters.getAdmissionPriceFilter() != -1) {

            for (int i = tempList.size(); i > 0; i--) {

                if (tempList.get(i - 1).getEventPrice() > customFilters.getAdmissionPriceFilter()) {

                    tempListID.add(tempList.get(i-1).getEventid());
                    tempList.remove(i - 1);
                }
            }
        }

        if(customFilters.getDateFilter().size() == 0 &&
                customFilters.getAdmissionPriceFilter() == -1) {

            tempListID.clear();
            tempListID.add("All");
            tempList = new ArrayList<>(events);
        }

        MyApplication.sourceEvents = new ArrayList<>(tempList);
        MyApplication.sourceEventsID = new ArrayList<>(tempListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempList);
        listView.setAdapter(adapter);
    }

    public void filterMap(HashMap<String,Marker> markerMap){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = ((MyApplication) mActivity.getApplicationContext()).getBackupEventList();

        tempList.clear();
        tempListID.clear();

        tempList = new ArrayList<>(events);

        // If the date filter is non null, filter off all events that dont have date...

        if(customFilters.getDateFilter().size() != 0) {

            for (int i = tempList.size(); i > 0; i--) {

                if (!customFilters.getDateFilter().contains(tempList.get(i - 1).getEventDate())) {

                    tempListID.add(tempList.get(i - 1).getEventid());
                    markerMap.get(tempList.get(i-1).getEventid()).setVisible(false);
                    tempList.remove(i - 1);
                }
                else{
                    markerMap.get(tempList.get(i-1).getEventid()).setVisible(true);
                }
            }
        }

        // If event price > admission filter, filter event off

        if(customFilters.getAdmissionPriceFilter() != -1) {

            for (int i = tempList.size(); i > 0; i--) {

                if (tempList.get(i - 1).getEventPrice() > customFilters.getAdmissionPriceFilter()) {

                    tempListID.add(tempList.get(i-1).getEventid());
                    markerMap.get(tempList.get(i-1).getEventid()).setVisible(false);
                    tempList.remove(i - 1);
                }
                else{
                    markerMap.get(tempList.get(i-1).getEventid()).setVisible(true);
                }
            }
        }

        if(customFilters.getDateFilter().size() == 0 &&
                customFilters.getAdmissionPriceFilter() == -1) {

            for(String key : markerMap.keySet()){
                markerMap.get(key).setVisible(true);
            }
            tempListID.clear();
            tempListID.add("All");
        }

        MyApplication.sourceEvents = new ArrayList<>(tempList);
        MyApplication.sourceEventsID = new ArrayList<>(tempListID);

    }
}
