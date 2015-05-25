package com.bruha.bruha.Adapters;

/**
 * Created by Thomas on 5/22/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruha.bruha.Model.Items;
import com.bruha.bruha.R;

import java.util.ArrayList;

/**
 * Created by Thomas on 5/21/2015.
 */
public class CategoryAdapter {

    private Context mContext;
    private LinearLayout mLinearListView;
    private ArrayList<Items> mMainList;

    // boolean variables reepresenting the upper and lower levels being selected
    //s

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;
    boolean isThirdViewClick=false;

    // Constructor for the adapter, takes a context, linear layout and "super" list

    public CategoryAdapter(Context context, LinearLayout linearListView, ArrayList<Items> mainList){

        this.mContext = context;
        this.mLinearListView = linearListView;
        this.mMainList = mainList;
    }

    public void set(){

        addFirstRow();
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
            inflater2 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView2 = inflater2.inflate(R.layout.row_second, null);

            TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
            final RelativeLayout mLinearSecondArrow=(RelativeLayout)mLinearView2.findViewById(R.id.linearSecond);
            final ImageView mImageArrowSecond=(ImageView)mLinearView2.findViewById(R.id.imageSecondArrow);
            final LinearLayout mLinearScrollThird=(LinearLayout)mLinearView2.findViewById(R.id.linear_scroll_third);

            // Similar listeners implements as previously for list item clicks

            isMenuOpen(isSecondViewClick, mImageArrowSecond, mLinearScrollThird);

            //Handles onclick effect on list item

            mLinearSecondArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSecondViewClick==false)
                    {
                        isSecondViewClick=true;
                        mImageArrowSecond.setBackgroundResource(android.R.drawable.arrow_down_float);
                        mLinearScrollThird.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        isSecondViewClick=false;
                        mImageArrowSecond.setBackgroundResource(android.R.drawable.arrow_up_float);
                        mLinearScrollThird.setVisibility(View.GONE);
                    }
                }
            });

            // Sets the title of the child level (primary category)

            final String catName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(j).getpSubCatName();
            mSubItemName.setText(catName);

            // Goes in one more level to add the child-child (sub category)

            addThirdRow(firstLevelNumber,j, mLinearScrollThird);

            mLinearScrollSecond.addView(mLinearView2);

        }
        mLinearListView.addView(mLinearView);
    }

    private void addThirdRow(int firstLevelNumber, int secondLevelNumber, LinearLayout mLinearScrollThird){

        //Adds items in subcategories

        // Loops through all child-children (subcategories)

        for (int k = 0; k < mMainList.get(firstLevelNumber).getmSubCategoryList().get(secondLevelNumber).getmItemListArray().size(); k++)
        {

            // Inflates each sub category as a super sub level list item

            LayoutInflater inflater3 = null;
            inflater3 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView3 = inflater3.inflate(R.layout.row_third, null);

            final LinearLayout childChildLayout = (LinearLayout)mLinearView3.findViewById(R.id.childChildItem);

            final TextView mItemName = (TextView) mLinearView3.findViewById(R.id.textViewItemName);

            childChildLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isThirdViewClick == false) {

                        isThirdViewClick = true;

                        mItemName.setBackgroundResource(android.R.color.holo_blue_bright);

                        //Toast.makeText(mContext, catName + " Selected", Toast.LENGTH_SHORT).show();

                    } else {

                        isThirdViewClick = false;

                        mItemName.setBackgroundResource(android.R.color.background_dark);

                        //Toast.makeText(mContext, catName + " No Longer Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final String itemName = mMainList.get(firstLevelNumber).getmSubCategoryList().get(secondLevelNumber).getmItemListArray().get(k).getItemName();
            mItemName.setText(itemName);

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
}
