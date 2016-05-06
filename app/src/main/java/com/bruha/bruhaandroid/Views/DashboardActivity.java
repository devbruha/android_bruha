// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.
// DashboardActivity - The main menu of the application

package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DashboardActivity extends ActionBarActivity {
    //The Buttons
    @InjectView(R.id.uploadButton) LinearLayout myUploadButton;
    @InjectView(R.id.ticketButton) LinearLayout myTicketButton;
    @InjectView(R.id.profileButton) LinearLayout profileButton;
    @InjectView(R.id.hotButton) LinearLayout hotButton;
    @InjectView(R.id.addictionButton) LinearLayout addictionButton;
    @InjectView(R.id.exploreButton) LinearLayout exploreButton;
    //The Buttons's TextViews
    @InjectView(R.id.UploadButton) TextView myUploadText;
    @InjectView(R.id.TicketButton) TextView myTicketText;
    @InjectView(R.id.ProfileButton) TextView profileText;
    @InjectView(R.id.HotButton) TextView hotText;
    @InjectView(R.id.AddictionButton) TextView addictionText;
    @InjectView(R.id.ExploreButton) TextView exploreText;
    //The Images of the Buttons
    @InjectView(R.id.dashboardMyTicketImage) ImageView myTicketImage;
    @InjectView(R.id.dashboardMyAddictionImage) ImageView myAddictionImage;
    @InjectView(R.id.dashboardDudeButtonImage) ImageView dudeButton;
    @InjectView(R.id.dashboardMyUploadImage) ImageView myUploadImage;
    @InjectView(R.id.dashboardExploreImage) ImageView exploreImage;
    @InjectView(R.id.dashboardprofileImage) ImageView profileImage;
    @InjectView(R.id.dashboardcalendarImage) ImageView calendarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);

        setCategories();  //Sets the Categories

        implementOnClicks();

        setImages();  //Calling the method that sets all the images inside the database.
        resize();     //Calling the method that resizes everything inside the database.

        MyApplication.listIconParam = dudeButton.getLayoutParams();

        //Checks to see if internet connection is available,if not then proceed with the app without using any internet connection.
        if(!isNetworkAvailable())
        {
            if(!MyApplication.internetCheck)
            {
                alertUserAboutError("No internet connection detected!","The information displayed is not up to date and some of our features will not be available to you due to no internet connection being detected. Please turn on your internet connection and restart the app to get updated information.");
                MyApplication.internetCheck = true;
            }
        }
    }

    private void implementOnClicks() {
        //The Button implementaion of EXPLORE which stays the same no matter logged in or not.
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(exploreButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        exploreButton.setAlpha(1f);
                        startExploreActivity(v);
                    }
                });
                animator.start();
            }
        });

        //MyTicket Button's Implementation.
        myTicketButton.setAlpha((float) 0.25);
        myTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(myTicketButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        myTicketButton.setAlpha(.25f);
                        ticketDialog();
                    }
                });
                animator.start();
            }
        });


        // The condition for the if statement is determined by the static varaible "loginCheck" found
        // in the MyApplication class, by default the variable is set to false but on successful login
        // it gets set to true

        //The myUploadButton's implementation.
        if(MyApplication.loginCheck == true)
        {
            //MyUpload Button's Implementation.
            myUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(myUploadButton, "alpha", 1f, 0.5f);
                    animator.setDuration(100);
                    animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            myUploadButton.setAlpha(1f);
                            startUploadActivity(v);
                        }
                    });
                    animator.start();
                }
            });

            //Profile Button's Implementation
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(profileButton, "alpha", 1f, 0.5f);
                    animator.setDuration(100);
                    animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            profileButton.setAlpha(1f);
                            startProfileActivity(v);
                        }
                    });
                    animator.start();
                }
            });

            //Profile Button's Implementation
            hotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(hotButton, "alpha", 1f, 0.5f);
                    animator.setDuration(100);
                    animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            hotButton.setAlpha(1f);
                            startHotActivity(v);
                        }
                    });
                    animator.start();
                }
            });

            //Profile Button's Implementation
            addictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(addictionButton, "alpha", 1f, 0.5f);
                    animator.setDuration(100);
                    animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            addictionButton.setAlpha(1f);
                            startAddictionAcitivty(v);
                        }
                    });
                    animator.start();
                }
            });
        }
        else { //If not Logged into the app.

            //UploadButton's Implementation.
            myUploadButton.setAlpha((float) 0.25);
            myUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    showDialog();
                }
            });

            //WhatsHot's Implementation
            hotButton.setAlpha((float) 0.25);
            hotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            //Profile Button's Implementation
            profileButton.setAlpha((float) 0.25);
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getApplicationContext(), "You Gotta Login First!!", Toast.LENGTH_LONG).show();
                    showDialog();
                }
            });

            //Addiction Button's Implementaion.
            addictionButton.setAlpha((float) 0.25);
            addictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
        }

        //Dashboard Button implementaion.
        dudeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(dudeButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        dudeButton.setAlpha(1f);
                        Toast.makeText(getApplicationContext(),"You are already in the Dashboard!",Toast.LENGTH_SHORT).show();
                    }
                });
                animator.start();
            }
        });
    }

    private void setCategories(){
        //Initializing the local database.
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        MyApplication.mainList.add(sqLiteUtils.getEventCategories(dbHelper).get(0));
        MyApplication.mainList.add(sqLiteUtils.getEventCategories(dbHelper).get(1));
        MyApplication.mainList.add(sqLiteUtils.getVenueCategories(dbHelper));
        MyApplication.mainList.add(sqLiteUtils.getArtistCategories(dbHelper));
        MyApplication.mainList.add(sqLiteUtils.getOrganizationCategories(dbHelper));
    }

    private void alertUserAboutError(String errorTitle, String errorMessage) {
        // A function to call the AlertDialogFragment Activity to alert the user about possible errors.
        AlertDialogFragment dialog = new AlertDialogFragment().newInstance( errorTitle,errorMessage );
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private boolean isNetworkAvailable() {
        // A function used to check whether users are connected to the internet
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // boolean variable initialized to false, set true if there is a connection

        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){

            isAvailable = true;
        }
        return isAvailable;
    }

    private void resize() {   //The method used to resize everything inside the app and set the font for.
        Typeface opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;
        int x= (int)Math.round(height*.025);

        //Setting the new text size and font.
        exploreText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        exploreText.setTypeface(opensansregfnt);
        hotText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        hotText.setTypeface(opensansregfnt);
        myTicketText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        myTicketText.setTypeface(opensansregfnt);
        myUploadText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        myUploadText.setTypeface(opensansregfnt);
        profileText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        profileText.setTypeface(opensansregfnt);
        addictionText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        addictionText.setTypeface(opensansregfnt);
    }

    private void setImages() {       //The method that sets all the images inside the Dashboard activity.
        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));
        myAddictionImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.myaddictions, 60));
        myTicketImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, 60));
        myUploadImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.myupload, 60));
        exploreImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.explore, 60));
        profileImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.profile, 60));
        calendarImage.setImageDrawable(svgToBitmapDrawable(getResources(),R.raw.calendar, 50));
    }

    //The OnClickListeners for the DashBoard Buttons:

    //OnClickListener for "Explore" that leads to the ListActivity.
    public void startExploreActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Addiction" that leads to the Addiction Activity.
    @OnClick(R.id.addictionButton)
    public void startAddictionAcitivty(View view){
        Intent intent = new Intent(this, myAddictions.class);
        startActivity(intent);
    }

    //OnClickListener for "Ticket" that leads to the Ticket Activity.
    @OnClick(R.id.ticketButton)
    public void startTicketAcitivity(View view){
        Intent intent = new Intent(this, MyTicketActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "CALENDAR" that leads to the Hot Activity.
    @OnClick(R.id.hotButton)
    public void startHotActivity(View view){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "MyUploads" that leads to the Upload Activity.
    public void startUploadActivity(View view){
        Intent intent = new Intent(this, MyUploadsActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Profile" that leads to the Profile Activity.
    @OnClick(R.id.profileButton)
    public void startProfileActivity(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void ticketDialog()
    {   //Alert Dialog to let the user know the page is under construction.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("This page is currently in development, Sorry!");
        builder.setCancelable(true);
        builder.setPositiveButton("Cancel!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDialog()
    {   //Alert dialog to let the user know the page cannot be accessed cause not logged in.
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

    //SVG Conversion method.
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        }catch(SVGParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        if(ListActivity.instance != null) {
            try {
                ListActivity.instance.finish();
            } catch (Exception e) {}
        }
    }
}
