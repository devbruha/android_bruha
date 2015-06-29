package com.bruha.bruha.PHP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruha.bruha.R;
import com.bruha.bruha.Views.DashboardActivity;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    private Button login;
    private EditText username, password;

    URL url = null;
    String response = null;
    HttpURLConnection connection;
    OutputStreamWriter request = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Stores user entered credentials as strings then passes them to tryLogin function

                String   mUsername = username.getText().toString();
                String  mPassword = password.getText().toString();

                tryLogin(mUsername, mPassword);
            }
        });
    }

    protected void tryLogin(String mUsername, String mPassword)
    {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc

        final String parameters = "username="+mUsername+"&password="+mPassword;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/Login.php?"+parameters);
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



        Log.v("Response",response);


        // toasting the response from the server, gg

        Toast.makeText(this,"Message from Server: \n"+ response, Toast.LENGTH_SHORT).show();

        Log.v("The response is:",response);
        Log.v("Is:","This");

        double x = Double.parseDouble(response);
        int y= (int) x;

        if(y==1)
        {
            Intent intent = new Intent(this, EventListing.class);
            startActivity(intent);
        }



    }
}
