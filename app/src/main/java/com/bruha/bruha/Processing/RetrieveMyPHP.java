package com.bruha.bruha.Processing;


import android.util.Log;
import com.bruha.bruha.Model.Event;
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
    ArrayList<String> mAddictedEvents = new ArrayList<>();
    ArrayList<String> mAddictedVenues = new ArrayList<>();
    ArrayList<String> mAddictedArtist = new ArrayList<>();
    ArrayList<String> mAddictedOrg = new ArrayList<>();


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
                    //   Log.v("response",response);
                    // You can perform UI operations here
                    isr.close();
                    reader.close();
                } catch (IOException e) {
                    Log.v("Exception", e + "");
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
            Log.v("Response", response);
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Event = x.getJSONObject(i);
                com.bruha.bruha.Model.Event even = new Event();

                even.setEventid(Event.getString("event_id"));
                even.setEventName(Event.getString("event_name"));
                even.setVenueid(Event.getString("venue_id"));
                even.setEventDescription(Event.getString("event_desc"));
                even.setEventDate(Event.getString("event_start_date"));
                even.setEventEndDate(Event.getString("event_end_date"));
                even.setEventStartTime(Event.getString("event_start_time"));
                even.setEventEndTime(Event.getString("event_end_time"));

                if (Event.getString("Admission_price") != null){

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
                if (Event.getString("image_link") != null){

                    even.setEventPicture("http://bruha.com/WorkingWebsite/"+Event.getString("image_link"));
                }
                else{

                    even.setEventPicture("http://bruha.com/WorkingWebsite/assets/uploads/Events/Concrete_Android-Lrg.jpg");
                }

                even.setEventPrimaryCategory((Event.getString("primary_category")));

                JSONArray evenSubJSON = ((JSONArray)Event.get("sub_category"));

                ArrayList<String> evenSubArrayList = new ArrayList<>();

                for(int j=0; j<evenSubJSON.length();j++){

                    evenSubArrayList.add(evenSubJSON.getString(j));
                }

                even.setEventSubCategories(evenSubArrayList);


              //  Log.v("UserEventName",even.getEventName());
                mUserEvents.add(even);
            }

            return mUserEvents;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mUserEvents;
    }

    //Returns a String array containing information about the User.
    public ArrayList<String> getUserInfo(String user) {
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

    //Addicted Stuff

    //The method to get addicted to an event.
    public void eventAddiction(String mUsername, String eventid) {

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

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/EventAddictions.php?"+parameters);
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
                    Log.v("ResponseAddicted",response);
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

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/deleteEventAddiction.php?"+parameters);
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
                    Log.v("ResponseAddictionDelete",response);
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

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&venue_id="+venueid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/deleteVenueAddiction.php?"+parameters);
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
                    Log.v("ResponseAddictionDelete",response);
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

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&artist_id="+artistid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/deleteArtistAddiction.php?"+parameters);
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
                    Log.v("ResponseArtistDelete",response);
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

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&organization_id="+organizationid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/deleteOrgAddiction.php?"+parameters);
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
                    Log.v("ResponseOrgDelete",response);
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

    //Addicted Stuff
    //The method to get addicted to a venue.
    public void venueAddiction(String mUsername, String venueid) {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&venue_id="+venueid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/VenueAddictions.php?"+parameters);
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
                    Log.v("ResponseAddicted",response);
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

    //Addicted Stuff
    //The method to get addicted to an artist.
    public void artistAddiction(String mUsername, String artistid) {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&artist_id="+artistid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/ArtistAddictions.php?"+parameters);
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
                    Log.v("ResponseAddicted",response);
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

    //Addicted Stuff
    //The method to get addicted to an organization.
    public void organizationAddiction(String mUsername, String orgid) {

        // creates parameters for the DB call to attach to the "initial" URL
        // to attach more paramenters its of the form:
        // "http://initialurllink?parameter1=parameter1Value&parameter2=parameter2Value&parameter3=parameter3Value" and etc
        final String parameters = "user_id="+mUsername+"&organization_id="+orgid;

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {

                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"

                    // alot of boiler plate stuff

                    url = new URL("http://bruha.com/mobile_php/OrgAddictions.php?"+parameters);
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
                    Log.v("ResponseAddicted",response);
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
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://bruha.com/mobile_php/getUserAddiction.php?" + parameters);
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
                    Log.v("Exception", e + "");
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
                    url = new URL("http://bruha.com/mobile_php/getVenueAddictions.php?" + parameters);
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
                    Log.v("Exception", e + "");
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
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://bruha.com/mobile_php/getArtistAddictions.php?" + parameters);
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
                    Log.v("Exception", e + "");
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
                    // construction new url object to be "http://bruha.com/mobile_php/login.php?username=mUsername&password=mPassword"
                    // alot of boiler plate stuff
                    url = new URL("http://bruha.com/mobile_php/getOrgAddictions.php?" + parameters);
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
                    Log.v("Exception", e + "");
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