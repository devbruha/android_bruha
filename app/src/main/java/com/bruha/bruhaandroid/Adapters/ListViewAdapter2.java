package com.bruha.bruhaandroid.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bruha.bruhaandroid.Fragments.ShowOnMapFragment;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.RetrieveMyPHP;
import com.bruha.bruhaandroid.R;
import com.bruha.bruhaandroid.Views.MoreInfoEventActivity;
import com.bruha.bruhaandroid.Views.ShowOnMapActivity;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ArhamRazaMac on 16-05-09.
 */
public class ListViewAdapter2 extends BaseSwipeAdapter{
    private FragmentActivity mActivity;
    private ArrayList<Event> mEvents;
    //public static int Clicks=0;
    private ArrayList<String> addictedEventsID;
    //RetrieveMyPHP retrieveMyPHP;
    //SQLiteDatabaseModel dbHelper;

    //the Constructor for the class.
    public ListViewAdapter2(FragmentActivity activity, ArrayList<Event> events) {
        mActivity = activity;
        mEvents = events;
        // addictedEventsID = addictevent;
        //  retrieveMyPHP = new RetrieveMyPHP();
        //   dbHelper = new SQLiteDatabaseModel(mActivity);

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
        String Displayed=getMonth(Month)+ " " + Dates + ", " + year;
        return Displayed;
    }

    //Part of the Implementation of the dateFormat Method.
    public String getMonth(String Month)
    {
        if(Month.equals("01"))
            Month="Jan.";
        if(Month.equals("02"))
            Month="Feb.";
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
            Month="Aug.";
        if(Month.equals("09"))
            Month="Sept.";
        if(Month.equals("10"))
            Month="Oct.";
        if(Month.equals("11"))
            Month="Nov.";
        if(Month.equals("12"))
            Month="Dec.";
        return Month;
    }

    //Checks if it is a free event, if so, Displays it.
    public String freeEventCheck(double price)
    {
        if(price==0.0)
        {return "Free!";}
        else {return "$"+price; }
    }

    public ArrayList<Event> getData() {
        return mEvents;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        //Inflates the view to be used
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_item2, parent, false);
        return convertView;
    }

    @Override
    public void fillValues(final int position, final View convertView) {

        //Setting all the variables and words for each ROW:
        ViewHolder holder;

        holder  = new ViewHolder(); //Making variable of class type ViewHolder def
        holder.eventImage = (ImageView) convertView.findViewById(R.id.eventImage);
        holder.eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
        holder.eventDate = (TextView) convertView.findViewById(R.id.eventDate);
        holder.eventPrice = (TextView) convertView.findViewById(R.id.eventPrice);
        holder.eventDistance = (TextView) convertView.findViewById(R.id.eventDistance);
        holder.distIcon = (ImageView) convertView.findViewById(R.id.distIcon);

        // Grabbing events
        final Event event = getItem(position);

        // Setting event picture
        Picasso.with(convertView.getContext()).load(event.getEventPicture()).fit().into(holder.eventImage);

        // Setting event name
        if(event.getEventName().length() > 23){
            holder.eventTitle.setText(event.getEventName().substring(0,23) + "...");
        }
        else{
            holder.eventTitle.setText(event.getEventName());
        }

        // Setting event date
        holder.eventDate.setText(dateFormat(event.getEventDate()));

        // Setting event price
        holder.eventPrice.setText(freeEventCheck((event.getEventPrice())));

        // Setting event distance (to do once database complete)


        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.leftSwipe));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.rightSwipe));

        //Setting the font
        Typeface opensansregfnt = Typeface.createFromAsset(mActivity.getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface domboldfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Bold.ttf");
        Typeface captureitfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Capture_it.ttf");

        // Resizing for different displays:
        // Android functions to determine the screen dimensions.
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        // Storing the screen height into an int variable and adjustment variables
        int height = size.y;
        double adjItem = 0.30; //for eventImage
        double adjTopText = (int)Math.round(height*0.0225); // for eventTitle and event Price
        double adjBottomText = (int)Math.round(height*0.015); // for eventDate and eventDistance

        // Layout Params of the item list and setting height for it
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height = (int)Math.round(height*adjItem);
        // Layout Params & height adjustment of eventTitle
        TextView eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
        eventTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) adjTopText);
        eventTitle.setTypeface(captureitfnt);
        //eventTitle.setTypeface(domboldfnt);
        // Layout Params & height adjustment of eventDate
        TextView eventDate = (TextView) convertView.findViewById(R.id.eventDate);
        eventDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) adjBottomText);
        eventDate.setTypeface(opensansregfnt);
        // Layout Params & height adjustment of eventPrice
        TextView eventPrice = (TextView) convertView.findViewById(R.id.eventPrice);
        eventPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) adjTopText);
        eventPrice.setTypeface(opensansregfnt);
        eventPrice.setTypeface(domboldfnt);

        //Implements the Button 'Buy Ticket' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoBuyTicketPage = (TableRow) convertView.findViewById(R.id.MapTopHalf);
        GoBuyTicketPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout BuyTicketLayout= (LinearLayout) convertView.findViewById(R.id.MapBuyTicketLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(BuyTicketLayout, "alpha", 1f, 0.5f);
                animator.setDuration(0);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        BuyTicketLayout.setAlpha(.25f);
                        Toast.makeText(mActivity.getApplicationContext(),"Still under development,sorry!",Toast.LENGTH_SHORT).show();
                    }
                });
                animator.start();
            }
        });

        final Fragment eventOnMap = new ShowOnMapFragment();
        //Intents
        Bundle bundle=new Bundle();
        bundle.putString("type","Event");
        bundle.putString("id",event.getEventid());
        bundle.putString("name",event.getEventName());
        bundle.putDouble("latitude", event.getEventLatitude());
        bundle.putDouble("longitude", event.getEventLongitude());
        bundle.putString("category",event.getEventPrimaryCategory());
        //set Fragmentclass Arguments
        eventOnMap.setArguments(bundle);

        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.MapPreview);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.MapPreviewLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(PreviewLayout, "alpha", 1f, 0.5f);
                animator.setDuration(0);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        PreviewLayout.setAlpha(.25f);
                        mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,eventOnMap).commit();
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public int getCount() {
        return mEvents.size();  //Returns length of the array of Events
    }

    @Override
    public Event getItem(int position) {
        return mEvents.get(position);  //Returns the Item being accessed in the the array}
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        ImageView eventImage;
        TextView eventTitle;
        TextView eventDate;
        TextView eventPrice;
        TextView eventDistance;
        ImageView distIcon;

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