package com.bruha.bruha.UI;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bruha.bruha.Database.SQLUtils;
import com.bruha.bruha.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SplashActivity extends ActionBarActivity {

    // Injecting the Buttons using Butterknife library

    @InjectView(R.id.loginButton) Button mLoginButton;
    @InjectView(R.id.noLoginButton) Button mNoLoginButton;
    @InjectView(R.id.registerButton) Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // using ButterKnife.inject to allow the InjectViews to take effect

        ButterKnife.inject(this);

    }

    // Setting the listeners for the choice the user is to make on button click

    @OnClick(R.id.loginButton)
    public void login(View view){

        // Proceed to the Login Activity

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.registerButton)
    public void register(View view){

        //Proceed to the Register Activity

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.noLoginButton)
    public void noLogin(View view){

        //Shall proceed to the list activity NOT signed in or registered

        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);

    }
}
