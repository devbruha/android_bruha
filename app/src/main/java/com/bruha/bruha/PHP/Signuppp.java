package com.bruha.bruha.PHP;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruha.bruha.R;
import com.bruha.bruha.Views.DashboardActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Signuppp extends ActionBarActivity {

    /** Called when the activity is first created. */
    private Button register;
    private EditText username, password, email;

    URL url = null;
    String response = null;
    HttpURLConnection connection;
    OutputStreamWriter request = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppp);

        register = (Button) findViewById(R.id.regist);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwordy);
        email = (EditText) findViewById(R.id.Email);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Stores user entered credentials as strings then passes them to tryLogin function

                String   mUsername = username.getText().toString();
                String  mPassword = password.getText().toString();
                String mEmail = email.getText().toString();


                tryRegister(mUsername, mPassword,mEmail);
            }
        });
    }

    protected void tryRegister(String mUsername, String mPassword,String mEmail)
    {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc

        final String parameters = "user_id="+mUsername+"&password="+mPassword+"&email="+mEmail;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/signUp.php?"+parameters);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestMethod("POST");

                    request = new OutputStreamWriter(connection.getOutputStream());
                    request.write(parameters);
                    request.flush();
                    request.close();
                    String line = "";
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    // Response from server after login process will be stored in response variable.

                    // in this case the response is the echo from the php script (i.e = 1) if successful

                    response = sb.toString();
                    // You can perform UI operations here
                    isr.close();
                    reader.close();

                }
                catch(IOException e)
                {
                    // Error
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // toasting the response from the server, gg

        Toast.makeText(this, "Message from Server: \n" + response, Toast.LENGTH_SHORT).show();

        Log.v("The response is:", response);
        Log.v("Is:","This");




    }

}








