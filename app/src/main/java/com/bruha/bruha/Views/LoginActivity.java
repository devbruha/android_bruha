package com.bruha.bruha.Views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.PHP.RetrieveEvents;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//TODO Continue with the attempted insertions into the local DB
//TODO Fix the error checked regarding the query injection

public class LoginActivity extends ActionBarActivity {

    // Our database hostname and the credentials for our showdom_android account

    RetrieveEvents Call = new RetrieveEvents(this);


    // Injecting the EditTexts using Butterknife library

    @InjectView(R.id.loginUsernameEditText) EditText mLoginUsernameEditText;
    @InjectView(R.id.loginPasswordEditText) EditText mLoginPasswordEditText;

    // Injecting the Buttons using Butterknife library

    @InjectView(R.id.loginButton) Button mLoginButton;
    @InjectView(R.id.continueNotLoggedButton) Button mNoLoginButton;

    // Create the local DB object

    SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // using ButterKnife.inject to allow the InjectViews to take effect

        ButterKnife.inject(this);
    }

    // A function to call the AlertDialogFragment Activity

    private void alertUserAboutError(String errorTitle, String errorMessage) {

        AlertDialogFragment dialog = new AlertDialogFragment().newInstance( errorTitle,errorMessage );
        dialog.show(getFragmentManager(), "error_dialog");
    }

    // A function used to check whether users are connected to the internet

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // boolean variable initialized to false, set true if there is a connection

        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){

            isAvailable = true;
        }

        return isAvailable;
    }

    // A function used to check whether users are entering a valid username

    private boolean isValidUsername(String username){

        boolean isAvailable = false;

        int length = username.length();

        // Ensure the proper length and legal characters to prevent query injecting

        if( length >= 6 && length <= 20 ){
            if( username.matches("^[a-zA-Z0-9_]*$")){

                isAvailable = true;
            }
        }

        return isAvailable;
    }

    // A function used to check whether users are entering a valid password

    private boolean isValidPassword(String password){

        boolean isAvailable = false;

        int length = password.length();

        // Ensure the proper length and legal characters to prevent query injecting

        if( length >= 6 && length <= 20 ){
            if( password.matches("^[a-zA-Z0-9_]*$")){

                isAvailable = true;
            }
        }

        return isAvailable;
    }

    // A function to run the previous check aswell as to call the PHP class to run the
    // queries

    private String isValidAccountInformation(String username, String password){

        String error;

        if(isNetworkAvailable()) {

            if ( isValidUsername(username) ) {

                if( isValidPassword(password) ) {

                    // Calling the init function within PHP with the parameters passed

                    error= Call.login(username,password);
                }
                // If password is invalid, error string is updated

                else{
                    error = "passwordInvalid";
                }
            }
            // If username is invalid, error string is updated

            else {
                error = "usernameInvalid";
            }
        }
        // If a connection cannot be established, update error string

        else{
            error = "connectionInvalid";
        }

        return error;
    }

    @OnClick(R.id.loginButton)
    public void loginAccount(View view){

        // Retrieving the entered information and converting to string

        String username = mLoginUsernameEditText.getText().toString();
        String password = mLoginPasswordEditText.getText().toString();

        // Importing ressources in order to reference stored string values

        Resources res = getResources();

        String response = isValidAccountInformation(username, password);

        // A message is displayed to the user corresponding to the response received

        switch(response){

            case "connectionInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_login),
                        res.getString(R.string.error_message_no_connection));
                break;

            case "usernameInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_login),
                        res.getString(R.string.error_message_username_non_existent));
                break;

            case "passwordInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_login),
                        res.getString(R.string.error_message_password_non_existent));
                break;

            case "1064":
                alertUserAboutError(
                        res.getString(R.string.error_title_bad_SQL),
                        res.getString(R.string.error_message_bad_SQL));
                break;

            case "badCredentials":
                alertUserAboutError(
                        res.getString(R.string.error_bad_login),
                        res.getString(R.string.error_message_credential_mismatch));
                break;

            case "Success":






                ArrayList<Event> userEvents=Call.GetUserEventList(username);
                ArrayList<String> userInfo = Call.GetUserInfo(username);


                SQLiteUtils sqLiteUtils = new SQLiteUtils();
                sqLiteUtils.insertUserEvents(dbHelper,userEvents);
                sqLiteUtils.insertNewUser(dbHelper, userInfo);



               /*
               // Storing the user_info value into a list

                List<String> user_info = sqlu.loginDatabasePush(username);
                ArrayList<Event> userEvents = sqlu.userEvents();

                // Passing the newly created list and SQLite DB to next class to perform the
                // insertion into the local DB

                SQLiteUtils sqLiteUtils = new SQLiteUtils();

                sqLiteUtils.insertNewUser(dbHelper, user_info);
                sqLiteUtils.getUserInfo(dbHelper);

                sqLiteUtils.insertUserEvents(dbHelper,userEvents);
                */

                // Alerting user of successfull login
                alertUserAboutError(
                        res.getString(R.string.success_title_login),
                        res.getString(R.string.success_message_login));

                // Updating the shared variable login check to true on successful login

                MyApplication.loginCheck = true;

                // Start the next activity right here
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);

                break;

            default:
                alertUserAboutError("Dev Error"+response, "Error " + response);
                break;

        }
    }

    @OnClick(R.id.continueNotLoggedButton)
    public void proceedWithoutAccount(View view){

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }

}
