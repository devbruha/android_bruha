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

    UserCustomFilters userCustomFilters = new UserCustomFilters();

    ArrayList<String> savedQuickie = new ArrayList<>();

    // Two calendar variables for different formats depending on tasks

    ArrayList<String> calendarSelected = new ArrayList<>();
    ArrayList<Date> savedDates = new ArrayList<>();

    Map<String, ArrayList<String>> savedCategories = new HashMap<>();

    int savedAdmissionPrice = 0;

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

    public int getSavedAdmissionPrice(){

        return savedAdmissionPrice;
    }

    public UserCustomFilters getUserCustomFilters(){

        return userCustomFilters;
    }


}
