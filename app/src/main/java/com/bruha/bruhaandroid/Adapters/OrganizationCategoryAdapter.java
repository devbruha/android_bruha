// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bruha.bruhaandroid.Model.Items;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.UserCustomFilters;
import com.bruha.bruhaandroid.Processing.FilterOut;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 5/22/2015.
 */
public class OrganizationCategoryAdapter {

    FilterOut filtering;

    private FragmentActivity mActivity;
    private LinearLayout mLinearListView;
    private ArrayList<Items> mMainList;

    ArrayList<String> organizationFilters = new ArrayList<>();

    UserCustomFilters mUserCustomFilter = MyApplication.userFilters;

    // boolean variables representing the upper and lower levels being selected

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;

    private OrganizationListViewAdapter mAdapter;
    HashMap<String, Marker> markerMap = new HashMap<>();

    // Constructor for the adapter, takes a context, linear layout and "super" list

    public OrganizationCategoryAdapter(FragmentActivity context, LinearLayout linearListView, ArrayList<Items> mainList, OrganizationListViewAdapter adapter, HashMap markerHashMap){

        this.mActivity = context;
        this.mLinearListView = linearListView;
        this.mMainList = mainList;

        filtering = new FilterOut(mActivity);

        for(int i = 0; i< mUserCustomFilter.getOrganizationFilter().size(); i++){

            organizationFilters.add(mUserCustomFilter.getOrganizationFilter().get(i));
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

            //if(!mUserCustomFilter.getOrganizationFilter().isEmpty()){

                mLinearFirstArrow.performClick();
            //}

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
            linearSecond.setBackgroundColor(Color.parseColor("#24163f"));


            final TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
            final ImageView mSubItemIcon = (ImageView) mLinearView2.findViewById(R.id.imageViewIcon);
            final String catName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(j).getpSubCatName();

            linearSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mUserCustomFilter.getOrganizationFilter().contains(catName)){

                        isSecondViewClick = true;
                    }
                    else{

                        isSecondViewClick = false;
                    }

                    // If not previously opened, isFirstViewClick is false, therefore we open the
                    // lower level rather than close it

                    if (isSecondViewClick == false) {

                        isSecondViewClick = true;

                        linearSecond.setBackgroundColor(Color.parseColor("#ea8ff0e4"));

                        if(!organizationFilters.contains(catName)){

                            organizationFilters.add(catName);
                        }
                    }
                    else {

                        isSecondViewClick = false;

                        linearSecond.setBackgroundColor(Color.parseColor("#24163f"));

                        organizationFilters.remove(catName);
                    }

                    mUserCustomFilter.setOrganizationFilter(organizationFilters);

                    if (mAdapter != null) {

                        filtering.filterOrganizationList(mAdapter);
                    }

                    if (markerMap != null) {
                        filtering.filterOrganizationMap(markerMap);
                    }
                }
            });

            if(mUserCustomFilter.getOrganizationFilter().contains(catName)){

                // simulating clicks if appropriate
                linearSecond.setBackgroundColor(Color.parseColor("#ea8ff0e4"));
            }

            mSubItemName.setText(catName);
            setIcon(catName, mSubItemIcon);

            mLinearScrollSecond.addView(mLinearView2);

        }
        mLinearListView.addView(mLinearView);
    }

    // A function used to set the layout depending on if the item is pressed or not

    private void isMenuOpen(boolean viewClick, ImageView image, LinearLayout linLayout)
    {

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

    //Method to set the icon of the event.
    public void setIcon(String catName,ImageView icon) {
        if(catName.contains("Academic"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgacademic, 30));}

        else if(catName.contains("Business"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgbusiness, 30));}

        else if(catName.contains("Charity"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgcharity, 30));}

        else if (catName.contains("Fashion"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgfashion, 30));}

        else if (catName.contains("Festival"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgnonprofit, 30));}

        else if (catName.contains("Fraternity"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgfraternity, 30));}

        else if (catName.contains("Music"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgpromoter, 30));}

        else if (catName.contains("Not-for-profit"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgnonprofit, 30));}

        else if (catName.contains("Sports"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgsports, 30));}

        else if (catName.contains("Association"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgstudent, 30));}

        else if (catName.contains("Union"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgstudent, 30));}

        else if (catName.contains("Student"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgstudent, 30));}

        else if (catName.contains("Performing"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgpromoter, 30));}

        else if (catName.contains("Religion"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgreligon, 30));}

        else if (catName.contains("Club"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgnonprofit, 30));}
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
