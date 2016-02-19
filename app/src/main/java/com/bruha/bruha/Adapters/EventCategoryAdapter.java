// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Adapters;

/**
 * Created by Thomas on 5/22/2015.
 */
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
import android.widget.Toast;

import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.FilterOut;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 5/21/2015.
 */
public class EventCategoryAdapter {

    FilterOut filtering;

    private FragmentActivity mActivity;
    private LinearLayout mLinearListView;
    private ArrayList<Items> mMainList;

    UserCustomFilters mEventFilter = MyApplication.userFilters;

    private EventListviewAdapter mAdapter;
    HashMap<String, Marker> markerMap = new HashMap<>();

    // Creating a hashmap to store the current categories selected by the user
    // the hashmap shall contain string ArrayLists for each primary category selected
    // each string arrayList shall contain the primary category as its first index, then
    // each other index will represent the subcategory(s) selected

    private Map<String, ArrayList<String>> mUserCategorySelected = new HashMap<>();

    // boolean variables representing the upper and lower levels being selected

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;
    boolean isThirdViewClick=false;

    // Constructor for the adapter, takes a context, linear layout and "super" list

    public EventCategoryAdapter(FragmentActivity activity, LinearLayout linearListView, ArrayList<Items> mainList, EventListviewAdapter adapter, HashMap markerHashMap){

        this.mActivity = activity;
        this.mLinearListView = linearListView;
        this.mMainList = mainList;

        filtering = new FilterOut(mActivity);

        for(String key: mEventFilter.getCategoryFilter().keySet()){

            mUserCategorySelected.put(key, mEventFilter.getCategoryFilter().get(key));
        }

        mAdapter = adapter;
        markerMap = markerHashMap;

        //this.mEventFilter = ((MyApplication) mActivity.getApplicationContext()).getUserCustomFilters();
    }

    public Map<String, ArrayList<String>> set(){

        addFirstRow();

        // Returning the HashMap of the categories selected

        return mUserCategorySelected;
    }

    private void addFirstRow(){

        //Adds data into first row

        // This for loop only performs once as we only have 1 item on the parent level

        for (int i = 0; i < mMainList.size(); i++)
        {

            //Inflating the first level

            LayoutInflater inflater = null;
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater.inflate(R.layout.row_first, null);

            final TextView mProductName = (TextView) mLinearView.findViewById(R.id.textViewName);
            final RelativeLayout mLinearFirstArrow =(RelativeLayout)mLinearView.findViewById(R.id.linearFirst);
            final ImageView mImageArrowFirst=(ImageView)mLinearView.findViewById(R.id.imageFirstArrow);
            final LinearLayout mLinearScrollSecond=(LinearLayout)mLinearView.findViewById(R.id.linear_scroll);

            // If the item is pressed, the linear layout representing the lower level is set to visible

            isMenuOpen(isFirstViewClick, mImageArrowFirst, mLinearScrollSecond);

            //We must have allow the adapter to reconize click for both the list item AND the
            // image view which is why we have both of these similar functions

            mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // If not previously opened, isFirstViewClick is false, therefore we open the
                    // lower level rather than close it

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

            if(!mEventFilter.getCategoryFilter().isEmpty()){

                // If there are selected categories from previous activity, simulate click the first level

                mLinearFirstArrow.performClick();
            }

            // Setting the title of the parent item (in this case categories)

            final String name = mMainList.get(i).getpName();
            mProductName.setText(name);

            // Calling function to go to the lower levels

            addSecondRow(i, mLinearView, mLinearScrollSecond);
        }
    }

