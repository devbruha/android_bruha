// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

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
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.RetrieveMyPHP;
import com.bruha.bruha.R;
import com.bruha.bruha.Views.EventPageActivity;
import com.bruha.bruha.Views.MoreInfoActivity;
import com.bruha.bruha.Views.ShowOnMapActivity;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Work on 2015-06-12.
 */
public class OrganizationListViewAdapter extends BaseSwipeAdapter {
    //Declaring the global variables to be referenced throughout the class.
    private Activity mActivity;
    private ArrayList<Organizations> mOrganizations;
    public static int Clicks=0;
    ArrayList<String> addictOrgID;
    RetrieveMyPHP retrieveMyPHP;
    SQLiteDatabaseModel dbHelper;

    public OrganizationListViewAdapter(Activity activity,ArrayList<Organizations> organizations,ArrayList<String> orgID) {
        mActivity=activity;
        mOrganizations=organizations;
        addictOrgID = orgID;
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
    public void setIcon(Organizations org,ImageView icon) {
        if(org.getOrgPrimaryCategory().contains("Academic"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgacademic, 30));}

        else if(org.getOrgPrimaryCategory().contains("Business"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgbusiness, 30));}

        else if(org.getOrgPrimaryCategory().contains("Charity"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgcharity, 30));}

        else if (org.getOrgPrimaryCategory().contains("Fashion"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgfashion, 30));}

        else if (org.getOrgPrimaryCategory().contains("Promoter"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgpromoter, 30));}

        else if (org.getOrgPrimaryCategory().contains("Fraternity"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgfraternity, 30));}

        else if (org.getOrgPrimaryCategory().contains("Not-for-profit"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgnonprofit, 30));}

        else if (org.getOrgPrimaryCategory().contains("Sports"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgsports, 30));}

        else if (org.getOrgPrimaryCategory().contains("Student"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgstudent, 30));}

        else if (org.getOrgPrimaryCategory().contains("Religion"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.orgreligon, 30));}

    }

    public void setAnimation(final ImageView sR, final ImageView sL) {
        final Animation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1000);

        final Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(1000);
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
        animation11.setDuration(1000);

        final Animation animation22 = new AlphaAnimation(1.0f, 0.0f);
        animation22.setDuration(1000);
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
        swipeRicon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.rightswipe, 30));
        ImageView swipeLicon = (ImageView) convertView.findViewById(R.id.swipeyleft);
        swipeLicon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.leftswipe, 30));
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
                    //OrganizationDistance.setVisibility(View.INVISIBLE);
                    swipeLicon.setVisibility(View.INVISIBLE);
                    swipeRicon.setVisibility(View.INVISIBLE);
                }
                else{
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.VISIBLE);
                    OrganizationName.setVisibility(View.VISIBLE);
                  ///  OrganizationDistance.setVisibility(View.VISIBLE);
                    swipeLicon.setVisibility(View.VISIBLE);
                    swipeRicon.setVisibility(View.VISIBLE);
                }
                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });

        //Initializing each item to the required type
        final Organizations Outfit = mOrganizations.get(position);

        //Summary Description of the Venue.
        holder.OrganizationPicture= (ImageView) convertView.findViewById(R.id.VenuePicture);
        holder.OrganizationIcon = (ImageView) convertView.findViewById(R.id.VAOIcon);
        holder.OrganizationName = (TextView) convertView.findViewById(R.id.VenueName);
        holder.OrganizationDistance = (TextView) convertView.findViewById(R.id.VenueDistance);

        //Detailed Description of the Venue
        holder.OrganizationDetailedName= (TextView) convertView.findViewById(R.id.DesVenueName);
        holder.OrganizationLocName= (TextView) convertView.findViewById(R.id.DesVenueLocName);
        holder.OrganizationLocSt= (TextView) convertView.findViewById(R.id.DesVenueLocStreet);
        holder.OrganizationLocAdd = (TextView) convertView.findViewById(R.id.DesVenueLocAddress);
        holder.OrganizationWeekDayHours= (TextView) convertView.findViewById(R.id.VenueMontoFriHour);
        holder.OrganizationSaturday = (TextView) convertView.findViewById(R.id.VenueSaturdayHour);
        holder.OrganizationSunday = (TextView) convertView.findViewById(R.id.VenueSundayHour);

        //Setting all the text inside the view.

        //Summary being set.

        if(Outfit.getOrgName().length()<=15)
        { holder.OrganizationName.setText(Outfit.getOrgName());}
        else { holder.OrganizationName.setText(Outfit.getOrgName().substring(0,15)+"..."); }
        //  holder.OrganizationDistance.setText("1.2 km");

        setIcon(Outfit,holder.OrganizationIcon);


        //Detailed Description being set.
        if(Outfit.getOrgName().length()<=15)
        { holder.OrganizationDetailedName.setText(Outfit.getOrgName());}
        else { holder.OrganizationDetailedName.setText(Outfit.getOrgName().substring(0,15)+"..."); }
        holder.OrganizationLocName.setText(Outfit.getOrgName());
        holder.OrganizationLocSt.setText(Outfit.getOrgSt());
        holder.OrganizationLocAdd.setText(Outfit.getOrgLocation());

        //Putting the picture into the view.
        Picasso.with(convertView.getContext()).load(Outfit.getOrgPicture()).into(holder.OrganizationPicture);

        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.Venue_Left_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.VenueRightSwipeLayout));

        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.VenuePreviewRow);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.VenuePreviewLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(PreviewLayout, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        PreviewLayout.setAlpha(1f);
                        Intent intent = new Intent(mActivity, ShowOnMapActivity.class);
                        intent.putExtra("Id",Outfit.getOrgId());
                        intent.putExtra("Type","Outfit");
                        mActivity.startActivity(intent);
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
                        Intent intent = new Intent(mActivity, MoreInfoActivity.class);
                        intent.putExtra("Id",Outfit.getOrgId());
                        intent.putExtra("Type","Outfit");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });

        //FONT,Resizing and addiction implementation:

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
        params.height =  (int)Math.round(height*.5);
        //Picture being resize
        ImageView Picture = (ImageView) convertView.findViewById(R.id.VenuePicture);
        ViewGroup.LayoutParams PictureParam = Picture.getLayoutParams();
        PictureParam.height =  (int)Math.round(height*.5);
        //Getting the LayoutParams of the circle and then setting it to quarter the screensize.
        ViewGroup.LayoutParams circleParams = circle.getLayoutParams();
        circleParams.height =  (int)Math.round(height*.3);
        circleParams.width = (int)Math.round(height*.3);

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
        //Hour text being resized.
        TextView DesVenueHourWeekDay = (TextView) convertView.findViewById(R.id.VenueMontoFriHour);
        int x6= (int)Math.round(height * .0165);
        DesVenueHourWeekDay.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourWeekDay.setTypeface(domregfnt);
        //Hour text being resized.
        TextView DesVenueHourSaturday=(TextView) convertView.findViewById(R.id.VenueSaturdayHour);
        DesVenueHourSaturday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourSaturday.setTypeface(domregfnt);
        //Hour text being resized.
        TextView DesVenueHourSunday= (TextView) convertView.findViewById(R.id.VenueSundayHour);
        DesVenueHourSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourSaturday.setTypeface(domregfnt);

        TextView Swipe2 = (TextView) convertView.findViewById(R.id.Swipe2);
        int x55= (int)Math.round(height * .030);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX, x55);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe3 = (TextView) convertView.findViewById(R.id.Swipe3);
        Swipe3.setTextSize(TypedValue.COMPLEX_UNIT_PX, x55);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe5 = (TextView) convertView.findViewById(R.id.Swipe5);
        Swipe5.setTextSize(TypedValue.COMPLEX_UNIT_PX,x55);
        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe6 = (TextView) convertView.findViewById(R.id.Swipe6);
        Swipe6.setTextSize(TypedValue.COMPLEX_UNIT_PX,x55);

        //The left swipe being implemented/Addiction or User deletion being implemented.
        if(MyApplication.loginCheck==true) {
            if (mActivity.getLocalClassName().equals("Views.MyUploadsActivity")) {
                final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);
                likeText.setText("Delete!");
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String x = retrieveMyPHP.deleteUserOrg(MyApplication.userName, Outfit.getOrgId());
                        Toast.makeText(mActivity.getApplicationContext(), x, Toast.LENGTH_SHORT).show();

                        if(x.contains("!")) {
                            dbHelper.deleteUserOrg(dbHelper.getWritableDatabase(),Outfit.getOrgId());
                            mOrganizations.remove(position);
                            notifyDataSetChanged();
                        }

                    }
                });
            } else {
                //myAddictions stuff:
                boolean addicted = false;

                if (addictOrgID != null) {

                    for (String ID : addictOrgID) {
                        if (ID.equals(Outfit.getOrgId())) {
                            addicted = true;
                        }
                    }

                    final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);
                    if (addicted == true) {
                        likeText.setText("Unlike!");
                        likeText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                retrieveMyPHP.deleteOrgAddiction(MyApplication.userName, Outfit.getOrgId());
                                dbHelper.deleteOrgAddiction(dbHelper.getWritableDatabase(),Outfit.getOrgId());
                                Toast.makeText(mActivity.getApplicationContext(), "You are Unaddicted!", Toast.LENGTH_SHORT).show();
                                likeText.setText("Like!");


                                for(int i=0;i<addictOrgID.size();i++)
                                {
                                    if(addictOrgID.get(i).equals(Outfit.getOrgId()))
                                    {
                                        addictOrgID.remove(i);
                                        break;
                                    }
                                }

                                if(mActivity.getLocalClassName().equals("Views.myAddictions"))
                                { mOrganizations.remove(position);}

                                notifyDataSetChanged();

                            }
                        });
                    } else {
                        likeText.setText("Like!");
                        likeText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                retrieveMyPHP.organizationAddiction(MyApplication.userName, Outfit.getOrgId());
                                Toast.makeText(mActivity.getApplicationContext(), "You are addicted", Toast.LENGTH_SHORT).show();
                                likeText.setText("Unlike!");

                                addictOrgID.add(Outfit.getOrgId());
                                dbHelper.insertOrgAddiction(dbHelper.getWritableDatabase(),Outfit.getOrgId());
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

    public ArrayList<Organizations> getData() {
        return mOrganizations;
    }

    @Override
    public int getCount() {
        return mOrganizations.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrganizations.get(position);  //Returns the Item being accessed in the the array
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //A view holder that contain the things that need to be changed for every event
    private static class ViewHolder{
        //The values holding summary description of the Venue.
        ImageView OrganizationPicture;
        ImageView OrganizationIcon;
        TextView OrganizationName;
        TextView OrganizationDistance;
        //Values holding the detailed description of venues.
        TextView OrganizationDetailedName;
        TextView OrganizationLocName;
        TextView OrganizationLocSt;
        TextView OrganizationLocAdd;
        TextView OrganizationWeekDayHours;
        TextView OrganizationSaturday;
        TextView OrganizationSunday;
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
