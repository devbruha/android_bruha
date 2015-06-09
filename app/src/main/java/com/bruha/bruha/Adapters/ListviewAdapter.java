package com.bruha.bruha.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;
import com.bruha.bruha.Views.EventPageActivity;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by Work on 2015-05-12.
 */
public class ListviewAdapter extends BaseSwipeAdapter {
    private Activity mActivity;
    private ArrayList<Event> mEvents;
    public static int Clicks=0;

    //the Constructor for the class.
    public ListviewAdapter(Activity activity, ArrayList<Event> events) {
        mActivity = activity;
        mEvents = events;
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

        LinearLayout MoreInfoLay= (LinearLayout) convertView.findViewById(R.id.MoreInfoLayout);

        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Assigning the Relative Layout that contains the detailed description.
                RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.DescriptionLayout);

               //Assigning the summary description stuff that will hide and reappear depending on the clicks.
                ImageView Bubble = (ImageView) v.findViewById(R.id.EventImageBubble);
                TextView EventName = (TextView) v.findViewById(R.id.TextEventName);
                TextView EventDate = (TextView) v.findViewById(R.id.TextEventDate);
                TextView EventPrice = (TextView) v.findViewById(R.id.TextEventPrice);
                TextView EventDistance = (TextView) v.findViewById(R.id.TextEventDistance);

