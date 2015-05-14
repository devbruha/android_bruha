package com.bruha.bruha.Views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bruha.bruha.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DashboardActivity extends ActionBarActivity {

    // Injecting the EditTexts using Butterknife  library

    @InjectView(R.id.mapButton) Button mMapButton;
    @InjectView(R.id.listButton) Button mListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.listButton)
    public void startListActivity(View view){

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.mapButton)
    public void startMapActivity(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }


}
