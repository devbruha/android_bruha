package com.bruha.bruhaandroid.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruhaandroid.R;

import java.util.List;

/**
 * Created by Billy on 2016-05-18.
 */
public class CustomArrayAdapter extends ArrayAdapter<String>{

    private List<String> objects;
    private Context context;

    public CustomArrayAdapter(Context context,int resourceId, List<String> objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    /*
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // View view = super.getView(position, convertView, parent);

        TextView text = (TextView) convertView.findViewById(R.id.meow1);
        if (position == 0) {
            text.setText("Events");
        }
        if (position == 1) {
            text.setText("Venues");
        }
        if (position == 2) {
            text.setText("Organizations");
        }

        return convertView;


    }
    */

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.simple_spinner_item, parent, false);
        TextView label=(TextView)row.findViewById(R.id.meow1);
        label.setText(objects.get(position));
        label.setTextColor(Color.parseColor("#ffffff"));

        /*
        if(position==0)
        {
            ImageView x = (ImageView) row.findViewById(R.id.arrowIcon);
            x.setVisibility(View.VISIBLE);
        }

*/
        return row;
    }
}