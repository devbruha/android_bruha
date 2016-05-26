package com.bruha.bruhaandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bruha.bruhaandroid.Adapters.ListViewAdapter2;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;


public class ExplorerFragment extends Fragment {

    ArrayList<Event> mEvents;
    View view;
    LinearLayout filter;
    RelativeLayout caaa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment_layout, container, false);

        caaa = (RelativeLayout) view.findViewById(R.id.CategoriesLayout);

        filter = (LinearLayout) getActivity().findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = caaa.getVisibility();

                if(visibility==View.GONE)
                    caaa.setVisibility(View.VISIBLE);

                if(visibility==View.VISIBLE)
                    caaa.setVisibility(View.GONE);
            }
        });

        parseData();
        return view;




    }


    public void parseData() {

        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this.getActivity());
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        mEvents = new ArrayList<>();
        ArrayList<Event> mEvents = new ArrayList<>();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        ObservableListView list = (ObservableListView) view.findViewById(R.id.list);
        list.setAdapter(new ListViewAdapter2(this.getActivity(),mEvents));
    }

}
