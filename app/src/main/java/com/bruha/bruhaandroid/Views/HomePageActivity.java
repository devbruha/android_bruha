package com.bruha.bruhaandroid.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bruha.bruhaandroid.Adapters.ListViewAdapter2;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomePageActivity extends AppCompatActivity {

    ArrayList<Event> mEvents = new ArrayList<>();
    @InjectView(android.R.id.list) ObservableListView listView;

    // Buttons to be implemented
    // Top Buttons
    ImageView filterButton;
    ImageView mapButton;
    //Bottom Buttons
    ImageView homeButton;
    ImageView calenderButton;
    ImageView discoverableButton;
    ImageView ticketsButton;
    ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.inject(this);

        importingData();

        parseData();
    }

    private void importingData() {

        assigningImages();
        settingImages();
    }



    private void assigningImages() {
        // top images
        filterButton = (ImageView) findViewById(R.id.filterIcon);
        mapButton = (ImageView) findViewById(R.id.mapIcon);
        // bottom images
        homeButton = (ImageView) findViewById(R.id.homeIcon);
        calenderButton = (ImageView) findViewById(R.id.calendarIcon);
        discoverableButton = (ImageView) findViewById(R.id.discoverableIcon);
        ticketsButton = (ImageView) findViewById(R.id.ticketsIcon);
        profileButton = (ImageView) findViewById(R.id.profileIcon);
    }

    private void settingImages() {
        int buttonResolution = 50;
        // top images
        //filterButton.setImageDrawable(;
        mapButton.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.mapicon, buttonResolution));
        // bottom images
        homeButton.setImageDrawable(svgToBitmapDrawable(getResources(),R.raw.explore, buttonResolution));
        calenderButton.setImageDrawable(svgToBitmapDrawable(getResources(),R.raw.calendar, buttonResolution));
        discoverableButton.setImageDrawable(svgToBitmapDrawable(getResources(),R.raw.bruhawhite, buttonResolution));
        ticketsButton.setImageDrawable(svgToBitmapDrawable(getResources(),R.raw.tickets, buttonResolution));
        profileButton.setImageDrawable(svgToBitmapDrawable(getResources(),R.raw.profile, buttonResolution));
    }

    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

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

    public void showDialog()
    {   //Alert dialog to let the user know discoverable is coming soon..
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("Discoverable Coming Soon!");
        builder.setCancelable(true);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void parseData() {
        // Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        mEvents = sqLiteUtils.getEventInfo(dbHelper);

        listView.setAdapter(new ListViewAdapter2(this, mEvents));
    }
}
