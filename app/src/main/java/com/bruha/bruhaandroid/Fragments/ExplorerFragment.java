package com.bruha.bruhaandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruha.bruhaandroid.Adapters.ListViewAdapter2;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;


public class ExplorerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);


// Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this.getActivity());
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        ArrayList<Event> mEvents = new ArrayList<>();
        mEvents = sqLiteUtils.getEventInfo(dbHelper);
        ObservableListView list = (ObservableListView) view.findViewById(R.id.list);
        list.setAdapter(new ListViewAdapter2(this.getActivity(),mEvents));
        return view;
    }

}