// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Processing;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Work on 2015-07-31.
 */
public class CredentialsPHP {
    //Variables used when connecting to a network.
    URL url = null;
    String response = null;
    HttpURLConnection connection;
    OutputStreamWriter request = null;

    ArrayList<String> loginList = new ArrayList<>();

    //The method to login.
    public ArrayList<String> login(String mEmail, String mPassword) {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "email="+mEmail+"&password="+mPassword;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://dev.bruha.com/api/v1/login?"+parameters);
                    // url = new URL("http://dev.bruha.com/api/v1/login?");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);

                    // connection.setRequestProperty("Accept","application/json; charset=UTF-8");
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    //connection.setRequestProperty("Authorization", "key="+apiKey);
                    // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // connection.setRequestProperty("Authorization", "application/json");
                    // connection.setRequestProperty("Content-Type","application/json");
                    //connectiom.setvalue("application/json", forHTTPHeaderField: "Accept")

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

        Log.v("Scene awnn:",response);

        try {
            JSONObject x = new JSONObject(response);

            loginList.add(x.getString("token"));
            loginList.add(x.getString("id"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*
        double x = Double.parseDouble(response);
        int y= (int) x;
        //For logging its a string, display the string
        String error;
        if(y==1)
        {
            error="Success";
        }

        else{
            error="badCredentials";
        }
        return error;
        */
        return loginList;
    }

    //The method to register an account.
    public String register(String mUsername, String mEmail, String mPassword) {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "name="+mUsername+"&email="+mEmail+"&password="+mPassword+"&password_confirmation="+mPassword;


        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                 //   url = new URL("http://bruha.com/mobile_php/CredentialsPHP/SignUp.php?"+parameters);
                    url = new URL("http://dev.bruha.com/api/v1/register?"+parameters);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                  //  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");

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

        if(response==null)
        {
            return "1062";
        }

        else
        {
            return "Success";
        }
        //Log.v("Scene",response);
        //return null;
      //  return response;
    }

    public String forgotPassword(String mUsername, String mEmail){

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "forget-email="+mEmail+"&forget-username="+mUsername;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://bruha.com/mobile_php/CredentialsPHP/forgotPassword2.php?"+parameters);
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