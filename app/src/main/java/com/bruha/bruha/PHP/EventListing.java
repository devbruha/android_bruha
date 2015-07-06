package com.bruha.bruha.PHP;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;

        import android.app.Activity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.bruha.bruha.Model.Artists;
        import com.bruha.bruha.Model.Event;
        import com.bruha.bruha.Model.Organizations;
        import com.bruha.bruha.Model.Venues;
        import com.bruha.bruha.R;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

public class EventListing extends Activity {
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

                GetUserEventList();
            }
        });
    }

    public void GetUserEventList() {



        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    url = new URL("http://bruha.com/mobile_php/EventList.php");
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
                    Log.v("Exception",e.toString());
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

               // mEvents.add(even);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

       // return mEvents;
    }



}