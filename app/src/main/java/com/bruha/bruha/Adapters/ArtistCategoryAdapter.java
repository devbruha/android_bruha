package com.bruha.bruha.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.UserCustomFilters;
import com.bruha.bruha.Processing.FilterOut;
import com.bruha.bruha.R;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 5/22/2015.
 */
public class ArtistCategoryAdapter {

    FilterOut filtering;

    private FragmentActivity mActivity;
    private LinearLayout mLinearListView;
    private ArrayList<Items> mMainList;

    ArrayList<String> artistFilters = new ArrayList<>();

    UserCustomFilters mUserCustomFilter = MyApplication.userFilters;

    // boolean variables representing the upper and lower levels being selected

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;

    private ArtistsListViewAdapter mAdapter;
    HashMap<String, Marker> markerMap = new HashMap<>();

    // Constructor for the adapter, takes a context, linear layout and "super" list

    public ArtistCategoryAdapter(FragmentActivity context, LinearLayout linearListView, ArrayList<Items> mainList, ArtistsListViewAdapter adapter, HashMap markerHashMap){

        this.mActivity = context;
        this.mLinearListView = linearListView;
        this.mMainList = mainList;

        filtering = new FilterOut(mActivity);

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
                    }
                    else {

                        isFirstViewClick = false;
                        mImageArrowFirst.setBackgroundResource(android.R.drawable.arrow_up_float);
                        mLinearScrollSecond.setVisibility(View.GONE);
                    }
                }
            });

            // If there are selected categories from previous activity, simulate click the first level

            if(!mUserCustomFilter.getArtistFilter().isEmpty()){

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
            View mLinearView2 = inflater2.inflate(R.layout.row_third, null);

            final LinearLayout childChildLayout = (LinearLayout)mLinearView2.findViewById(R.id.childChildItem);

            final TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewItemName);

            final String catName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(j).getpSubCatName();

            childChildLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mUserCustomFilter.getArtistFilter().contains(catName)){

                        isSecondViewClick = true;
                    }
                    else{

                        isSecondViewClick = false;
                    }

                    // If not previously opened, isFirstViewClick is false, therefore we open the
                    // lower level rather than close it

                    if (isSecondViewClick == false) {

                        isSecondViewClick = true;

                        mSubItemName.setBackgroundColor(Color.parseColor("#e95f5f5f"));

                        if(!artistFilters.contains(catName)){

                            artistFilters.add(catName);
                        }
                    }
                    else {

                        isSecondViewClick = false;

                        mSubItemName.setBackgroundResource(android.R.color.background_dark);

                        artistFilters.remove(catName);
                    }

                    mUserCustomFilter.setArtistFilter(artistFilters);

                    if (mAdapter != null) {

                        filtering.filterArtistList(mAdapter);
                    }

                    if (markerMap != null) {
                        //filtering.filterEventMap(markerMap);
                    }
                }
            });

            if(mUserCustomFilter.getArtistFilter().contains(catName)){

                // simulating clicks if appropriate
                mSubItemName.setBackgroundColor(Color.parseColor("#e95f5f5f"));
            }

            mSubItemName.setText(catName);

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

}
