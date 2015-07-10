package com.bruha.bruha.Views;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends ActionBarActivity {

    ArrayList<String> UserInfo=new ArrayList<>();
    SQLiteDatabaseModel dbHelper;
    SQLiteUtils sqLiteUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.inject(this);



        dbHelper = new SQLiteDatabaseModel(this);
        sqLiteUtils = new SQLiteUtils();

        init();

        resize();



    }



    private void resize()
    {
        //The EventPage dimensions being set to fit all types of screen:

        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable.
        int height = size.y;


        //Assigning the PageEventPicture to a variable to alter its dimensions after with.
        ImageView UserPicture = (ImageView) findViewById(R.id.UserPicture);
        ViewGroup.LayoutParams EventPictureParams = UserPicture.getLayoutParams();
        EventPictureParams.height =  (int)Math.round(height*.25);
        EventPictureParams.width  =  (int)Math.round(height*.25);


        ImageView Info = (ImageView) findViewById(R.id.InfoBox);
        ViewGroup.LayoutParams InfoParam = Info.getLayoutParams();
        InfoParam.height = (int)Math.round(height*.40);
        InfoParam.width = (int)Math.round(height*.50);

        TextView Name = (TextView) findViewById(R.id.Name);
        Name.setText(UserInfo.get(1));
        TextView Username = (TextView) findViewById(R.id.username);
        TextView Email = (TextView) findViewById(R.id.email);
        TextView Age = (TextView) findViewById(R.id.age);
        TextView Sex = (TextView) findViewById(R.id.sex);
        TextView Location = (TextView) findViewById(R.id.location);

        TextView UsernameText = (TextView) findViewById(R.id.usernametext);
        UsernameText.setText(UserInfo.get(0));
        TextView EmailText = (TextView) findViewById(R.id.emailtext);
        EmailText.setText(UserInfo.get(4));
        TextView AgeText = (TextView) findViewById(R.id.agetext);
        AgeText.setText(UserInfo.get(2));
        TextView SexText = (TextView) findViewById(R.id.sextext);
        SexText.setText(UserInfo.get(3));
        TextView LocationText = (TextView) findViewById(R.id.locationtext);
        LocationText.setText(UserInfo.get(5));

        int x= (int)Math.round(height * .0275);
        int x1= (int)Math.round(height * .0485);

        Name.setTextSize(TypedValue.COMPLEX_UNIT_PX,x1);
        Username.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        Email.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        Age.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        Sex.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        Location.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);

        UsernameText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        EmailText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        AgeText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        SexText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);
        LocationText.setTextSize(TypedValue.COMPLEX_UNIT_PX,x);


    }

    private void init() {
        UserInfo=sqLiteUtils.getUserInfo(dbHelper);
    }

    @OnClick(R.id.logouty)
    public void Logout(View view)
    {
        MyApplication.loginCheck = false;
        dbHelper.onLogout(dbHelper.getWritableDatabase(),1,1);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }



}
