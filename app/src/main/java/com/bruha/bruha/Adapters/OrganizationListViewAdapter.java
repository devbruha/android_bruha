package com.bruha.bruha.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
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

import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Work on 2015-06-12.
 */
public class OrganizationListViewAdapter extends BaseSwipeAdapter {

    private Activity mActivity;
    private ArrayList<Organizations> mOrganizations;
    public static int Clicks=0;

    public OrganizationListViewAdapter(Activity activity,ArrayList<Organizations> organizations)
    {
        mActivity=activity;
        mOrganizations=organizations;
    }



    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup viewGroup) {
        //Inflates the view to be used
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.venue_item, viewGroup, false);

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

                if (Clicks % 2 == 0) {
                    //Popping the detailed description into view.
                    layout.setVisibility(View.VISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.INVISIBLE);
                    OrganizationName.setVisibility(View.INVISIBLE);
                    OrganizationDistance.setVisibility(View.INVISIBLE);
                }
                else{
                    //Hiding the detailed description upon the 2nd click.
                    layout.setVisibility(View.INVISIBLE);
                    //Hiding the summary Description from view to display the detailed description.
                    Bubble.setVisibility(View.VISIBLE);
                    OrganizationName.setVisibility(View.VISIBLE);
                    OrganizationDistance.setVisibility(View.VISIBLE);
                }
                Clicks++; //Adds to the number of times the user has tapped on an item.
            }
        });

        //Initializing each item to the required type
        final Organizations artist = mOrganizations.get(position);

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




        //Setting all the text inside the view.

        //Summary being set.
      //  holder.OrganizationPicture.setImageResource();
      // holder.OrganizationIcon.setImageResource();
        holder.OrganizationName.setText("The Forge");
        holder.OrganizationDistance.setText("1.2 km");

        //Detailed Description being set.
        holder.OrganizationDetailedName.setText("The Forge");
        holder.OrganizationLocName.setText("McMaster Innovation Park");
        holder.OrganizationLocSt.setText("Main at Longwood");
        holder.OrganizationLocAdd.setText("Hamilton, ON Canada");
        holder.OrganizationWeekDayHours.setText("Mon to Fri: 8 AM - 10 PM ");
        holder.OrganizationSaturday.setText("Saturday: 10 AM - 5 PM");
        holder.OrganizationSunday.setText("Sunday: Closed");





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
                        //Intent intent = new Intent(mActivity, DashboardActivity.class);
                        // mActivity.startActivity(intent);
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
                        //Intent intent = new Intent(mActivity, EventPageActivity.class);
                        // intent.putExtra("EventId", event.getEventid());
                        // mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });


        return convertView;
    }




    @Override
    public void fillValues(int i, View view) {

        //Assigning the ImageBubble to a variable to alter iits dimensions after with.
        ImageView circle = (ImageView) view.findViewById(R.id.VenueImageBubble);

        // Android functions to determine the screen dimensions.
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;


        //Sets the height to 1/3 the screensize.
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height =  (int)Math.round(height*.33);


        //Getting the LayoutParams of the circle and then setting it to quarter the screensize.
        ViewGroup.LayoutParams circleParams = circle.getLayoutParams();
        circleParams.height =  (int)Math.round(height*.25);
        circleParams.width = (int)Math.round(height*.25);


        //Summary being resized.

        //The VenueName being Formatted.
        TextView VenueName = (TextView) view.findViewById(R.id.VenueName);
        int x1= (int)Math.round(height*.030);
        VenueName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);

        //The VenueDistance being formatted.
        TextView VenueDistance = (TextView) view.findViewById(R.id.VenueDistance);
        int x2= (int)Math.round(height * .018);
        VenueDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);

        //Detailed Description being resized.

        //The VenueDistance being formatted.
        TextView DesVenueName = (TextView) view.findViewById(R.id.DesVenueName);
        int x3= (int)Math.round(height * .030);
        DesVenueName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);

        //The VenueDistance being formatted.
        TextView DesVenueLocName = (TextView) view.findViewById(R.id.DesVenueLocName);
        int x4= (int)Math.round(height * .0215);
        DesVenueLocName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);

        //The VenueDistance being formatted.
        TextView DesVenueLocSt = (TextView) view.findViewById(R.id.DesVenueLocStreet);
        DesVenueLocSt.setTextSize(TypedValue.COMPLEX_UNIT_PX, x4);

        //The VenueDistance being formatted.
        TextView DesVenueLocAdd = (TextView) view.findViewById(R.id.DesVenueLocAddress);
        DesVenueLocAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX, x4);

        //The VenueDistance being formatted.
        TextView DesVenueHourText = (TextView) view.findViewById(R.id.VenueHourText);
        int x5= (int)Math.round(height * .0185);
        DesVenueHourText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);

        TextView DesVenueHourWeekDay = (TextView) view.findViewById(R.id.VenueMontoFriHour);
        int x6= (int)Math.round(height * .0165);
        DesVenueHourWeekDay.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);

        TextView DesVenueHourSaturday=(TextView) view.findViewById(R.id.VenueSaturdayHour);
        DesVenueHourSaturday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);

        TextView DesVenueHourSunday= (TextView) view.findViewById(R.id.VenueSundayHour);
        DesVenueHourSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX,x6);

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe1 = (TextView) view.findViewById(R.id.VenueSwipeBarSize1);
        int x7= (int)Math.round(height*.030);
        Swipe1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x7);

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe2 = (TextView) view.findViewById(R.id.VenueSwipeBarSize2);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX,x7);

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
