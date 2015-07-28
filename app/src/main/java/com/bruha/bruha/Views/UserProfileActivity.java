package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserProfileActivity extends ActionBarActivity {
    ArrayList<String> userInfo=new ArrayList<>();
    SQLiteDatabaseModel dbHelper;
    SQLiteUtils sqLiteUtils;
    @InjectView(R.id.userprofileDashboardImage) ImageView dudeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.inject(this);



        dbHelper = new SQLiteDatabaseModel(this);
        sqLiteUtils = new SQLiteUtils();

        init(); // Initializes the array to contain the information to be displayed.(User Info)
        setPage(); //Setting up the page.
        resizeButton();
    }

   private void resizeButton() {
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


    //Resizes the page, sets the fonts, sets the text.
    private void setPage()
    {
        //FONT SHIT
        Typeface domboldfnt = Typeface.createFromAsset(this.getAssets(),"fonts/Domine-Bold.ttf");
        Typeface opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");

        //The EventPage dimensions being set to fit all types of screen:

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;

        //Assigning the UserPicture to a variable to alter its dimensions after with.
        ImageView userPicture = (ImageView) findViewById(R.id.UserPicture);
        ViewGroup.LayoutParams userPictureLayoutParams = userPicture.getLayoutParams();
        userPictureLayoutParams.height =  (int)Math.round(height*.25);
        userPictureLayoutParams.width  =  (int)Math.round(height*.25);

        //Resizing the Box containing the information to a certain size.
        ImageView infoBox = (ImageView) findViewById(R.id.InfoBox);
        ViewGroup.LayoutParams infoBoxLayoutParams = infoBox.getLayoutParams();
        infoBoxLayoutParams.height = (int)Math.round(height*.40);
        infoBoxLayoutParams.width = (int)Math.round(height*.50);

        //Resizing, changing font and setting the text of the following.

        TextView name = (TextView) findViewById(R.id.Name);
        name.setText(userInfo.get(1));
        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.email);
        TextView age = (TextView) findViewById(R.id.age);
        TextView sex = (TextView) findViewById(R.id.sex);
        TextView location = (TextView) findViewById(R.id.location);

        TextView usernameText = (TextView) findViewById(R.id.usernametext);
        usernameText.setText(userInfo.get(0));
        TextView emailText = (TextView) findViewById(R.id.emailtext);
        emailText.setText(userInfo.get(4));
        TextView ageText = (TextView) findViewById(R.id.agetext);
        ageText.setText(userInfo.get(2));
        TextView sexText = (TextView) findViewById(R.id.sextext);
        sexText.setText(userInfo.get(3));
        TextView locationText = (TextView) findViewById(R.id.locationtext);
        locationText.setText(userInfo.get(5));

        int x= (int)Math.round(height * .0275);
        int x1= (int)Math.round(height * .0485);

        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        name.setTypeface(domboldfnt);
        username.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        username.setTypeface(domboldfnt);
        email.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        email.setTypeface(domboldfnt);
        age.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        age.setTypeface(domboldfnt);
        sex.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        sex.setTypeface(domboldfnt);
        location.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        location.setTypeface(domboldfnt);

        usernameText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        usernameText.setTypeface(opensansregfnt);
        emailText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        emailText.setTypeface(opensansregfnt);
        ageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        ageText.setTypeface(opensansregfnt);
        sexText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        sexText.setTypeface(opensansregfnt);
        locationText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        locationText.setTypeface(opensansregfnt);
    }

    private void init() {
        userInfo =sqLiteUtils.getUserInfo(dbHelper);
    }

    @OnClick(R.id.logouty)
    public void onClickListenerLogout(View view)
    {
        logoutDialog();
    }




    public void logout(View view)
    {
        MyApplication.loginCheck = false;
        dbHelper.onLogout(dbHelper.getWritableDatabase(), 1, 1);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @OnClick(R.id.userprofileDashboardImage)
    public void startDashboardActivity(View view)
    {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
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



    public void logoutDialog()
    {
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

}
