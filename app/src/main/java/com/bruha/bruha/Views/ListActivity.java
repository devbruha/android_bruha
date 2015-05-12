package com.bruha.bruha.Views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListActivity extends ActionBarActivity {

    //Injecting Buttons using ButterKnife Library
    @InjectView(R.id.MapButton) Button mMapButton;
    @InjectView(R.id.DashboardButton) Button mDashboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ButterKnife.inject(this);

        ListviewAdapter adapter=new ListviewAdapter(this); //Calling the adapter ListView to help set the List



    }

    //Button Implementation for navigating to the Map from ListView.
    @OnClick(R.id.MapButton)
    public void StartMapActivity(View view)
    {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }


    //Button Implementation for navigating to the Dashboard from ListView.
    @OnClick(R.id.DashboardButton)
    public void StartDashboardActivity(View view)
    {
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }





}
