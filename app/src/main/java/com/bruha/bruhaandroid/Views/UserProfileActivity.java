// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.TextView;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class UserProfileActivity extends ActionBarActivity {
    ArrayList<String> userInfo = new ArrayList<>();
    SQLiteDatabaseModel dbHelper;
    SQLiteUtils sqLiteUtils;
    @InjectView(R.id.userprofileDashboardImage)
    ImageView dudeButton;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.inject(this);

        //Initializing the local database.
        dbHelper = new SQLiteDatabaseModel(this);
        sqLiteUtils = new SQLiteUtils();

        init(); // Initializes the array to contain the information to be displayed.(User Info)
        setPage(); //Setting up the page.
        resizeButton(); //Resizing the page.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void resizeButton() {
        //The method that resizes the buttons.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;
        //Assigning the PageEventCoverPicture to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams dudeButtonLayoutParams = dudeButton.getLayoutParams();
        dudeButtonLayoutParams.height = (int) Math.round(height * .07);
        dudeButtonLayoutParams.width = (int) Math.round(height * .07);
        //Setting the Button Image
        dudeButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));

        dudeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(dudeButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        dudeButton.setAlpha(1f);
                        startDashboardActivity(v);
                    }
                });
                animator.start();
            }
        });
    }

    private void setPage() {   //Resizes the page, sets the fonts, sets the text.

        //FONT SHIT
        Typeface domboldfnt = Typeface.createFromAsset(this.getAssets(), "fonts/Domine-Bold.ttf");
        Typeface opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");

        //The EventPage dimensions being set to fit all types of screen:

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;

        //Assigning the UserPicture to a variable to alter its dimensions after with.
        CircleImageView userPicture = (CircleImageView) findViewById(R.id.UserPicture);
        ViewGroup.LayoutParams userPictureLayoutParams = userPicture.getLayoutParams();
        userPictureLayoutParams.height = (int) Math.round(height * .25);
        userPictureLayoutParams.width = (int) Math.round(height * .25);

        //Resizing, changing font and setting the text of the following.
        TextView usernameText = (TextView) findViewById(R.id.usernametext);
        usernameText.setText(userInfo.get(0));
        TextView emailText = (TextView) findViewById(R.id.emailtext);
        emailText.setText(userInfo.get(4));
        int x = (int) Math.round(height * .0275);
        int x1 = (int) Math.round(height * .0485);
        usernameText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        usernameText.setTypeface(domboldfnt);
        emailText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        emailText.setTypeface(opensansregfnt);
    }

    private void init() {
        userInfo = sqLiteUtils.getUserInfo(dbHelper);
    }

    @OnClick(R.id.logouty)
    public void onClickListenerLogout(View view) {
        logoutDialog();
    }

    public void logout(View view) {
        MyApplication.loginCheck = false;
        MyApplication.userName = "false";
        dbHelper.onLogout(dbHelper.getWritableDatabase(), 1, 1);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void startDashboardActivity(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size) {
        //SVG Conversion
        try {
            size = (int) (size * res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logoutDialog() {   //Dialog that confirms the user would like to log out.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("Are you sure you want to log out?!");
        builder.setCancelable(true);
        builder.setPositiveButton("Cancel!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                logout(null);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserProfile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.bruha.bruhaandroid.Views/http/host/path")
        );
        AppIndex.AppIndexApi.start(mClient, viewAction);
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserProfile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.bruha.bruhaandroid.Views/http/host/path")
        );
        AppIndex.AppIndexApi.end(mClient, viewAction);
        mClient.disconnect();
    }
    */
}



