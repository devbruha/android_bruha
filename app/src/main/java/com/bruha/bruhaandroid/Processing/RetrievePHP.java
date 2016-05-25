// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Processing;

import android.provider.ContactsContract;
import android.util.Log;

import com.bruha.bruhaandroid.Model.Artist;
import com.bruha.bruhaandroid.Model.Event;
import com.bruha.bruhaandroid.Model.Organizations;
import com.bruha.bruhaandroid.Model.Venue;
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

                    url = new URL("http://dev.bruha.com/api/v1/explore/search?type=event&search=");
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


        Log.v("Awn", response);

        try {


            JSONObject x = new JSONObject(response);
            JSONArray EventArray = x.getJSONArray("events");

            for (int i = 0; i < EventArray.length(); i++) {
                JSONObject Event = EventArray.getJSONObject(i);
                final com.bruha.bruhaandroid.Model.Event even = new Event();

                Log.v("Awn50", EventArray.length() + "");

                //Event
                even.setEventid(Event.getString("id"));
                even.setEventName(Event.getString("name"));
                even.setVenueid(Event.getString("venue_id"));
                even.setEventDescription(Event.getString("description"));
                even.setEventDate(Event.getString("startdate"));
                even.setEventEndDate(Event.getString("enddate"));

                //PlaceHolders
                even.setEventStartTime(" ");
                even.setEventEndTime(" ");
                even.setEventPrice(0.0);

                //Venue
                JSONObject venueSubJSON = Event.getJSONObject("venue");
                even.setEventLocName(venueSubJSON.getString("name"));

                //Location
                JSONObject locationSubJSON = venueSubJSON.getJSONObject("location");
                even.setEventLocSt(locationSubJSON.getString("street"));
                even.setEventLocAdd(locationSubJSON.getString("city") + ", " + locationSubJSON.getString("country"));
                even.setEventLatitude(Double.parseDouble(locationSubJSON.getString("latitude")));
                even.setEventLongitude(Double.parseDouble(locationSubJSON.getString("longitude")));

                //Category
                JSONArray categorySubJSON = Event.getJSONArray("evocategories");
                JSONObject categoryObjJSON = categorySubJSON.getJSONObject(0);
                even.setEventPrimaryCategory(categoryObjJSON.getString("name"));

                mEvents.add(even);
            }

        }
        catch (JSONException e) {e.printStackTrace();}
        return mEvents;
    }

        //Gets the List of Venue uploaded in the Database.
    public ArrayList<Venue> getVenueList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://dev.bruha.com/api/v1/explore/search?type=venue&search=");
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

       /*try {
            JSONArray x = new JSONArray(response);

            for (int i = 0; i < x.length(); i++) {
                JSONObject Venue = x.getJSONObject(i);
                com.bruha.bruhaandroid.Model.Venue ven = new Venue();

                ven.setVenueId(Venue.getString("venue_id"));
                ven.setVenueName(Venue.getString("venue_name"));
                ven.setVenueDescription(Venue.getString("venue_desc"));
                ven.setVenuePrimaryCategory((Venue.getString("primary_category")));
                ven.setVenueSt(Venue.getString("street_no") + " " + Venue.getString("street_name") + ", " + Venue.getString("postal_code"));
                ven.setVenueLocation(Venue.getString("location_city") + ", " + Venue.getString("country"));
                ven.setLat(Double.parseDouble(Venue.getString("location_lat")));
                ven.setLng(Double.parseDouble(Venue.getString("location_lng")));
                ven.setVenuePicture("http://bruha.com/"+Venue.getString("media"));
                ven.setVenuePrimaryCategory(Venue.getString("primary_category"));

                mVenues.add(ven);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mVenues;

        */
        Log.v("Awn1:",response);
        return null;
    }

    //Gets the List of Outfits uploaded in the Database.
    public ArrayList<Organizations> getOrgList() {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    url = new URL("http://dev.bruha.com/api/v1/explore/search?type=organization&search=");
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
            JSONObject x = new JSONObject(response);
            JSONArray OrganizationArray = x.getJSONArray("organizations");

            for (int i = 0; i < OrganizationArray.length(); i++) {
                JSONObject Organization = OrganizationArray.getJSONObject(i);
                final com.bruha.bruhaandroid.Model.Organizations org = new Organizations();


                org.setOrgId(Organization.getString("id"));

                org.setOrgName(Organization.getString("name"));

                org.setOrgDescription(Organization.getString("description"));


                //Location
                JSONObject locationSubJSON = Organization.getJSONObject("location");
                org.setLocId(Integer.parseInt(locationSubJSON.getString("id")));

                org.setOrgSt(locationSubJSON.getString("street") + ", " + locationSubJSON.getString("postalcode"));
                org.setOrgLocation(locationSubJSON.getString("city") + ", " +locationSubJSON.getString("province") + ", " + locationSubJSON.getString("country"));
                org.setLat(Double.parseDouble(locationSubJSON.getString("latitude")));
                org.setLng(Double.parseDouble(locationSubJSON.getString("longitude")));



                //Category
                JSONArray categorySubJSON = Organization.getJSONArray("evocategories");
                JSONObject categoryObjJSON = categorySubJSON.getJSONObject(0);
                org.setOrgPicture("https://usercontent1.hubstatic.com/8875588.jpg");
                org.setOrgPrimaryCategory(categoryObjJSON.getString("name"));

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
                Artist.setArtistPicture("http://bruha.com/"+mArtist.getString("Artist_media"));
                Artist.setArtistPrimaryCategory(mArtist.getString("primary_category"));

                mArtists.add(Artist);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mArtists;
    }
    
}