                if (Clicks % 2 == 0) {
                    //Popping the detailed description into view.
                    layout.setVisibility(View.VISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.INVISIBLE);
                    EventName.setVisibility(View.INVISIBLE);
                    EventDate.setVisibility(View.INVISIBLE);
                    EventPrice.setVisibility(View.INVISIBLE);
                    EventDistance.setVisibility(View.INVISIBLE);
                } else {
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);
                    //Displaying the summary description back upon the 2nd click.
                    Bubble.setVisibility(View.VISIBLE);
                    EventName.setVisibility(View.VISIBLE);
                    EventDate.setVisibility(View.VISIBLE);
                    EventPrice.setVisibility(View.VISIBLE);
                    EventDistance.setVisibility(View.VISIBLE);
                }
                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });

        convertView.setTag(holder); //sets the tag

        //Summary Description of the events.
        holder.EventPicture= (ImageView) convertView.findViewById(R.id.ImageEventPicture);
        holder.EventIcon = (ImageView) convertView.findViewById(R.id.ImageEventIcon);
        holder.EventName = (TextView) convertView.findViewById(R.id.TextEventName);
        holder.EventDate = (TextView) convertView.findViewById(R.id.TextEventDate);
        holder.EventPrice= (TextView) convertView.findViewById(R.id.TextEventPrice);
        holder.EventDistance= (TextView) convertView.findViewById(R.id.TextEventDistance);

        //Initializing each item to the required type
        Event event = mEvents.get(position);

        //Detailed Description of the events.
        holder.EventDName=(TextView) convertView.findViewById(R.id.DesEventName);
        holder.EventDPrice= (TextView) convertView.findViewById(R.id.DesEventPrice);
        holder.EventLocName=(TextView) convertView.findViewById(R.id.DesEventLocName);
        holder.EventLocSt=(TextView) convertView.findViewById(R.id.DesEventLocStreet);
        holder.EventLocAdd=(TextView) convertView.findViewById(R.id.DesEventLocAddress);
        holder.EventStartDate=(TextView) convertView.findViewById(R.id.DesEventStartDate);
        holder.EventStartTime=(TextView) convertView.findViewById(R.id.DesEventStartTime);
        holder.EventEndDate = (TextView) convertView.findViewById(R.id.DesEventEndDate);
        holder.EventEndTime= (TextView) convertView.findViewById(R.id.DesEventEndTime);
        //Setting the text boxes to the information retrieved from the arrays of events

        //Setting the summary description
        holder.EventDistance.setText(event.getEventDistance() + "km");
        holder.EventName.setText(event.getEventName());
        holder.EventDate.setText(dateFormat(event.getEventDate()));
        holder.EventPrice.setText(freeEventCheck(event.getEventPrice()));
        //holder.EventIcon.setImageResource(event.getEventIcon());
        //holder.EventPicture.setImageResource(event.getEventPicture());

        //Setting the detailed description.
        holder.EventDName.setText(event.getEventName());
        holder.EventDPrice.setText("$"+event.getEventPrice());
        holder.EventLocName.setText(event.getEventLocName());
        holder.EventLocSt.setText(event.getEventLocSt());
        holder.EventLocAdd.setText(event.getEventLocAdd());
        holder.EventStartDate.setText(dateFormat(event.getEventDate()));
        holder.EventStartTime.setText(event.getEventStartTime());
        holder.EventEndDate.setText(dateFormat(event.getEventEndDate()));
        holder.EventEndTime.setText(event.getEventEndTime());

        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.mLinear));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });

        //A Click listener for double click.
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mActivity, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
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
                        BuyTicketLayout.setAlpha(1f);
                        //Intent intent = new Intent(mActivity, DashboardActivity.class);
                        //mActivity.startActivity(intent);
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
                        //Intent intent = new Intent(mActivity, DashboardActivity.class);
                        // mActivity.startActivity(intent);
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
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });
        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        //Assigning the ImageBubble to a variable to alter iits dimensions after with.
        ImageView circle = (ImageView) convertView.findViewById(R.id.EventImageBubble);
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
        //Getting the LayoutParams of the circle and then setting it to quarter the screensize.
        ViewGroup.LayoutParams circleParams = circle.getLayoutParams();
        circleParams.height =  (int)Math.round(height*.25);
        circleParams.width = (int)Math.round(height*.25);
        //Text Formatting inside the EventImage Bubble
        //The EventName being Formatted.
        TextView EventName = (TextView) convertView.findViewById(R.id.TextEventName);
        int x1= (int)Math.round(height*.0290);
        EventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
        //The EventDate being formatted.
        TextView EventDate = (TextView) convertView.findViewById(R.id.TextEventDate);
        int x2= (int)Math.round(height*.0295);
        EventDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);
        //The EventPrice being formatted.
        TextView EventPrice = (TextView) convertView.findViewById(R.id.TextEventPrice);
        int x3= (int)Math.round(height*.0300);
        EventPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        //The EventDistance being formatted.
        TextView EventDistance = (TextView) convertView.findViewById(R.id.TextEventDistance);
        int x4= (int)Math.round(height*.020);
        EventDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);
        //TextViews inside the Detailed view being formatted.
        //The DesEventName being Formatted.
        TextView DesEventName = (TextView) convertView.findViewById(R.id.DesEventName);
        int y= (int)Math.round(height*.028);
        DesEventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,y);
        //The DesEventPrice being formatted.
        TextView DesPrice = (TextView) convertView.findViewById(R.id.DesEventPrice);
        int y12= (int)Math.round(height*.028);
        DesPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,y12);
        //The DesLocName being Formatted.
        TextView DesLocName = (TextView) convertView.findViewById(R.id.DesEventLocName);
        int y1= (int)Math.round(height*.018);
        DesLocName.setTextSize(TypedValue.COMPLEX_UNIT_PX,y1);
        //The DesLocSt being formatted.
        TextView DesLocSt = (TextView) convertView.findViewById(R.id.DesEventLocStreet);
        int y2= (int)Math.round(height*.018);
        DesLocSt.setTextSize(TypedValue.COMPLEX_UNIT_PX,y2);
        //The DesLocAdd being Formatted.
        TextView DesLocAdd = (TextView) convertView.findViewById(R.id.DesEventLocAddress);
        int y3= (int)Math.round(height*.018);
        DesLocAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,y3);
        //The DesStartDate being formatted.
        TextView DesStartDate = (TextView) convertView.findViewById(R.id.DesEventStartDate);
        int y4= (int)Math.round(height*.014);
        DesStartDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, y4);
        //The DesStartTime being formatted.
        TextView DesStartTime = (TextView) convertView.findViewById(R.id.DesEventStartTime);
        int y5= (int)Math.round(height*.014);
        DesStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,y5);
        //The DesEndDate being formatted.
        TextView DesEndDate = (TextView) convertView.findViewById(R.id.DesEventEndDate);
        int y6= (int)Math.round(height * .014);
        DesEndDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,y6);
        //The DesEndTime being formattted.
        TextView DesEndTime = (TextView) convertView.findViewById(R.id.DesEventEndTime);
        int y7= (int)Math.round(height*.014);
        DesEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,y7);
        //The TextView saying "start" being formatted.
        TextView start = (TextView) convertView.findViewById(R.id.StartText);
        int y8= (int)Math.round(height*.0127);
        start.setTextSize(TypedValue.COMPLEX_UNIT_PX,y8);
        //The TextView saying "end" being formatted.
        TextView end = (TextView) convertView.findViewById(R.id.EndText);
        int y9= (int)Math.round(height*.0127);
        end.setTextSize(TypedValue.COMPLEX_UNIT_PX,y9);
        //Swipe Bars being resized.
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
}