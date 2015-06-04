package com.bruha.bruha.Adapters;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;
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
        View convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_item, parent, false);

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
        holder.EventEndDate=(TextView) convertView.findViewById(R.id.DesEventEndDate);
        holder.EventEndTime= (TextView) convertView.findViewById(R.id.DesEventEndTime);

        //Setting the text boxes to the information retrieved from the arrays of events

        //Setting the summary description
        holder.EventDistance.setText(event.getEventDistance()+"km");
        holder.EventName.setText(event.getEventName());
        holder.EventDate.setText(event.getEventDate());
        holder.EventPrice.setText("$"+event.getEventPrice());
        //holder.EventIcon.setImageResource(event.getEventIcon());
        //holder.EventPicture.setImageResource(event.getEventPicture());

        //Setting the detailed description.
        holder.EventDName.setText(event.getEventName());
        holder.EventDPrice.setText("$"+event.getEventPrice());
        holder.EventLocName.setText(event.getEventLocName());
        holder.EventLocSt.setText(event.getEventLocSt());
        holder.EventLocAdd.setText(event.getEventLocAdd());
        holder.EventStartDate.setText(event.getEventDate());
        holder.EventStartTime.setText(event.getEventStartTime());
        holder.EventEndDate.setText(event.getEventEndDate());
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

        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {

        ImageView circle = (ImageView) convertView.findViewById(R.id.EventImageBubble);

        // Android functions to determine the screen dimensions

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;

        ViewGroup.LayoutParams params = convertView.getLayoutParams();

        params.height =  (int)Math.round(height*.33);

        ViewGroup.LayoutParams circleParams = circle.getLayoutParams();

        circleParams.height =  (int)Math.round(height*.25);
        circleParams.width = (int)Math.round(height*.25);
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

