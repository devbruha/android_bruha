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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bruha.bruha.Adapters.ArtistsListViewAdapter;
import com.bruha.bruha.Adapters.EventListviewAdapter;
import com.bruha.bruha.Adapters.OrganizationListViewAdapter;
import com.bruha.bruha.Adapters.VenueListViewAdapter;
import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class myAddictions extends ActionBarActivity {

    //Event's Addictions shit
    ArrayList<Event> mEvents = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Event> addictedEvents = new ArrayList<>();
    ArrayList<String> eventID = new ArrayList<>();
    EventListviewAdapter adapter;                      //Initialzing the Adapter for the ListView.

    //Venue's Addictions shit
    ArrayList<Venue> mVenues = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Venue> addictedVenues = new ArrayList<>();
    ArrayList<String> venueID = new ArrayList<>();

    //Artist's Addictions shit
    ArrayList<Artist> mArtist = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Artist> addictedArtists = new ArrayList<>();
    ArrayList<String> artistID = new ArrayList<>();

    //Organization's Addictions shit
    ArrayList<Organizations> mOrg = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Organizations> addictedOrg = new ArrayList<>();
    ArrayList<String> orgID = new ArrayList<>();

    //Injecting Views using ButterKnife Library
    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.myaddictionsDashboardImage) ImageView dudeButton;

    //The Linear layout to be set OnCLickListener to and background changing.
    @InjectView(R.id.venueButton)
    LinearLayout venueButton;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addictions);
        ButterKnife.inject(this);

        init();

        setUpFilters();

        //Creating an variable of type Listview Adapter to create the list view.
        adapter=new EventListviewAdapter(this, addictedEvents, eventID); //Calling the eventAdapter mListView to help set the List


        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        resize();

        eventButton(null);

    }

    private void setUpFilters(){
        // Calling the FilterView class to set the layout for the filters
        FilterView filterView = new FilterView(this, adapter,null,null,null, null);
        filterView.init();
        SlidingUpPanelLayout slidepanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout_lower);
        slidepanel.setTouchEnabled(false);
    }

    private void resize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;
        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height =  (int)Math.round(height*.07);
        dudeButtonLayoutParams.width =  (int)Math.round(height*.07);
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

    public void startDashboardActivity(View view)
    {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void init(){
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        //Reteieve the Lists to display.
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        eventID = sqLiteUtils.getEventAddictions(dbHelper);
        mVenues = sqLiteUtils.getVenuesInfo(dbHelper);
        venueID = sqLiteUtils.getVenueAddictions(dbHelper);
        mArtist = sqLiteUtils.getArtistInfo(dbHelper);
        artistID = sqLiteUtils.getArtistAddictions(dbHelper);
        mOrg = sqLiteUtils.getOrganizationsInfo(dbHelper);
        orgID = sqLiteUtils.getOrgAddictions(dbHelper);

        for(String Id:eventID)
        {
            for(int i =0 ; i<mEvents.size();i++)
            {
                if(Id.equals(mEvents.get(i).getEventid()))
                {
                    addictedEvents.add(mEvents.get(i));
                }
            }

        }

        for(String Id:venueID)
        {
            for(int i =0 ; i<mVenues.size();i++)
            {
                if(Id.equals(mVenues.get(i).getVenueId()))
                {
                    addictedVenues.add(mVenues.get(i));
                }
            }

        }

        for(String Id:artistID)
        {
            for(int i =0 ; i<mArtist.size();i++)
            {
                if(Id.equals(mArtist.get(i).getArtistId()))
                {
                    addictedArtists.add(mArtist.get(i));
                }
            }

        }

        for(String Id:orgID)
        {
            for(int i =0 ; i<mOrg.size();i++)
            {
                if(Id.equals(mOrg.get(i).getOrgId()))
                {
                    addictedOrg.add(mOrg.get(i));
                }
            }

        }

    }



    //venueButton Implemented to switch the mListView to show List of Venue.
    @OnClick(R.id.venueButton)
    public void venueButton(View view) {


        //Changing shit:
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));

        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venueorange, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));


        VenueListViewAdapter venueAdapter;

        //Creating an variable of type Listview Adapter to create the list view.
       venueAdapter=new VenueListViewAdapter(this, addictedVenues,venueID); //Calling the eventAdapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);

        venueText.setTextColor(Color.parseColor("#FFFFBB33"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
    }

    //venueButton Implemented to switch the mListView to show List of Venue.
    @OnClick(R.id.outfitButton)
    public void organizationButton(View view) {

        OrganizationListViewAdapter OrgAdapter;


        //Creating an variable of type Listview Adapter to create the list view.
        OrgAdapter=new OrganizationListViewAdapter(this, addictedOrg,orgID); //Calling the eventAdapter mListView to help set the List

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

        mListView.setAdapter(adapter);


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


        //Changing shit:
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));

        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistorange, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));


       ArtistsListViewAdapter artistsListViewAdapter;


        //Creating an variable of type Listview Adapter to create the list view.
        artistsListViewAdapter=new ArtistsListViewAdapter(this, addictedArtists,artistID); //Calling the eventAdapter mListView to help set the List

        //Sets the Adapter from the class Listview Adapter.
        mListView.setAdapter(artistsListViewAdapter);


        artistText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
    }


}