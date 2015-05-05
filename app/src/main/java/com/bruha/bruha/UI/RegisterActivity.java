package com.bruha.bruha.UI;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruha.bruha.Database.SQLUtils;
import com.bruha.bruha.R;

import javax.xml.datatype.Duration;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends ActionBarActivity {

    // Our database hostname and the credentials for our showdom_android account

    String url = "jdbc:mysql://66.147.244.109:3306/showdomc_web2"; //
    String user = "showdomc_android";
    String pass = "show12345!";

    // Injecting the EditTexts using Butterknife library

    @InjectView(R.id.registerUsernameEditText) EditText mRegisterUsernameEditText;
    @InjectView(R.id.registerPasswordEditText) EditText mRegisterPasswordEditText;
    @InjectView(R.id.registerEmailEditText) EditText mRegisterEmailEditText;

    // Injecting the Buttons using Butterknife library

    @InjectView(R.id.createAccountButton) Button mCreateAccountButton;
    @InjectView(R.id.continueNotRegisteredButton) Button mNoRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // using ButterKnife.inject to allow the InjectViews to take effect

        ButterKnife.inject(this);

    }

    // A function that shall be used to check that all fields are non-empty

    public boolean isEntryFieldEmpty(String username, String password, String email){

        //Setting a signal to return true / false

        // Init signal to false
        boolean signal = false;

        // If either of the fields are empty signal is set true
        // and toast is made indicating the error

        if(username != null || password != null || email != null){

            signal = true;

            Toast toast = Toast.makeText(this,
                    "Error: Please ensure all fields are filled in.",
                    Toast.LENGTH_SHORT);

            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        return signal;
    }

    // Setting the listeners for the choice the user is to make on button click

    @OnClick(R.id.createAccountButton)
    public void createAccount(View view){

        // Retrieving the entered information and converting to string

        String username = mRegisterUsernameEditText.getText().toString();
        String password = mRegisterPasswordEditText.getText().toString();
        String email = mRegisterEmailEditText.getText().toString();


        // Checking to ensure that all fields are non empty

        if( !isEntryFieldEmpty(username, password, email) ){

            // Calling the init function within SQLUtils with the parameters passed

            SQLUtils sqlu = new SQLUtils(url,user,pass);
            sqlu.init(username,password,email);

        }
    }

    @OnClick(R.id.continueNotRegisteredButton)
    public void proceedWithoutAccount(View view){

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

    }
}
