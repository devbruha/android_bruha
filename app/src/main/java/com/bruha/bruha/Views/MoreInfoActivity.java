package com.bruha.bruha.Views;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bruha.bruha.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MoreInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        panelSetup();
    }

    private void panelSetup() {

        // Retrieving the device API level to determine if the modification to the sliding panel need
        //to be made (click instead of swiping)

        int device_sdk = android.os.Build.VERSION.SDK_INT;

        Log.v("DEVICE API", device_sdk + "");

        // Android functions to determine the screen dimensions

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        // Taking the status bar height into account for height calculations

        int workingHeight = height - getStatusBarHeight();

        // Storing the sliding panel into mLayout

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)findViewById(R.id.more_info_sliding_layout);

        // Creating buttons for all the buttons on the sliding panel handle


        // Finding the handle layout

        LinearLayout handleLayout = (LinearLayout)findViewById(R.id.moreInfoHandle);

        //TODO Make the setPanelHeight a dynamic setting

        mLayout.setDragView(handleLayout);

        // Setting an anchor point at the halfway point

        mLayout.setAnchorPoint(.5f);

        // Storing the sliding panel (lin layout) into a linear layout variable

        LinearLayout dragLayout = (LinearLayout)findViewById(R.id.moreInfoSlideLayout);

        ViewGroup.LayoutParams handleParams = handleLayout.getLayoutParams();
        //handleParams.height = (int)Math.round(workingHeight*.33);
        mLayout.setPanelHeight((int)Math.round(workingHeight*.40));

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .65 the max screen height (status bar included)

        params.height =  (int)Math.round(workingHeight);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
