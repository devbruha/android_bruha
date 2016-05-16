package com.bruha.bruhaandroid.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruhaandroid.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends AppCompatActivity {

   // @InjectView(R.id.profilePageTitle) TextView mPageTitle;
    @InjectView(R.id.profilePageAddictionText) TextView mPageAddictionText;
    @InjectView(R.id.profilePageAddictionPic) ImageView mPageAddictionImage;
    @InjectView(R.id.profilePageUploadText) TextView mPageUploadText;
    @InjectView(R.id.profilePageUploadPic) ImageView mPageUploadImage;
    @InjectView(R.id.profilePageUserName) TextView mPageUserName;
    @InjectView(R.id.profilePageUserEmail) TextView mPageUserEmail;
    @InjectView(R.id.profilePageUserPic) ImageView mPageUserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
    }






}
