package com.bruha.bruhaandroid.Adapters;

import android.app.Activity;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bruha.bruhaandroid.Model.Organizations;

import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ABC on 19/05/2016.
 */
public class OrgListViewAdapter2 extends BaseSwipeAdapter {

    private static class ViewHolder{
        ImageView OrganizationImage;
        TextView OrganizationTitle;
        TextView OrganizationAddress;
        TextView OrganizationMoreInfo;
        ImageView OrganizationIcon;
        TextView OrganizationShowMap;

    }

    private Activity mActivity;
    private ArrayList<Organizations> mOrganizations;
    //public static int Clicks=0;
    private ArrayList<String> addictedVenuesID;
    //RetrieveMyPHP retrieveMyPHP;
    //SQLiteDatabaseModel dbHelper;

    //the Constructor for the class.
    public OrgListViewAdapter2(Activity activity, ArrayList<Organizations> organizations) {
        mActivity = activity;
        mOrganizations = organizations;
        // addictedVenuesID = addictvenue;
        //  retrieveMyPHP = new RetrieveMyPHP();
        //   dbHelper = new SQLiteDatabaseModel(mActivity);
    }
    public ArrayList<Organizations> getData() {
        return mOrganizations;
    }


    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        //Inflates the view to be used
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_venorg, parent, false);
        return convertView;
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        //Setting all the variables and words for each ROW:
        ViewHolder holder;

        holder  = new ViewHolder(); //Making variable of class type ViewHolder def
        holder.OrganizationImage = (ImageView) convertView.findViewById(R.id.orgvenImage);
        holder.OrganizationTitle = (TextView) convertView.findViewById(R.id.orgvenName);
        holder.OrganizationAddress = (TextView) convertView.findViewById(R.id.orgvenAddress);
        holder.OrganizationMoreInfo = (TextView) convertView.findViewById(R.id.orgvenMoreInfo);
        holder.OrganizationIcon = (ImageView) convertView.findViewById(R.id.orgvenIcon);
        holder.OrganizationShowMap = (TextView) convertView.findViewById(R.id.orgvenShowMap);



        /* Grabbing events */
        final Organizations organization = (Organizations) getItem(position);

        // Setting event picture
        Picasso.with(convertView.getContext()).load(organization.getOrgPicture()).fit().into(holder.OrganizationImage);

        // Setting event name
        if(organization.getOrgName().length() > 23){
            holder.OrganizationTitle.setText(organization.getOrgName().substring(0,23) + "...");
        }
        else{
            holder.OrganizationTitle.setText(organization.getOrgName());
        }




        //Swipe methods being Implemented
        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.leftSwipe));

        //Setting the font
        Typeface opensansregfnt = Typeface.createFromAsset(mActivity.getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface domboldfnt = Typeface.createFromAsset(mActivity.getAssets(),"fonts/Domine-Bold.ttf");

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
        TextView organizationTitle = (TextView) convertView.findViewById(R.id.orgvenName);
        organizationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) adjTopText);
        organizationTitle.setTypeface(opensansregfnt);
        organizationTitle.setTypeface(domboldfnt);
    }

    //Method to set the icon of the event.
    public void setIcon(Organizations organization,ImageView icon) {
        if(organization.getOrgPrimaryCategory().contains("Club"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.club, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Performing"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.performing, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Business"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.business, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Ceremony"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.ceremony, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Tech"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.tech, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Comedy"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.comedy, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Fashion"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.fashion, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Festivals"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.festivals, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Film"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.film, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Food"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.food, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Party"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.party, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Music"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.music, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Political"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.political, 30));}

        else if(organization.getOrgPrimaryCategory().contains("School"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.school, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Sports"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.sports, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Tour"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.tour, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Arts"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.arts, 30));}

        else if(organization.getOrgPrimaryCategory().contains("Social"))
        {icon.setImageDrawable(svgToBitmapDrawable(mActivity.getResources(), R.raw.social, 30));}
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

    @Override
    public int getCount() {
        return mOrganizations.size();  //Returns length of the array of Events
    }

    @Override
    public Organizations getItem(int position) {
        return mOrganizations.get(position);  //Returns the Item being accessed in the the array}
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
