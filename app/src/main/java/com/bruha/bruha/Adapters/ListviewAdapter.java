package com.bruha.bruha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruha.R;

/**
 * Created by Work on 2015-05-12.
 */
public class ListviewAdapter extends BaseAdapter {

    private Context mContext;

    //the Constructor for the class
    public ListviewAdapter(Context context)
    {mContext=context;}




    @Override
    public int getCount() {
        return 0;  //Retuns length of the view
    }

    @Override
    public Object getItem(int position) {
        return null;  //Returns the Item being accessed in the view
    }

    @Override
    public long getItemId(int position) {
        return 0;   //Id of the Itel being accessed in the view
    }


    //Gets the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {
            // brand new view if it is null
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.EventPicture= (ImageView) convertView.findViewById(R.id.ImageEventPicture);
            holder.EventIcon = (ImageView) convertView.findViewById(R.id.ImageEventIcon);
            holder.EventName = (TextView) convertView.findViewById(R.id.TextEventName);
            holder.EventDate = (TextView) convertView.findViewById(R.id.TextEventDate);
            holder.EventPrice= (TextView) convertView.findViewById(R.id.TextEventPrice);
            holder.EventDistance= (TextView) convertView.findViewById(R.id.TextEventDistance);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }




        return convertView;
    }




    //A view holder that contain the things that need to be changed everytime
    private static class ViewHolder{
        ImageView EventPicture;
        ImageView EventIcon;
        TextView EventName;
        TextView EventDate;
        TextView EventPrice;
        TextView EventDistance;



    }

}
