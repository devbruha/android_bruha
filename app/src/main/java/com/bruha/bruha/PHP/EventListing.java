package com.bruha.bruha.PHP;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bruha.bruha.Model.Event;
import com.bruha.bruha.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventListing extends ActionBarActivity {

    public ArrayList<Event> New = new ArrayList<>();


    public EventListing()
    {

    }

    public ArrayList<Event> getNew()
    {
        GetList();
        return New; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_listing);


    }

    private void GetList() {

        String Url = "http://bruha.com/mobile_php/JSONTest.php";


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Url)
                    .build();



            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                 ;
                }



                @Override
                public void onResponse(Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });


                    try {

                        Log.v("Does it work?","YES");

                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            Log.v("Works Again","Yes");
                            Log.v("What",jsonData) ;

                            JSONArray x = new JSONArray(jsonData);




                            for (int i = 0; i < x.length(); i++) {
                                JSONObject Event = x.getJSONObject(i);
                                Event even = new Event();

                                even.setEventName(Event.getString("event_name"));
                                even.setEventDate(Event.getString("evnt_start_date"));
                                even.setEventEndDate(Event.getString("event_end_date"));
                                even.setEventStartTime(Event.getString("event_start_time"));
                                even.setEventEndTime(Event.getString("event_end_time"));
                                even.setEventLocAdd(Event.getString("venue_location"));
                             //  even.setEventPrice(Event.getDouble("Admission_price"));

                                New.add(even) ;
                            }

                            Log.v("WOW","WOWW");



                        }

                    }



                    catch(Exception e)
                    {
                        Log.v("Exception" , e+"");
                }


            };
                });


    }

    /*

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();


            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }

        return days;
    }

*/


}
