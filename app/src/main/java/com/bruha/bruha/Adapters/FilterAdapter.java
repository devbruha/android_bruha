package com.bruha.bruha.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bruha.bruha.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Thomas on 5/14/2015.
 */
public class FilterAdapter extends BaseExpandableListAdapter{

    private Context context;
    private HashMap<String, List<String>> Quickie_Fields;
    private List<String> Quickie_List;

    public FilterAdapter(Context context, HashMap<String, List<String>> Quickie_Fields, List<String> Quickie_List){

        this.context = context;
        this.Quickie_Fields = Quickie_Fields;
        this.Quickie_List = Quickie_List;
    }

    @Override
    public int getGroupCount() {
        return Quickie_List.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Quickie_Fields.get(Quickie_List.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Quickie_List.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {

        return Quickie_Fields.get(Quickie_List.get(parent))
                .get(child);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int parent, int child) {

        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {

        String group_title = (String) getGroup(parent);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, parentView, false);
        }

        TextView parentTextView = (TextView) convertView.findViewById(R.id.parent_text);
        parentTextView.setTypeface(null, Typeface.BOLD);
        parentTextView.setText(group_title);

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean isLastChild, View convertView,
                             ViewGroup parentView) {

        String child_title = (String) getChild(parent, child);

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, parentView, false);
        }

        TextView child_textview = (TextView) convertView.findViewById(R.id.child_text);

        child_textview.setText(child_title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
