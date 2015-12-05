// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Views;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.bruha.bruha.Adapters.TicketListView;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;

import java.util.ArrayList;


public class MyTicketActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);


        ListView List = (ListView) findViewById(R.id.ticketList);


        ArrayList<Event> Ev = new ArrayList<>();
        Ev.add(new Event());
        Ev.add(new Event());
        Ev.add(new Event());
        Ev.add(new Event());
        Ev.add(new Event());


        TicketListView adapter = new TicketListView(this,Ev);
        List.setAdapter(adapter);
    }
}
