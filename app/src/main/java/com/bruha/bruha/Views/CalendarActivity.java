package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.bruha.bruha.Adapters.MapListViewAdapter;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends FragmentActivity {
    // Creating a CaldroidFragment object
    ArrayList<Date> datesSaved;
    UserCustomFilters userCustomFilters;
  //  private CaldroidFragment caldroidFragment;
    ArrayList<String> calendarSelected;
    //The List of mEvents to be displayed when clicked on a certain date.
    ArrayList<Event> selectedDateEvents = new ArrayList<>();
    // Creating a CaldroidFragment object
    private CaldroidFragment caldroidFragment;
    //The List of events containing in the local database.
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Event> addictionEvents = new ArrayList<>();
    ArrayList<String> addictEID;
    MapListViewAdapter adapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_hot);

         mListView = (ListView) findViewById(R.id.ChangeList);
        init();
        setCalendar();
        setButton();
    }

    private void setButton() {
        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;

        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        final ImageView dudeButton = (ImageView) findViewById(R.id.whatshotDashboadButton);
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height = (int) Math.round(height * .0665);
        dudeButtonLayoutParams.width = (int) Math.round(height * .07);
        //Setting the Button Image
        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));

        dudeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(dudeButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        dudeButton.setAlpha(1f);
                        startDashboardActivity(v);
                    }
                });
                animator.start();
            }
        });
    }

    private void init() {
            // Create the local DB object
            SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
            SQLiteUtils sqLiteUtils = new SQLiteUtils();
            mEvents = sqLiteUtils.getUserEventInfo(dbHelper);
            addictEID= sqLiteUtils.getEventAddictions(dbHelper);
            ArrayList<Event> eventList = sqLiteUtils.getEventInfo(dbHelper);

        for(String Id:addictEID)
        {
            for(int i =0 ; i<eventList.size();i++)
            {
                if(Id.equals(eventList.get(i).getEventid()))
                {
                    addictionEvents.add(eventList.get(i));
                }
            }

        }
        }

    private void setCalendar(){
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

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = formatter.format(cal.getTime());

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

        for(Event x: addictionEvents) {
            Date ThisDate= currentDate;
            try {
                ThisDate = formatter.parse(x.getEventDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_orange_dark,ThisDate);
        }

        for(Event x: mEvents) {
            for(Event y: addictionEvents) {
                Date ThisDate= currentDate;
                Date NextDate = currentDate;
                try {
                    ThisDate = formatter.parse(x.getEventDate());
                    NextDate = formatter.parse(y.getEventDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(ThisDate.equals(NextDate))
                {caldroidFragment.setBackgroundResourceForDate(android.R.color.holo_purple,ThisDate);}
            }
        }

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                // Setup listener for date onClick
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

                for(Event x: addictionEvents)
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
        adapter = new MapListViewAdapter(this, selectedDateEvents,addictEID);
    }

    public void startDashboardActivity(View view) {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    //SVG Conversion:
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        }catch(SVGParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
