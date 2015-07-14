package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DashboardActivity extends ActionBarActivity {

    @InjectView(R.id.UploadButton) TextView myUploadButton;
    @InjectView(R.id.TicketButton) TextView myTicketButton;
    @InjectView(R.id.ProfileButton) TextView profileButton;
    @InjectView(R.id.HotButton) TextView hotButton;
    @InjectView(R.id.AddictionButton) TextView addictionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);



        // The condition for the if statement is determined by the static varaible "loginCheck" found
        // in the MyApplication class, by default the variable is set to false but on successful login
        // it gets set to true

        //The myUploadButton's implementation.

        if(MyApplication.loginCheck == true)
        {

            //MyUpload Button's Implementation.
            myUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUploadActivity(v);
                }
            });

            //MyTicket Button's Implementation.
            myTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startTicketAcitivity(v);
                }
            });

            //Profile Button's Implementation
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startProfileActivity(v);
                }
            });

            //Profile Button's Implementation
            hotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startHotActivity(v);
                }
            });

            //Profile Button's Implementation
            addictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAddictionAcitivty(v);
                }
            });
        }
        else {

            //UploadButton's Implementation.
            myUploadButton.setTextColor(Color.WHITE);
            myUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    showDialog();
                }
            });

            //TicketButton's Implementation.
            myTicketButton.setTextColor(Color.WHITE);
            myTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });


            //WhatsHot's Implementation
            //Profile Button's Implementation
            hotButton.setTextColor(Color.WHITE);
            hotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            //Profile Button's Implementation
            profileButton.setTextColor(Color.WHITE);
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getApplicationContext(), "You Gotta Login First!!", Toast.LENGTH_LONG).show();
                    showDialog();
                }
            });


            addictionButton.setTextColor(Color.WHITE);
            addictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
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

    //OnClickListener for "Explore" that leads to the mListView Activity.
    @OnClick(R.id.ExploreButton)
    public void startExploreActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Addiction Activity.
    @OnClick(R.id.AddictionButton)
    public void startAddictionAcitivty(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Ticket Activity.
    @OnClick(R.id.TicketButton)
    public void startTicketAcitivity(View view){
        Intent intent = new Intent(this, MyTicketActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Hot Activity.
    @OnClick(R.id.HotButton)
    public void startHotActivity(View view){
        Intent intent = new Intent(this, WhatsHotActivity.class);
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
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void showDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("Oopse! You are not logged in!");
        builder.setCancelable(true);
        builder.setPositiveButton("Cancel!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Sign in!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startLoginActivity(new View(getApplicationContext()));
            }
        });
        builder.setNeutralButton("Sign up!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startRegisterActivity(new View(getApplicationContext()));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void startRegisterActivity(View view)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public  void startLoginActivity(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
