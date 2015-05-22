package com.bruha.bruha.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bruha.bruha.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DashboardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);

/*
        //Code temporarily here for testing, this is the swipe layout code.
        MyPagerAdapter adapter = new MyPagerAdapter();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(R.layout.activity_dashboard);
*/
    }

    @OnClick(R.id.mapButton)
    public void startMapActivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }



    @OnClick(R.id.listButton)
    public void startListActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

    }

/*
    //Code temporarily here for testing,this is part of the code needed for the swipe layout
    public class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3; //set  number of swipe screens here
        }
        @Override
        public Object instantiateItem(final View collection, final int position) {
            LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int resId = 0;
            switch (position) {
                case 2:
                    resId = R.layout.activity_dashboard; //set which layout will show on load
                    break;
                case 1:
                    resId = R.layout.activity_splash_discover; //what layout swiping shows//
                     break;
                case 0:
                    resId = R.layout.activity_register;
                    break;
            }
            View view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view, 0);
            return view;
        }
        @Override
        public void destroyItem(final View arg0, final int arg1, final Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }
        @Override
        public boolean isViewFromObject(final View arg0, final Object arg1) {
            return arg0 == ((View) arg1);
        }
    }

*/



}
