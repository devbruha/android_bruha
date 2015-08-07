package com.bruha.bruha.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bruha.bruha.Views.ShowOnMapActivity;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Work on 2015-06-12.
 */
public class OrganizationListViewAdapter extends BaseSwipeAdapter {

    private Activity mActivity;
    private ArrayList<Organizations> mOrganizations;
    public static int Clicks=0;
    ArrayList<String> addictOrgID;
    RetrieveMyPHP retrieveMyPHP;
    SQLiteDatabaseModel dbHelper;

    public OrganizationListViewAdapter(Activity activity,ArrayList<Organizations> organizations,ArrayList<String> orgID)
    {
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


    @Override
    public void fillValues(final int position, final View convertView) {

        //Test

        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def

        convertView.setTag(holder); //sets the tag

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
                    OrganizationDistance.setVisibility(View.INVISIBLE);
                    swipeLicon.setVisibility(View.INVISIBLE);
                    swipeRicon.setVisibility(View.INVISIBLE);
                }
                else{
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.VISIBLE);
                    OrganizationName.setVisibility(View.VISIBLE);
                    OrganizationDistance.setVisibility(View.VISIBLE);
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
        holder.OrganizationIcon = (ImageView) convertView.findViewById(R.id.VenueIcon);
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


        //FONT SHIT.
        Typeface fnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Regular.ttf");
        Typeface tfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Bold.ttf");
        Typeface rest = Typeface.createFromAsset(mActivity.getAssets(),"fonts/OpenSans-Regular.ttf");



        //Setting all the text inside the view.

        //Summary being set.
        holder.OrganizationName.setText(Outfit.getOrgName());
        //  holder.OrganizationDistance.setText("1.2 km");

        //Detailed Description being set.
        holder.OrganizationDetailedName.setText(Outfit.getOrgName());
        holder.OrganizationLocName.setText(Outfit.getOrgName());
        holder.OrganizationLocSt.setText(Outfit.getOrgSt());
        holder.OrganizationLocAdd.setText(Outfit.getOrgLocation());


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
                        Intent intent = new Intent(mActivity, EventPageActivity.class);
                        intent.putExtra("Id",Outfit.getOrgId());
                        intent.putExtra("Type","Outfit");
                        mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });


        //FONT SHIT
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


        //Sets the height to 1/3 the screensize.
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height =  (int)Math.round(height*.33);

        ImageView Picture = (ImageView) convertView.findViewById(R.id.VenuePicture);
        ViewGroup.LayoutParams PictureParam = Picture.getLayoutParams();
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

        TextView DesVenueHourWeekDay = (TextView) convertView.findViewById(R.id.VenueMontoFriHour);
        int x6= (int)Math.round(height * .0165);
        DesVenueHourWeekDay.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourWeekDay.setTypeface(domregfnt);

        TextView DesVenueHourSaturday=(TextView) convertView.findViewById(R.id.VenueSaturdayHour);
        DesVenueHourSaturday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourSaturday.setTypeface(domregfnt);

        TextView DesVenueHourSunday= (TextView) convertView.findViewById(R.id.VenueSundayHour);
        DesVenueHourSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);
        DesVenueHourSaturday.setTypeface(domregfnt);

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe1 = (TextView) convertView.findViewById(R.id.VenueSwipeBarSize1);
        int x7= (int)Math.round(height * .030);
        Swipe1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x7);

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe2 = (TextView) convertView.findViewById(R.id.VenueSwipeBarSize2);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX,x7);

        if(MyApplication.loginCheck==true) {

            if (mActivity.getLocalClassName().equals("Views.MyUploadsActivity")) {
                final Button likeText = (Button) convertView.findViewById(R.id.likeVenButton);
                likeText.setText("Delete!");
                likeText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String x = retrieveMyPHP.deleteUserOrg(MyApplication.userName, Outfit.getOrgId());
                        Toast.makeText(mActivity.getApplicationContext(), x, Toast.LENGTH_SHORT).show();
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



}
