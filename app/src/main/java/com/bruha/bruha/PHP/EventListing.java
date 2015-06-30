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

        import com.bruha.bruha.Model.Event;
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

                GetUserEventList(mUsername);
            }
        });
    }

    public void GetUserEventList(String user) {


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
                    Log.v("YEAH",response);
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

               // com.bruha.bruha.Model.Event even = new Event();

                UserInfo.add(User.getString("Name"));
               UserInfo.add(User.getString("Username"));
                UserInfo.add(User.getString("Gender"));
                UserInfo.add(User.getString("Birthdate"));;
                UserInfo.add(User.getString("Email"));
                UserInfo.add(User.getString("City"));


                Log.v("USER:", UserInfo.get(1));
            }


            Log.v("USERTEST:", response);
            //return mUserEvents;
        } catch (JSONException e) {
            e.printStackTrace();
        }

       // return mUserEvents;
    }



}