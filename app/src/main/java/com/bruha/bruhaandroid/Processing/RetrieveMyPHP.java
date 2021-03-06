// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Processing;

import android.util.Log;

import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.User;
import com.bruha.bruhaandroid.Model.Venue;

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
public class RetrieveMyPHP {
    //List of Arrays used to store respective information that is returned by each method called.
    ArrayList<Event> mUserEvents = new ArrayList<>();
    ArrayList<Venue> mUserVenues = new ArrayList<>();
    ArrayList<Artist> mUserArist = new ArrayList<>();
    ArrayList<Organizations> mUserOrg = new ArrayList<>();
    ArrayList<String> mAddictedEvents = new ArrayList<>();
    ArrayList<String> mAddictedVenues = new ArrayList<>();
    ArrayList<String> mAddictedArtist = new ArrayList<>();
    ArrayList<String> mAddictedOrg = new ArrayList<>();
    String display = "";
    User newUser = new User();
    //Variables used when connecting to a network.
    URL url = null;
    String response = null;
    HttpURLConnection connection;
    OutputStreamWriter request = null;

    //Gets the List of Events that the user uploaded.
    public ArrayList<Event> getUserEventList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/UserEventList.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                com.bruha.bruhaandroid.Model.Event even = new Event();

                even.setEventid(Event.getString("event_id"));
                even.setEventName(Event.getString("event_name"));
                even.setVenueid(Event.getString("venue_id"));
                even.setEventDescription(Event.getString("event_desc"));
                even.setEventDate(Event.getString("event_start_date"));
                even.setEventEndDate(Event.getString("event_end_date"));
                even.setEventStartTime(Event.getString("event_start_time"));
                even.setEventEndTime(Event.getString("event_end_time"));

                if (!Event.getString("Admission_price").equals("null")){

                    even.setEventPrice(Double.parseDouble(Event.getString("Admission_price")));
                }
                else{

                    even.setEventPrice(0.0);
                }
                even.setEventLocName(Event.getString("venue_name"));
                even.setEventLocSt(Event.getString("street_no") +" "+ Event.getString("street_name"));
                even.setEventLocAdd(Event.getString("location_city") + ", " + Event.getString("country"));
                even.setEventLatitude(Double.parseDouble(Event.getString("location_lat")));
                even.setEventLongitude(Double.parseDouble(Event.getString("location_lng")));

                if (!Event.getString("image_link").equals("null")){

                    even.setEventPicture("http://bruha.com/"+Event.getString("image_link"));
                }
                else{

                    even.setEventPicture("http://bruha.com/assets/uploads/Events/Concrete_Android-Lrg.jpg");
                }

                even.setEventPrimaryCategory((Event.getString("primary_category")));

                JSONArray evenSubJSON = ((JSONArray)Event.get("sub_category"));
                JSONArray evenSubIDJSON = ((JSONArray)Event.get("sub_category_id"));

                JSONArray evenOrgJSON = ((JSONArray)Event.get("organization_id"));

                ArrayList<String> evenSubArrayList = new ArrayList<>();
                ArrayList<String> evenSubIDArrayList = new ArrayList<>();

                ArrayList<String> evenOrgList = new ArrayList<>();

                for(int j=0; j<evenOrgJSON.length();j++){

                    evenOrgList.add(evenOrgJSON.getString(j));
                }

                even.setOrganizationid(evenOrgList);

                for(int j=0; j<evenSubJSON.length();j++){

                    evenSubArrayList.add(evenSubJSON.getString(j));
                    evenSubIDArrayList.add(evenSubIDJSON.getString(j));
                }

                even.setEventSubCategories(evenSubArrayList);
                even.setEventSubCategoriesID(evenSubIDArrayList);

