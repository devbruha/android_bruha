package com.bruha.bruhaandroid.Fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bruha.bruhaandroid.Adapters.ListViewAdapter2;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;


public class ExplorerFragment extends Fragment {

    ArrayList<Event> mEvents;
    View view;
    LinearLayout filterButton;
    RelativeLayout filterBox;
    int color;

    TableRow category1;
    TableRow category2;
    TableRow category3;
    TableRow category4;
    TableRow category5;
    TableRow category6;
    TableRow category7;
    TableRow category8;
    TableRow category9;
    TableRow category10;
    TableRow category11;
    TableRow category12;
    TableRow category13;
    TableRow category14;
    TableRow category15;
    TableRow category16;


    ImageView categoryim1;
    ImageView categoryim2;
    ImageView categoryim3;
    ImageView categoryim4;
    ImageView categoryim5;
    ImageView categoryim6;
    ImageView categoryim7;
    ImageView categoryim8;
    ImageView categoryim9;
    ImageView categoryim10;
    ImageView categoryim11;
    ImageView categoryim12;
    ImageView categoryim13;
    ImageView categoryim14;
    ImageView categoryim15;
    ImageView categoryim16;

    TextView categorytxt1;
    TextView categorytxt2;
    TextView categorytxt3;
    TextView categorytxt4;
    TextView categorytxt5;
    TextView categorytxt6;
    TextView categorytxt7;
    TextView categorytxt8;
    TextView categorytxt9;
    TextView categorytxt10;
    TextView categorytxt11;
    TextView categorytxt12;
    TextView categorytxt13;
    TextView categorytxt14;
    TextView categorytxt15;
    TextView categorytxt16;
    ArrayList<String> categories = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment_layout, container, false);

        filterSetup();

        parseData();
        setcategories();
        seteventviews();
        return view;




    }

    private void filterSetup() {
        filterBox = (RelativeLayout) view.findViewById(R.id.CategoriesLayout);
        filterButton = (LinearLayout) getActivity().findViewById(R.id.filterButton);
        filterBox.setVisibility(View.GONE);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = filterBox.getVisibility();

                if(visibility==View.GONE)
                    filterBox.setVisibility(View.VISIBLE);

                if(visibility==View.VISIBLE)
                    filterBox.setVisibility(View.GONE);
            }
        });
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

    int c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16 = 0;

    private void seteventviews() {
        categorytxt1.setText("Art");

        categoryim1.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.arts, 30));
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1 == 0){
                    category1.setBackgroundColor(Color.parseColor("#24163f "));
                    c1++;
                }
                else{
                    category1.setBackgroundColor(Color.parseColor("#e146287f"));
                    c1--;
                }
                categories.add("Art");
            }
        });

        categorytxt2.setText("Business");
        categoryim2.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.business, 30));
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c2 == 0){
                    category2.setBackgroundColor(Color.parseColor("#24163f "));
                    c2++;
                }
                else{
                    category2.setBackgroundColor(Color.parseColor("#e146287f"));
                    c2--;
                }
                categories.add("Business");
            }
        });

        categorytxt3.setText("Music");
        categoryim3.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.music, 30));
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c3== 0){
                category3.setBackgroundColor(Color.parseColor("#24163f "));
                c3++;
            }
            else{
                category3.setBackgroundColor(Color.parseColor("#e146287f"));
                c3--;
            }
                categories.add("Music");
            }
        });

        categorytxt4.setText("Fashion & Beauty");
        categoryim4.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.fashion, 30));
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c4 == 0){
                category4.setBackgroundColor(Color.parseColor("#24163f "));
                c4++;
            }
            else{
                category4.setBackgroundColor(Color.parseColor("#e146287f"));
                c4--;
            }
                categories.add("Fashion & Beauty");
            }
        });

        categorytxt5.setText("Party");
        categoryim5.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.party, 30));
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c5 == 0){
                    category4.setBackgroundColor(Color.parseColor("#24163f "));
                    c5++;
                }
                else{
                    category5.setBackgroundColor(Color.parseColor("#e146287f"));
                    c5--;
                }
                categories.add("Party");
            }
        });

        categorytxt6.setText("Comedy");
        categoryim6.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.comedy, 30));
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c6 == 0){
                category6.setBackgroundColor(Color.parseColor("#24163f "));
                c6++;
            }
            else{
                category6.setBackgroundColor(Color.parseColor("#e146287f"));
                c6--;
            }
                categories.add("Comedy");
            }
        });

        categorytxt7.setText("Sports Fitness");
        categoryim7.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.sports, 30));
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c7 == 0){
                category7.setBackgroundColor(Color.parseColor("#24163f "));
                c7++;
            }
            else{
                category7.setBackgroundColor(Color.parseColor("#e146287f"));
                c7--;
            }
                categories.add("Sports Fitness");
            }
        });

        categorytxt8.setText("Performing Arts");
        categoryim8.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.performing, 30));
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1 == 0){
                    category8.setBackgroundColor(Color.parseColor("#24163f "));
                    c8++;
                }
                else{
                    category8.setBackgroundColor(Color.parseColor("#e146287f"));
                    c8--;
                }
                categories.add("Performing Arts");
            }
        });


        categorytxt9.setText("Social");
        categoryim9.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.social, 30));
        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c9 == 0){
                    category9.setBackgroundColor(Color.parseColor("#24163f "));
                    c9++;
                }
                else{
                    category9.setBackgroundColor(Color.parseColor("#e146287f"));
                    c9--;
                }
                categories.add("Social");
            }
        });


        categorytxt10.setText("Festival");
        categoryim10.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.festivals, 30));
        category10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c10 == 0){
                    category10.setBackgroundColor(Color.parseColor("#24163f "));
                    c10++;
                }
                else{
                    category10.setBackgroundColor(Color.parseColor("#e146287f"));
                    c10--;
                }
                categories.add("Festival");
            }
        });

        categorytxt11.setText("School Event");
        categoryim11.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.school, 30));
        category11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c12 == 0){
                    category11.setBackgroundColor(Color.parseColor("#24163f "));
                    c11++;
                }
                else{
                    category11.setBackgroundColor(Color.parseColor("#e146287f"));
                    c11--;
                }
                categories.add("School Event");
            }
        });

        categorytxt12.setText("Tech");
        categoryim12.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.tech, 30));
        category12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c12 == 0){
                    category12.setBackgroundColor(Color.parseColor("#24163f "));
                    c12++;
                }
                else{
                    category12.setBackgroundColor(Color.parseColor("#e146287f"));
                    c12--;
                }
                categories.add("Tech");
            }
        });


        categorytxt13.setText("Food & Drink");
        categoryim13.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.food, 30));
        category13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c13 == 0){
                    category13.setBackgroundColor(Color.parseColor("#24163f "));
                    c1++;
                }
                else{
                    category13.setBackgroundColor(Color.parseColor("#e146287f"));
                    c13--;
                }
                categories.add("Food & Drink");
            }
        });

        categorytxt14.setText("Film");
        categoryim14.setImageDrawable(svgToBitmapDrawable(this.getResources(), R.raw.film, 30));
        category14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1 == 0){
                    category14.setBackgroundColor(Color.parseColor("#24163f "));
                    c14++;
                }
                else{
                    category14.setBackgroundColor(Color.parseColor("#e146287f"));
                    c14--;
                }
                categories.add("Film");
            }
        });
    }

    private void setcategories() {
        category1= (TableRow) view.findViewById(R.id.Category1);
        categoryim1= (ImageView) view.findViewById(R.id.CategoryPicture1);
        categorytxt1 =(TextView) view.findViewById(R.id.CategoryText1);

        category2= (TableRow) view.findViewById(R.id.Category2);
        categoryim2= (ImageView) view.findViewById(R.id.CategoryPicture2);
        categorytxt2 =(TextView) view.findViewById(R.id.CategoryText2);

        category3= (TableRow) view.findViewById(R.id.Category3);
        categoryim3= (ImageView) view.findViewById(R.id.CategoryPicture3);
        categorytxt3 =(TextView) view.findViewById(R.id.CategoryText3);

        category4= (TableRow) view.findViewById(R.id.Category4);
        categoryim4= (ImageView) view.findViewById(R.id.CategoryPicture4);
        categorytxt4 =(TextView) view.findViewById(R.id.CategoryText4);

        category5= (TableRow) view.findViewById(R.id.Category5);
        categoryim5= (ImageView) view.findViewById(R.id.CategoryPicture5);
        categorytxt5 =(TextView) view.findViewById(R.id.CategoryText5);

        category6= (TableRow) view.findViewById(R.id.Category6);
        categoryim6= (ImageView) view.findViewById(R.id.CategoryPicture6);
        categorytxt6 =(TextView) view.findViewById(R.id.CategoryText6);

        category7= (TableRow) view.findViewById(R.id.Category7);
        categoryim7= (ImageView) view.findViewById(R.id.CategoryPicture7);
        categorytxt7 =(TextView) view.findViewById(R.id.CategoryText7);

        category8= (TableRow) view.findViewById(R.id.Category8);
        categoryim8= (ImageView) view.findViewById(R.id.CategoryPicture8);
        categorytxt8 =(TextView) view.findViewById(R.id.CategoryText8);

        category9= (TableRow) view.findViewById(R.id.Category9);
        categoryim9= (ImageView) view.findViewById(R.id.CategoryPicture9);
        categorytxt9 =(TextView) view.findViewById(R.id.CategoryText9);

        category10= (TableRow) view.findViewById(R.id.Category10);
        categoryim10= (ImageView) view.findViewById(R.id.CategoryPicture10);
        categorytxt10 =(TextView) view.findViewById(R.id.CategoryText10);

        category11= (TableRow) view.findViewById(R.id.Category11);
        categoryim11= (ImageView) view.findViewById(R.id.CategoryPicture11);
        categorytxt11 =(TextView) view.findViewById(R.id.CategoryText11);

        category12= (TableRow) view.findViewById(R.id.Category12);
        categoryim12= (ImageView) view.findViewById(R.id.CategoryPicture12);
        categorytxt12 =(TextView) view.findViewById(R.id.CategoryText12);

        category13= (TableRow) view.findViewById(R.id.Category13);
        categoryim13= (ImageView) view.findViewById(R.id.CategoryPicture13);
        categorytxt13 =(TextView) view.findViewById(R.id.CategoryText13);

        category14= (TableRow) view.findViewById(R.id.Category14);
        categoryim14= (ImageView) view.findViewById(R.id.CategoryPicture14);
        categorytxt14 =(TextView) view.findViewById(R.id.CategoryText14);

        category15= (TableRow) view.findViewById(R.id.Category15);
        categoryim15= (ImageView) view.findViewById(R.id.CategoryPicture15);
        categorytxt15 =(TextView) view.findViewById(R.id.CategoryText15);

        category16= (TableRow) view.findViewById(R.id.Category16);
        categoryim16= (ImageView) view.findViewById(R.id.CategoryPicture16);
        categorytxt16 =(TextView) view.findViewById(R.id.CategoryText16);
    }

    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getActivity().getApplicationContext(), resource);

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

}
