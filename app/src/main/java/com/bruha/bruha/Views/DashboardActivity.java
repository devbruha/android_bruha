package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.R;
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

    //The Buttons
    @InjectView(R.id.UploadButton) TextView myUploadText;
    @InjectView(R.id.TicketButton) TextView myTicketText;
    @InjectView(R.id.ProfileButton) TextView profileText;
    @InjectView(R.id.HotButton) TextView hotText;
    @InjectView(R.id.AddictionButton) TextView addictionText;
    @InjectView(R.id.ExploreButton) TextView exploreText;


    @InjectView(R.id.dashboardMyTicketImage) ImageView myTicketImage;
    @InjectView(R.id.dashboardMyAddictionImage) ImageView myAddictionImage;
    @InjectView(R.id.dashboardDudeButtonImage) ImageView dudeButton;


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
            myUploadText.setTextColor(Color.WHITE);
            myUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    showDialog();
                }
            });

            //TicketButton's Implementation.
            myTicketText.setTextColor(Color.WHITE);
            myTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });


            //WhatsHot's Implementation
            //Profile Button's Implementation
            hotText.setTextColor(Color.WHITE);
            hotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            //Profile Button's Implementation
            profileText.setTextColor(Color.WHITE);
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getApplicationContext(), "You Gotta Login First!!", Toast.LENGTH_LONG).show();
                    showDialog();
                }
            });


            addictionText.setTextColor(Color.WHITE);
            addictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
        }




        //Shade Animator for ListButton

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


        setImages();
        resize();
    }

    private void resize()
    {
        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        int x= (int)Math.round(height*.025);
        exploreText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        hotText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        myTicketText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        myUploadText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        profileText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        addictionText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);

    }

    private void setImages()
    {
        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));
        myAddictionImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.myaddictions, 60));
        myTicketImage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, 60));

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
    @OnClick(R.id.exploreButton)
    public void startExploreActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Addiction Activity.
    @OnClick(R.id.addictionButton)
    public void startAddictionAcitivty(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Ticket Activity.
    @OnClick(R.id.ticketButton)
    public void startTicketAcitivity(View view){
        Intent intent = new Intent(this, MyTicketActivity.class);
        startActivity(intent);
    }

    //OnClickListener for "Explore" that leads to the Hot Activity.
    @OnClick(R.id.hotButton)
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
    @OnClick(R.id.profileButton)
    public void startProfileActivity(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
        finish();
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

    //SVG SHIT:
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
}
