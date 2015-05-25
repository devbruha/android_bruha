package com.bruha.bruha.Views;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bruha.bruha.Adapters.CategoryAdapter;
import com.bruha.bruha.Adapters.QuickieAdapter;
import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Processing.FilterGen;
import com.bruha.bruha.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

/**
 * Created by Thomas on 5/25/2015.
 */
public class FilterView {

    private Activity mActivity;

    public FilterView(Activity activity){

        mActivity = activity;
    }

    public void init(){

        setPanel();

        setQuickieList();

        setCategoryList();

        setAdmissionPrice();
    }

    private void setPanel(){

        // Storing the sliding panel into mLayout

        final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout);

        // Storing the handle layout into handleLayout

        LinearLayout handleLayout = (LinearLayout)mActivity.findViewById(R.id.handleLayout);
        Button eventButton = (Button)mActivity.findViewById(R.id.eventButton);
        Button venueButton = (Button)mActivity.findViewById(R.id.venueButton);
        Button artistButton = (Button)mActivity.findViewById(R.id.artistButton);
        Button orgButton = (Button)mActivity.findViewById(R.id.orgButton);

        // Disabling touch to drag the panel (to allow for only the handle to move the window)

        //mLayout.setTouchEnabled(false);

        // Setting an anchor point at the halfway point

        mLayout.setAnchorPoint(.5f);

        // Storing the sliding panel (lin layout) into a linear layout variable

        LinearLayout dragLayout = (LinearLayout)mActivity.findViewById(R.id.mapFilterLayout);

        // Android functions to determine the screen dimensions

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = dragLayout.getLayoutParams();

        // Re-setting the height parameter to .75 the max screen height

        params.height =  (int)Math.round(height*.75);

        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelCollapsed(View view) {

            }

            @Override
            public void onPanelExpanded(View view) {

                mLayout.setTouchEnabled(false);
            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setTouchEnabled(true);
            }
        });


    }

    private void setQuickieList(){

        // Storing the quickie layout into mQuickieListview

        LinearLayout mQuickieListView = (LinearLayout)mActivity.findViewById(R.id.quickie_listview);

        // Calling the FilterGen class to set the populate the list of the filters

        FilterGen quickieGen = new FilterGen();
        ArrayList<Items> mainList = quickieGen.initRecommended();

        // calling and setting the "adapter" to set the list items

        QuickieAdapter adapter = new QuickieAdapter(mActivity, mQuickieListView, mainList);
        adapter.set();
    }

    private void setCategoryList(){

        // Storing the quickie layout into mCategoryListview

        LinearLayout mCategoryListView = (LinearLayout)mActivity.findViewById(R.id.category_listview);

        // Calling the FilterGen class to set the populate the list of the filters

        FilterGen catGen = new FilterGen();
        ArrayList<Items> mainList = catGen.initCategory();

        // calling and setting the "adapter" to set the list items

        CategoryAdapter adapter = new CategoryAdapter(mActivity, mCategoryListView, mainList);
        adapter.set();
    }

    private void setAdmissionPrice(){

        // Storing both the seek bar and the price textview display

        SeekBar mSeekBar = (SeekBar) mActivity.findViewById(R.id.priceBar);
        final TextView seekBarValue = (TextView)mActivity.findViewById(R.id.priceDisplay);

        // Setting a listener for the seekbar changing values

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // If the progress bar is 0 set the text to free, otherwise to progress value

                if(progress == 0){
                    seekBarValue.setText("Free Events");
                }
                else{
                    seekBarValue.setText(String.valueOf(progress)+" $");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
