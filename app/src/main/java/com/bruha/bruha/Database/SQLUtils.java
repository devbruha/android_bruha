package com.bruha.bruha.Database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by Thomas on 5/5/2015.
 */
public class SQLUtils {

    // Initializing the url to the database, username and password

    private String CONNECTION_URL;
    private String user;
    private String pass;

    // Assigning the passed in url/user/password to the previously initialized variables

    public SQLUtils(String conn_url, String user, String pass) {
        this.CONNECTION_URL = conn_url;
        this.user = user;
        this.pass = pass;
    }

    // Creating a thread to run the database call
    // to avoid main thread over usage

    public void init(final String username,final String password,final String email) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                insert(username, password, email);

            }
        }).start();
    }

    private void insert(String username, String password, String email) {

        try {

            // Instantiating the JDBC library

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Creating a connection using the passed in URL, username, and password

            Connection c = DriverManager.getConnection(CONNECTION_URL, user, pass);

            // Creating the SQL statement to insert this new user into the database
            // Eventually a check shall need to be implemented for no repeats, etc

            String sql = "insert into user"
                    + "(user_id, password , email)"
                    + "values ('"+username+"', '"+password+"', '"+email+"')";

            // Creates a statement from the SQL string made previously

            PreparedStatement st = c.prepareStatement(sql);

            //Exceuting the statement

            st.execute();

            //Closing both the statement and the connection

            st.close();
            c.close();
        }

        catch (ClassNotFoundException e) {

            e.printStackTrace();

        }
        catch (SQLException e) {

            e.printStackTrace();
            Log.d("sql", "SQL Insert Fail");

        }
        catch (InstantiationException e) {
            e.printStackTrace();

        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
