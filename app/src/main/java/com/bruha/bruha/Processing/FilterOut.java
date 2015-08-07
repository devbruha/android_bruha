package com.bruha.bruha.Processing;

import android.app.Activity;
import android.widget.ListView;

import com.bruha.bruha.Adapters.EventListviewAdapter;
import com.bruha.bruha.Adapters.VenueListViewAdapter;
import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Model.Venue;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 6/12/2015.
 */

// A class that shall be used to apply the filters to the list/map dynamically
public class FilterOut {

    Activity mActivity;

    UserCustomFilters customFilters = MyApplication.userEventFilters;

    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Venue> venues = new ArrayList<>();
    ArrayList<Artist> artists = new ArrayList<>();
    ArrayList<Organizations> organizations = new ArrayList<>();

    ArrayList<Event> tempEventList = new ArrayList<>();
    ArrayList<String> tempEventListID = new ArrayList<>();

    ArrayList<Venue> tempVenueList = new ArrayList<>();
    ArrayList<String> tempVenueListID = new ArrayList<>();

    public FilterOut(Activity activity){

        mActivity = activity;

        //customFilters = ((MyApplication) mActivity.getApplicationContext()).getUserCustomFilters();
    }

    public void filterEventList(EventListviewAdapter adapter){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = MyApplication.backupEventList;

        tempEventList.clear();
        tempEventListID.clear();

        tempEventList = new ArrayList<>(events);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        // If the date filter is non null, filter off all events that dont have date...

        if(customFilters.getDateFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                if (!customFilters.getDateFilter().contains(tempEventList.get(i - 1).getEventDate())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    tempEventList.remove(i - 1);
                }
            }
        }

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getCategoryFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getCategoryFilter().containsKey(tempEventList.get(i - 1).getEventPrimaryCategory())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    tempEventList.remove(i - 1);
                }

                else{

                    ArrayList<Event> tempEvent = new ArrayList<>();
                    ArrayList<String> tempEventID = new ArrayList<>();

                    outerloop:
                    for(String key: customFilters.getCategoryFilter().keySet()){

                        if(customFilters.getCategoryFilter().get(key).size() != 0) {

                            for (int j = 0; j < tempEventList.get(i - 1).getEventSubCategories().size(); j++) {

                                if (customFilters.getCategoryFilter().get(key).contains(tempEventList.get(i - 1).getEventSubCategoriesID().get(j))) {

                                    if(!tempEvent.contains(tempEventList.get(i-1))){

                                        tempEvent.add(tempEventList.get(i-1));
                                        tempEventID.add(tempEventList.get(i-1).getEventid());
                                    }
                                }
                            }
                        }
                        else{

                            if( key.equals(tempEventList.get(i - 1).getEventPrimaryCategory()) ){

                                if(!tempEvent.contains(tempEventList.get(i-1))){

                                    tempEvent.add(tempEventList.get(i-1));
                                    tempEventID.add(tempEventList.get(i-1).getEventid());
                                }
                            }
                        }
                    }

                    if(!tempEvent.contains(tempEventList.get(i-1))){

                        tempEventListID.add(tempEventList.get(i - 1).getEventid());
                        tempEventList.remove(i - 1);
                    }
                }
            }
        }

        // If event price > admission filter, filter event off

        if(customFilters.getAdmissionPriceFilter() != -1) {

            for (int i = tempEventList.size(); i > 0; i--) {

                if (tempEventList.get(i - 1).getEventPrice() > customFilters.getAdmissionPriceFilter()) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    tempEventList.remove(i - 1);
                }
            }
        }

        if(customFilters.getDateFilter().size() == 0 &&
                customFilters.getAdmissionPriceFilter() == -1 &&
                customFilters.getCategoryFilter().size() == 0) {

            tempEventListID.clear();
            tempEventListID.add("All");
            tempEventList = new ArrayList<>(events);
        }

        MyApplication.sourceEvents = new ArrayList<>(tempEventList);
        MyApplication.sourceEventsID = new ArrayList<>(tempEventListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempEventList);
        listView.setAdapter(adapter);
    }

    public void filterEventMap(HashMap<String, Marker> markerMap){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = MyApplication.backupEventList;

        tempEventList.clear();
        tempEventListID.clear();

        tempEventList = new ArrayList<>(events);

        // If the date filter is non null, filter off all events that dont have date...

        if(customFilters.getDateFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                if (!customFilters.getDateFilter().contains(tempEventList.get(i - 1).getEventDate())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                    tempEventList.remove(i - 1);
                }
                else{
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(true);
                }
            }
        }

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getCategoryFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getCategoryFilter().containsKey(tempEventList.get(i - 1).getEventPrimaryCategory())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                    tempEventList.remove(i - 1);
                }

                else{

                    ArrayList<Event> tempEvent = new ArrayList<>();
                    ArrayList<String> tempEventID = new ArrayList<>();

                    outerloop:
                    for(String key: customFilters.getCategoryFilter().keySet()){

                        if(customFilters.getCategoryFilter().get(key).size() != 0) {

                            for (int j = 0; j < tempEventList.get(i - 1).getEventSubCategories().size(); j++) {

                                if (customFilters.getCategoryFilter().get(key).contains(tempEventList.get(i - 1).getEventSubCategoriesID().get(j))) {

                                    if(!tempEvent.contains(tempEventList.get(i-1))){

                                        tempEvent.add(tempEventList.get(i-1));
                                        tempEventID.add(tempEventList.get(i-1).getEventid());
                                    }
                                }
                            }
                        }
                        else{

                            if( key.equals(tempEventList.get(i-1).getEventPrimaryCategory()) ){

                                if(!tempEvent.contains(tempEventList.get(i-1))){

                                    tempEvent.add(tempEventList.get(i-1));
                                    tempEventID.add(tempEventList.get(i-1).getEventid());
                                }
                            }
                        }
                    }

                    if(!tempEvent.contains(tempEventList.get(i-1))){

                        tempEventListID.add(tempEventList.get(i - 1).getEventid());
                        markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                        tempEventList.remove(i - 1);
                    }
                    else{

                        markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(true);
                    }
                }
            }
        }

        // If event price > admission filter, filter event off

        if(customFilters.getAdmissionPriceFilter() != -1) {

            for (int i = tempEventList.size(); i > 0; i--) {

                if (tempEventList.get(i - 1).getEventPrice() > customFilters.getAdmissionPriceFilter()) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                    tempEventList.remove(i - 1);
                }
                else{
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(true);
                }
            }
        }

        if(customFilters.getDateFilter().size() == 0 &&
                customFilters.getAdmissionPriceFilter() == -1 &&
                customFilters.getCategoryFilter().size() == 0) {

            for(String key : markerMap.keySet()){
                markerMap.get(key).setVisible(true);
            }
            tempEventListID.clear();
            tempEventListID.add("All");
        }

        MyApplication.sourceEvents = new ArrayList<>(tempEventList);
        MyApplication.sourceEventsID = new ArrayList<>(tempEventListID);

    }

    public void filterVenueList(VenueListViewAdapter adapter){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        venues = MyApplication.backupVenueList;

        tempVenueList.clear();
        tempVenueListID.clear();

        tempVenueList = new ArrayList<>(venues);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getCategoryFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getCategoryFilter().containsKey(tempEventList.get(i - 1).getEventPrimaryCategory())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    tempEventList.remove(i - 1);
                }

                else{

                    ArrayList<Event> tempEvent = new ArrayList<>();
                    ArrayList<String> tempEventID = new ArrayList<>();

                    outerloop:
                    for(String key: customFilters.getCategoryFilter().keySet()){

                        if(customFilters.getCategoryFilter().get(key).size() != 0) {

                            for (int j = 0; j < tempEventList.get(i - 1).getEventSubCategories().size(); j++) {

                                if (customFilters.getCategoryFilter().get(key).contains(tempEventList.get(i - 1).getEventSubCategoriesID().get(j))) {

                                    if(!tempEvent.contains(tempEventList.get(i-1))){

                                        tempEvent.add(tempEventList.get(i-1));
                                        tempEventID.add(tempEventList.get(i-1).getEventid());
                                    }
                                }
                            }
                        }
                        else{

                            if( key.equals(tempEventList.get(i - 1).getEventPrimaryCategory()) ){

                                if(!tempEvent.contains(tempEventList.get(i-1))){

                                    tempEvent.add(tempEventList.get(i-1));
                                    tempEventID.add(tempEventList.get(i-1).getEventid());
                                }
                            }
                        }
                    }

                    if(!tempEvent.contains(tempEventList.get(i-1))){

                        tempEventListID.add(tempEventList.get(i - 1).getEventid());
                        tempEventList.remove(i - 1);
                    }
                }
            }
        }

        if( customFilters.getVenueFilter().size() == 0) {

            tempVenueList.clear();
            tempVenueListID.add("All");
            tempVenueList = new ArrayList<>(venues);
        }

        MyApplication.sourceVenues = new ArrayList<>(tempVenueList);
        MyApplication.sourceVenuesID = new ArrayList<>(tempVenueListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempVenueList);
        listView.setAdapter(adapter);
    }

    public void filterVenueMap(HashMap<String, Marker> markerMap){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = MyApplication.backupEventList;

        tempEventList.clear();
        tempEventListID.clear();

        tempEventList = new ArrayList<>(events);

        // If the date filter is non null, filter off all events that dont have date...

        if(customFilters.getDateFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                if (!customFilters.getDateFilter().contains(tempEventList.get(i - 1).getEventDate())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                    tempEventList.remove(i - 1);
                }
                else{
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(true);
                }
            }
        }

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getCategoryFilter().size() != 0) {

            for (int i = tempEventList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getCategoryFilter().containsKey(tempEventList.get(i - 1).getEventPrimaryCategory())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                    tempEventList.remove(i - 1);
                }

                else{

                    ArrayList<Event> tempEvent = new ArrayList<>();
                    ArrayList<String> tempEventID = new ArrayList<>();

                    outerloop:
                    for(String key: customFilters.getCategoryFilter().keySet()){

                        if(customFilters.getCategoryFilter().get(key).size() != 0) {

                            for (int j = 0; j < tempEventList.get(i - 1).getEventSubCategories().size(); j++) {

                                if (customFilters.getCategoryFilter().get(key).contains(tempEventList.get(i - 1).getEventSubCategoriesID().get(j))) {

                                    if(!tempEvent.contains(tempEventList.get(i-1))){

                                        tempEvent.add(tempEventList.get(i-1));
                                        tempEventID.add(tempEventList.get(i-1).getEventid());
                                    }
                                }
                            }
                        }
                        else{

                            if( key.equals(tempEventList.get(i-1).getEventPrimaryCategory()) ){

                                if(!tempEvent.contains(tempEventList.get(i-1))){

                                    tempEvent.add(tempEventList.get(i-1));
                                    tempEventID.add(tempEventList.get(i-1).getEventid());
                                }
                            }
                        }
                    }

                    if(!tempEvent.contains(tempEventList.get(i-1))){

                        tempEventListID.add(tempEventList.get(i - 1).getEventid());
                        markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                        tempEventList.remove(i - 1);
                    }
                    else{

                        markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(true);
                    }
                }
            }
        }

        // If event price > admission filter, filter event off

        if(customFilters.getAdmissionPriceFilter() != -1) {

            for (int i = tempEventList.size(); i > 0; i--) {

                if (tempEventList.get(i - 1).getEventPrice() > customFilters.getAdmissionPriceFilter()) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(false);
                    tempEventList.remove(i - 1);
                }
                else{
                    markerMap.get(tempEventList.get(i-1).getEventid()).setVisible(true);
                }
            }
        }

        if(customFilters.getDateFilter().size() == 0 &&
                customFilters.getAdmissionPriceFilter() == -1 &&
                customFilters.getCategoryFilter().size() == 0) {

            for(String key : markerMap.keySet()){
                markerMap.get(key).setVisible(true);
            }
            tempEventListID.clear();
            tempEventListID.add("All");
        }

        MyApplication.sourceEvents = new ArrayList<>(tempEventList);
        MyApplication.sourceEventsID = new ArrayList<>(tempEventListID);

    }

}
