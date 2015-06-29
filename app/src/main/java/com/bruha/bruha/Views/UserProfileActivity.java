package com.bruha.bruha.Views;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruha.R;

public class UserProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


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
        TextView Username = (TextView) findViewById(R.id.username);
        TextView Email = (TextView) findViewById(R.id.email);
        TextView Age = (TextView) findViewById(R.id.age);
        TextView Sex = (TextView) findViewById(R.id.sex);
        TextView Location = (TextView) findViewById(R.id.location);

        TextView UsernameText = (TextView) findViewById(R.id.usernametext);
        TextView EmailText = (TextView) findViewById(R.id.emailtext);
        TextView AgeText = (TextView) findViewById(R.id.agetext);
        TextView SexText = (TextView) findViewById(R.id.sextext);
        TextView LocationText = (TextView) findViewById(R.id.locationtext);

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


}
