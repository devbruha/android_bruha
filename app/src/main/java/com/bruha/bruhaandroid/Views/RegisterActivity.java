// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.
// RegisterActivity - Activity for user to register and sign up for account

package com.bruha.bruhaandroid.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruhaandroid.Model.MyApplication;
import com.bruha.bruhaandroid.Processing.CredentialsPHP;
import com.bruha.bruhaandroid.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends ActionBarActivity {
    // Injecting the EditTexts using Butterknife library
    @InjectView(R.id.registerUsernameEditText) EditText mRegisterUsernameEditText;
    @InjectView(R.id.registerPasswordEditText) EditText mRegisterPasswordEditText;
    @InjectView(R.id.registerEmailEditText) EditText mRegisterEmailEditText;
    @InjectView(R.id.registerEmailTextView) TextView mRegisterEmailTextView;
    @InjectView(R.id.registerUsernameTextView) TextView mRegisterUsernameTextView;
    @InjectView(R.id.registerPasswordTextView) TextView mRegisterPasswordTextView;
    @InjectView(R.id.reenterregisterPasswordEditText) EditText mRegisterReenterPasswordEditText;
    @InjectView(R.id.reenterregisterPasswordTextView) TextView mRegisterReenterPasswordTextView;

    //Injevting the Buttons:
    @InjectView(R.id.createAccountButton) Button registerButton;
    @InjectView(R.id.continueNotRegisteredButton) Button noRegisterButton;
    @InjectView(R.id.backtoSplashButton) Button mBacktoSplashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // using ButterKnife.inject to allow the InjectViews to take effect
        ButterKnife.inject(this);

        //Setting the OnClickListeners for all the buttons inside the page.
        implementOnClicks();

        resize(); //Calling the method that resizes everything inside the page.
    }

    private void implementOnClicks() {
        //Setting the bruha face inside the Layout.
        ImageView im = (ImageView) findViewById(R.id.registerbruhaface);
        im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhapurpleface, 100));

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
        mBacktoSplashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mBacktoSplashButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        mBacktoSplashButton.setAlpha(1f);
                        startBackActivity(null);
                    }
                });
                animator.start();
            }
        });
    }

    private String isValidAccountInformation(String username, String password, String email, String Reenterpassword){
        // A function to run the previous check aswell as to call the PHP class to run the queries.
        String error;

        if(isNetworkAvailable()) {

            if ( isValidUsername(username) ) {

                if( isValidPassword(password) ) {

                    if( isReenterPasswordMatches(password, Reenterpassword) ) {

                            if (isValidEmail(email)) {
                                // Calling the init function within PHP with the parameters passed
                                CredentialsPHP credentialsPHP = new CredentialsPHP();
                                error = credentialsPHP.register(username, email, password);
                            }
                            // If email is invalid, error string is updated

                            else {
                                error = "emailInvalid";
                            }
                     }

                    else{
                        error = "unmatchedPassword";
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

    public void createAccount(View view){
        // Setting the listeners for the choice the user is to make on button click


        // Retrieving the entered information and converting to string.
        String username = mRegisterUsernameEditText.getText().toString();
        String password = mRegisterPasswordEditText.getText().toString();
        String Reenterpassword = mRegisterReenterPasswordEditText.getText().toString();
        String email = mRegisterEmailEditText.getText().toString();

        // Importing ressources in order to reference stored string values

        Resources res = getResources();

        String response = isValidAccountInformation(username, password, email, Reenterpassword);

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
                        MyApplication.credentialError);
                break;

            case "passwordInvalid":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        MyApplication.credentialError);
                break;

            case "unmatchedPassword":
                alertUserAboutError(
                        res.getString(R.string.error_bad_register),
                        MyApplication.credentialError);
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
                alertUserAboutError(response, "Error, " + response);
                break;
        }
    }


    private void resize() {
        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        Typeface opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");

        mRegisterEmailEditText.setTypeface(opensansregfnt);
        mRegisterEmailTextView.setTypeface(opensansregfnt);
        mRegisterPasswordEditText.setTypeface(opensansregfnt);
        mRegisterPasswordTextView.setTypeface(opensansregfnt);
        mRegisterUsernameEditText.setTypeface(opensansregfnt);
        mRegisterUsernameTextView.setTypeface(opensansregfnt);
        mRegisterReenterPasswordEditText.setTypeface(opensansregfnt);
        mRegisterReenterPasswordTextView.setTypeface(opensansregfnt);


        int x= (int)Math.round(height * .024);
        int x1= (int)Math.round(height * .024);
        mRegisterEmailEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        mRegisterEmailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        mRegisterPasswordEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        mRegisterPasswordTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        mRegisterUsernameEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        mRegisterUsernameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        mRegisterReenterPasswordEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        mRegisterReenterPasswordTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);


        ViewGroup.LayoutParams mRegisterUsernameEditTextLayoutParams = mRegisterUsernameEditText.getLayoutParams();
        ViewGroup.LayoutParams mRegisterPasswordEditTextLayoutParams = mRegisterPasswordEditText.getLayoutParams();
        ViewGroup.LayoutParams mRegisterReenterPasswordEditTextLayoutParams = mRegisterReenterPasswordEditText.getLayoutParams();
        ViewGroup.LayoutParams mRegisterEmailEditTextLayoutParams = mRegisterEmailEditText.getLayoutParams();
        mRegisterUsernameEditTextLayoutParams.height =  (int)Math.round(height*.04);
        mRegisterPasswordEditTextLayoutParams.height =  (int)Math.round(height*.04);
        mRegisterEmailEditTextLayoutParams.height =  (int)Math.round(height*.04);
        mRegisterReenterPasswordEditTextLayoutParams.height = (int)Math.round(height*.04);
        mRegisterUsernameEditTextLayoutParams.width = (int) Math.round(height*.40);
        mRegisterReenterPasswordEditTextLayoutParams.width = (int) Math.round(height*.40);
        mRegisterPasswordEditTextLayoutParams.width  = (int) Math.round(height*.40);
        mRegisterEmailEditTextLayoutParams.width  = (int) Math.round(height*.40);

        ViewGroup.LayoutParams mBacktoSplashButtonLayoutParams = mBacktoSplashButton.getLayoutParams();
        ViewGroup.LayoutParams noLoginButtonLayoutParams = noRegisterButton.getLayoutParams();
        ViewGroup.LayoutParams registerButtonLayoutParams = registerButton.getLayoutParams();
        mBacktoSplashButtonLayoutParams.height =  (int)Math.round(height*.05);
        noLoginButtonLayoutParams.height =  (int)Math.round(height*.05);
        registerButtonLayoutParams.height =  (int)Math.round(height*.05);
        mBacktoSplashButtonLayoutParams.width  = (int) Math.round(height*.17);
        noLoginButtonLayoutParams.width  = (int) Math.round(height*.17);
        registerButtonLayoutParams.width  = (int) Math.round(height*.17);

        mBacktoSplashButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        registerButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        noRegisterButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
    }

    private void startBackActivity(View view) {
        Intent intent = new Intent(this,SplashActivity.class);
        startActivity(intent);
        finish();
    }

    private void alertUserAboutError(String errorTitle, String errorMessage) {
        // A function to call the AlertDialogFragment Activity to alert the user about possible errors
        AlertDialogFragment dialog = new AlertDialogFragment().newInstance( errorTitle,errorMessage );
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private boolean isReenterPasswordMatches(String password,String Reenterpassword) {
        // A function used to check whether password matches Reenterpassword
        if( password.equals(Reenterpassword)  ){
            return true;
        }
        else {
            MyApplication.credentialError = "Your re-entered password does not match your password.";
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        // A function used to check whether users are connected to the internet
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

    private boolean isValidEmail(String email) {
        // A function used to check whether users are entering a valid email address
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    private boolean isValidUsername(String username){
        // A function used to check whether users are entering a valid username
        boolean isAvailable = false;
        int length = username.length();

        // Ensure the proper length and legal characters to prevent query injecting
        if( length >= 1 && length <= 20 ){
            if( username.matches("^[a-zA-Z0-9_]*$")){

                isAvailable = true;
            }
            else{
                MyApplication.credentialError = "Your username must only contain letters, numbers, or underscore (_)";
            }
        }
        else{
            MyApplication.credentialError = "Your username must not contain more than 20 characters.";
        }
        return isAvailable;
    }

    private boolean isValidPassword(String password){

        // A function used to check whether users are entering a valid password
        boolean isAvailable = false;
        int length = password.length();

        // Ensure the proper length and legal characters to prevent query injecting
        if( length >= 8 && length <= 20 ){
            if( password.matches("^[a-zA-Z0-9_]*$")){
                if(password.matches(".*\\d.*")){
                    isAvailable = true;
                }
                else{
                    MyApplication.credentialError = "Your password must contain at least one number";
                }
            }
            else{
                MyApplication.credentialError = "Your password must only contain letters, numbers, or underscore (_)";
            }
        }
        else{
            MyApplication.credentialError = "Your password must contain at least 8 characters and no more than 20 characters.";
        }

        return isAvailable;
    }

    @OnClick(R.id.continueNotRegisteredButton)
    public void proceedWithoutAccount(View view){

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }

    //SVG conversion.
    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        }catch(SVGParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
