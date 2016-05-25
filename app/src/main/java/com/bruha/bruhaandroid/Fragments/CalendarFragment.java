package com.bruha.bruhaandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ArhamRazaMac on 16-05-17.
 */

public class CalendarFragment extends Fragment {

    // Creating a CaldroidFragment object
    private CaldroidFragment caldroidFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_whats_hot, container, false);

        setCalendar();


        return view;
    }


    private void setCalendar() {
        // Setup caldroid fragment
        caldroidFragment = new CaldroidFragment();

        // Setup arguments for the CalDroid
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();

        // Setting month / year
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));

        // Swipe nav between months
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);

        // Enable 6 weeks in calendar
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

        // Dark Theme
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

        // Applying the arguments
        caldroidFragment.setArguments(args);

        // Attach to the activity
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.CalendarLayout, caldroidFragment);
        t.commit();

        // Getting current date and setting background and adding to arraylist
        cal.add(Calendar.DATE, 0);
        Date currentDate = cal.getTime();

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = formatter.format(cal.getTime());

        // Overriding the default background for current date
        caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_green_light, currentDate);
    }
}

