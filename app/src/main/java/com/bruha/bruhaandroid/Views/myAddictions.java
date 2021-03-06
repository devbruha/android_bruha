// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.
// myAddictions - The my addictions activity page

package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruha.bruhaandroid.Adapters.EventListviewAdapter;
import com.bruha.bruhaandroid.Adapters.OrganizationListViewAdapter;
import com.bruha.bruhaandroid.Adapters.VenueListViewAdapter;
import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Model.Venue;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class myAddictions extends FragmentActivity implements ObservableScrollViewCallbacks {

    //Event'spinner Addictions Variables.
    ArrayList<Event> mEvents = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Event> addictedEvents = new ArrayList<>();
    ArrayList<String> eventID = new ArrayList<>();
    EventListviewAdapter adapter;
    //Venue'spinner Addictions Variables.
    ArrayList<Venue> mVenues = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Venue> addictedVenues = new ArrayList<>();
    ArrayList<String> venueID = new ArrayList<>();
    //Artist'spinner Addictions Variables.
    ArrayList<Artist> mArtist = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Artist> addictedArtists = new ArrayList<>();
    ArrayList<String> artistID = new ArrayList<>();
    //Organization'spinner Addictions Variables.
    ArrayList<Organizations> mOrg = new ArrayList<>(); //The Array that will hold the mEvents that we will pass around(to Adapter,the List...
    ArrayList<Organizations> addictedOrg = new ArrayList<>();
    ArrayList<String> orgID = new ArrayList<>();
    //Injecting Views using ButterKnife Library
    @InjectView(android.R.id.list) ObservableListView mListView;
    ImageView dudeButton;
    ImageView mapButton;
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
    //MyAddiction page title
    @InjectView(R.id.addictionText) TextView addictionText;
    @InjectView(R.id.addictionImage) ImageView addictionImage;
    ActionBar mActionBar;
    @InjectView(R.id.exploreEmptypic) ImageView emptyStatepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addictions);
        ButterKnife.inject(this);

        mListView.setScrollViewCallbacks(this);
        actionbar();

        init();             //Initializes the local variables to the info inside the local database.

        setUpFilters();     //Sets up the filters in the page.

        //Creating an variable of type Listview Adapter to create the list view.
        adapter=new EventListviewAdapter(this, addictedEvents, eventID); //Calling the eventAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(adapter);

        eventButton(null); //Populate the Events upon opening the page.
    }

    private void actionbar() {
        /*
        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        */
        dudeButton =(ImageView) findViewById(R.id.addictionDudeButton);
       // mapButton = (ImageView) mCustomView.findViewById(R.id.MapButton);
       // mapButton.setVisibility(View.INVISIBLE);
        resize();       //Resizing the page and buttons
     //   mActionBar.setCustomView(mCustomView);
      //  mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setUpFilters(){
        // Calling the FilterView class to set the layout for the filters
        FilterView filterView = new FilterView(this, adapter,null,null,null, null);
        filterView.init();
        SlidingUpPanelLayout slidepanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout_lower);
        slidepanel.setTouchEnabled(false);
    }

    private void resize() {
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

        dudeOnClick();

        Typeface opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");
        ViewGroup.LayoutParams addictionTextLayoutParams = addictionText.getLayoutParams();
        addictionTextLayoutParams.height =  (int)Math.round(height*.04);
        addictionTextLayoutParams.width =  (int)Math.round(height*.15);
        int x= (int)Math.round(height * .02);
        addictionText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        addictionText.setTypeface(opensansregfnt);

        ViewGroup.LayoutParams addictionImageLayoutParams = addictionImage.getLayoutParams();
        addictionImageLayoutParams.height =  (int)Math.round(height*.04);
        addictionImageLayoutParams.width =  (int)Math.round(height*.05);
        //Setting the Button Image
        addictionImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.myaddictions, 30));

        //Empty State ImageView being resized
        ViewGroup.LayoutParams emptyStatePicLayoutParams = emptyStatepic.getLayoutParams();
        emptyStatePicLayoutParams.height =  (int)Math.round(height * .45);
    }

    private void dudeOnClick() {
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

    //SVG Conversion.
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

    public void startDashboardActivity(View view) {
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

    @OnClick(R.id.venueButton)
    public void venueButton(View view) {
        //venueButton Implemented to switch the mListView to show List of Venue.

        //Changing filters:
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venueorange, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        venueText.setTextColor(Color.parseColor("#FFFFBB33"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));

        //Creating an variable of type Listview Adapter to create the list view.
        VenueListViewAdapter venueAdapter;
        venueAdapter=new VenueListViewAdapter(this, addictedVenues,venueID); //Calling the venueAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(venueAdapter);
        if(addictedVenues.size()==0)
        {
            emptyStatepic.setVisibility(View.VISIBLE);
        }
        else{
            emptyStatepic.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.outfitButton)
    public void organizationButton(View view) {
        //organization.Button Implemented to switch the mListView to show List of Organizations.
        //Creating an variable of type Listview Adapter to create the list view.
        OrganizationListViewAdapter OrgAdapter;
        OrgAdapter=new OrganizationListViewAdapter(this, addictedOrg,orgID); //Calling the eventAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter
        mListView.setAdapter(OrgAdapter);

        //Changing filters:
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitorange, 50));
        outfitText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
        if(addictedOrg.size()==0)
        {
            emptyStatepic.setVisibility(View.VISIBLE);
        }
        else{
            emptyStatepic.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.eventButton)
    public void eventButton(View view) {
        mListView.setAdapter(adapter);
        if(addictedEvents.size()==0)
        {
            emptyStatepic.setVisibility(View.VISIBLE);
        }
        else{
            emptyStatepic.setVisibility(View.INVISIBLE);
        }

        //Changing filters:
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventorange, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        eventText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        artistText.setTextColor(Color.parseColor("#ffffff"));
    }

    @OnClick(R.id.artistButton)
    public void artistButton(View view) {
       /* //Changing filters:
        artistButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.borderorange));
        venueButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        orgButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
        eventImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 50));
        venueImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 50));
        artistImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhapurpleface, 50));
        outfitImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 50));
        artistText.setTextColor(Color.parseColor("#FFFFBB33"));
        venueText.setTextColor(Color.parseColor("#ffffff"));
        outfitText.setTextColor(Color.parseColor("#ffffff"));
        eventText.setTextColor(Color.parseColor("#ffffff"));

        //Creating an variable of type Listview Adapter to create the list view.
        ArtistsListViewAdapter artistsListViewAdapter;
        artistsListViewAdapter=new ArtistsListViewAdapter(this, addictedArtists,artistID); //Calling the eventAdapter mListView to help set the List
        //Sets the Adapter from the class Listview Adapter.
        mListView.setAdapter(artistsListViewAdapter);
        */
        showDialog();
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (mActionBar == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (mActionBar.isShowing()) {
                mActionBar.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!mActionBar.isShowing()) {
                mActionBar.show();
            }
        }

    }

    public void showDialog()
    {   //Alert dialog to let the user know discoverable is coming soon.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("Discoverable Coming Soon!");
        builder.setCancelable(true);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}