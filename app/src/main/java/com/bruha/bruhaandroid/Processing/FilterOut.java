// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Processing;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bruha.bruhaandroid.Adapters.ArtistsListViewAdapter;
import com.bruha.bruhaandroid.Adapters.EventListviewAdapter;
import com.bruha.bruhaandroid.Adapters.OrganizationListViewAdapter;
import com.bruha.bruhaandroid.Adapters.VenueListViewAdapter;
import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.UserCustomFilters;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.R;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 6/12/2015.
 */

// A class that shall be used to apply the filters to the list/map dynamically
public class FilterOut {

    Activity mActivity;

    UserCustomFilters customFilters = MyApplication.userFilters;

    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Venue> venues = new ArrayList<>();
    ArrayList<Artist> artists = new ArrayList<>();
    ArrayList<Organizations> organizations = new ArrayList<>();

    ArrayList<Event> tempEventList = new ArrayList<>();
    ArrayList<String> tempEventListID = new ArrayList<>();

    ArrayList<Venue> tempVenueList = new ArrayList<>();
    ArrayList<String> tempVenueListID = new ArrayList<>();

    ArrayList<Artist> tempArtistList = new ArrayList<>();
    ArrayList<String> tempArtistListID = new ArrayList<>();

    ArrayList<Organizations> tempOrganizationList = new ArrayList<>();
    ArrayList<String> tempOrganizationListID = new ArrayList<>();
    ImageView emptyStatePic;

    public FilterOut(Activity activity){

        mActivity = activity;


        //customFilters = ((MyApplication) mActivity.getApplicationContext()).getUserCustomFilters();
    }

    public void filterEventList(EventListviewAdapter adapter){
        emptyStatePic = (ImageView) mActivity.findViewById(R.id.exploreEmptypic);

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        events = MyApplication.backupEventList;

        tempEventList.clear();
        tempEventListID.clear();

        tempEventList = new ArrayList<>(events);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        if(!customFilters.getEventStringFilter().equals("")){

            for (int i = tempEventList.size(); i > 0; i--) {

                if (!tempEventList.get(i-1).getEventName().toLowerCase().contains(customFilters.getEventStringFilter().toLowerCase())) {

                    tempEventListID.add(tempEventList.get(i - 1).getEventid());
                    tempEventList.remove(i - 1);
                }
            }


        }

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
                customFilters.getCategoryFilter().size() == 0 &&
                customFilters.getEventStringFilter().equals("")) {

            tempEventListID.clear();
            tempEventListID.add("All");
            tempEventList = new ArrayList<>(events);
        }

        MyApplication.sourceEvents = new ArrayList<>(tempEventList);
        MyApplication.sourceEventsID = new ArrayList<>(tempEventListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempEventList);
        if(adapter.getCount()==0)
        {
            emptyStatePic.setVisibility(View.VISIBLE);
            emptyStatePic.setImageResource(R.drawable.event_taco_white_lrg);
        }
        else{
            emptyStatePic.setVisibility(View.INVISIBLE);
        }
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
        emptyStatePic = (ImageView) mActivity.findViewById(R.id.exploreEmptypic);

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        venues = MyApplication.backupVenueList;

        tempVenueList.clear();
        tempVenueListID.clear();

        tempVenueList = new ArrayList<>(venues);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        if(!customFilters.getVenueStringFilter().equals("")){

            for (int i = tempVenueList.size(); i > 0; i--) {

                if (!tempVenueList.get(i-1).getVenueName().toLowerCase().contains(customFilters.getVenueStringFilter().toLowerCase())) {

                    tempVenueListID.add(tempVenueList.get(i - 1).getVenueId());
                    tempVenueList.remove(i - 1);
                }
            }
        }

