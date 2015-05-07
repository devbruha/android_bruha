package com.bruha.bruha.Views;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.bruha.bruha.Processing.SQLUtils;
import com.bruha.bruha.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends ActionBarActivity {

    // Our database hostname and the credentials for our showdom_android account

    String url = "jdbc:mysql://66.147.244.109:3306/showdomc_web2"; //
    String user = "showdomc_android";
    String pass = "show12345!";

    String task = "login";

    // Injecting the EditTexts using Butterknife library

    @InjectView(R.id.loginUsernameEditText) EditText mLoginUsernameEditText;
    @InjectView(R.id.loginPasswordEditText) EditText mLoginPasswordEditText;

    // Injecting the Buttons using Butterknife library

    @InjectView(R.id.loginButton) Button mLoginButton;
    @InjectView(R.id.continueNotLoggedButton) Button mNoLoginButton;

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
            if( username.matches("[a-zA-Z]+") ||
                    username.contains("0") ||
                    username.contains("1") ||
                    username.contains("2") ||
                    username.contains("3") ||
                    username.contains("4") ||
                    username.contains("5") ||
                    username.contains("6") ||
                    username.contains("7") ||
                    username.contains("8") ||
                    username.contains("9") ||
                    username.contains("_") ){

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
            if( password.matches("[a-zA-Z]+") ||
                    password.contains("0") ||
                    password.contains("1") ||
                    password.contains("2") ||
                    password.contains("3") ||
                    password.contains("4") ||
                    password.contains("5") ||
                    password.contains("6") ||
                    password.contains("7") ||
                    password.contains("8") ||
                    password.contains("9") ||
                    password.contains("_") ){

                isAvailable = true;
            }
        }

        return isAvailable;
    }

    // A function to run the previous check aswell as to call the SQLUtils class to run the
    // queries

    private String isValidAccountInformation(String username, String password){

        String error;

        if(isNetworkAvailable()) {

            if ( isValidUsername(username) ) {

                if( isValidPassword(password) ) {

                    // Calling the init function within SQLUtils with the parameters passed

                    SQLUtils sqlu = new SQLUtils(url, user, pass);
                    error = sqlu.init(username, password, "null", task);
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

                alertUserAboutError(
                        res.getString(R.string.success_title_login),
                        res.getString(R.string.success_message_login));

                // Start the next activity right here

                break;

            default:
                alertUserAboutError("Dev Error"+response, "Error " + response);
                break;
        }
    }

}
