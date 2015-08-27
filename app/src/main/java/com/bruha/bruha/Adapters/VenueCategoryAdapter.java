package com.bruha.bruha.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.FilterOut;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 5/22/2015.
 */
public class VenueCategoryAdapter {

    FilterOut filtering;

    private FragmentActivity mActivity;
    private LinearLayout mLinearListView;
    private ArrayList<Items> mMainList;

    ArrayList<String> venueFilters = new ArrayList<>();

    UserCustomFilters mUserCustomFilter = MyApplication.userFilters;

    // boolean variables representing the upper and lower levels being selected

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;

    private VenueListViewAdapter mAdapter;
    HashMap<String, Marker> markerMap = new HashMap<>();

    // Constructor for the adapter, takes a context, linear layout and "super" list

    public VenueCategoryAdapter(FragmentActivity context, LinearLayout linearListView, ArrayList<Items> mainList, VenueListViewAdapter adapter, HashMap markerHashMap){

        this.mActivity = context;
        this.mLinearListView = linearListView;
        this.mMainList = mainList;

        filtering = new FilterOut(mActivity);

        for(int i = 0; i< mUserCustomFilter.getVenueFilter().size(); i++){

            venueFilters.add(mUserCustomFilter.getVenueFilter().get(i));
        }

        mAdapter = adapter;
        markerMap = markerHashMap;
    }

    public void set(){

        addFirstRow();
    }

    private void addFirstRow(){

        //Adds data into first row

        // This for loop only performs once as we only have 1 item on the parent level

        for (int i = 0; i < mMainList.size(); i++) {

            //Inflating the first level

            LayoutInflater inflater = null;
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater.inflate(R.layout.row_first, null);

            final TextView mProductName = (TextView) mLinearView.findViewById(R.id.textViewName);
            final RelativeLayout mLinearFirstArrow=(RelativeLayout)mLinearView.findViewById(R.id.linearFirst);
            final ImageView mImageArrowFirst=(ImageView)mLinearView.findViewById(R.id.imageFirstArrow);
            final LinearLayout mLinearScrollSecond=(LinearLayout)mLinearView.findViewById(R.id.linear_scroll);

            final String name = mMainList.get(i).getpName();

            // If the item is pressed, the linear layout representing the lower level is set to visible

            isMenuOpen(isFirstViewClick, mImageArrowFirst, mLinearScrollSecond);

            //We must have allow the adapter to reconize click for both the list item AND the
            // image view which is why we have both of these similar functions


            mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isFirstViewClick == false) {

                        isFirstViewClick = true;
                        mImageArrowFirst.setBackgroundResource(android.R.drawable.arrow_down_float);
                        mLinearScrollSecond.setVisibility(View.VISIBLE);

                    } else {

                        isFirstViewClick = false;
                        mImageArrowFirst.setBackgroundResource(android.R.drawable.arrow_up_float);
                        mLinearScrollSecond.setVisibility(View.GONE);
                    }
                }
            });

            // If there are selected categories from previous activity, simulate click the first level

            if(!mUserCustomFilter.getVenueFilter().isEmpty()){

                mLinearFirstArrow.performClick();
            }

            // Setting the title of the parent item (in this case categories)


            mProductName.setText(name);

            // Calling function to go to the lower levels

            addSecondRow(i, mLinearView, mLinearScrollSecond);
        }
    }

    private void addSecondRow(int firstLevelNumber, View mLinearView, LinearLayout mLinearScrollSecond){

        //Adds data into second row

        // Retrieves the children of the corresponding parent (in this case children of "category")

        for (int j = 0; j < mMainList.get(firstLevelNumber).getmSubCategoryList().size(); j++) {

            // Each child (primary category) gets inflated as a row item (row second)

            LayoutInflater inflater2 = null;
            inflater2 = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView2 = inflater2.inflate(R.layout.row_second, null);

            final RelativeLayout linearSecond = (RelativeLayout) mLinearView2.findViewById(R.id.linearSecond);
            linearSecond.setBackgroundColor(Color.BLACK);

            final TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
            final ImageView mSubItemIcon = (ImageView) mLinearView2.findViewById(R.id.imageViewIcon);
            final String catName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(j).getpSubCatName();

            mSubItemName.setText(catName);
            setIcon(catName, mSubItemIcon);
            mSubItemName.setTag(catName);

            linearSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mUserCustomFilter.getVenueFilter().contains(catName)) {

                        isSecondViewClick = true;
                    } else {

                        isSecondViewClick = false;
                    }

                    // If not previously opened, isFirstViewClick is false, therefore we open the
                    // lower level rather than close it

                    if (isSecondViewClick == false) {

                        isSecondViewClick = true;

                        linearSecond.setBackgroundColor(Color.parseColor("#e95f5f5f"));

                        if (!venueFilters.contains(catName)) {

                            venueFilters.add(catName);
                        }
                    } else {

                        isSecondViewClick = false;

                        linearSecond.setBackgroundResource(android.R.color.background_dark);

                        venueFilters.remove(catName);
                    }

                    mUserCustomFilter.setVenueFilter(venueFilters);

                    if (mAdapter != null) {

                        filtering.filterVenueList(mAdapter);
                    }

                    if (markerMap != null) {
                        filtering.filterVenueMap(markerMap);
                    }
                }
            });

            if(mUserCustomFilter.getVenueFilter().contains(catName)){

                linearSecond.setBackgroundColor(Color.parseColor("#e95f5f5f"));

            }

            mLinearScrollSecond.addView(mLinearView2);

        }
        mLinearListView.addView(mLinearView);
    }

    // A function used to set the layout depending on if the item is pressed or not

    private void isMenuOpen(boolean viewClick, ImageView image, LinearLayout linLayout){

        if(viewClick==false)
        {
            // If no click, set the lower level lin layout to invisible
            linLayout.setVisibility(View.GONE);

            // update the arrow icon for an UNselected item
            image.setBackgroundResource(android.R.drawable.arrow_up_float);
        }
        else
        {
            linLayout.setVisibility(View.VISIBLE);
            image.setBackgroundResource(android.R.drawable.arrow_down_float);
        }

    }

    public void setIcon(String catName,ImageView icon) {
        if(catName.contains("Amphitheatre"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venamphiteather, 30));}

        else if(catName.contains("Bar/Pub"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venbars, 30));}

        else if(catName.contains("Casino"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.vencasino, 30));}

        else if (catName.contains("Church"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venchurch, 30));}

        else if (catName.contains("Cinema"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.vencinema, 30));}

        else if (catName.contains("Club"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venclubs, 30));}

        else if (catName.contains("Coffee"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.vencoffee, 30));}

        else if (catName.contains("Comedy"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.vencomedy, 30));}

        else if (catName.contains("Community"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.vencommunity, 30));}

        else if (catName.contains("Fairgrounds")) {
            icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venfairground, 30));}

        else if (catName.contains("Gallery"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.vengallery, 30));}

        else if (catName.contains("Park"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venparks, 30));}

        else if (catName.contains("Restaurant")) {
            icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venrestauratns, 30));}

        else if (catName.contains("House/Residence")) {
            icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venhouse, 30));}

        else if (catName.contains("School"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venschool, 30));}

        else if (catName.contains("Sports/Arena"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venarena, 30));}

        else if (catName.contains("Store"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.venstore, 30));}

        else if (catName.contains("Theatre"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.ventheater, 30));}
    }

    //SVG Conversion.
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(mActivity.getApplicationContext(), resource);

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
