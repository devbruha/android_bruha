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
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
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
 * Created by Work on 2015-06-10.
 */
public class MapListViewAdapter extends BaseSwipeAdapter {
    //Global variables being declared to be used throughout the class.
    private Activity mActivity;
    private ArrayList<Event> mEvent;
    private ArrayList<String> addictedEventsID;
    RetrieveMyPHP retrieveMyPHP;
    SQLiteDatabaseModel dbHelper;

    public MapListViewAdapter(Activity activity,ArrayList<Event> event,ArrayList<String> addictevent) {
        mActivity=activity;
        mEvent=event;
        addictedEventsID = addictevent;
        retrieveMyPHP = new RetrieveMyPHP();
        dbHelper = new SQLiteDatabaseModel(mActivity);
    }

    //Checks if it is a free event, if so, Displays it.
    public String freeEventCheck(double price) {
        if(price==0.0)
        {return "Free!";}
        else {return "$"+price; }
    }

    public String TimeFormat(String Time) {
        String Hour= Time.substring(0,2);
        String Min= Time.substring(3, 5);
        double hr= Double.parseDouble(Hour);
        int t= (int) (hr/12);
        String M= "";
        if(t==0)
        {M="AM" ;}
        else { M= "PM" ; }
        hr=hr%12;
        int x= (int) hr;
        Hour= x+"";
        String time = Hour + ":" + Min + " " + M;
        return time;

    }

    //Method to Format the Date that will be displayed.
    public String dateFormat(String Date) {
        String year=Date.substring(0,4);
        String Month=Date.substring(5,7);
        String Dates=Date.substring(8,10);
        String Displayed=getMonth(Month)+ " " + Dates + "," + year;
        return Displayed;
    }