        // If the category filter is non null, filter off all events that dont have date...

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getVenueFilter().size() != 0) {

            for (int i = tempVenueList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getVenueFilter().contains(tempVenueList.get(i - 1).getVenuePrimaryCategory())) {

                    tempVenueListID.add(tempVenueList.get(i - 1).getVenueId());
                    tempVenueList.remove(i - 1);
                }
            }
        }

        if( customFilters.getVenueFilter().size() == 0 &&
                customFilters.getVenueStringFilter().equals("")) {

            tempVenueList.clear();
            tempVenueListID.add("All");
            tempVenueList = new ArrayList<>(venues);
        }

        MyApplication.sourceVenues = new ArrayList<>(tempVenueList);
        MyApplication.sourceVenuesID = new ArrayList<>(tempVenueListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempVenueList);
        if(adapter.getCount()==0)
        {
            emptyStatePic.setVisibility(View.VISIBLE);
            emptyStatePic.setImageResource(R.drawable.venue_stool_white);
        }
        else{
            emptyStatePic.setVisibility(View.INVISIBLE);
        }
        listView.setAdapter(adapter);
    }

    public void filterVenueMap(HashMap<String, Marker> markerMap){

        venues = MyApplication.backupVenueList;

        tempVenueList.clear();
        tempVenueListID.clear();

        tempVenueList = new ArrayList<>(venues);

        // If the category filter is non null, filter off all events that dont have date...

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getVenueFilter().size() != 0) {

            for (int i = tempVenueList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getVenueFilter().contains(tempVenueList.get(i - 1).getVenuePrimaryCategory())) {

                    tempVenueListID.add(tempVenueList.get(i - 1).getVenueId());
                    markerMap.get(tempVenueList.get(i-1).getVenueId()).setVisible(false);
                    tempVenueList.remove(i - 1);
                }
                else{

                    markerMap.get(tempVenueList.get(i-1).getVenueId()).setVisible(true);
                }
            }
        }

        if( customFilters.getVenueFilter().size() == 0) {

            for(String key : markerMap.keySet()){
                markerMap.get(key).setVisible(true);
            }

            tempVenueList.clear();
            tempVenueListID.add("All");
            tempVenueList = new ArrayList<>(venues);
        }

        MyApplication.sourceVenues = new ArrayList<>(tempVenueList);
        MyApplication.sourceVenuesID = new ArrayList<>(tempVenueListID);
    }

    public void filterArtistList(ArtistsListViewAdapter adapter){

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        artists = MyApplication.backupArtistList;

        tempArtistList.clear();
        tempArtistListID.clear();

        tempArtistList = new ArrayList<>(artists);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        // If the category filter is non null, filter off all events that dont have date...

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getArtistFilter().size() != 0) {

            for (int i = tempArtistList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getArtistFilter().contains(tempArtistList.get(i - 1).getArtistPrimaryCategory())) {

                    tempArtistListID.add(tempArtistList.get(i - 1).getArtistId());
                    tempArtistList.remove(i - 1);
                }
            }
        }

        if( customFilters.getArtistFilter().size() == 0) {

            tempArtistList.clear();
            tempArtistListID.add("All");
            tempArtistList = new ArrayList<>(artists);
        }

        MyApplication.sourceArtists = new ArrayList<>(tempArtistList);
        MyApplication.sourceArtistsID = new ArrayList<>(tempArtistListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempArtistList);
        listView.setAdapter(adapter);
    }

    public void filterOrganizationList(OrganizationListViewAdapter adapter){
        emptyStatePic = (ImageView) mActivity.findViewById(R.id.exploreEmptypic);

        // Retrieves the shared variables events and filteredEvents, events contains all events and filterd
        // events contains the filtered

        organizations = MyApplication.backupOrganizationList;

        tempOrganizationList.clear();
        tempOrganizationListID.clear();

        tempOrganizationList = new ArrayList<>(organizations);

        ListView listView = (ListView)mActivity.findViewById(android.R.id.list);

        if(!customFilters.getOrganizationStringFilter().equals("")){

            for (int i = tempOrganizationList.size(); i > 0; i--) {

                if (!tempOrganizationList.get(i-1).getOrgName().toLowerCase().contains(customFilters.getOrganizationStringFilter().toLowerCase())) {

                    tempOrganizationListID.add(tempOrganizationList.get(i - 1).getOrgId());
                    tempOrganizationList.remove(i - 1);
                }
            }
        }

        // If the category filter is non null, filter off all events that dont have date...

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getOrganizationFilter().size() != 0) {

            for (int i = tempOrganizationList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getOrganizationFilter().contains(tempOrganizationList.get(i - 1).getOrgPrimaryCategory())) {

                    tempOrganizationListID.add(tempOrganizationList.get(i - 1).getOrgId());
                    tempOrganizationList.remove(i - 1);
                }
            }
        }

        if( customFilters.getOrganizationFilter().size() == 0 &&
                customFilters.getOrganizationStringFilter().equals("")) {

            tempOrganizationList.clear();
            tempOrganizationListID.add("All");
            tempOrganizationList = new ArrayList<>(organizations);
        }

        MyApplication.sourceOrganizations = new ArrayList<>(tempOrganizationList);
        MyApplication.sourceOrganizationsID = new ArrayList<>(tempOrganizationListID);

        adapter.getData().clear();
        adapter.getData().addAll(tempOrganizationList);
        if(adapter.getCount()==0)
        {
            emptyStatePic.setVisibility(View.VISIBLE);
            emptyStatePic.setImageResource(R.drawable.glasses_white_lrg);
        }
        else{
            emptyStatePic.setVisibility(View.INVISIBLE);
        }
        listView.setAdapter(adapter);
    }

    public void filterOrganizationMap(HashMap<String, Marker> markerMap){

        organizations = MyApplication.backupOrganizationList;

        tempOrganizationList.clear();
        tempOrganizationListID.clear();

        tempOrganizationList = new ArrayList<>(organizations);

        // If the category filter is non null, filter off all events that dont have date...

        // If the category filter is non null, filter off all events that dont have date...

        if(customFilters.getOrganizationFilter().size() != 0) {

            for (int i = tempOrganizationList.size(); i > 0; i--) {

                // If the item's primary category is NOT contained in the filter, remove said item

                if (!customFilters.getOrganizationFilter().contains(tempOrganizationList.get(i - 1).getOrgPrimaryCategory())) {

                    tempOrganizationListID.add(tempOrganizationList.get(i - 1).getOrgId());
                    markerMap.get(tempOrganizationList.get(i-1).getOrgId()).setVisible(false);
                    tempOrganizationList.remove(i - 1);
                }
                else{

                    markerMap.get(tempOrganizationList.get(i-1).getOrgId()).setVisible(true);
                }
            }
        }

        if( customFilters.getOrganizationFilter().size() == 0) {

            for(String key : markerMap.keySet()){
                markerMap.get(key).setVisible(true);
            }

            tempOrganizationList.clear();
            tempOrganizationListID.add("All");
            tempOrganizationList = new ArrayList<>(organizations);
        }

        MyApplication.sourceOrganizations = new ArrayList<>(tempOrganizationList);
        MyApplication.sourceOrganizationsID = new ArrayList<>(tempOrganizationListID);
    }

}
