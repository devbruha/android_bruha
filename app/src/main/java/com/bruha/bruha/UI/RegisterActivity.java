package com.bruha.bruha.UI;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bruha.bruha.Database.SQLUtils;
import com.bruha.bruha.R;
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

        // Init signal to true
        boolean signal = false;

        // If either of the fields are empty signal is set false

        if(username.length() == 0 || password.length() == 0 || email.length() == 0){

            signal = true;
        }

        return signal;
    }

    private void alertUserAboutError(String errorTitle, String errorMessage) {

        AlertDialogFragment dialog = new AlertDialogFragment().newInstance(errorTitle,errorMessage);
        dialog.show(getFragmentManager(), "error_dialog");
    }

    // Setting the listeners for the choice the user is to make on button click

    @OnClick(R.id.createAccountButton)
    public void createAccount(View view){

        // Retrieving the entered information and converting to string

        String username = mRegisterUsernameEditText.getText().toString();
        String password = mRegisterPasswordEditText.getText().toString();
        String email = mRegisterEmailEditText.getText().toString();

        // Importing ressources in order to reference stored string values

        Resources res = getResources();

        // Checking to ensure that all fields are non empty

        if( !isEntryFieldEmpty(username, password, email) ){

            // Calling the init function within SQLUtils with the parameters passed

            SQLUtils sqlu = new SQLUtils(url,user,pass);
            int errorCode = sqlu.init(username, password, email);

            // If error code is non zero, a message is displayed to user explaining error
            // Switch case to determine what message to send depending on the error
            // Uses values-> strings to populate the title and message

            switch(errorCode){

                case 1062: alertUserAboutError(
                        res.getString(R.string.error_title_username_taken),
                        res.getString(R.string.error_message_username_taken));
                    break;

                default: alertUserAboutError(
                        res.getString(R.string.success_title_account_created),
                        res.getString(R.string.success_message_account_created));
                    break;
            }
        }

        // If fields are not filled, error message displayed accordingly

        else{

            alertUserAboutError(
                    res.getString(R.string.error_title_fields_empty),
                    res.getString(R.string.error_message_fields_empty));
        }
    }

    @OnClick(R.id.continueNotRegisteredButton)
    public void proceedWithoutAccount(View view){

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

    }
}
