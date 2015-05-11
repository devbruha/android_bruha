package com.bruha.bruha.Processing;

import android.util.Log;

import com.bruha.bruha.Model.SQLiteDatabaseModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Thomas on 5/5/2015.
 */
public class SQLUtils {

    // Initializing the url to the database, username and password

    private String CONNECTION_URL;
    private String user;
    private String pass;

    // Initializing the result set variable rs
    ResultSet rs = null;

    // Initializing a list to store the results from "rs" to pass back to other activity
    List<String> user_info = new ArrayList<>();

    // Error code to be used for user notification

    private String errorCode =  "Error Code Init Value";

    // Creating tags for debugging purposes

    private final String DB_DEBUGGING = "DB_DEBUGGING";

    // Assigning the passed in url/user/password to the previously initialized variables

    public SQLUtils(String conn_url, String user, String pass) {
        this.CONNECTION_URL = conn_url;
        this.user = user;
        this.pass = pass;
    }

    public String loginCheck(final String username, final String password){

        // Creating a thread to run the database interaction on a seperate thread

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    // Instantiating the JDBC library

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    // Creating a connection using the passed in URL, username, and password

                    Connection c = DriverManager.getConnection(CONNECTION_URL, user, pass);

                    // Creating the SQL statements for checking if valid user credentials and for
                    // retrieving the user information

                    String sqlCheck = " SELECT USER_ID "
                            + " FROM user "
                            + " WHERE user_id = '" + username + "' AND password = '" + password + "' ";

                    Statement st = c.createStatement();

                    // Creating a result set from the results of the query

                    rs = st.executeQuery(sqlCheck);

                    if (!rs.next()) {

                        // Setting code to reflect no results from the DB

                        errorCode = "badCredentials";

                        //Closing both the statement and the connection

                        st.close();
                        c.close();
                    }
                    else{

                        errorCode = "Success";

                        //Closing both the statement and the connection

                        st.close();
                        c.close();
                    }
                }

                catch (ClassNotFoundException e) {

                    e.printStackTrace();
                }

                catch (SQLException e) {

                    e.printStackTrace();
                    errorCode = e.getErrorCode()+"";
                    Log.v(DB_DEBUGGING, errorCode + "");
                    Log.v(DB_DEBUGGING, e.getMessage());
                }

                catch (Exception e){
                    e.printStackTrace();
                    String message = e.getMessage();
                    Log.v(DB_DEBUGGING, message);
                }
            }
        });

        // Execution of the thread

        thread.start();

        // Using the thread.join function in order to ensure that the thread is waited on before
        // returning errorCode, otherwise null pointer error

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return errorCode;
    }

    public String register(final String username, final String password, final String email) {

        // Creating a thread to run the database interaction on a seperate thread

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    // Instantiating the JDBC library

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    // Creating a connection using the passed in URL, username, and password

                    Connection c = DriverManager.getConnection(CONNECTION_URL, user, pass);

                    // Creating the SQL statement to insert this new user into the database

                    String sql = "insert into user"
                            + "(user_id, password , email)"
                            + "values ('" + username + "', '" + password + "', '" + email + "')";

                    // Creating a statement from the SQL string made previously

                    PreparedStatement st = c.prepareStatement(sql);

                    //Exceuting the statement

                    st.execute();

                    //Closing both the statement and the connection

                    st.close();
                    c.close();

                    errorCode = "Success";
                }

                catch (ClassNotFoundException e) {

                    e.printStackTrace();
                }

                catch (SQLException e) {

                    e.printStackTrace();
                    errorCode = e.getErrorCode()+"";
                    Log.v(DB_DEBUGGING, errorCode + "");
                    Log.v(DB_DEBUGGING, e.getMessage());
                }

                catch (Exception e){
                    e.printStackTrace();
                    String message = e.getMessage();
                    Log.v(DB_DEBUGGING, message);
                }
            }
        });

        thread.start();

        // Running thread.join so ensure the operation finishes before the main thread returns
        // the errorCode value

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return errorCode;
    }

    public List<String> loginDatabasePush( final String username ){

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    // Instantiating the JDBC library

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    // Creating a connection using the passed in URL, username, and password

                    Connection c = DriverManager.getConnection(CONNECTION_URL, user, pass);

                    // Creating the SQL statements for checking if valid user credentials and for
                    // retrieving the user information

                    String sqlRetrieve = " SELECT * "
                            + " FROM user_info "
                            + " WHERE user_id = '" + username + "' ";

                    Statement st = c.createStatement();

                    // Creating a result set from the results of the query

                    rs = st.executeQuery(sqlRetrieve);

                    // A loop to run through all values from the resultSet

                    while (rs.next()) {

                        // Adding the 4 results from the resultSet to the user_info list
                        // which has been initialized at the start of the class

                        user_info.add(rs.getString("user_id"));
                        user_info.add(rs.getString("name"));

                        Date birthdate = rs.getDate("birtthdate");
                        String birthdateString = birthdate + "";
                        user_info.add(birthdateString);

                        user_info.add(rs.getString("gender"));
                    }

                    //Closing both the statement and the connection

                    st.close();
                    c.close();
                }

                catch (ClassNotFoundException e) {

                    e.printStackTrace();
                }

                catch (SQLException e) {

                    e.printStackTrace();
                    Log.v(DB_DEBUGGING, e.getMessage());
                }

                catch (Exception e){
                    e.printStackTrace();
                    String message = e.getMessage();
                    Log.v(DB_DEBUGGING, message);
                }
            }
        });

        thread.start();

        // Running thread.join so ensure the operation finishes before the main thread returns
        // the errorCode value

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user_info;
    }
}
