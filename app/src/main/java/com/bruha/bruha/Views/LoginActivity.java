package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.MyApplication;
import com.bruha.bruha.PHP.RetrievePHP;
import com.bruha.bruha.Model.SQLiteDatabaseModel;
import com.bruha.bruha.Processing.SQLiteUtils;
import com.bruha.bruha.R;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//TODO Continue with the attempted insertions into the local DB
//TODO Fix the error checked regarding the query injection

public class LoginActivity extends ActionBarActivity {
    //Initialzing the PHP call used to retrieving Data to be stored into the local database.
    RetrievePHP retrievePHP = new RetrievePHP();

    // Injecting the EditTexts using Butterknife library
    @InjectView(R.id.loginUsernameEditText) EditText mLoginUsernameEditText;
    @InjectView(R.id.loginPasswordEditText) EditText mLoginPasswordEditText;

    @InjectView(R.id.loginButton) Button loginButton;
    @InjectView(R.id.continueNotLoggedButton) Button noLoginButton;
    @InjectView(R.id.loginRegisterButton) Button registerButton;


    // Create the local DB object
    SQLiteDatabaseModel dbHelper = new SQLiteDatabaseModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // using ButterKnife.inject to allow the InjectViews to take effect
        ButterKnife.inject(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(loginButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        loginButton.setAlpha(1f);
                        loginAccount(null);
                    }
                });
                animator.start();
            }
        });

        noLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(noLoginButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        noLoginButton.setAlpha(1f);
                        proceedWithoutAccount(null);
                    }
                });
                animator.start();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(registerButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        registerButton.setAlpha(1f);
                        startRegisterActivity(null);
                    }
                });
                animator.start();
            }
        });

    }

    private void startRegisterActivity(View view)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
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

    // A function to run the previous check aswell as to call the PHP class to run the queries
    private String isValidAccountInformation(String username, String password){
        String error;

        if(isNetworkAvailable()) {

            if ( isValidUsername(username) ) {

                if( isValidPassword(password) ) {

                    // Calling the init function within PHP with the parameters passed

                    error= retrievePHP.login(username,password);
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


                //Storing the information of the user and his uploaded events into the local database.
                ArrayList<Event> userEvents= retrievePHP.getUserEventList(username);
                ArrayList<String> userInfo = retrievePHP.getUserInfo(username);
                SQLiteUtils sqLiteUtils = new SQLiteUtils();
                sqLiteUtils.insertUserEvents(dbHelper,userEvents);
                sqLiteUtils.insertNewUser(dbHelper, userInfo);


                // Alerting user of successfull login
                alertUserAboutError(
                        res.getString(R.string.success_title_login),
                        res.getString(R.string.success_message_login));

                // Updating the shared variable login check to true on successful login

                MyApplication.loginCheck = true;

                // Start the next activity right here
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                break;

            default:
                alertUserAboutError("Dev Error"+response, "Error " + response);
                break;

        }
    }


    public void proceedWithoutAccount(View view){

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }

}
