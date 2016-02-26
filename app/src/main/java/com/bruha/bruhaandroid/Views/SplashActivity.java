// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {
   //The bulb Images to be used.
    BitmapDrawable selected;
    BitmapDrawable unselected;
    Typeface opensansregfnt;  //Font to be used.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // using ButterKnife.inject to allow the InjectViews to take effect.
        ButterKnife.inject(this);

        //Initializing the global variables delcared above.
        selected = svgToBitmapDrawable(getResources(), R.raw.selected, 10);
        unselected=svgToBitmapDrawable(getResources(), R.raw.notselected, 10);
        opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");

        //Setting Splash page up.
       // ImageView loginimage = (ImageView) findViewById(R.id.logindude);
        // Android functions to determine the screen dimensions.
        //loginimage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));

        //Code to execute the swipe code.
        MyPagerAdapter adapter = new MyPagerAdapter();          //Making variable eventAdapter of class MyPageAdapter defined below.
        ViewPager pager = (ViewPager) findViewById(R.id.pager); //The Layout where the new Layout will be displayed.
        pager.setAdapter(adapter);                              //Setting the Adapter of the layout to eventAdapter.
        pager.setCurrentItem(0);                                 //The first page to be displayed in the

        //Buttons of the splash page being implemented and their respective onClickListeners.
        //final LinearLayout loginButton = (LinearLayout) findViewById(R.id.splashloginButton);
        //final LinearLayout registerButton = (LinearLayout) findViewById(R.id.splashregisterButton);
        final Button loginButton = (Button) findViewById(R.id.splashloginButton);
        final Button registerButton = (Button) findViewById(R.id.splashregisterButton);
        final Button skipButton = (Button) findViewById(R.id.noLoginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(loginButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        loginButton.setAlpha(.75f);
                        startLoginActivity(null);
                    }
                });
                animator.start();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(registerButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        registerButton.setAlpha(.75f);
                        startRegisterActivity(null);
                    }
                });
                animator.start();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(skipButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        skipButton.setAlpha(1f);
                        noLogin(v);
                    }
                });
                animator.start();
            }
        });

       // resize();  //The method that resizes and sets the font size according to the screen size.

        //Checks if internet connection is available,if not app continues without internet connection.
        if(!isNetworkAvailable())
        {
            if(!MyApplication.internetCheck) {
                alertUserAboutError("No internet connection detected!", "The information displayed is not up to date and some of our features will not be available to you due to no internet connection being detected. Please turn on your internet connection and restart the app to get updated information.");
                MyApplication.internetCheck = true;
            }
        }

    }

    private void alertUserAboutError(String errorTitle, String errorMessage) {
        // A function to call the AlertDialogFragment Activity that alerts the user about possible errors.
        AlertDialogFragment dialog = new AlertDialogFragment().newInstance( errorTitle,errorMessage );
        dialog.show(getFragmentManager(), "error_dialog");
    }

   /* private void resize() {   //The method that resizes the splash page according to the screen size and sets the font.

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;
        int x= (int)Math.round(height * .045);

        Setting text size and font.
        TextView loginText = (TextView) findViewById(R.id.splashLoginText);
        TextView registerText = (TextView) findViewById(R.id.splashRegisterText);
        loginText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        registerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        loginText.setTypeface(opensansregfnt);
        registerText.setTypeface(opensansregfnt);
    }*/

    public class MyPagerAdapter extends PagerAdapter {
        //MyPageAdapter class to decide which page is going to be swiped to.
        //set  number of swipe screens here
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(final View collection, final int position) {
            LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.layout.splash_bruha; //Set which Layout to be Displayed on Load.
                    break;
                case 1:
                    resId = R.layout.splash_discover; //The first Layout to be shown when swiped.
                    break;
                case 2:
                    resId = R.layout.splash_tickets;
                    break;
                case 3:
                    resId=R.layout.splash_events;
                    break;
                case 4:
                    resId=R.layout.splash_addicted; //The last Layout to be shown through swiping.
                    break;
            }
            View view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view, 0);
            loadpics(view, resId);
            return view;
        }

        //OverRiding the Interface.
        @Override
        public void destroyItem(final View arg0, final int arg1, final Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(final View arg0, final Object arg1) {
            return arg0 == ((View) arg1);
        }
    }

    private void loadpics(View view,int resId) {
        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        //Checks what the Page ID is and populates the page and its images accordingly.
        if(resId==R.layout.splash_bruha) {
            /*
            ImageView im = (ImageView) view.findViewById(R.id.splashImage);
            // Android functions to determine the screen dimensions.
            im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.splash, 300));
*/

            ImageView im1 = (ImageView) view.findViewById(R.id.imageView31);
            ImageView im2 = (ImageView) view.findViewById(R.id.imageView32);
            ImageView im3 = (ImageView) view.findViewById(R.id.imageView3);
            ImageView im4 = (ImageView) view.findViewById(R.id.imageView33);
            ImageView im5 = (ImageView) view.findViewById(R.id.imageView34);
            im1.setImageDrawable(selected);
            im2.setImageDrawable(unselected);
            im3.setImageDrawable(unselected);
            im4.setImageDrawable(unselected);
            im5.setImageDrawable(unselected);


        }

        if(resId==R.layout.splash_addicted)
        {
            ImageView im = (ImageView) view.findViewById(R.id.addictedimage);
            // Android functions to determine the screen dimensions.
            im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.myaddictions, 300));


            int x= (int)Math.round(height * .018);
            TextView text = (TextView) findViewById(R.id.splashaddictedtext);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text.setTypeface(opensansregfnt);

            ImageView im1 = (ImageView) view.findViewById(R.id.imageView31);
            ImageView im2 = (ImageView) view.findViewById(R.id.imageView32);
            ImageView im3 = (ImageView) view.findViewById(R.id.imageView3);
            ImageView im4 = (ImageView) view.findViewById(R.id.imageView33);
            ImageView im5 = (ImageView) view.findViewById(R.id.imageView34);
            im1.setImageDrawable(unselected);
            im2.setImageDrawable(unselected);
            im3.setImageDrawable(unselected);
            im4.setImageDrawable(unselected);
            im5.setImageDrawable(selected);

        }

        if(resId==R.layout.splash_tickets)
        {
            ImageView im = (ImageView) view.findViewById(R.id.ticketimage);
            // Android functions to determine the screen dimensions.
            im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, 300));

            ImageView im1 = (ImageView) view.findViewById(R.id.imageView31);
            ImageView im2 = (ImageView) view.findViewById(R.id.imageView32);
            ImageView im3 = (ImageView) view.findViewById(R.id.imageView3);
            ImageView im4 = (ImageView) view.findViewById(R.id.imageView33);
            ImageView im5 = (ImageView) view.findViewById(R.id.imageView34);
            im1.setImageDrawable(unselected);
            im2.setImageDrawable(unselected);
            im3.setImageDrawable(selected);
            im4.setImageDrawable(unselected);
            im5.setImageDrawable(unselected);

        }

        if(resId==R.layout.splash_discover)
        {
            ImageView event = (ImageView) view.findViewById(R.id.imageevent);
            ImageView venue = (ImageView) view.findViewById(R.id.imagevenue);
            ImageView artist = (ImageView) view.findViewById(R.id.imageartist);
            ImageView outfit = (ImageView) view.findViewById(R.id.imageoutfit);
            // Android functions to determine the screen dimensions.
            event.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.eventwhite, 70));
            venue.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.venuewhite, 70));
            artist.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.artistwhite, 70));
            outfit.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.outfitwhite, 70));

            ImageView im1 = (ImageView) view.findViewById(R.id.imageView31);
            ImageView im2 = (ImageView) view.findViewById(R.id.imageView32);
            ImageView im3 = (ImageView) view.findViewById(R.id.imageView3);
            ImageView im4 = (ImageView) view.findViewById(R.id.imageView33);
            ImageView im5 = (ImageView) view.findViewById(R.id.imageView34);
            im1.setImageDrawable(unselected);
            im2.setImageDrawable(selected);
            im3.setImageDrawable(unselected);
            im4.setImageDrawable(unselected);
            im5.setImageDrawable(unselected);


            //TextResizing
            //Resizing the Text
            TextView eventtext = (TextView) view.findViewById(R.id.discoverEvent);
            TextView eventDestext = (TextView) view.findViewById(R.id.discoverEventDes);
            TextView venuetext = (TextView) view.findViewById(R.id.discoverVenues);
            TextView venueDestext = (TextView) view.findViewById(R.id.discoverVenuesDes);
            TextView artisttext = (TextView) view.findViewById(R.id.discoverArtist);
            TextView artistDestext = (TextView) view.findViewById(R.id.discoverArtistDes);
            TextView outfittext = (TextView) view.findViewById(R.id.discoverOutfits);
            TextView outfitDestext = (TextView) view.findViewById(R.id.discoverOutfitsDes);

            int x= (int)Math.round(height * .05);
            int x2= (int)Math.round(height * .04);
            int x1= (int)Math.round(height*.02);
            eventtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            eventtext.setTypeface(opensansregfnt);
            venuetext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            venuetext.setTypeface(opensansregfnt);
            artisttext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            artisttext.setTypeface(opensansregfnt);
            outfittext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x2);
            outfittext.setTypeface(opensansregfnt);
            eventDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
            eventDestext.setTypeface(opensansregfnt);
            venueDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
            venueDestext.setTypeface(opensansregfnt);
            artistDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
            artistDestext.setTypeface(opensansregfnt);
            outfitDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
            outfitDestext.setTypeface(opensansregfnt);
        }

        if(resId==R.layout.splash_events)
        {
            ImageView arts = (ImageView) view.findViewById(R.id.imageart);
            ImageView business = (ImageView) view.findViewById(R.id.imagebusiness);
            ImageView ceremony = (ImageView) view.findViewById(R.id.imageceremony);
            ImageView club = (ImageView) view.findViewById(R.id.imageclub);
            ImageView comedy = (ImageView) view.findViewById(R.id.imagecomedy);
            ImageView fashion = (ImageView) view.findViewById(R.id.imagefashion);
            ImageView festivals = (ImageView) view.findViewById(R.id.imagefestival);
            ImageView film = (ImageView) view.findViewById(R.id.imagefilm);
            ImageView food = (ImageView) view.findViewById(R.id.imagefood);
            ImageView music = (ImageView) view.findViewById(R.id.imagemusic);
            ImageView party = (ImageView) view.findViewById(R.id.imageparty);
            ImageView performing = (ImageView) view.findViewById(R.id.imageperforming);
            ImageView political = (ImageView) view.findViewById(R.id.imagepolitical);
            ImageView school = (ImageView) view.findViewById(R.id.imageschool);
            ImageView social = (ImageView) view.findViewById(R.id.imagesocial);
            ImageView sport = (ImageView) view.findViewById(R.id.imagesports);
            ImageView tech = (ImageView) view.findViewById(R.id.imagetech);
            ImageView tour = (ImageView) view.findViewById(R.id.imagetour);

            // Android functions to determine the screen dimensions.
            arts.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.arts, 50));
            business.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.business, 50));
            ceremony.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.ceremony, 50));
            club.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.club, 50));
            comedy.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.comedy, 50));
            fashion.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.fashion, 50));
            festivals.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.festivals, 50));
            film.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.film, 50));
            food.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.food, 50));
            music.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.music, 50));
            party.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.party, 50));
            performing.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.performing, 50));
            political.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.political, 50));
            school.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.school, 50));
            social.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.social, 50));
            sport.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.sports, 50));
            tech.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tech, 50));
            tour.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tour, 50));



            //TextBoxes being resized:
            TextView text1 = (TextView) findViewById(R.id.seventtext1);
            TextView text2 = (TextView) findViewById(R.id.seventtext2);
            TextView text3 = (TextView) findViewById(R.id.seventtext3);
            TextView text4 = (TextView) findViewById(R.id.seventtext4);
            TextView text5 = (TextView) findViewById(R.id.seventtext5);
            TextView text6 = (TextView) findViewById(R.id.seventtext6);
            TextView text7 = (TextView) findViewById(R.id.seventtext7);
            TextView text8 = (TextView) findViewById(R.id.seventtext8);
            TextView text9 = (TextView) findViewById(R.id.seventtext9);
            TextView text10 = (TextView) findViewById(R.id.seventtext10);
            TextView text11 = (TextView) findViewById(R.id.seventtext11);
            TextView text12 = (TextView) findViewById(R.id.seventtext12);
            TextView text13 = (TextView) findViewById(R.id.seventtext13);
            TextView text14 = (TextView) findViewById(R.id.seventtext14);
            TextView text15 = (TextView) findViewById(R.id.seventtext15);
            TextView text16 = (TextView) findViewById(R.id.seventtext16);
            TextView text17 = (TextView) findViewById(R.id.seventtext17);
            TextView text18 = (TextView) findViewById(R.id.seventtext18);


            int x = (int) Math.round(height * .03);
            text1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text1.setTypeface(opensansregfnt);
            text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text2.setTypeface(opensansregfnt);
            text3.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text3.setTypeface(opensansregfnt);
            text4.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text4.setTypeface(opensansregfnt);
            text5.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text5.setTypeface(opensansregfnt);
            text6.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text6.setTypeface(opensansregfnt);
            text7.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text7.setTypeface(opensansregfnt);
            text8.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text8.setTypeface(opensansregfnt);
            text9.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text9.setTypeface(opensansregfnt);
            text10.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text10.setTypeface(opensansregfnt);
            text11.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text11.setTypeface(opensansregfnt);
            text12.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text12.setTypeface(opensansregfnt);
            text13.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text13.setTypeface(opensansregfnt);
            text14.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text14.setTypeface(opensansregfnt);
            text15.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text15.setTypeface(opensansregfnt);
            text16.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text16.setTypeface(opensansregfnt);
            text17.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text17.setTypeface(opensansregfnt);
            text18.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
            text18.setTypeface(opensansregfnt);


            ImageView im1 = (ImageView) view.findViewById(R.id.imageView31);
            ImageView im2 = (ImageView) view.findViewById(R.id.imageView32);
            ImageView im3 = (ImageView) view.findViewById(R.id.imageView3);
            ImageView im4 = (ImageView) view.findViewById(R.id.imageView33);
            ImageView im5 = (ImageView) view.findViewById(R.id.imageView34);
            im1.setImageDrawable(unselected);
            im2.setImageDrawable(unselected);
            im3.setImageDrawable(unselected);
            im4.setImageDrawable(selected);
            im5.setImageDrawable(unselected);
        }
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

    public void startLoginActivity(View view) {
        //Login Button implementation to navigate to the Login Activity.
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void startRegisterActivity(View view) {
        //Register Button implementation to navigate to the Register Activity.
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }

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

    public void noLogin(View view){
        //Button to skip the logging in process.
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}