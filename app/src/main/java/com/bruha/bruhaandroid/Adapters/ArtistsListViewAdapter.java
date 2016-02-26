// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.RetrieveMyPHP;
import com.bruha.bruhaandroid.R;
import com.bruha.bruhaandroid.Views.EventPageActivity;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Work on 2015-06-12.
 */
public class ArtistsListViewAdapter extends BaseSwipeAdapter {
    //Global variables to  be used throughout the app.
    private Activity mActivity;             //The Activity where it is to be displayed.
    private ArrayList<Artist> mArtists;    //The List of Artist to be displayed.
    public static int Clicks=0;             //The number of times tapped on the screen.
    RetrieveMyPHP retrieveMyPHP;
    ArrayList<String> addictArtistID;
    SQLiteDatabaseModel dbHelper;

    public ArtistsListViewAdapter(Activity activity,ArrayList<Artist> artists, ArrayList<String> artistID) {
        mActivity = activity;
        mArtists = artists;
        addictArtistID = artistID;
        retrieveMyPHP = new RetrieveMyPHP();
        dbHelper = new SQLiteDatabaseModel(mActivity);
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup viewGroup) {
        //Inflates the view to be used
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.venue_item, viewGroup, false);
        return convertView;
    }

    //Method to set the icon of the event.
    public void setIcon(Artist artist,ImageView icon) {

         if(artist.getArtistPrimaryCategory().contains("Band"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistband, 30));}

        else if (artist.getArtistPrimaryCategory().contains("Designer"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistdesigner, 30));}

        else if (artist.getArtistPrimaryCategory().contains("Performing"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistphotographer, 30));}

        else if (artist.getArtistPrimaryCategory().contains("Photographer"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistdesigner, 30));}

        else if (artist.getArtistPrimaryCategory().contains("Videographer"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistvisualarts, 30));}

        else if (artist.getArtistPrimaryCategory().contains("Solo"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistsolomusician, 30));}

        else if (artist.getArtistPrimaryCategory().contains("Writer"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.artistwriter, 30));}

    }

    public void setAnimation(final ImageView sR, final ImageView sL) {
        final Animation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(3000);

        final Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(3000);
        //animation1 AnimationListener
        animation1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation2 when animation1 ends (continue)
                sL.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });


        //animation2 AnimationListener
        animation2.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation1 when animation2 ends (repeat)
                sL.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });


        sL.setAnimation(animation1);

        final Animation  animation11 = new AlphaAnimation(0.0f, 1.0f);
        animation11.setDuration(3000);

        final Animation animation22 = new AlphaAnimation(1.0f, 0.0f);
        animation22.setDuration(3000);
        //animation1 AnimationListener
        animation11.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation2 when animation1 ends (continue)
                sR.startAnimation(animation22);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });


        //animation2 AnimationListener
        animation22.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation1 when animation2 ends (repeat)
                sR.startAnimation(animation11);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });


        sR.setAnimation(animation11);
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        convertView.setTag(holder); //sets the tag

        ImageView swipeRicon = (ImageView) convertView.findViewById(R.id.swipeyright);
        ImageView swipeLicon = (ImageView) convertView.findViewById(R.id.swipeyleft);
        setAnimation(swipeLicon, swipeRicon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Assigning the Relative Layout that contains the detailed description.
                RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.VenueDescriptionLayout);
                //Assigning the summary description stuff that will hide and reappear depending on the clicks.
                ImageView Bubble = (ImageView) v.findViewById(R.id.VenueImageBubble);
                TextView OrganizationName = (TextView) v.findViewById(R.id.VenueName);
                TextView OrganizationDistance = (TextView) v.findViewById(R.id.VenueDistance);
                ImageView swipeRicon = (ImageView) v.findViewById(R.id.swipeyright);
                ImageView swipeLicon = (ImageView) v.findViewById(R.id.swipeyleft);

                if (Clicks % 2 == 0) {
                    //Popping the detailed description into view.
                    layout.setVisibility(View.VISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.INVISIBLE);
                    OrganizationName.setVisibility(View.INVISIBLE);
                  //  OrganizationDistance.setVisibility(View.INVISIBLE);
                    swipeLicon.setVisibility(View.INVISIBLE);
                    swipeRicon.setVisibility(View.INVISIBLE);
                }
                else{
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.VISIBLE);
                    OrganizationName.setVisibility(View.VISIBLE);
                  //  OrganizationDistance.setVisibility(View.VISIBLE);
                    swipeLicon.setVisibility(View.VISIBLE);
                    swipeRicon.setVisibility(View.VISIBLE);
                }
                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });

        //Initializing each item to the required type
        final Artist artist = mArtists.get(position);

        //Summary Description of the Venue.
        holder.ArtistPicture= (ImageView) convertView.findViewById(R.id.VenuePicture);
        holder.ArtistIcon = (ImageView) convertView.findViewById(R.id.VAOIcon);
        holder.ArtistName = (TextView) convertView.findViewById(R.id.VenueName);
        holder.ArtistDistance = (TextView) convertView.findViewById(R.id.VenueDistance);

        //Detailed Description of the Venue
        holder.ArtistLocName= (TextView) convertView.findViewById(R.id.DesVenueLocName);
        holder.ArtistLocSt= (TextView) convertView.findViewById(R.id.DesVenueLocStreet);
        holder.ArtistLocAdd = (TextView) convertView.findViewById(R.id.DesVenueLocAddress);
        holder.ArtistEventTiming= (TextView) convertView.findViewById(R.id.VenueMontoFriHour);
        holder.DesArtistName=(TextView) convertView.findViewById(R.id.DesVenueName);

        //Setting all the text inside the view.

        setIcon(artist,holder.ArtistIcon);

        //Summary being set.
        // holder.ArtistPicture.setImageResource();
        // holder.ArtistIcon.setImageResource();
        //holder.ArtistName.setText(artist.getArtistName());
        if(artist.getArtistName().length()<=15)
        { holder.ArtistName.setText(artist.getArtistName());}
        else { holder.ArtistName.setText(artist.getArtistName().substring(0,15)+"..."); }
        // holder.ArtistDistance.setText(artist.getArtistDescription());

        //Detailed Description being set.
        //holder.DesArtistName.setText(artist.getArtistName());
        if(artist.getArtistName().length()<=15)
        { holder.DesArtistName.setText(artist.getArtistName());}
        else { holder.DesArtistName.setText(artist.getArtistName().substring(0,15)+"..."); }
        //  holder.ArtistLocName.setText("X Lounge");
        //holder.ArtistLocSt.setText("1250 Main St. West");
        //holder.ArtistLocAdd.setText("Hamilton, ON Canada");
        //holder.ArtistEventTiming.setText("30 September,2015 At 3:30:00");

        //Sets the picture of the view.
        Picasso.with(convertView.getContext()).load(artist.getArtistPicture()).into(holder.ArtistPicture);


        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.Venue_Left_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.VenueRightSwipeLayout));

        final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.VenuePreviewLayout);
        PreviewLayout.setAlpha(.25f);

        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.VenuePreviewRow);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator animator = ObjectAnimator.ofFloat(PreviewLayout, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        PreviewLayout.setAlpha(.25f);
                        Toast.makeText(mActivity.getApplicationContext(),"Still under development!",Toast.LENGTH_SHORT).show();
                    }
                });
                animator.start();
            }
        });

        //Implements the Button 'More Info' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoMoreInfoPage = (TableRow) convertView.findViewById(R.id.VenueMoreInfoRow);
        GoMoreInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout MoreInfoLay = (LinearLayout) convertView.findViewById(R.id.VenueMoreInfoLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(MoreInfoLay, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        MoreInfoLay.setAlpha(1f);
                        Intent intent = new Intent(mActivity, EventPageActivity.class);
                        intent.putExtra("Id",artist.getArtistId());
                        intent.putExtra("Type","Artist");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });

        Typeface domregfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Regular.ttf");
        Typeface domboldfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Bold.ttf");
        Typeface opensansregfnt = Typeface.createFromAsset(mActivity.getAssets(), "fonts/OpenSans-Regular.ttf");

        //Assigning the ImageBubble to a variable to alter iits dimensions after with.
        ImageView circle = (ImageView) convertView.findViewById(R.id.VenueImageBubble);

        // Android functions to determine the screen dimensions.
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;

        //Getting the icon and setting its size
        ImageView icon = (ImageView) convertView.findViewById(R.id.VAOIcon);
        ViewGroup.LayoutParams iconLayoutParams = icon.getLayoutParams();
        iconLayoutParams.height =  (int)Math.round(height*.05);
        iconLayoutParams.width =  (int)Math.round(height*.05);
        //Sets the height to 1/3 the screensize.
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height =  (int)Math.round(height*.33);
        //Picture being resized to the view height
        ImageView Pic = (ImageView) convertView.findViewById(R.id.VenuePicture);
        ViewGroup.LayoutParams PictureParam = Pic.getLayoutParams();
        PictureParam.height =  (int)Math.round(height*.33);
        //Getting the LayoutParams of the circle and then setting it to quarter the screensize.
        ViewGroup.LayoutParams circleParams = circle.getLayoutParams();
        circleParams.height =  (int)Math.round(height*.25);
        circleParams.width = (int)Math.round(height*.25);

        //Summary being resized.

        //The VenueName being Formatted.
        TextView VenueName = (TextView) convertView.findViewById(R.id.VenueName);
        int x1= (int)Math.round(height*.030);
        VenueName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
        VenueName.setTypeface(domboldfnt);
        //The VenueDistance being formatted.
        TextView VenueDistance = (TextView) convertView.findViewById(R.id.VenueDistance);
        int x2= (int)Math.round(height * .018);
        VenueDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);
        VenueDistance.setTypeface(domboldfnt);

        //Detailed Description being resized.

        //The VenueDistance being formatted.
        TextView DesVenueName = (TextView) convertView.findViewById(R.id.DesVenueName);
        int x3= (int)Math.round(height * .030);
        DesVenueName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        DesVenueName.setTypeface(domboldfnt);
        //The VenueDistance being formatted.
        TextView DesVenueLocName = (TextView) convertView.findViewById(R.id.DesVenueLocName);
        int x4= (int)Math.round(height * .0215);
        DesVenueLocName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);
        DesVenueLocName.setTypeface(opensansregfnt);
        //The VenueDistance being formatted.
        TextView DesVenueLocSt = (TextView) convertView.findViewById(R.id.DesVenueLocStreet);
        DesVenueLocSt.setTextSize(TypedValue.COMPLEX_UNIT_PX, x4);
        DesVenueLocSt.setTypeface(opensansregfnt);
        //The VenueDistance being formatted.
        TextView DesVenueLocAdd = (TextView) convertView.findViewById(R.id.DesVenueLocAddress);
        DesVenueLocAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX, x4);
        DesVenueLocAdd.setTypeface(opensansregfnt);
        //The VenueDistance being formatted.
        TextView DesVenueHourText = (TextView) convertView.findViewById(R.id.VenueHourText);
        int x5= (int)Math.round(height * .0185);
        DesVenueHourText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
        DesVenueHourText.setTypeface(domboldfnt);
        //Hour text being resized
        TextView DesVenueHourWeekDay = (TextView) convertView.findViewById(R.id.VenueMontoFriHour);
        int x6= (int)Math.round(height * .0165);
        DesVenueHourWeekDay.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourWeekDay.setTypeface(domregfnt);
        //Saturday text being resized
        TextView DesVenueHourSaturday=(TextView) convertView.findViewById(R.id.VenueSaturdayHour);
        DesVenueHourSaturday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourSaturday.setTypeface(domregfnt);
        //Sunday text resizing
        TextView DesVenueHourSunday= (TextView) convertView.findViewById(R.id.VenueSundayHour);
        DesVenueHourSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourSunday.setTypeface(domregfnt);

        /*
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe1 = (TextView) convertView.findViewById(R.id.VenueSwipeBarSize1);
        int x7= (int)Math.round(height * .030);
        Swipe1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x7);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe2 = (TextView) convertView.findViewById(R.id.VenueSwipeBarSize2);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX,x7);
        */

        //The left swipe/Addiction/ User deletion being initialized according to the view opened.
        if(MyApplication.loginCheck==true) {

            if (mActivity.getLocalClassName().equals("Views.MyUploadsActivity")) {
                final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);
                likeText.setText("Delete!");
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String x = retrieveMyPHP.deleteUserArtist(MyApplication.userName, artist.getArtistId());
                        Toast.makeText(mActivity.getApplicationContext(), x, Toast.LENGTH_SHORT).show();

                        if(x.contains("!")) {
                            dbHelper.deleteUserArtist(dbHelper.getWritableDatabase(),artist.getArtistId());
                            mArtists.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
            } else {

                //myAddictions stuff:
                boolean addicted = false;

                if (addictArtistID != null) {

                    for (String ID : addictArtistID) {
                        if (ID.equals(artist.getArtistId())) {
                            addicted = true;
                        }
                    }

                    final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);

                    if (addicted == true) {
                        likeText.setText("Addicted!");
                        likeText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                retrieveMyPHP.deleteArtistAddiction(MyApplication.userName, artist.getArtistId());
                                dbHelper.deleteArtistAddiction(dbHelper.getWritableDatabase(),artist.getArtistId());
                                Toast.makeText(mActivity.getApplicationContext(), "You are Unaddicted!", Toast.LENGTH_SHORT).show();
                                likeText.setText("Get Addicted!!");

                                for(int i=0;i<addictArtistID.size();i++)
                                {
                                    if(addictArtistID.get(i).equals(artist.getArtistId()))
                                    {
                                        addictArtistID.remove(i);
                                        break;
                                    }
                                }

                                if(mActivity.getLocalClassName().equals("Views.myAddictions"))
                                { mArtists.remove(position);}

                                notifyDataSetChanged();

                            }
                        });
                    } else {
                        likeText.setText("Get Addicted!!");
                        likeText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                retrieveMyPHP.artistAddiction(MyApplication.userName, artist.getArtistId());
                                Toast.makeText(mActivity.getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                                likeText.setText("Addicted!!");

                                addictArtistID.add(artist.getArtistId());
                                dbHelper.insertArtistAddiction(dbHelper.getWritableDatabase(),artist.getArtistId());
                                notifyDataSetChanged();
                            }

                        });
                    }

                }
            }
        }

        else{
            final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);

            likeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mActivity.getApplicationContext(),"You gotta log in for this!!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public ArrayList<Artist> getData() {
        return mArtists;
    }

    @Override
    public int getCount() {
        return mArtists.size();  //Returns length of the array of Events
    }

    @Override
    public Object getItem(int position) {
        return mArtists.get(position);  //Returns the Item being accessed in the the array
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //A view holder that contain the things that need to be changed for every event
    private static class ViewHolder{
        //The values holding summary description of the Venue.
        ImageView ArtistPicture;
        ImageView ArtistIcon;
        TextView ArtistName;
        TextView ArtistDistance;
        //Values holding the detailed description of venues.
        TextView DesArtistName;
        TextView ArtistLocName;
        TextView ArtistLocSt;
        TextView ArtistLocAdd;
        TextView ArtistEventTiming;
    }

    //SVG Conversion.
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(mActivity.getApplicationContext(), resource);

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
