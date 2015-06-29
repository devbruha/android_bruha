package com.bruha.bruha.Adapters;


import android.app.Activity;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import java.util.ArrayList;

/**
 * Created by Work on 2015-06-28.
 */
public class TicketListView extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<Event> mEvent;

    public TicketListView(Activity activity, ArrayList<Event> event) {
        mActivity = activity;
        mEvent = event;
    }


    //Checks if it is a free event, if so, Displays it.
    public String freeEventCheck(double price) {
        if (price == 0.0) {
            return "Free!";
        } else {
            return "$" + price;
        }
    }


    public String TimeFormat(String Time) {
        String Hour = Time.substring(0, 2);
        String Min = Time.substring(3, 5);
        double hr = Double.parseDouble(Hour);
        int t = (int) (hr / 12);
        String M = "";
        if (t == 0) {
            M = "AM";
        } else {
            M = "PM";
        }
        hr = hr % 12;
        int x = (int) hr;
        Hour = x + "";
        String time = Hour + ":" + Min + " " + M;
        return time;

    }


    //Method to Format the Date that will be displayed.
    public String dateFormat(String Date) {
        String year = Date.substring(0, 4);
        String Month = Date.substring(5, 7);
        String Dates = Date.substring(8, 10);
        String Displayed = getMonth(Month) + " " + Dates + "," + year;
        return Displayed;
    }

    //Part of the Implementation of the dateFormat Method.
    public String getMonth(String Month) {
        if (Month.equals("01"))
            Month = "January";
        if (Month.equals("02"))
            Month = "Febuary";
        if (Month.equals("03"))
            Month = "March";
        if (Month.equals("04"))
            Month = "April";
        if (Month.equals("05"))
            Month = "May";
        if (Month.equals("06"))
            Month = "June";
        if (Month.equals("07"))
            Month = "July";
        if (Month.equals("08"))
            Month = "August";
        if (Month.equals("09"))
            Month = "September";
        if (Month.equals("10"))
            Month = "October";
        if (Month.equals("11"))
            Month = "November";
        if (Month.equals("12"))
            Month = "December";
        return Month;
    }


    /*

    //Generates the view,look at ListViewAdapter when implementing this.
    public View generateView(int position, ViewGroup viewGroup) {
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.my_ticket_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        convertView.setTag(holder); //sets the tag


        //Obtaining References to the Image/Text to change inside the layout.
        holder.Picture = (ImageView) convertView.findViewById(R.id.ticketPic);
        holder.Title = (TextView) convertView.findViewById(R.id.ticketName);
        holder.Type = (TextView) convertView.findViewById(R.id.ticketType);
        holder.Remaining = (TextView) convertView.findViewById(R.id.ticketRemaining);
        holder.End = (TextView) convertView.findViewById(R.id.ticketEnd);
        holder.Quantity = (TextView) convertView.findViewById(R.id.ticketQuantity);
        holder.Total = (TextView) convertView.findViewById(R.id.ticketTotal);

        //Initializing each item to the required type
        final Event event = mEvent.get(position);


        //Changing the text in the fields everytime.
        holder.Title.setText("Lights");
        holder.Type.setText("Concert");
        holder.Remaining.setText("30");
        holder.End.setText("May 27,2015");
        holder.Quantity.setText("2");
        holder.Total.setText("500");
        //holder.Picture.setImageResource(); {


        return convertView;
    }

    */





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

    @Override
    public View getView(int position, View x, ViewGroup viewGroup) {
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.my_ticket_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        convertView.setTag(holder); //sets the tag


        //Obtaining References to the Image/Text to change inside the layout.
        holder.Picture = (ImageView) convertView.findViewById(R.id.ticketPic);
        holder.Title = (TextView) convertView.findViewById(R.id.ticketName);
        holder.Type = (TextView) convertView.findViewById(R.id.ticketType);
        holder.Remaining = (TextView) convertView.findViewById(R.id.ticketRemaining);
        holder.End = (TextView) convertView.findViewById(R.id.ticketEnd);
        holder.Quantity = (TextView) convertView.findViewById(R.id.ticketQuantity);
        holder.Total = (TextView) convertView.findViewById(R.id.ticketTotal);

        //Initializing each item to the required type
        final Event event = mEvent.get(position);


        //Changing the text in the fields everytime.
        holder.Title.setText("Lights");
        holder.Type.setText("Concert");
        holder.Remaining.setText("30");
        holder.End.setText("May 27,2015");
        holder.Quantity.setText("2");
        holder.Total.setText("500");
        //holder.Picture.setImageResource(); {


        return convertView;
    }


    //A view holder that contain the things that need to be changed for every event
    private static class ViewHolder{
        private ImageView Picture;
        private TextView Title;
        private TextView Type;
        private TextView Remaining;
        private TextView End;
        private TextView Quantity;
        private TextView Total;

    }



}
