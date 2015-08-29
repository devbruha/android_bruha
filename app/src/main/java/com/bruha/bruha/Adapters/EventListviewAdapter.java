package com.bruha.bruha.Adapters;

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
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.RetrieveMyPHP;
import com.bruha.bruha.R;
import com.bruha.bruha.Views.EventPageActivity;
import com.bruha.bruha.Views.ShowOnMapActivity;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Work on 2015-05-12.
 */
public class EventListviewAdapter extends BaseSwipeAdapter {
    private Activity mActivity;
    private ArrayList<Event> mEvents;
    public static int Clicks=0;
    private ArrayList<String> addictedEventsID;
    RetrieveMyPHP retrieveMyPHP;
    SQLiteDatabaseModel dbHelper;

    //the Constructor for the class.
    public EventListviewAdapter(Activity activity, ArrayList<Event> events, ArrayList<String> addictevent) {
        mActivity = activity;
        mEvents = events;
        addictedEventsID = addictevent;
        retrieveMyPHP = new RetrieveMyPHP();
        dbHelper = new SQLiteDatabaseModel(mActivity);

    }

    public String TimeFormat(String Time)
    {
        //Sets the time format from 24 hour clock to 12 hour.
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
    public String dateFormat(String Date)
    {
        String year=Date.substring(0,4);
        String Month=Date.substring(5,7);
        String Dates=Date.substring(8,10);
        String Displayed=getMonth(Month)+ " " + Dates + "," + year;
        return Displayed;
    }

    //Part of the Implementation of the dateFormat Method.
    public String getMonth(String Month)
    {
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

    //Checks if it is a free event, if so, Displays it.
    public String freeEventCheck(double price)
    {
        if(price==0.0)
        {return "Free!";}
        else {return "$"+price; }
    }

    @Override
    public int getCount() {
        return mEvents.size();  //Returns length of the array of Events
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);  //Returns the Item being accessed in the the array}
    }

    @Override
    public long getItemId(int position) {
        return 0;   //Id of the Item being accessed in the view
    }

    public ArrayList<Event> getData() {
        return mEvents;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        //Inflates the view to be used
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_item, parent, false);
        return convertView;
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
        //Setting all the variables and words for each ROW:
        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        ImageView swipeRicon = (ImageView) convertView.findViewById(R.id.swipeyright);
        ImageView swipeLicon = (ImageView) convertView.findViewById(R.id.swipeyleft);
        setAnimation(swipeLicon,swipeRicon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Assigning the Relative Layout that contains the detailed description.
                RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.DescriptionLayout);
                //Assigning the summary description stuff that will hide and reappear depending on the clicks.
                ImageView Bubble = (ImageView) v.findViewById(R.id.VenueImageBubble);
                ImageView swipeRicon = (ImageView) v.findViewById(R.id.swipeyright);
                ImageView swipeLicon = (ImageView) v.findViewById(R.id.swipeyleft);
                TextView EventName = (TextView) v.findViewById(R.id.TextEventName);
                TextView EventDate = (TextView) v.findViewById(R.id.VenueName);
                TextView EventPrice = (TextView) v.findViewById(R.id.TextEventPrice);
                TextView EventDistance = (TextView) v.findViewById(R.id.VenueDistance);


                if (Clicks % 2 == 0) {
                    //Popping the detailed description into view.
                    layout.setVisibility(View.VISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.INVISIBLE);
                    EventName.setVisibility(View.INVISIBLE);
                    EventDate.setVisibility(View.INVISIBLE);
                    EventPrice.setVisibility(View.INVISIBLE);
                 //   EventDistance.setVisibility(View.INVISIBLE);
                    swipeLicon.setVisibility(View.INVISIBLE);
                    swipeRicon.setVisibility(View.INVISIBLE);
                } else {
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);
                    //Displaying the summary description back upon the 2nd click.
                    Bubble.setVisibility(View.VISIBLE);
                    EventName.setVisibility(View.VISIBLE);
                    EventDate.setVisibility(View.VISIBLE);
                    EventPrice.setVisibility(View.VISIBLE);
                 //   EventDistance.setVisibility(View.VISIBLE);
                    swipeLicon.setVisibility(View.VISIBLE);
                    swipeRicon.setVisibility(View.VISIBLE);
                }
                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });

        convertView.setTag(holder); //sets the tag

        //Summary Description of the events.
        holder.EventPicture= (ImageView) convertView.findViewById(R.id.ImageEventPicture);
        holder.EventIcon = (ImageView) convertView.findViewById(R.id.VenueIcon);
        holder.EventName = (TextView) convertView.findViewById(R.id.TextEventName);
        holder.EventDate = (TextView) convertView.findViewById(R.id.VenueName);
        holder.EventPrice= (TextView) convertView.findViewById(R.id.TextEventPrice);
        holder.EventActivityIcon= (ImageView) convertView.findViewById(R.id.VenueActivityIcon);

        //Initializing each item to the required type
        final Event event = mEvents.get(position);

        //Detailed Description of the events.
        holder.EventDName=(TextView) convertView.findViewById(R.id.DesVenueName);
        holder.EventDPrice= (TextView) convertView.findViewById(R.id.DesEventPrice);
        holder.EventLocName=(TextView) convertView.findViewById(R.id.DesVenueLocName);
        holder.EventLocSt=(TextView) convertView.findViewById(R.id.DesVenueLocStreet);
        holder.EventLocAdd=(TextView) convertView.findViewById(R.id.DesVenueLocAddress);
        holder.EventStartDate=(TextView) convertView.findViewById(R.id.DesEventStartDate);
        holder.EventStartTime=(TextView) convertView.findViewById(R.id.DesEventStartTime);
        holder.EventEndDate = (TextView) convertView.findViewById(R.id.DesEventEndDate);
        holder.EventEndTime= (TextView) convertView.findViewById(R.id.DesEventEndTime);
        //Setting the text boxes to the information retrieved from the arrays of events

        //Setting the summary description
        holder.EventName.setText(event.getEventName());
        holder.EventDate.setText(dateFormat(event.getEventDate()));
        holder.EventPrice.setText(freeEventCheck(event.getEventPrice()));
        // holder.EventDistance.setText(event.getEventDistance() + "km");
        setIcon(event,holder.EventIcon);

        //Setting the background image of the event.
        Picasso.with(convertView.getContext()).load(event.getEventPicture()).fit().into(holder.EventPicture);

        //Setting the detailed description..
        holder.EventDName.setText(event.getEventName());
        holder.EventDPrice.setText("$"+event.getEventPrice());
        holder.EventLocName.setText(event.getEventLocName());
        holder.EventLocSt.setText(event.getEventLocSt());
        holder.EventLocAdd.setText(event.getEventLocAdd());
        holder.EventStartDate.setText(dateFormat(event.getEventDate()));
        holder.EventStartTime.setText(TimeFormat(event.getEventStartTime()));
        holder.EventEndDate.setText(dateFormat(event.getEventEndDate()));
        holder.EventEndTime.setText(TimeFormat(event.getEventEndTime()));

        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.mLinear));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
        });


        //Implements the Button 'Buy Ticket' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoBuyTicketPage = (TableRow) convertView.findViewById(R.id.BuyTicketRow);
        GoBuyTicketPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout BuyTicketLayout= (LinearLayout) convertView.findViewById(R.id.BuyTicketLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(BuyTicketLayout, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        BuyTicketLayout.setAlpha(.25f);
                        Toast.makeText(mActivity.getApplicationContext(),"Still under development,sorry!",Toast.LENGTH_SHORT).show();
                    }
                });
                animator.start();
            }
        });

        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.PreviewRow);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.PreviewLayout);
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
        TableRow GoMoreInfoPage = (TableRow) convertView.findViewById(R.id.MoreInfoRow);
        GoMoreInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout MoreInfoLay= (LinearLayout) convertView.findViewById(R.id.MoreInfoLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(MoreInfoLay, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        MoreInfoLay.setAlpha(1f);
                        Intent intent = new Intent(mActivity, EventPageActivity.class);
                        intent.putExtra("Id",event.getEventid());
                        intent.putExtra("Type","Event");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });


        //Setting the font and resizing everything.

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
        //Gets the Layout Params of the itemList.
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        //Sets the height to 1/3 the screensize.
        params.height =  (int)Math.round(height*.33);

        //Getting layoutparam of image and setting it to the view size.
        ImageView Picture = (ImageView) convertView.findViewById(R.id.ImageEventPicture);
        ViewGroup.LayoutParams PicParam = Picture.getLayoutParams();
        PicParam.height =  (int)Math.round(height*.33);

        //Getting the icon and setting its size
        ImageView icon = (ImageView) convertView.findViewById(R.id.VenueIcon);
        ViewGroup.LayoutParams iconLayoutParams = icon.getLayoutParams();
        iconLayoutParams.height =  (int)Math.round(height*.05);
        iconLayoutParams.width =  (int)Math.round(height*.05);

        //Getting the LayoutParams of the circle and then setting it to quarter the screensize.
        ViewGroup.LayoutParams circleParams = circle.getLayoutParams();
        circleParams.height =  (int)Math.round(height*.25);
        circleParams.width = (int)Math.round(height*.25);
        //Text Formatting inside the EventImage Bubble
        //The EventName being Formatted.
        TextView EventName = (TextView) convertView.findViewById(R.id.TextEventName);
        int x1= (int)Math.round(height*.0290);
        EventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
        EventName.setTypeface(domboldfnt);
        //The EventDate being formatted.
        TextView EventDate = (TextView) convertView.findViewById(R.id.VenueName);
        int x2= (int)Math.round(height*.0295);
        EventDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);
        EventDate.setTypeface(domboldfnt);
        //The EventPrice being formatted.
        TextView EventPrice = (TextView) convertView.findViewById(R.id.TextEventPrice);
        int x3= (int)Math.round(height*.0300);
        EventPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        EventPrice.setTypeface(domboldfnt);
        //The EventDistance being formatted.
      /*  TextView EventDistance = (TextView) convertView.findViewById(R.id.VenueDistance);
        int x4= (int)Math.round(height*.020);
        EventDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);
        EventDistance.setTypeface(domboldfnt);
        */
        //TextViews inside the Detailed view being formatted.
        //The DesEventName being Formatted.
        TextView DesEventName = (TextView) convertView.findViewById(R.id.DesVenueName);
        int y= (int)Math.round(height*.028);
        DesEventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,y);
        DesEventName.setTypeface(domboldfnt);
        //The DesEventPrice being formatted.
        TextView DesPrice = (TextView) convertView.findViewById(R.id.DesEventPrice);
        int y12= (int)Math.round(height*.028);
        DesPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,y12);
        DesPrice.setTypeface(domboldfnt);
        //The DesLocName being Formatted.
        TextView DesLocName = (TextView) convertView.findViewById(R.id.DesVenueLocName);
        int y1= (int)Math.round(height*.018);
        DesLocName.setTextSize(TypedValue.COMPLEX_UNIT_PX,y1);
        DesLocName.setTypeface(opensansregfnt);
        //The DesLocSt being formatted.
        TextView DesLocSt = (TextView) convertView.findViewById(R.id.DesVenueLocStreet);
        int y2= (int)Math.round(height*.018);
        DesLocSt.setTextSize(TypedValue.COMPLEX_UNIT_PX,y2);
        DesLocSt.setTypeface(opensansregfnt);
        //The DesLocAdd being Formatted.
        TextView DesLocAdd = (TextView) convertView.findViewById(R.id.DesVenueLocAddress);
        int y3= (int)Math.round(height*.018);
        DesLocAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,y3);
        DesLocAdd.setTypeface(opensansregfnt);
        //The DesStartDate being formatted.
        TextView DesStartDate = (TextView) convertView.findViewById(R.id.DesEventStartDate);
        int y4= (int)Math.round(height*.014);
        DesStartDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, y4);
        DesStartDate.setTypeface(opensansregfnt);
        //The DesStartTime being formatted.
        TextView DesStartTime = (TextView) convertView.findViewById(R.id.DesEventStartTime);
        int y5= (int)Math.round(height*.014);
        DesStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,y5);
        DesStartTime.setTypeface(opensansregfnt);
        //The DesEndDate being formatted.
        TextView DesEndDate = (TextView) convertView.findViewById(R.id.DesEventEndDate);
        int y6= (int)Math.round(height * .014);
        DesEndDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,y6);
        DesEndDate.setTypeface(opensansregfnt);
        //The DesEndTime being formattted.
        TextView DesEndTime = (TextView) convertView.findViewById(R.id.DesEventEndTime);
        int y7= (int)Math.round(height*.014);
        DesEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,y7);
        DesEndTime.setTypeface(opensansregfnt);
        //The TextView saying "start" being formatted.
        TextView start = (TextView) convertView.findViewById(R.id.VenueHourText);
        int y8= (int)Math.round(height*.0127);
        start.setTextSize(TypedValue.COMPLEX_UNIT_PX,y8);
        start.setTypeface(domboldfnt);
        //The TextView saying "end" being formatted.
        TextView end = (TextView) convertView.findViewById(R.id.PageEndText);
        int y9= (int)Math.round(height*.0127);
        end.setTextSize(TypedValue.COMPLEX_UNIT_PX,y9);
        end.setTypeface(domboldfnt);

        //Swipe Bars being resized:

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe1 = (TextView) convertView.findViewById(R.id.SwipeBarsize1);
        int yx9= (int)Math.round(height*.030);
        Swipe1.setTextSize(TypedValue.COMPLEX_UNIT_PX,yx9);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe2 = (TextView) convertView.findViewById(R.id.SwipeBarSize2);
        int yx8= (int)Math.round(height*.030);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX,yx8);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe3 = (TextView) convertView.findViewById(R.id.SwipeBarSize3);
        int yx7= (int)Math.round(height*.030);
        Swipe3.setTextSize(TypedValue.COMPLEX_UNIT_PX,yx7);



        //The Button implementation of the left swipe button(addiction button).
        if(MyApplication.loginCheck==true) {

            if(mActivity.getLocalClassName().equals("Views.MyUploadsActivity"))
            {
                holder.EventActivityIcon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.myupload, 30));

                final Button likeText = (Button) convertView.findViewById(R.id.likeButton);
                likeText.setText("Delete!");
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      String x=  retrieveMyPHP.deleteUserEvent(MyApplication.userName,event.getEventid());
                        Toast.makeText(mActivity.getApplicationContext(),x,Toast.LENGTH_SHORT).show();

                        if(x.contains("!")) {
                            dbHelper.deleteUserEvent(dbHelper.getWritableDatabase(), event.getEventid());
                            mEvents.remove(position);
                            notifyDataSetChanged();
                        }
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
                            holder.EventActivityIcon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.myaddictions, 30));
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
                                { mEvents.remove(position);}

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
                                dbHelper.insertEventAddiction(dbHelper.getWritableDatabase(),event.getEventid());
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
    }

    //Method to set the icon of the event.
    public void setIcon(Event event,ImageView icon) {
     if(event.getEventPrimaryCategory().contains("Club"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.club, 30));}

     else if(event.getEventPrimaryCategory().contains("Performing"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.performing, 30));}

     else if(event.getEventPrimaryCategory().contains("Business"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.business, 30));}

     else if(event.getEventPrimaryCategory().contains("Ceremony"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.ceremony, 30));}

     else if(event.getEventPrimaryCategory().contains("Tech"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.tech, 30));}

     else if(event.getEventPrimaryCategory().contains("Comedy"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.comedy, 30));}

     else if(event.getEventPrimaryCategory().contains("Fashion"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.fashion, 30));}

     else if(event.getEventPrimaryCategory().contains("Festivals"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.festivals, 30));}

     else if(event.getEventPrimaryCategory().contains("Film"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.film, 30));}

     else if(event.getEventPrimaryCategory().contains("Food"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.food, 30));}

     else if(event.getEventPrimaryCategory().contains("Party"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.party, 30));}

     else if(event.getEventPrimaryCategory().contains("Music"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.music, 30));}

     else if(event.getEventPrimaryCategory().contains("Political"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.political, 30));}

     else if(event.getEventPrimaryCategory().contains("School"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.school, 30));}

     else if(event.getEventPrimaryCategory().contains("Sports"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.sports, 30));}

     else if(event.getEventPrimaryCategory().contains("Tour"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.tour, 30));}

     else if(event.getEventPrimaryCategory().contains("Arts"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.arts, 30));}

     else if(event.getEventPrimaryCategory().contains("Social"))
     {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.social, 30));}
    }

    //A view holder that contain the things that need to be changed for every event
    private static class ViewHolder{
        //The values holding summary description of the event.
        ImageView EventPicture;
        ImageView EventIcon;
        TextView EventName;
        TextView EventDate;
        TextView EventPrice;
        TextView EventDistance;
        ImageView EventActivityIcon;
        //The Values holding detailed description of the event.
        TextView EventDName;
        TextView EventDPrice;
        TextView EventLocName;
        TextView EventLocSt;
        TextView EventLocAdd;
        TextView EventStartDate;
        TextView EventStartTime;
        TextView EventEndDate;
        TextView EventEndTime;
        //No need for Name,Price and Start Date for event as it is already given in first batch above

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