                mUserEvents.add(even);
            }

            return mUserEvents;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mUserEvents;
    }

    //The method to delete an user's event.
    public String deleteUserEvent(String mUsername, String eventid) {
        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&event_id="+eventid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteUserEvent.php?"+parameters);
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
                    display = response;

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
        return display;
    }

    //The method to delete an user's venue.
    public String deleteUserVenue(String mUsername, String venueid) {
        final String parameters = "user_id="+mUsername+"&venue_id="+venueid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteUserVenue.php?"+parameters);
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
                    display = response;
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
        return display;
    }

    //The method to delete an user's artist.
    public String deleteUserArtist(String mUsername, String artistid) {
        final String parameters = "user_id="+mUsername+"&artist_id="+artistid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteUserArtist.php?"+parameters);
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
                    display = response;
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
        return display;
    }

    //The method to delete an user's organization.
    public String deleteUserOrg(String mUsername, String organizationid) {
        final String parameters = "user_id="+mUsername+"&organization_id="+organizationid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteUserOrg.php?"+parameters);
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
                    display = response;
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
        return display;
    }

    //Gets the List of Events that the user uploaded.
    public ArrayList<Venue> getUserVenueList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/UserVenueList.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {

            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Venue = x.getJSONObject(i);
                com.bruha.bruhaandroid.Model.Venue ven = new Venue();

                ven.setVenueId(Venue.getString("venue_id"));
                ven.setVenueName(Venue.getString("venue_name"));
                ven.setVenueDescription(Venue.getString("venue_desc"));
                ven.setVenueSt(Venue.getString("street_no") + " " + Venue.getString("street_name") + ", " + Venue.getString("postal_code"));
                ven.setVenueLocation(Venue.getString("location_city") + ", " + Venue.getString("country"));
                ven.setLat(Double.parseDouble(Venue.getString("location_lat")));
                ven.setLng(Double.parseDouble(Venue.getString("location_lng")));
                ven.setVenuePicture("http://bruha.com/"+Venue.getString("media"));
                ven.setVenuePrimaryCategory(Venue.getString("primary_category"));

                mUserVenues.add(ven);
            }
            return mUserVenues;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mUserVenues;
    }

    //Gets the List of Events that the user uploaded.
    public ArrayList<Organizations> getUserOrgList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/UserOrgList.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {

            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Organization = x.getJSONObject(i);
                com.bruha.bruhaandroid.Model.Organizations org = new Organizations();

                org.setOrgId(Organization.getString("organization_id"));
                org.setOrgName(Organization.getString("organization_name"));
                org.setOrgDescription(Organization.getString("organization_desc"));
                org.setOrgSt(Organization.getString("street_no") + " " + Organization.getString("street_name") + ", " + Organization.getString("postal_code"));
                org.setOrgLocation(Organization.getString("location_city") + ", " + Organization.getString("country"));
                org.setLat(Double.parseDouble(Organization.getString("location_lat")));
                org.setLng(Double.parseDouble(Organization.getString("location_lng")));
                org.setOrgPicture("http://bruha.com/" + Organization.getString("media"));
                org.setOrgPrimaryCategory(Organization.getString("primary_category"));

                mUserOrg.add(org);
            }

            return mUserOrg;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mUserOrg;
    }

    //Gets the List of Events that the user uploaded.
    public ArrayList<Artist> getUserArtistList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/UserArtistList.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {

            JSONArray x = new JSONArray(response);


            for (int i = 0; i < x.length(); i++) {
                JSONObject mArtist = x.getJSONObject(i);
                Artist Artist = new Artist();

                Artist.setArtistId(mArtist.getString("Artist_id"));
                Artist.setArtistName(mArtist.getString("Artist_name"));
                Artist.setArtistDescription(mArtist.getString("Artist_desc"));
                Artist.setArtistPicture("http://bruha.com/" + mArtist.getString("Artist_media"));
                Artist.setArtistPrimaryCategory(mArtist.getString("primary_category"));

                mUserArist.add(Artist);
            }
            return mUserArist;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mUserArist;
    }

    //Returns a String array containing information about the User.
    public User getUserInfo(String user) {
        ArrayList<String> UserInfo = new ArrayList<>();

        //"http://bruha.com/api/v1/user?"+parameters
        //final String parameters = "1?api_token=uR862Mh3vMuVnxdQq22ANGdP1WlQtC2B0f98x53NdZETHhKbrkp6JJQ5RWos";
       // final String parameters = "1?api_token=uR862Mh3vMuVnxdQq22ANGdP1WlQtC2B0f98x53NdZETHhKbrkp6JJQ5RWos";

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("https://bruha.com/api/v1/user/1?api_token=uR862Mh3vMuVnxdQq22ANGdP1WlQtC2B0f98x53NdZETHhKbrkp6JJQ5RWos");
                    connection = (HttpURLConnection) url.openConnection();
                   // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                   // connection.setRequestMethod("GET");
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

        Log.v("Sceneee",response);

        try {

            JSONObject x = new JSONObject(response);

            JSONObject userE = x.getJSONObject("user");
            newUser.setName(userE.getString("name"));
            newUser.setId(userE.getString("id"));
            newUser.setEmail(userE.getString("email"));



        } catch (JSONException e) {
            e.printStackTrace();
        }



        return newUser;
    }

    //Addicted Stuff

    //The method to get addicted to an event.
    public void eventAddiction(String mUsername, String eventid) {
        final String parameters = "user_id="+mUsername+"&event_id="+eventid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/EventAddictions.php?"+parameters);
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
                    Log.v("The Response:",response);
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
    }

    //The method to delete an addicted event.
    public void deleteEventAddiction(String mUsername, String eventid) {
        final String parameters = "user_id="+mUsername+"&event_id="+eventid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteEventAddiction.php?"+parameters);
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
    }

    //The method to delete an addicted venue.
    public void deleteVenueAddiction(String mUsername, String venueid) {
        final String parameters = "user_id="+mUsername+"&venue_id="+venueid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteVenueAddiction.php?"+parameters);
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
    }

    //The method to delete an addicted artist.
    public void deleteArtistAddiction(String mUsername, String artistid) {
        final String parameters = "user_id="+mUsername+"&artist_id="+artistid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteArtistAddiction.php?"+parameters);
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
    }


    //The method to delete an addicted event.
    public void deleteOrgAddiction(String mUsername, String organizationid) {
        final String parameters = "user_id="+mUsername+"&organization_id="+organizationid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/deleteOrgAddiction.php?"+parameters);
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
    }


    //The method to get addicted to a venue.
    public void venueAddiction(String mUsername, String venueid) {
        final String parameters = "user_id="+mUsername+"&venue_id="+venueid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/VenueAddictions.php?"+parameters);
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
    }

    //The method to get addicted to an artist.
    public void artistAddiction(String mUsername, String artistid) {
        final String parameters = "user_id="+mUsername+"&artist_id="+artistid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/ArtistAddictions.php?"+parameters);
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
    }

    //The method to get addicted to an organization.
    public void organizationAddiction(String mUsername, String orgid) {
        final String parameters = "user_id="+mUsername+"&organization_id="+orgid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/OrgAddictions.php?"+parameters);
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
    }

    //Gets the List of Events that the user is addicted to.
    public ArrayList<String> getAddictedList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/getUserAddiction.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                mAddictedEvents.add(Event.getString("event_id"));
            }
            return mAddictedEvents;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAddictedEvents;
    }

    //Gets the List of Venues that the user is addicted to.
    public ArrayList<String> getAddictedVenueList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/getVenueAddictions.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                mAddictedVenues.add(Event.getString("venue_id"));
            }
            return mAddictedVenues;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAddictedVenues;
    }

    //Gets the List of Artists that the user is addicted to.
    public ArrayList<String> getAddictedArtistList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/getArtistAddictions.php?" + parameters);
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

        try {
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                mAddictedArtist.add(Event.getString("artist_id"));
            }
            return mAddictedArtist;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAddictedArtist;
    }

    //Gets the List of Organizations that the user is addicted to.
    public ArrayList<String> getAddictedOrgList(String user) {
        final String parameters = "username=" + user;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrieveMyPHP/getOrgAddictions.php?" + parameters);
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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
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

        try {
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                mAddictedOrg.add(Event.getString("organization_id"));
            }
            return mAddictedOrg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAddictedOrg;
    }
}