// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Model.Venue;
import com.bruha.bruha.Processing.RetrieveMyPHP;
import com.bruha.bruha.R;
import com.bruha.bruha.Views.EventPageActivity;
import com.bruha.bruha.Views.MoreInfoActivity;
import com.bruha.bruha.Views.ShowOnMapActivity;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Work on 2015-06-12.
 */
public class MapVenListViewAdapter extends BaseSwipeAdapter {
    //Declaring the global variables to be used throughout the class.
    private Activity mActivity;
    private ArrayList<Venue> mVenues;
    private ArrayList<String> addictedVenueID;
    RetrieveMyPHP retrieveMyPHP;
    SQLiteDatabaseModel dbHelper;

    public MapVenListViewAdapter(Activity activity,ArrayList<Venue> venue,ArrayList<String> addictVenue) {
        mActivity=activity;
        mVenues=venue;
        addictedVenueID = addictVenue;
        retrieveMyPHP = new RetrieveMyPHP();
        dbHelper=new SQLiteDatabaseModel(mActivity);
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
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

    //Generates the view,look at ListViewAdapter when implementing this.
    @Override
    public View generateView(final int position, ViewGroup viewGroup) {
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.map_ven_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def
        convertView.setTag(holder); //sets the tag

        final ImageView swipeyright = (ImageView) convertView.findViewById(R.id.swipeyright);
        final ImageView swipeyleft = (ImageView) convertView.findViewById(R.id.swipeyleft);
        setAnimation(swipeyright, swipeyleft);

        //Obtaining References to the Image/Text to change inside the layout.
        holder.Picture=(ImageView) convertView.findViewById(R.id.MapEventPicture);
        holder.Title= (TextView) convertView.findViewById(R.id.MapEventName);
        holder.Price= (TextView) convertView.findViewById(R.id.MapEventPrice);
        holder.LocName= (TextView) convertView.findViewById(R.id.MapEventLocName);
        holder.LocSt= (TextView) convertView.findViewById(R.id.MapEventLocSt);
        holder.LocAdd= (TextView) convertView.findViewById(R.id.MapEventLocAddress);
        holder.Hours= (TextView) convertView.findViewById(R.id.MapEventStartDateAndTime);

        //Initializing each item to the required type
        final Venue venue = mVenues.get(position);

        //Changing the text in the fields everytime.
        if(venue.getVenueName().length()<=15)
        { holder.Title.setText(venue.getVenueName());}
        else { holder.Title.setText(venue.getVenueName().substring(0,15)+"..."); }
        holder.Price.setVisibility(View.INVISIBLE);
        holder.LocName.setText(venue.getVenueLocation());
        holder.LocSt.setText("Street 42.");
        holder.LocAdd.setText(venue.getVenueDescription());
        holder.Hours.setText("20 September, 2015 At 8:30 PM");
       // Setting the image of the venue
        Picasso.with(viewGroup.getContext()).load(venue.getVenuePicture()).fit().into(holder.Picture);

        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.left_wrapper_mapven));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.MapvenRightSwipeLayout));

        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.venMapPreview);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.venMapPreviewLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(PreviewLayout, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        PreviewLayout.setAlpha(1f);
                        Intent intent = new Intent(mActivity, ShowOnMapActivity.class);
                        intent.putExtra("Id",venue.getVenueId());
                        intent.putExtra("Type","Venue");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });
        //Implements the Button 'More Info' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoMoreInfoPage = (TableRow) convertView.findViewById(R.id.venMapMoreInfo);
        GoMoreInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout MoreInfoLay = (LinearLayout) convertView.findViewById(R.id.venMapMoreInfoLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(MoreInfoLay, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        MoreInfoLay.setAlpha(1f);
                        Intent intent = new Intent(mActivity, MoreInfoActivity.class);
                        intent.putExtra("Id",venue.getVenueId());
                        intent.putExtra("Type","Venue");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });

        //Implementing the left swipe/Addiction stuff
        if(MyApplication.loginCheck==true) {
            //myAddictions stuff:
            boolean addicted = false;

            if (addictedVenueID != null) {

                for (String ID : addictedVenueID) {
                    if (ID.equals(venue.getVenueId())) {
                        addicted = true;
                    }
                }

                final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);
                if (addicted == true) {
                    likeText.setText("Unlike!");
                    likeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retrieveMyPHP.deleteVenueAddiction(MyApplication.userName, venue.getVenueId());
                            dbHelper.deleteVenueAddiction(dbHelper.getWritableDatabase(),venue.getVenueId());
                            Toast.makeText(mActivity.getApplicationContext(), "You are Unaddicted!", Toast.LENGTH_SHORT).show();
                            likeText.setText("Like!");


                            for(int i=0;i<addictedVenueID.size();i++)
                            {
                                if(addictedVenueID.get(i).equals(venue.getVenueId()))
                                {
                                    addictedVenueID.remove(i);
                                    break;
                                }
                            }

                            if(mActivity.getLocalClassName().equals("Views.myAddictions")) { mVenues.remove(position);}
                            final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout_upper);
                            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    likeText.setText("Like!");
                    likeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retrieveMyPHP.venueAddiction(MyApplication.userName, venue.getVenueId());
                            Toast.makeText(mActivity.getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                            likeText.setText("Unlike!");

                            addictedVenueID.add(venue.getVenueId());
                            dbHelper.insertVenueAddiction(dbHelper.getWritableDatabase(), venue.getVenueId());

                            final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout_upper);
                            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            notifyDataSetChanged();
                        }

                    });
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
        return convertView;
    }

    //Use for resizing everything like in the Event ListViewAdapter.
    @Override
    public void fillValues(int i, View view) {
        //FONT setting and resizing everything

        Typeface domboldfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Bold.ttf");
        Typeface opensansregfnt = Typeface.createFromAsset(mActivity.getAssets(), "fonts/OpenSans-Regular.ttf");

        // Android functions to determine the screen dimensions.
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        // Storing the screen height into an int variable.
        int height = size.y;

        //Sets the height to 1/3 the screensize.
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height =  (int)Math.round(height*.33);
        //Assigning the ImageBubble to a variable to alter its dimensions after with.
        ImageView MapPicture = (ImageView) view.findViewById(R.id.MapEventPicture);
        ViewGroup.LayoutParams circleParams = MapPicture.getLayoutParams();
        circleParams.height =  (int)Math.round(height*.20);
        circleParams.width = (int)Math.round(height*.18);
        //The EventName being Formatted.
        TextView EventName = (TextView) view.findViewById(R.id.MapEventName);
        int x1= (int)Math.round(height*.0310);
        EventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
        EventName.setTypeface(domboldfnt);
        //The EventPrice being formatted.
        TextView EventPrice = (TextView) view.findViewById(R.id.MapEventPrice);
        int x2= (int)Math.round(height*.0310);
        EventPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);
        //The EventPrice being formatted.
        TextView EventLocName = (TextView) view.findViewById(R.id.MapEventLocName);
        int x3= (int)Math.round(height*.02175);
        EventLocName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        EventLocName.setTypeface(opensansregfnt);
        //The EventPrice being formatted.
        TextView EventLocSt = (TextView) view.findViewById(R.id.MapEventLocSt);
        EventLocSt.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        EventLocSt.setTypeface(opensansregfnt);
        //The EventPrice being formatted.
        TextView EventLocAdd = (TextView) view.findViewById(R.id.MapEventLocAddress);
        EventLocAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        EventLocAdd.setTypeface(opensansregfnt);
        //The EventDate being formatted.
        TextView EventDate = (TextView) view.findViewById(R.id.MapEventStartDateAndTime);
        int x4= (int)Math.round(height * .022);
        EventDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);
        EventDate.setTypeface(domboldfnt);

        //Swipe Bars being resized.

        TextView Swipe2 = (TextView) view.findViewById(R.id.Swipe2);
        int x5= (int)Math.round(height * .030);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX, x5);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe3 = (TextView) view.findViewById(R.id.Swipe3);
        Swipe3.setTextSize(TypedValue.COMPLEX_UNIT_PX, x5);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe5 = (TextView) view.findViewById(R.id.Swipe5);
        Swipe5.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe6 = (TextView) view.findViewById(R.id.Swipe6);
        Swipe6.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
    }

    @Override
    public int getCount() {
        return mVenues.size();  //Returns length of the array of Events
    }

    @Override
    public Object getItem(int position) {
        return mVenues.get(position);  //Returns the Item being accessed in the the array
    }

    @Override
    public long getItemId(int position) {
        return 0;  //Id of the Item being accessed in the view
    }

    //A view holder that contain the things that need to be changed for every event
    private static class ViewHolder{
        private ImageView Picture;
        private TextView Title;
        private TextView Price;
        private TextView LocName;
        private TextView LocSt;
        private TextView LocAdd;
        private TextView Hours;
    }
}
