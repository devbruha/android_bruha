package com.bruha.bruha.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bruha.bruha.Model.Venues;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by Work on 2015-06-09.
 */
public class VenueListViewAdapter extends BaseSwipeAdapter {

    private Activity mActivity;
    private ArrayList<Venues> mVenue;

    public VenueListViewAdapter(Activity activity,ArrayList<Venues> venues)
    {
        mActivity=activity;
        mVenue=venues;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return 0;
    }

    //Generates the view,look at ListViewAdapter when implementing this.
    @Override
    public View generateView(int i, ViewGroup viewGroup) {
        return null;
    }


    //Use for resizing everything like in the Event ListViewAdapter.
    @Override
    public void fillValues(int i, View view) {

    }

    @Override
    public int getCount() {
        return mVenue.size();  //Returns length of the array of Events
    }

    @Override
    public Object getItem(int position) {
        return mVenue.get(position);  //Returns the Item being accessed in the the array
    }

    @Override
    public long getItemId(int position) {
        return 0;  //Id of the Item being accessed in the view
    }
}
