package com.bruha.bruha.PHP;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bruha.bruha.Model.Artists;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.Venues;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
 * Created by Work on 2015-06-23.
 */
public class RetrieveEvents {
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Venues> mVenues = new ArrayList<>();
    ArrayList<Organizations> mOrg = new ArrayList<>();
    ArrayList<Artists> mArtists = new ArrayList<>();
    ArrayList<Event> mUserEvents = new ArrayList<>();

    //UserEvents Stuff.
    URL url = null;
    String response = null;
    HttpURLConnection connection;
    OutputStreamWriter request = null;



    public ArrayList<Event> GetEventList() {

        final String Url = "http://bruha.com/mobile_php/EventList.php";


        Thread T = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();

                Call call = client.newCall(request);

                try {

                    Response response = call.execute();
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {

                        JSONArray x = new JSONArray(jsonData);

                        for (int i = 0; i < x.length(); i++) {
                            JSONObject Event = x.getJSONObject(i);
                            com.bruha.bruha.Model.Event even = new Event();

                            even.setEventName(Event.getString("event_name"));
                            even.setEventDate(Event.getString("evnt_start_date"));
                            even.setEventEndDate(Event.getString("event_end_date"));
                            even.setEventStartTime(Event.getString("event_start_time"));
                            even.setEventEndTime(Event.getString("event_end_time"));
                            even.setEventPrice(Double.parseDouble(Event.getString("Admission_price")));
                            even.setEventid(Event.getString("event_id"));
                            even.setEventDescription(Event.getString("event_desc"));
                            even.setVenueid(Event.getString("venue_id"));
                            even.setLocationID(Event.getString("location_id"));
                            even.setEventLocName(Event.getString("venue_name"));
                            even.setEventLocSt(Event.getString("venue_location"));
                            even.setEventLocAdd(Event.getString("location_city"));
                            even.setEventLatitude(Double.parseDouble(Event.getString("location_lat")));
                            even.setEventLongitude(Double.parseDouble(Event.getString("location_lng")));

                            mEvents.add(even);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        T.start();

        try {
            T.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mEvents;
    }

    public ArrayList<Venues> GetVenueList() {

        final String Url = "http://bruha.com/mobile_php/VenueList.php";


        Thread T = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();

                Call call = client.newCall(request);

                try {

                    Response response = call.execute();
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {

                        JSONArray x = new JSONArray(jsonData);

                        for (int i = 0; i < x.length(); i++) {
                            JSONObject Venue = x.getJSONObject(i);
                            com.bruha.bruha.Model.Venues ven = new Venues();

//                            ven.setVenueId(Integer.parseInt(Venue.getString("venue_id")));
                            ven.setVenueName(Venue.getString("venue_name"));
                            ven.setVenueDescription(Venue.getString("venue_desc"));
                            ven.setVenueLocation(Venue.getString("venue_location"));
                            ven.setLat(Double.parseDouble(Venue.getString("location_lat")));
                            ven.setLng(Double.parseDouble(Venue.getString("location_lng")));

                            mVenues.add(ven);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        T.start();

        try {
            T.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mVenues;
    }

    public ArrayList<Organizations> GetOrgList() {

        final String Url = "http://bruha.com/mobile_php/OrganizationList.php";


        Thread T = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();

                Call call = client.newCall(request);

                try {

                    Response response = call.execute();
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {

                        JSONArray x = new JSONArray(jsonData);

                        for (int i = 0; i < x.length(); i++) {
                            JSONObject Organization = x.getJSONObject(i);
                            com.bruha.bruha.Model.Organizations org = new Organizations();

//                            org.setOrgId(Integer.parseInt(Organization.getString("organization_id")));
                            org.setOrgName(Organization.getString("organization_name"));
                            org.setOrgDescription(Organization.getString("organization_desc"));
                            org.setOrgLocation(Organization.getString("organization_location"));
                            org.setLocId(Integer.parseInt(Organization.getString("location_id")));
                            org.setLat(Double.parseDouble(Organization.getString("location_lat")));
                            org.setLng(Double.parseDouble(Organization.getString("location_lng")));


                            mOrg.add(org);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        T.start();

        try {
            T.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mOrg;
    }

    public ArrayList<Artists> GetArtistList() {

        final String Url = "http://bruha.com/mobile_php/ArtistList.php";


        Thread T = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();

                Call call = client.newCall(request);

                try {

                    Response response = call.execute();
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {

                        JSONArray x = new JSONArray(jsonData);

                        for (int i = 0; i < x.length(); i++) {
                            JSONObject mArtist = x.getJSONObject(i);
                            com.bruha.bruha.Model.Artists Artist = new Artists();

                            Artist.setArtistId(Integer.parseInt(mArtist.getString("Artist_id")));
                            Artist.setArtistName(mArtist.getString("Artist_name"));
                            Artist.setArtistDescription(mArtist.getString("Artist_desc"));

                            mArtists.add(Artist);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        T.start();

        try {
            T.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mArtists;
    }

    public ArrayList<Event> GetUserEventList(String user) {


        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/UserEventList.php?" + parameters);
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
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    // Response from server after login process will be stored in response variable.

                    // in this case the response is the echo from the php script (i.e = 1) if successful

                    response = sb.toString();
                    Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();

                } catch (IOException e) {
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

        try {
            JSONArray x = new JSONArray(response);


            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                com.bruha.bruha.Model.Event even = new Event();

                even.setEventName(Event.getString("event_name"));
                even.setEventid(Event.getString("event_id"));
                even.setVenueid(Event.getString("venue_id"));
                even.setLocationID(Event.getString("location_id"));
                even.setEventDescription(Event.getString("event_desc"));
                even.setEventPrice(Double.parseDouble(Event.getString("Admission_price")));
                even.setEventLocAdd(Event.getString("location_city"));
                even.setEventLatitude(Double.parseDouble(Event.getString("location_lat")));
                even.setEventLongitude(Double.parseDouble(Event.getString("location_lng")));
                even.setEventStartTime(Event.getString("event_start_time"));
                even.setEventEndTime(Event.getString("event_end_time"));
                even.setEventDate(Event.getString("event_start_date"));
                even.setEventEndDate(Event.getString("event_end_date"));
                even.setEventLocName(Event.getString("venue_name"));
                even.setEventLocSt(Event.getString("venue_location"));


                mUserEvents.add(even);
                Log.v("Event:", even.getEventName());
            }


            Log.v("TEST:", response);
            return mUserEvents;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mUserEvents;
    }

    public String login(String mUsername, String mPassword)
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


        Log.v("The response is:", response);
        Log.v("Is:", "This");

        double x = Double.parseDouble(response);
        int y= (int) x;

        String error;
        if(y==1)
        {
            error="Success";
        }

        else{ error="badCredentials"; }

        return error;


    }

    public void Register(String mUsername, String mPassword,String mEmail)
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



        Log.v("The response is:", response);
        Log.v("Is:","This");




    }

    public ArrayList<String> GetUserInfo(String user) {


        ArrayList<String> UserInfo = new ArrayList<>();

        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/UserInfo.php?" + parameters);
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
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    // Response from server after login process will be stored in response variable.

                    // in this case the response is the echo from the php script (i.e = 1) if successful

                    response = sb.toString();
                    // You can perform UI operations here
                    isr.close();
                    reader.close();

                } catch (IOException e) {
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

        try {
            JSONArray x = new JSONArray(response);


            for (int i = 0; i < x.length(); i++) {
                JSONObject User = x.getJSONObject(i);

                UserInfo.add(User.getString("Username"));
                UserInfo.add(User.getString("Name"));
                UserInfo.add(User.getString("Birthdate"));
                UserInfo.add(User.getString("Gender"));
                UserInfo.add(User.getString("Email"));
                UserInfo.add(User.getString("City"));
            }
            return UserInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }

         return UserInfo;
    }

}

