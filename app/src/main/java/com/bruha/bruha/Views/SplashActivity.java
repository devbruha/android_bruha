package com.bruha.bruha.Views;

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
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        // using ButterKnife.inject to allow the InjectViews to take effect.
        ButterKnife.inject(this);

        //Setting SVG image
        ImageView loginimage = (ImageView) findViewById(R.id.logindude);
        // Android functions to determine the screen dimensions.
        loginimage.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhawhite, 30));


        //Code to execute the swipe code.
        MyPagerAdapter adapter = new MyPagerAdapter();          //Making variable adapter of class MyPageAdapter defined below.
        ViewPager pager = (ViewPager) findViewById(R.id.pager); //The Layout where the new Layout will be displayed.
        pager.setAdapter(adapter);                              //Setting the Adapter of the layout to adapter.
        pager.setCurrentItem(0);                                 //The first page to be displayed in the


        final LinearLayout loginButton = (LinearLayout) findViewById(R.id.splashloginButton);
        final LinearLayout registerButton = (LinearLayout) findViewById(R.id.splashregisterButton);
        final Button skipButton = (Button) findViewById(R.id.noLoginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(loginButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        loginButton.setAlpha(1f);
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
                        registerButton.setAlpha(1f);
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

        int x= (int)Math.round(height * .045);

        TextView loginText = (TextView) findViewById(R.id.splashLoginText);
        TextView registerText = (TextView) findViewById(R.id.splashRegisterText);
        loginText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        registerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);


    }

    public void startLoginActivity(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


    public void startRegisterActivity(View view)
    {
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

    public Bitmap svgToBitmap(Resources res, int resource, int size) {
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp;
            bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);


            return bmp;
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }




    //Button to skip the logging in process.
    public void noLogin(View view){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }


    //MyPageAdapter class to decide which page is going to be swiped to.
    public class MyPagerAdapter extends PagerAdapter {
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


        if(resId==R.layout.splash_bruha) {
            ImageView im = (ImageView) view.findViewById(R.id.splashImage);
            // Android functions to determine the screen dimensions.
            im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.splash, 300));


        }

        if(resId==R.layout.splash_addicted)
        {
            ImageView im = (ImageView) view.findViewById(R.id.addictedimage);
            // Android functions to determine the screen dimensions.
            im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.myaddictions, 300));
        }

        if(resId==R.layout.splash_tickets)
        {
            ImageView im = (ImageView) view.findViewById(R.id.ticketimage);
            // Android functions to determine the screen dimensions.
            im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.tickets, 300));
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
            int x1= (int)Math.round(height*.02);
            eventtext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            venuetext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            artisttext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            outfittext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            eventDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
            venueDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
            artistDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
            outfitDestext.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
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


            int x= (int)Math.round(height * .03);
            text1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text2.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text3.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text4.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text5.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text6.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text7.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text8.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text9.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text10.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text11.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text12.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text13.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text14.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text15.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text16.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text17.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
            text18.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);





        }
    }
}