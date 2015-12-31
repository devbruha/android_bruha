// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Model;

import android.app.Application;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

// This is a class that shall be used to store global variables for the application
// In this context, that pretty much means filters...

/**
 * Created by Thomas on 6/10/2015.
 */

public class MyApplication extends Application{

    public static String credentialError = "";

    public static boolean internetCheck = false;

    public static ArrayList<Event> backupEventList = new ArrayList<>();
    public static ArrayList<Venue> backupVenueList = new ArrayList<>();
    public static ArrayList<Artist> backupArtistList = new ArrayList<>();
    public static ArrayList<Organizations> backupOrganizationList = new ArrayList<>();

    // Variable to represent whether the user is logged in or not, static so that it can be called
    // from any other class and updated.

    public static boolean loginCheck = false;

    public static String venueID = "000000000";
    public static String organizationID = "00000000";
    public static String moreInfoCheck = "none";

    public static String userName = "false";

    public static String filterTracker = "Event";

    public static ViewGroup.LayoutParams listIconParam;

    public static ArrayList<Event> sourceEvents= new ArrayList<>();
    public static ArrayList<String> sourceEventsID= new ArrayList<>();

    public static ArrayList<Venue> sourceVenues= new ArrayList<>();
    public static ArrayList<String> sourceVenuesID= new ArrayList<>();

    public static ArrayList<Artist> sourceArtists= new ArrayList<>();
    public static ArrayList<String> sourceArtistsID= new ArrayList<>();

    public static ArrayList<Organizations> sourceOrganizations= new ArrayList<>();
    public static ArrayList<String> sourceOrganizationsID= new ArrayList<>();

    public static ArrayList<ArrayList<Items>> mainList = new ArrayList<>();

    public static UserCustomFilters userFilters = new UserCustomFilters();

    ArrayList<String> savedQuickie = new ArrayList<>();

    // Two calendar variables for different formats depending on tasks

    ArrayList<String> calendarSelected = new ArrayList<>();
    ArrayList<Date> savedDates = new ArrayList<>();

    public ArrayList<String> getDatesSelected(){

        return calendarSelected;
    }

    public ArrayList<Date> getSavedDates(){

        return savedDates;
    }

    public ArrayList<String> getSavedQuickie(){

        return savedQuickie;
    }
}
