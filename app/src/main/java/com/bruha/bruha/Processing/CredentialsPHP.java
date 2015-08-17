package com.bruha.bruha.Processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Work on 2015-07-31.
 */
public class CredentialsPHP {
    //Variables used when connecting to a network.
    URL url = null;
    String response = null;
    HttpURLConnection connection;
    OutputStreamWriter request = null;

    //The method to login.
    public String login(String mUsername, String mPassword) {

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
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double x = Double.parseDouble(response);
        int y= (int) x;

        String error;
        if(y==1)
        {
            error="Success";
        }

        else{
            error="badCredentials";
        }
        return error;
    }

    //The method to register an account.
    public String register(String mUsername, String mPassword, String mEmail) {

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
                    url = new URL("http://bruha.com/mobile_php/SignUp.php?"+parameters);
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
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
