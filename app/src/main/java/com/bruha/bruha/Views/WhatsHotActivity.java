package com.bruha.bruha.Views;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.bruha.bruha.Adapters.MapListViewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WhatsHotActivity extends FragmentActivity {
    ArrayList<Date> datesSaved;
    UserCustomFilters userCustomFilters;

    // Creating a CaldroidFragment object
  //  private CaldroidFragment caldroidFragment;
    ArrayList<String> calendarSelected;

    //The List of mEvents to be displayed when clicked on a certain date.
    ArrayList<Event> selectedDateEvents = new ArrayList<>();

    // Creating a CaldroidFragment object
    private CaldroidFragment caldroidFragment;

    //The List of events containing in the local database.
    ArrayList<Event> mEvents = new ArrayList<>();
    MapListViewAdapter adapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_hot);


         mListView = (ListView) findViewById(R.id.ChangeList);
        init();
        setCalendar();
    }

    private void init() {
            // Create the local DB object
            SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
            SQLiteUtils sqLiteUtils = new SQLiteUtils();
            mEvents = sqLiteUtils.getUserEventInfo(dbHelper);
        }

    private void setCalendar(){
/*
 // Dynamically changing the calendar height / width due to the bug while it is within a
        // scrollview

        LinearLayout linearCalendar = (LinearLayout) findViewById(R.id.CalendarLayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

    // Storing the screen height and width into int variables
        int height = size.y;
        int width = size.x;

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = linearCalendar.getLayoutParams();

// Re-setting the height parameter to .75 the max screen height
        params.height =  (int)Math.round(height*.55);
        params.width = (int)Math.round(width*.80);
        */

        // Creating a date formatter for the calendar


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
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.CalendarLayout, caldroidFragment);
        t.commit();

        // Getting current date and setting background and adding to arraylist

        cal.add(Calendar.DATE, 0);
        Date currentDate = cal.getTime();


        Log.v("Date:", currentDate.toString());
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = formatter.format(cal.getTime());
        Log.v("Date:",formatted);




        // Overriding the default background for current date
        caldroidFragment.setBackgroundResourceForDate(android.R.color.black, currentDate);

      for(Event x: mEvents) {

          Date ThisDate= currentDate;
          try {
               ThisDate = formatter.parse(x.getEventDate());
          } catch (ParseException e) {
              e.printStackTrace();
          }
          caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_blue_dark,ThisDate);

      }

        // Setup listener for date onClick
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                selectedDateEvents.clear();

                final SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                String NewDate = Format.format(date);

                for(Event x: mEvents)
                {
                    if(x.getEventDate().equals(NewDate))
                    {
                        selectedDateEvents.add(x);
                    }
                }


                setAdapter();
                mListView.setAdapter(adapter);

            }
        };

        // Setting the listener to the calendar
        caldroidFragment.setCaldroidListener(listener);
    }

    private void setAdapter() {
        adapter = new MapListViewAdapter(this, selectedDateEvents);
    }
}
