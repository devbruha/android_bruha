// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Processing;

import android.util.Log;

import com.bruha.bruha.Model.Artist;
import com.bruha.bruha.Model.Event;
import com.bruha.bruha.Model.Organizations;
import com.bruha.bruha.Model.Venue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Work on 2015-06-23.
 */
public class RetrievePHP {
    //List of Arrays used to store respective information that is returned by each method called.
    ArrayList<Event> mEvents = new ArrayList<>();
    ArrayList<Venue> mVenues = new ArrayList<>();
    ArrayList<Organizations> mOrg = new ArrayList<>();
    ArrayList<Artist> mArtists = new ArrayList<>();
    HashMap<String,ArrayList<ArrayList<String>>> eventArrayList = new HashMap<>();
    ArrayList<String> venueArrayList = new ArrayList<>();
    ArrayList<String> artistArrayList = new ArrayList<>();
    ArrayList<String> organizationArrayList = new ArrayList<>();
    //Variables used when connecting to a network.
    URL url = null;
    String response = null;
    HttpURLConnection connection;

    //Gets the List of Events uploaded in the Database.
    public HashMap<String,ArrayList<ArrayList<String>>> getEventCategoryList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/CategoryList.php");
                    connection = (HttpURLConnection) url.openConnection();

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

        // Get event cats

        try {
            JSONObject x = new JSONObject(response);

            JSONObject eventCat  = x.getJSONObject("event_cat");

            Iterator<String> iter = eventCat.keys();
            String primCatName;

            while( iter.hasNext() ){

                ArrayList<String> itemSub = new ArrayList<>();
                ArrayList<String> itemSubID = new ArrayList<>();

                ArrayList<ArrayList<String>> superArray = new ArrayList<>();

                primCatName = iter.next();

                JSONArray subCatList = eventCat.getJSONArray(primCatName);
                JSONArray subCatIDJSON = subCatList.getJSONArray(subCatList.length()-1);

                // -1 to the max cause of the last item that holds the array of ID's

                for( int i = 0; i<subCatList.length()-1; i++){

                    itemSub.add(subCatList.getString(i));
                    itemSubID.add(subCatIDJSON.getString(i));
                }

                superArray.add(itemSubID);
                superArray.add(itemSub);

                eventArrayList.put(primCatName,superArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventArrayList;
    }

    public ArrayList<String> getVenueCategoryList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/CategoryList.php");
                    connection = (HttpURLConnection) url.openConnection();

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

        // Get venue cats

        try {
            JSONObject x = new JSONObject(response);

            JSONArray venueCat  = x.getJSONArray("venue_cat");

            for(int i = 0; i< venueCat.length(); i++){

                venueArrayList.add(venueCat.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return venueArrayList;
    }

    public ArrayList<String> getArtistCategoryList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/CategoryList.php");
                    connection = (HttpURLConnection) url.openConnection();

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

        // Get venue cats

        try {
            JSONObject x = new JSONObject(response);

            JSONArray artistCat  = x.getJSONArray("artist_cat");

            for(int i = 0; i< artistCat.length(); i++){

                artistArrayList.add(artistCat.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return artistArrayList;
    }

    public ArrayList<String> getOrganizationCategoryList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/CategoryList.php");
                    connection = (HttpURLConnection) url.openConnection();

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

        // Get venue cats

        try {
            JSONObject x = new JSONObject(response);

            JSONArray organizationCat  = x.getJSONArray("organization_cat");

            for(int i = 0; i< organizationCat.length(); i++){

                organizationArrayList.add(organizationCat.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return organizationArrayList;
    }

    //Gets the List of Events uploaded in the Database.
    public ArrayList<Event> getEventList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/EventList.php");
                    connection = (HttpURLConnection) url.openConnection();

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
                final com.bruha.bruha.Model.Event even = new Event();

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
                even.setEventLocSt(Event.getString("street_no") + " " + Event.getString("street_name"));
                even.setEventLocAdd(Event.getString("location_city") + ", " + Event.getString("country"));
                even.setEventLatitude(Double.parseDouble(Event.getString("location_lat")));
                even.setEventLongitude(Double.parseDouble(Event.getString("location_lng")));

                if (!Event.getString("image_link").equals("null")){

                    even.setEventPicture("http://bruha.com/WorkingWebsite/"+Event.getString("image_link"));
                }
                else{

                    even.setEventPicture("http://bruha.com/WorkingWebsite/assets/uploads/Events/Concrete_Android-Lrg.jpg");
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

                mEvents.add(even);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return mEvents;
    }

    //Gets the List of Venue uploaded in the Database.
    public ArrayList<Venue> getVenueList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/VenueList.php");
                    connection = (HttpURLConnection) url.openConnection();

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
                com.bruha.bruha.Model.Venue ven = new Venue();

                ven.setVenueId(Venue.getString("venue_id"));
                ven.setVenueName(Venue.getString("venue_name"));
                ven.setVenueDescription(Venue.getString("venue_desc"));
                ven.setVenuePrimaryCategory((Venue.getString("primary_category")));
                ven.setVenueSt(Venue.getString("street_no") + " " + Venue.getString("street_name") + ", " + Venue.getString("postal_code"));
                ven.setVenueLocation(Venue.getString("location_city") + ", " + Venue.getString("country"));
                ven.setLat(Double.parseDouble(Venue.getString("location_lat")));
                ven.setLng(Double.parseDouble(Venue.getString("location_lng")));
                ven.setVenuePicture("http://bruha.com/WorkingWebsite/"+Venue.getString("media"));
                ven.setVenuePrimaryCategory(Venue.getString("primary_category"));

                mVenues.add(ven);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mVenues;
    }

    //Gets the List of Outfits uploaded in the Database.
    public ArrayList<Organizations> getOrgList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/OrganizationList.php");
                    connection = (HttpURLConnection) url.openConnection();

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
                com.bruha.bruha.Model.Organizations org = new Organizations();

                org.setOrgId(Organization.getString("organization_id"));
                org.setOrgName(Organization.getString("organization_name"));
                org.setOrgDescription(Organization.getString("organization_desc"));
                org.setOrgPrimaryCategory((Organization.getString("primary_category")));
                org.setOrgSt(Organization.getString("street_no") + " " + Organization.getString("street_name") + ", " + Organization.getString("postal_code"));
                org.setOrgLocation(Organization.getString("location_city") + ", " + Organization.getString("country"));
                org.setLat(Double.parseDouble(Organization.getString("location_lat")));
                org.setLng(Double.parseDouble(Organization.getString("location_lng")));
                org.setOrgPicture("http://bruha.com/WorkingWebsite/"+Organization.getString("media"));
                org.setOrgPrimaryCategory(Organization.getString("primary_category"));

                mOrg.add(org);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mOrg;
    }

    //Gets the List of Artist uploaded in the Database.
    public ArrayList<Artist> getArtistList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://bruha.com/mobile_php/RetrievePHP/ArtistList.php");
                    connection = (HttpURLConnection) url.openConnection();

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
                Artist.setArtistPrimaryCategory((mArtist.getString("primary_category")));
                Artist.setArtistPicture("http://bruha.com/WorkingWebsite/"+mArtist.getString("Artist_media"));
                Artist.setArtistPrimaryCategory(mArtist.getString("primary_category"));

                mArtists.add(Artist);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mArtists;
    }
    
}