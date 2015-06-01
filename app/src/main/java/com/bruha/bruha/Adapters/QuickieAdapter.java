package com.bruha.bruha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Model.Items;
import com.bruha.bruha.R;

import java.util.ArrayList;

/**
 * Created by Thomas on 5/22/2015.
 */
public class QuickieAdapter {

    private Context mContext;
    private LinearLayout mLinearListView;
    private ArrayList<Items> mMainList;

    ArrayList<String> quickieFilters = new ArrayList<>();

    // boolean variables representing the upper and lower levels being selected

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;

    // Constructor for the adapter, takes a context, linear layout and "super" list

    public QuickieAdapter(Context context, LinearLayout linearListView, ArrayList<Items> mainList){

        this.mContext = context;
        this.mLinearListView = linearListView;
        this.mMainList = mainList;
    }

    public ArrayList<String> set(){

        addFirstRow();
        return quickieFilters;
    }

    private void addFirstRow(){

        //Adds data into first row

        // This for loop only performs once as we only have 1 item on the parent level

        for (int i = 0; i < mMainList.size(); i++)
        {

            //Inflating the first level

            LayoutInflater inflater = null;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            inflater2 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView2 = inflater2.inflate(R.layout.row_third, null);

            final LinearLayout childChildLayout = (LinearLayout)mLinearView2.findViewById(R.id.childChildItem);

            final TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewItemName);

            final String catName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(j).getpSubCatName();

            childChildLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isSecondViewClick == false) {

                        isSecondViewClick = true;

                        mSubItemName.setBackgroundResource(android.R.color.holo_blue_bright);

                        quickieFilters.add(catName);

                    } else {

                        isSecondViewClick = false;

                        mSubItemName.setBackgroundResource(android.R.color.background_dark);

                        quickieFilters.remove(catName);
                    }
                }
            });

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
