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
import android.widget.TableRow;
import android.widget.TextView;

import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Work on 2015-06-12.
 */
public class MapArtistListViewAdapter extends BaseSwipeAdapter {
    private Activity mActivity;
    private ArrayList<Artists> mArtists;

    public MapArtistListViewAdapter(Activity activity,ArrayList<Artists> artists)
    {
        mActivity=activity;
        mArtists=artists;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    //Generates the view,look at ListViewAdapter when implementing this.
    @Override
    public View generateView(int position, ViewGroup viewGroup) {
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.map_ven_item, viewGroup, false);

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

        //Initializing each item to the required type
        final Artists artist = mArtists.get(position);




        //Changing the text in the fields everytime.
        holder.Title.setText("The Arkells");
        holder.Price.setText("$20.50");
        holder.LocName.setText("McMaster University");
        holder.LocSt.setText("1280 Main St. West");
        holder.LocAdd.setText("Hamilton, ON Canada");
        holder.Hours.setText("20 September, 2015 At 8:30 PM");
        // holder.Picture.setImageResource();


        //Picasso.with(viewGroup.getContext()).load(artist.getArtistPicture()).into(holder.Picture);



        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.left_wrapper_mapven));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.MapvenRightSwipeLayout));




        //Implements the Button 'Preview' that appears after swipe right,Shows Button Highlight for half a second when clicked.
        TableRow GoPreviewPage  = (TableRow) convertView.findViewById(R.id.venMapPreview);
        GoPreviewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout PreviewLayout= (LinearLayout) convertView.findViewById(R.id.venMapPreviewLayout);
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
        TableRow GoMoreInfoPage = (TableRow) convertView.findViewById(R.id.venMapMoreInfo);
        GoMoreInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout MoreInfoLay = (LinearLayout) convertView.findViewById(R.id.venMapMoreInfoLayout);
                ObjectAnimator animator = ObjectAnimator.ofFloat(MoreInfoLay, "alpha", 1f, 0.5f);
                animator.setDuration(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        MoreInfoLay.setAlpha(1f);
                        //  Intent intent = new Intent(mActivity, EventPageActivity.class);
                        //  intent.putExtra("EventId", event.getEventid());
                        // mActivity.startActivity(intent);
                    }
                });
                animator.start();
            }
        });


        return convertView;
    }


    //Use for resizing everything like in the Event ListViewAdapter.
    @Override
    public void fillValues(int i, View view) {

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

        //Text Formatting inside the EventImage Bubble
        //The EventName being Formatted.
        TextView EventName = (TextView) view.findViewById(R.id.MapEventName);
        int x1= (int)Math.round(height*.0310);
        EventName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);

        //The EventPrice being formatted.
        TextView EventPrice = (TextView) view.findViewById(R.id.MapEventPrice);
        int x2= (int)Math.round(height*.0310);
        EventPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,x2);

        //The EventPrice being formatted.
        TextView EventLocName = (TextView) view.findViewById(R.id.MapEventLocName);
        int x3= (int)Math.round(height*.02175);
        EventLocName.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);

        //The EventPrice being formatted.
        TextView EventLocSt = (TextView) view.findViewById(R.id.MapEventLocSt);
        EventLocSt.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);

        //The EventPrice being formatted.
        TextView EventLocAdd = (TextView) view.findViewById(R.id.MapEventLocAddress);
        EventLocAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,x3);

        //The EventDate being formatted.
        TextView EventDate = (TextView) view.findViewById(R.id.MapEventStartDateAndTime);
        int x4= (int)Math.round(height*.022);
        EventDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,x4);

        //Swipe Bars being resized.

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe1 = (TextView) view.findViewById(R.id.venMapSwipeBarSize1);
        int x5= (int)Math.round(height*.030);
        Swipe1.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);

        //The TextView "LOLi" that helps set size of right swipe bar being formatted.
        TextView Swipe2 = (TextView) view.findViewById(R.id.venMapSwipeBarSize2);
        Swipe2.setTextSize(TypedValue.COMPLEX_UNIT_PX,x5);

    }

    @Override
    public int getCount() {
        return mArtists.size();  //Returns length of the array of Events
    }

    @Override
    public Object getItem(int position) {
        return mArtists.get(position);  //Returns the Item being accessed in the the array
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