    private void addSecondRow(int firstLevelNumber, View mLinearView, LinearLayout mLinearScrollSecond){

        //Adds data into second row

        // Retrieves the children of the corresponding parent (in this case children of "category")

        for (int j = 0; j < mMainList.get(firstLevelNumber).getmSubCategoryList().size(); j++)
        {
            // Each child (primary category) gets inflated as a row item (row second)

            LayoutInflater inflater2 = null;
            inflater2 = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView2 = inflater2.inflate(R.layout.row_second, null);

            final TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
            final ImageView mSubItemIcon = (ImageView) mLinearView2.findViewById(R.id.imageViewIcon);
            final RelativeLayout mLinearSecondArrow=(RelativeLayout)mLinearView2.findViewById(R.id.linearSecond);
            final ImageView mImageArrowSecond=(ImageView)mLinearView2.findViewById(R.id.imageSecondArrow);
            final LinearLayout mLinearScrollThird=(LinearLayout)mLinearView2.findViewById(R.id.linear_scroll_third);

            // Retrieving the name for the selected primary category

            final String catName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(j).getpSubCatName();

            //Handles onclick effect on list item


            mLinearSecondArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Setting the isSecondViewClick variable dependant if the category is already stored in the filter

                    if (mEventFilter.getCategoryFilter().containsKey(catName)) {

                        isSecondViewClick = true;
                    } else {

                        isSecondViewClick = false;
                    }

                    if (isSecondViewClick == false) {

                        if (mEventFilter.getCategoryFilter().size() < 3) {

                            mImageArrowSecond.setBackgroundResource(android.R.drawable.arrow_down_float);

                            //mLinearScrollThird.setVisibility(View.VISIBLE);

                            mLinearSecondArrow.setBackgroundColor(Color.parseColor("#ea8ff0e4"));

                            // Each key of the hashmap shall be the primary category(s) selected
                            // this is only if it does not already exist in the categories variable
                            // in order to prevent duplicate entries

                            if (!mEventFilter.getCategoryFilter().containsKey(catName)) {

                                ArrayList<String> primaryCategory = new ArrayList<>();
                                //primaryCategory.add(catName);

                                mUserCategorySelected.put(catName, primaryCategory);
                            }

                        } else {

                            Toast toast = Toast.makeText(mActivity, "While filtering please select only up to 3 primary categories!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        mLinearSecondArrow.setBackgroundColor(Color.parseColor("#ea553285"));

                        mImageArrowSecond.setBackgroundResource(android.R.drawable.arrow_up_float);

                        //for (int i = 0; i < mLinearScrollThird.getChildCount(); i++) {

                            //((LinearLayout) mLinearScrollThird.getChildAt(i)).getChildAt(0).setBackgroundResource(android.R.color.black);
                        //}

                        //mLinearScrollThird.setVisibility(View.GONE);

                        // Removing the key if the primary category is de-selected

                        mUserCategorySelected.remove(catName);
                    }

                    mEventFilter.setCategoryFilter(mUserCategorySelected);

                    if (mAdapter != null) {

                        filtering.filterEventList(mAdapter);
                    }

                    if (markerMap != null) {
                        filtering.filterEventMap(markerMap);
                    }

                }
            });


            // Checking to see if there is a stored category variable
            // if there is, the corresponding item is click simulated, aswell as inflated
            // otherwise it is not


            if(mEventFilter.getCategoryFilter().containsKey(catName)){

                mLinearScrollThird.setVisibility(View.VISIBLE);
                mImageArrowSecond.setBackgroundResource(android.R.drawable.arrow_down_float);

                mLinearSecondArrow.setBackgroundColor(Color.parseColor("#ea553285"));
            }
            else{

                mLinearScrollThird.setVisibility(View.GONE);
                mImageArrowSecond.setBackgroundResource(android.R.drawable.arrow_up_float);
            }


            // Sets the title of the child level (primary category)

            mSubItemName.setText(catName);
            setIcon(catName,mSubItemIcon);
            mLinearSecondArrow.setTag(catName);

            // Goes in one more level to add the child-child (sub category)

            addThirdRow(firstLevelNumber, j, mLinearScrollThird, catName);

            mLinearScrollSecond.addView(mLinearView2);

        }
        mLinearListView.addView(mLinearView);
    }

    private void addThirdRow(int firstLevelNumber, int secondLevelNumber, LinearLayout mLinearScrollThird, final String catName){

        //Adds items in subcategories

        // Loops through all child-children (subcategories)

        for (int k = 0; k < mMainList.get(firstLevelNumber).getmSubCategoryList().get(secondLevelNumber).getmItemListArray().size(); k++)
        {

            // Inflates each sub category as a super sub level list item

            LayoutInflater inflater3 = null;
            inflater3 = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView3 = inflater3.inflate(R.layout.row_third, null);

            final LinearLayout childChildLayout = (LinearLayout)mLinearView3.findViewById(R.id.childChildItem);

            final TextView mSubCategoryName = (TextView) mLinearView3.findViewById(R.id.textViewItemName);

            // Retrieving the name for the selected subcategory

            final String subCategoryName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(secondLevelNumber).getmItemListArray().get(k).getItemName();
            final String subCategoryID = MyApplication.mainList.get(0).get(firstLevelNumber).getmSubCategoryList().get(secondLevelNumber).getmItemListArray().get(k).getItemName();

            mSubCategoryName.setText(subCategoryName);
            mSubCategoryName.setTag(subCategoryID);

            childChildLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> categoryArrayList = mUserCategorySelected.get(catName);

                    if (mEventFilter.getCategoryFilter().get(catName).contains(mSubCategoryName.getTag() + "")) {

                        isThirdViewClick = true;
                    } else {

                        isThirdViewClick = false;
                    }


                    if (isThirdViewClick == false) {

                        isThirdViewClick = true;

                        mSubCategoryName.setBackgroundColor(Color.parseColor("#e95f5f5f"));
                        categoryArrayList.add(mSubCategoryName.getTag() + "");


                        // Putting the subcategory into the corresponding primary category arrayList

                        mUserCategorySelected.put(catName, categoryArrayList);

                    } else {

                        isThirdViewClick = false;

                        mSubCategoryName.setBackgroundResource(android.R.color.background_dark);
                        categoryArrayList.remove(mSubCategoryName.getTag() + "");

                        // Removing the subcategory from the ArrayList if deselected

                        mUserCategorySelected.put(catName, categoryArrayList);

                    }

                    if (mAdapter != null) {

                        filtering.filterEventList(mAdapter);
                    }

                    if (markerMap != null) {
                        filtering.filterEventMap(markerMap);
                    }
                }
            });

            for(String key: mEventFilter.getCategoryFilter().keySet()) {

                if (mEventFilter.getCategoryFilter().get(key).contains(subCategoryID)) {

                    // simulating clicks if appropriate
                    mSubCategoryName.setBackgroundColor(Color.parseColor("#e95f5f5f"));
                }
            }


            mLinearScrollThird.addView(mLinearView3);
        }
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

    //Method to set the icon of the event.
    public void setIcon(String catName,ImageView icon) {
        if(catName.contains("Club"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.club, 30));}

        else if(catName.contains("Performing"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.performing, 30));}

        else if(catName.contains("Business"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.business, 30));}

        else if(catName.contains("Ceremony"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.ceremony, 30));}

        else if(catName.contains("Tech"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.tech, 30));}

        else if(catName.contains("Comedy"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.comedy, 30));}

        else if(catName.contains("Fashion"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.fashion, 30));}

        else if(catName.contains("Festivals"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.festivals, 30));}

        else if(catName.contains("Film"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.film, 30));}

        else if(catName.contains("Food"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.food, 30));}

        else if(catName.contains("Party"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.party, 30));}

        else if(catName.contains("Music"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.music, 30));}

        else if(catName.contains("Political"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.political, 30));}

        else if(catName.contains("School"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.school, 30));}

        else if(catName.contains("Sports"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.sports, 30));}

        else if(catName.contains("Tour"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.tour, 30));}

        else if(catName.contains("Arts"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.arts, 30));}

        else if(catName.contains("Social"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.social, 30));}
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
