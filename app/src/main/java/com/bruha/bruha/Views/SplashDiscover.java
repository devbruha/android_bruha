package com.bruha.bruha.Views;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bruha.bruha.R;

import butterknife.InjectView;

public class SplashDiscover extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_discover);
        /*
        LinearLayout layout = (LinearLayout)findViewById(R.id.DiscoverLayout);
// Gets the layout params that will allow you to resize the layout
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 100;
        params.width = 100;
        layout.setLayoutParams(params);




       // Making the screen half the size doesnt work,ask thomas about implementing this, code works are compiles but
        //for some reason it doesnt run on the android app. Use manual way to set the relative layout if problem presists.
*/



    }




}
