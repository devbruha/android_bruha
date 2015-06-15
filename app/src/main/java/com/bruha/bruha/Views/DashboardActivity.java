package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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


        //Shade Animator for MapButton
        final Button MapButton=(Button) findViewById(R.id.mapButton);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(MapButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        MapButton.setAlpha(1f);
                        startMapActivity(v);
                    }
                });
                animator.start();
            }
        });

        //Shade Animator for ListButton
        final Button ListButton=(Button) findViewById(R.id.listButton);
        ListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(ListButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        ListButton.setAlpha(1f);
                        startListActivity(v);
                    }
                });
                animator.start();
            }
        });

    }


    //The OnClickListeners for the DashBoard Buttons:

    public void startMapActivity(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void startListActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

    }

    //OnClickListener for "Explore" that leads to the ListView Activity.
    @OnClick(R.id.ExploreButton)
    public void startExploreActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Addiction Activity.
    @OnClick(R.id.AddictionButton)
    public void startAddictionAcitivty(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Ticket Activity.
    @OnClick(R.id.TicketButton)
    public void startTicketAcitivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Hot Activity.
    @OnClick(R.id.HotButton)
    public void startHotActivity(View view){
        Intent intent = new Intent(this, EventPageActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Upload Activity.
    @OnClick(R.id.UploadButton)
    public void startUploadActivity(View view){
        Intent intent = new Intent(this, MyUploadsActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Profile Activity.
    @OnClick(R.id.ProfileButton)
    public void startProfileActivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }




}
