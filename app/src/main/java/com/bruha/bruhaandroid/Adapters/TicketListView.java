// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Adapters;


import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.R;

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
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.my_ticket_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        convertView.setTag(holder); //sets the tag


/*

        //Obtaining References to the Image/Text to change inside the layout.
        holder.Picture = (ImageView) convertView.findViewById(R.id.ticketPic);
        holder.Title = (TextView) convertView.findViewById(R.id.ticketName);
        holder.Type = (TextView) convertView.findViewById(R.id.ticketType);
        holder.Remaining = (TextView) convertView.findViewById(R.id.ticketRemaining);
        holder.End = (TextView) convertView.findViewById(R.id.ticketEnd);
        holder.Quantity = (TextView) convertView.findViewById(R.id.ticketQuantity);
        holder.Total = (TextView) convertView.findViewById(R.id.ticketTotal);
        holder.Price= (TextView) convertView.findViewById(R.id.ticketPrice);
*/
        //Initializing each item to the required type
        final Event event = mEvent.get(position);


        //Changing the text in the fields everytime.
        holder.Title.setText("Three Day Grace");
        holder.Type.setText("Concert");
        holder.Remaining.setText("30");
        holder.End.setText("May 27,2015");
        holder.Quantity.setText("2");
        holder.Total.setText("500");
        holder.Price.setText("$20");
        //holder.Picture.setImageResource(); {


        //FONT SHIT.
        Typeface fnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Regular.ttf");
        Typeface tfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Bold.ttf");
        Typeface rest = Typeface.createFromAsset(mActivity.getAssets(),"fonts/OpenSans-Regular.ttf");




        // Android functions to determine the screen dimensions.
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;


        //Sets the height to 1/3 the screensize.
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height =  (int)Math.round(height*.33);



        //Assigning the ImageBubble to a variable to alter its dimensions after with.
        ViewGroup.LayoutParams ticketPicParam = holder.Picture.getLayoutParams();
        ticketPicParam.height =  (int)Math.round(height*.22);
        ticketPicParam.width = (int)Math.round(height*.20);


        //Text Formatting inside the EventImage Bubble
        //The EventName being Formatted.
        int x1= (int)Math.round(height*.0380);
        holder.Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        holder.Title.setTypeface(tfnt);

        //The EventPrice being formatted.
        int x3= (int)Math.round(height * .02175);
        holder.Type.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
       // holder.Type.setTypeface(fnt);

        //The EventPrice being formatted.
        holder.Remaining.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
      //  holder.Remaining.setTypeface(fnt);

        //The EventPrice being formatted.
        holder.End.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
      //  holder.End.setTypeface(fnt);



        //The EventPrice being formatted.
        holder.Quantity.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);

        //The EventDate being formatted.
        holder.Total.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);

        holder.Price.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);

/*
        TextView typ = (TextView) convertView.findViewById(R.id.type);
        TextView rem = (TextView) convertView.findViewById(R.id.rem);
        TextView nd = (TextView) convertView.findViewById(R.id.en);
        TextView prce = (TextView) convertView.findViewById(R.id.prce);
        TextView quan = (TextView) convertView.findViewById(R.id.quan);
        TextView ttl = (TextView) convertView.findViewById(R.id.ttl);


        typ.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        typ.setTypeface(rest);
        rem.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        rem.setTypeface(rest);
        nd.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        nd.setTypeface(rest);
        prce.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        prce.setTypeface(rest);
        quan.setTextSize(TypedValue.COMPLEX_UNIT_PX, x3);
        quan.setTypeface(rest);
        ttl.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);
        ttl.setTypeface(rest);

        */




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
        private TextView Price;

    }



}
