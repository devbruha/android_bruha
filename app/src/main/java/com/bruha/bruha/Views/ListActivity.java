package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bruha.bruha.Adapters.ArtistsListViewAdapter;
import com.bruha.bruha.Adapters.EventListviewAdapter;
import com.bruha.bruha.Adapters.OrganizationListViewAdapter;
import com.bruha.bruha.Adapters.VenueListViewAdapter;
import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venues;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends FragmentActivity {
    //The Arrays that will contain the information retrieved from the local database.
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Organizations> mOutfit = new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();

    //Initualiing the Filter Obects to hide and display everytime Venue,Artist,Event and Outfit Filters are applied.
    LinearLayout mEventCategoryListView;
    LinearLayout mVenueCategoryListView;
    LinearLayout mArtistCategoryListView;
    LinearLayout mOrganizationCategoryListView;



    LinearLayout linearCalendar ;
    TextView admission;
    TextView mPrice;
    SeekBar prce;

    ArrayList<Event> backupEventList;         //Array backupEventList of the whole list,since mEvent changes when we update the adapter in filter save button.
    EventListviewAdapter adapter;

    //Injecting Buttons using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;

    //The Linear layout to be set OnCLickListener to and background changing.
    @InjectView(R.id.venueButton) LinearLayout venueButton;
    @InjectView(R.id.artistButton) LinearLayout artistButton;
    @InjectView(R.id.outfitButton) LinearLayout orgButton;
    @InjectView(R.id.eventButton) LinearLayout eventButton;

    //The Image Views to set and Change for the Filters
    @InjectView(R.id.eventButtonImage) ImageView eventImage;
    @InjectView(R.id.venueButtonImage) ImageView venueImage;
    @InjectView(R.id.artistButtonImage) ImageView artistImage;
    @InjectView(R.id.outfitButtonImage) ImageView outfitImage;

    //The TextViews to be RESIZED(HELLO,RESIZE EM!!) and Changed.
    @InjectView(R.id.filtereventtext) TextView eventText;
    @InjectView(R.id.filtervenuetext) TextView venueText;
    @InjectView(R.id.filterartisttext) TextView artistText;
    @InjectView(R.id.filteroutfittext) TextView outfitText;
    @InjectView(R.id.DashboardButton) ImageView dudeButton;
    @InjectView(R.id.MapButton) ImageView mapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);                   //Injecting all the objects to be imported from above.

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height =  (int)Math.round(height*.07);
        dudeButtonLayoutParams.width =  (int)Math.round(height*.07);

        ViewGroup.LayoutParams mapButtonLayoutParams = mapButton.getLayoutParams();
        mapButtonLayoutParams.height =  (int)Math.round(height*.07);
        mapButtonLayoutParams.width =  (int)Math.round(height*.07);

        init();

        if(MyApplication.sourceEvents.size() == 0) {

            //Creating an variable of type Listview Adapter to create the list view.
            adapter = new EventListviewAdapter(this, mEvents); //Calling the adapter mListView to help set the List
        }
        else{
            adapter = new EventListviewAdapter(this, MyApplication.sourceEvents);
        }

        setUpFilters();

        //Setting the Filters to the Filter Item once they have been declared and set in the view,later to be altered with.

        mEventCategoryListView = (LinearLayout) findViewById(R.id.event_category_listview);
        mVenueCategoryListView = (LinearLayout) findViewById(R.id.venue_category_listview);
        mArtistCategoryListView = (LinearLayout) findViewById(R.id.artist_category_listview);
        mOrganizationCategoryListView = (LinearLayout) findViewById(R.id.organization_category_listview);

        linearCalendar = (LinearLayout) findViewById(R.id.calendarView);
        prce = (SeekBar) findViewById(R.id.priceBar);
        mPrice = (TextView) findViewById(R.id.priceDisplay);
        admission = (TextView) findViewById(R.id.admissionTextView);

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        //Swipe stuff
       // adapter.setMode(Attributes.Mode.Single);

        setButtons();

        if(MyApplication.filterTracker.equals("Event"))
        {
            eventButton(null);
        }

       else if(MyApplication.filterTracker.equals("Venue"))
        {
            venueButton(null);
        }

        else if(MyApplication.filterTracker.equals("Outfit"))
        {
            organizationButton(null);
        }

        else{
            artistButton(null);
        }

    }

    private void setButtons() {

        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));
        mapButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.mapicon, 30));


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

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mapButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        mapButton.setAlpha(1f);
                        startMapActivity(v);
                    }
                });
                animator.start();
            }
        });
    }

    private void init() {

        backupEventList = ((MyApplication) getApplicationContext()).getBackupEventList();
        backupEventList.clear();

        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        mVenues = sqLiteUtils.getVenuesInfo(dbHelper);
        mOutfit = sqLiteUtils.getOutfitsInfo(dbHelper);
        mArtists = sqLiteUtils.getArtistInfo(dbHelper);


        Log.v("This",mEvents.size()+"");
        for (int i = 0; i < mEvents.size(); i++)
        {
            Log.v("Name",mEvents.get(i).getEventName());
        }


        for(Event x:mEvents)
        {
            backupEventList.add(x);
        }
    }

    private void setUpFilters(){
        // Calling the FilterView class to set the layout for the filters
        FilterView filterView = new FilterView(this, adapter, null);
        filterView.init();
    }

    //venueButton Implemented to switch the mListView to show List of Venues.
    @OnClick(R.id.venueButton)
    public void venueButton(View view) {

        MyApplication.filterTracker = "Venue";

        //Changing shit:
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));

        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venueorange, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        
        mVenueCategoryListView.setVisibility(view.VISIBLE);

        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.GONE);
        mOrganizationCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);



        VenueListViewAdapter venueAdapter;

        //Creating an variable of type Listview Adapter to create the list view.
        venueAdapter=new VenueListViewAdapter(this, mVenues); //Calling the adapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);

        venueText.setTextColor(Color.parseColor("#FFFFBB33"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
    }

    //venueButton Implemented to switch the mListView to show List of Venues.
    @OnClick(R.id.outfitButton)
    public void organizationButton(View view) {

        MyApplication.filterTracker = "Outfit";

        OrganizationListViewAdapter OrgAdapter;

        mOrganizationCategoryListView.setVisibility(view.VISIBLE);

        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mVenueCategoryListView.setVisibility(view.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);

        //Creating an variable of type Listview Adapter to create the list view.
        OrgAdapter=new OrganizationListViewAdapter(this, mOutfit); //Calling the adapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(OrgAdapter);

        //Changing shit:
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));

        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitorange, 50));

        outfitText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
    }

    @OnClick(R.id.eventButton)
    public void eventButton(View view) {

        MyApplication.filterTracker = "Event";
        mListView.setAdapter(adapter);


        TextView Admission = (TextView) findViewById(R.id.admissionTextView);
        TextView Price = (TextView) findViewById(R.id.priceDisplay);
        SeekBar prce = (SeekBar) findViewById(R.id.priceBar);

        Admission.setVisibility(View.VISIBLE);
        Price.setVisibility(View.VISIBLE);
        prce.setVisibility(View.VISIBLE);
        mVenueCategoryListView.setVisibility(view.GONE);
        mEventCategoryListView.setVisibility(view.VISIBLE);
        mArtistCategoryListView.setVisibility(view.GONE);
        mOrganizationCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.VISIBLE);


        //Changing shit:
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));

        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventorange, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));

        eventText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));


    }

    @OnClick(R.id.artistButton)
    public void artistButton(View view) {

        MyApplication.filterTracker = "Artist";

        //Changing shit:
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));

        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistorange, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));

        admission.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        prce.setVisibility(View.GONE);
        mEventCategoryListView.setVisibility(view.GONE);
        mArtistCategoryListView.setVisibility(view.VISIBLE);
        mOrganizationCategoryListView.setVisibility(view.GONE);
        mVenueCategoryListView.setVisibility(view.GONE);
        linearCalendar.setVisibility(view.GONE);

        ArtistsListViewAdapter artistsListViewAdapter;

        //Creating an variable of type Listview Adapter to create the list view.
        artistsListViewAdapter=new ArtistsListViewAdapter(this, mArtists); //Calling the adapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter.
        mListView.setAdapter(artistsListViewAdapter);

        artistText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
    }

    //Button Implementation for navigating to the Map from mListView.
    public void startMapActivity(View view)
    {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
        finish();
    }

    //Button Implementation for navigating to the Dashboard from mListView.
    public void startDashboardActivity(View view)
    {
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }


    //SVG SHIT:
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