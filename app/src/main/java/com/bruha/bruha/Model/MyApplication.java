package com.bruha.bruha.Model;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// This is a class that shall be used to store global variables for the application
// In this context, that pretty much means filters...

/**
 * Created by Thomas on 6/10/2015.
 */

public class MyApplication extends Application{

    ArrayList<Event> backupEventList = new ArrayList<>();
    ArrayList<Event> selectedEventList = new ArrayList<>();

    // Variable to represent whether the user is logged in or not, static so that it can be called
    // from any other class and updated.

    public static boolean loginCheck = false;
    public static ArrayList<Event> sourceEvents= new ArrayList<>();

    UserCustomFilters userCustomFilters = new UserCustomFilters();

    ArrayList<String> savedQuickie = new ArrayList<>();

    // Two calendar variables for different formats depending on tasks

    ArrayList<String> calendarSelected = new ArrayList<>();
    ArrayList<Date> savedDates = new ArrayList<>();

    Map<String, ArrayList<String>> savedCategories = new HashMap<>();

    public ArrayList<String> getDatesSelected(){

        return calendarSelected;
    }

    public ArrayList<Date> getSavedDates(){

        return savedDates;
    }

    public ArrayList<String> getSavedQuickie(){

        return savedQuickie;
    }

    public Map<String,ArrayList<String>> getSavedCategories(){

        return savedCategories;
    }

    public UserCustomFilters getUserCustomFilters(){

        return userCustomFilters;
    }

    public ArrayList<Event> getBackupEventList(){

        return backupEventList;
    }

    public ArrayList<Event> getSelectedEventList(){

        return selectedEventList;
    }
}
