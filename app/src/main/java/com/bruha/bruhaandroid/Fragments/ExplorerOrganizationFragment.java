package com.bruha.bruhaandroid.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.bruha.bruhaandroid.Adapters.ListViewAdapter2;
import com.bruha.bruhaandroid.Adapters.OrgListViewAdapter2;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.SQLiteDatabaseModel;
import com.bruha.bruhaandroid.Processing.SQLiteUtils;
import com.bruha.bruhaandroid.R;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;

/**
 * Created by ArhamRazaMac on 16-05-19.
 */
public class ExplorerOrganizationFragment extends Fragment{
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

    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment_layout, container, false);

        setcategories();
        setorganizationviews();
// Create the local DB object
        SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this.getActivity());
        SQLiteUtils sqLiteUtils = new SQLiteUtils();

        ArrayList<Organizations> mOrganizations = new ArrayList<>();
        mOrganizations = sqLiteUtils.getOrganizationsInfo(dbHelper);
        ObservableListView list = (ObservableListView) view.findViewById(R.id.list);
        list.setAdapter(new OrgListViewAdapter2(this.getActivity(),mOrganizations)); // add organization adapters
        return view;
    }

    private void setorganizationviews() {

        categorytxt1.setText("Spiritual");
        //categoryim1.setimage....
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category1.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Spiritual");
            }
        });

        categorytxt2.setText("Fashion");
        //categoryim2.setimage....
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category1.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Fashion");
            }
        });

        categorytxt3.setText("Fraternity");
        //categoryim3.setimage....
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category1.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Fraternity");
            }
        });

        categorytxt4.setText("Student");
        //categoryim1.setimage....
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category4.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Student");
            }
        });

        categorytxt5.setText("Sport");
        //categoryim1.setimage....
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category5.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Sport");
            }
        });

        categorytxt6.setText("Business");
        //categoryim1.setimage....
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category6.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Business");
            }
        });

        categorytxt7.setText("Charity");
        //categoryim1.setimage....
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category7.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Charity");
            }
        });

        categorytxt8.setText("Not For Profit");
        //categoryim1.setimage....
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category8.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Not For Profit");
            }
        });


        categorytxt9.setText("Academic");
        //categoryim1.setimage....
        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category9.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Academic");
            }
        });


        categorytxt10.setText("Promoter");
        //categoryim1.setimage....
        category10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category10.setBackgroundColor(Color.parseColor("#24163f"));
                categories.add("Promoter");
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


}
