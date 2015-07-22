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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bruha.bruha.PHP.RetrievePHP;
import com.bruha.bruha.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends ActionBarActivity {
    // Injecting the EditTexts using Butterknife library
    @InjectView(R.id.registerUsernameEditText) EditText mRegisterUsernameEditText;
    @InjectView(R.id.registerPasswordEditText) EditText mRegisterPasswordEditText;
    @InjectView(R.id.registerEmailEditText) EditText mRegisterEmailEditText;

    //Injevting the Buttons:
    @InjectView(R.id.createAccountButton) Button registerButton;
    @InjectView(R.id.continueNotRegisteredButton) Button noRegisterButton;
    @InjectView(R.id.registerLoginButton) Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // using ButterKnife.inject to allow the InjectViews to take effect
        ButterKnife.inject(this);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(registerButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        registerButton.setAlpha(1f);
                        createAccount(null);
                    }
                });
                animator.start();
            }
        });

        noRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(noRegisterButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        noRegisterButton.setAlpha(1f);
                        proceedWithoutAccount(null);
                    }
                });
                animator.start();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(loginButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        loginButton.setAlpha(1f);
                        startLoginActivity(null);
                    }
                });
                animator.start();
            }
        });


    }

   private void startLoginActivity(View view)
   {
       Intent intent = new Intent(this,LoginActivity.class);
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

    // A function used to check whether users are entering a valid email address
    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
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

    // A function to run the previous check aswell as to call the PHP class to run the queries.
    private String isValidAccountInformation(String username, String password, String email){

        String error;

        if(isNetworkAvailable()) {

            if ( isValidUsername(username) ) {

                if( isValidPassword(password) ) {

                    if (isValidEmail(email)) {

                        //error="Success";

                        // Calling the init function within PHP with the parameters passed

                        error="Success";
                        RetrievePHP retrievePHP = new RetrievePHP();
                        retrievePHP.register(username, password, email);



                    }
                    // If email is invalid, error string is updated

                    else {
                        error = "emailInvalid";
                    }
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

    // Setting the listeners for the choice the user is to make on button click

    public void createAccount(View view){

        // Retrieving the entered information and converting to string.
        String username = mRegisterUsernameEditText.getText().toString();
        String password = mRegisterPasswordEditText.getText().toString();
        String email = mRegisterEmailEditText.getText().toString();

        // Importing ressources in order to reference stored string values

        Resources res = getResources();

        String response = isValidAccountInformation(username, password, email);

        // A message is displayed to the user corresponding to the response received
        switch(response){

            case "connectionInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        res.getString(R.string.error_message_no_connection));
                break;

            case "usernameInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        res.getString(R.string.error_message_invalid_username));
                break;

            case "passwordInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        res.getString(R.string.error_message_invalid_password));
                break;

            case "emailInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        res.getString(R.string.error_message_invalid_email));
                break;

            case "1062":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        res.getString(R.string.error_message_username_taken));
                break;

            case "1064":
                alertUserAboutError(
                        res.getString(R.string.error_title_bad_SQL),
                        res.getString(R.string.error_message_bad_SQL));
                break;

            case "Success":
                alertUserAboutError(
                        res.getString(R.string.success_title_account_created),
                        res.getString(R.string.success_message_account_created));

                // Start the next activity here after the message

                break;

            default:
                alertUserAboutError(response, "Error " + response);
                break;
        }
    }

    @OnClick(R.id.continueNotRegisteredButton)
    public void proceedWithoutAccount(View view){

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }
}