    //Part of the Implementation of the dateFormat Method.
    public String getMonth(String Month) {
        if(Month.equals("01"))
            Month="January";
        if(Month.equals("02"))
            Month="Febuary";
        if(Month.equals("03"))
            Month="March";
        if(Month.equals("04"))
            Month="April";
        if(Month.equals("05"))
            Month="May";
        if(Month.equals("06"))
            Month="June";
        if(Month.equals("07"))
            Month="July";
        if(Month.equals("08"))
            Month="August";
        if(Month.equals("09"))
            Month="September";
        if(Month.equals("10"))
            Month="October";
        if(Month.equals("11"))
            Month="November";
        if(Month.equals("12"))
            Month="December";
        return Month;
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
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.map_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def
        convertView.setTag(holder); //sets the tag

        //Obtaining References to the Image/Text to change inside the layout.
        holder.Picture=(ImageView) convertView.findViewById(R.id.MapEventPicture);
        holder.Title= (TextView) convertView.findViewById(R.id.MapEventName);
        holder.Price= (TextView) convertView.findViewById(R.id.MapEventPrice);
        holder.LocName= (TextView) convertView.findViewById(R.id.MapEventLocName);
        holder.LocSt= (TextView) convertView.findViewById(R.id.MapEventLocSt);
        holder.LocAdd= (TextView) convertView.findViewById(R.id.MapEventLocAddress);
        holder.Hours= (TextView) convertView.findViewById(R.id.MapEventStartDateAndTime);

       final ImageView swipeyright = (ImageView) convertView.findViewById(R.id.swipeyright);
        final ImageView swipeyleft = (ImageView) convertView.findViewById(R.id.swipeyleft);
        setAnimation(swipeyright,swipeyleft);


        //Initializing each item to the required type
        final Event event = mEvent.get(position);

        //Changing the text in the fields everytime.
        holder.Title.setText(event.getEventName());
        holder.Price.setText(freeEventCheck(event.getEventPrice()));
        holder.LocName.setText(event.getEventLocName());
        holder.LocSt.setText(event.getEventLocSt());
        holder.LocAdd.setText(event.getEventLocAdd());
        holder.Hours.setText(dateFormat(event.getEventDate())+" At "+TimeFormat(event.getEventStartTime()));
        //Setting the Image
        Picasso.with(viewGroup.getContext()).load(event.getEventPicture()).fit().into(holder.Picture);

        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.left_wrapper_map));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.MapRightSwipeLayout));

        //Implements the Button 'Buy Ticket' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoBuyTicketPage = (TableRow) convertView.findViewById(R.id.MapBuyTicket);
        GoBuyTicketPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout BuyTicketLayout= (LinearLayout) convertView.findViewById(R.id.MapBuyTicketLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(BuyTicketLayout, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        BuyTicketLayout.setAlpha(.25f);
                        Toast.makeText(mActivity.getApplicationContext(), "Still under development,sorry!", Toast.LENGTH_SHORT).show();
                    }
                });
                animator.start();
            }
        });
        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.MapPreview);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.MapPreviewLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(PreviewLayout, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        PreviewLayout.setAlpha(1f);
                        Intent intent = new Intent(mActivity, ShowOnMapActivity.class);
                        intent.putExtra("Id",event.getEventid());
                        intent.putExtra("Type","Event");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });
        //Implements the Button 'More Info' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoMoreInfoPage = (TableRow) convertView.findViewById(R.id.MapMoreInfo);
        GoMoreInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout MoreInfoLay = (LinearLayout) convertView.findViewById(R.id.MapMoreInfoLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(MoreInfoLay, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        MoreInfoLay.setAlpha(1f);
                        Intent intent = new Intent(mActivity, MoreInfoActivity.class);
                        intent.putExtra("Id",event.getEventid());
                        intent.putExtra("Type","Event");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });


        //Implements the left swipe /Addiction stuff.
        if(MyApplication.loginCheck==true) {

            if(mActivity.getLocalClassName().equals("Views.CalendarActivity"))
            {
                final Button likeText = (Button) convertView.findViewById(R.id.likeButton);
                likeText.setText("Dont click me!");
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity.getApplicationContext(),"Dude, seriously, stahp!! -.-",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            else {

                //myAddictions stuff:
                boolean addicted = false;

                if (addictedEventsID != null) {

                    for (String ID : addictedEventsID) {
                        if (ID.equals(event.getEventid())) {
                            addicted = true;
                        }
                    }

                    final Button likeText = (Button) convertView.findViewById(R.id.likeButton);
                    if (addicted == true) {
                        likeText.setText("Unlike!");
                        likeText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                retrieveMyPHP.deleteEventAddiction(MyApplication.userName, event.getEventid());
                                dbHelper.deleteEventAddiction(dbHelper.getWritableDatabase(), event.getEventid());
                                Toast.makeText(mActivity.getApplicationContext(), "You are Unaddicted!", Toast.LENGTH_SHORT).show();
                                likeText.setText("Like!");
                                for(int i=0;i<addictedEventsID.size();i++)
                                {
                                    if(addictedEventsID.get(i).equals(event.getEventid()))
                                    {
                                        addictedEventsID.remove(i);
                                        break;
                                    }
                                }

                                if(mActivity.getLocalClassName().equals("Views.myAddictions"))
                                { mEvent.remove(position);}

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
                                retrieveMyPHP.eventAddiction(MyApplication.userName, event.getEventid());
                                Toast.makeText(mActivity.getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                                likeText.setText("Unlike!");

                                addictedEventsID.add(event.getEventid());
                                dbHelper.insertEventAddiction(dbHelper.getWritableDatabase(), event.getEventid());
                                final SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout_upper);
                                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                notifyDataSetChanged();
                            }

                        });
                    }

                }
            }
        }

        else{
            final Button likeText = (Button) convertView.findViewById(R.id.likeButton);

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
        //FONT,Resizing
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
        EventPrice.setTypeface(domboldfnt);
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
        int x4= (int)Math.round(height*.022);
        EventDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);
        EventDate.setTypeface(domboldfnt);

        //Swipe Bars being resized.

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe1 = (TextView) view.findViewById(R.id.MapSwipeBarSize1);
        int x5= (int)Math.round(height*.030);
        Swipe1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe2 = (TextView) view.findViewById(R.id.MapSwipeBarSize2);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe3 = (TextView) view.findViewById(R.id.MapSwipeBarSize3);
        Swipe3.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);
    }

    @Override
    public int getCount() {
        return mEvent.size();  //Returns length of the array of Events
    }

    @Override
    public Object getItem(int position) {
        return mEvent.get(position);  //Returns the Item being accessed in the the array
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