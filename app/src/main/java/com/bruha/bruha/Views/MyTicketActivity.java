package com.bruha.bruha.Views;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bruha.bruha.Adapters.ListviewAdapter;
import com.bruha.bruha.Adapters.TicketListView;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


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


       // Typeface x = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Domine-Regular.ttf");

     //   Typeface fnt = Typeface.createFromAsset(getAssets(),"fonts/Domine-Regular.ttf");






    }







}
