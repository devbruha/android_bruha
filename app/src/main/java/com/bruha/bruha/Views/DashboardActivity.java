package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DashboardActivity extends ActionBarActivity {

    @InjectView(R.id.UploadButton) TextView MyUploadButton;
    @InjectView(R.id.TicketButton) TextView MyTicketButton;
    @InjectView(R.id.ProfileButton) TextView ProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);


        // The condition for the if statement is determined by the static varaible "loginCheck" found
        // in the MyApplication class, by default the variable is set to false but on successful login
        // it gets set to true

        //The MyUploadButton's implementation.

        if(MyApplication.loginCheck == true)
        {

            //MyUpload Button's Implementation.
            MyUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUploadActivity(v);
                }
            });

            //MyTicket Button's Implementation.
            MyTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startTicketAcitivity(v);
                }
            });

            //Profile Button's Implementation
            ProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startProfileActivity(v);
                }
            });
        }
        else {

            //UploadButton's Implementation.
            MyUploadButton.setTextColor(Color.WHITE);
            MyUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"You Gotta Login First!!",Toast.LENGTH_LONG).show();
                }
            });

            //TicketButton's Implementation.
            MyTicketButton.setTextColor(Color.WHITE);
            MyTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"You Gotta Login First!!",Toast.LENGTH_LONG).show();
                }
            });

            //Profile Button's Implementation
            ProfileButton.setTextColor(Color.WHITE);
            ProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"You Gotta Login First!!",Toast.LENGTH_LONG).show();
                }
            });

        }



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
        Intent intent = new Intent(this, MyTicketActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Upload Activity.

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
