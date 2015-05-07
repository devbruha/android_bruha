package com.bruha.bruha.Processing;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by Thomas on 5/5/2015.
 */
public class SQLUtils {

    // Initializing the url to the database, username and password

    private String CONNECTION_URL;
    private String user;
    private String pass;

    // Error code to be used for user notification

    private String errorCode;

    // Creating tags for debugging purposes

    private final String DB_DEBUGGING = "DB_DEBUGGING";

    // Assigning the passed in url/user/password to the previously initialized variables

    public SQLUtils(String conn_url, String user, String pass) {
        this.CONNECTION_URL = conn_url;
        this.user = user;
        this.pass = pass;
    }

    // Creating a thread to run the database call
    // to avoid main thread over usage

    public String init(final String username, final String password, final String email, final String task) {

        // Creating new thread to run the database query

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Running the function aswell as saving the errorcode into "errorCode"

                switch(task){

                    case "register":
                        errorCode = register(username, password, email);
                        break;

                    case "login":
                        errorCode = login(username, password);

                    default:
                        break;
                }

            }
        });

        //Starting thread

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

    private String register(String username, String password, String email) {

        String code = "Error Code Init Value";

        try {

            // Instantiating the JDBC library

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Creating a connection using the passed in URL, username, and password

            Connection c = DriverManager.getConnection(CONNECTION_URL, user, pass);

            // Creating the SQL statement to insert this new user into the database

            String sql = "insert into user"
                    + "(user_id, password , email)"
                    + "values ('" + username + "', '" + password + "', '" + email + "')";

            // Creates a statement from the SQL string made previously

            PreparedStatement st = c.prepareStatement(sql);

            //Exceuting the statement

            st.execute();

            //Closing both the statement and the connection

            st.close();
            c.close();

            code = "Success";
        }

        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        catch (SQLException e) {

            e.printStackTrace();
            code = e.getErrorCode()+"";
            Log.v(DB_DEBUGGING, code + "");
            Log.v(DB_DEBUGGING, e.getMessage());
        }

        catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();
            Log.v(DB_DEBUGGING, message);
        }

        return code;
    }

    private String login(String username, String password){

        String code = "Error Code Init Value";

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

            String sqlRetrieve = " SELECT * "
                    + " FROM user_info "
                    + " WHERE user_id = '" + username + "' ";

            Statement st = c.createStatement();

            // Creating a result set from the results of the query

            ResultSet rs = st.executeQuery(sqlCheck);

            if (!rs.next()) {

                // Setting code to reflect no results from the DB

                code = "badCredentials";

                //Closing both the statement and the connection

                st.close();
                c.close();
            }
            else{

                code = "Success";

                rs = st.executeQuery(sqlRetrieve);

                while( rs.next() ){

                    String user_id = rs.getString("user_id");
                    String name = rs.getString("name");
                    Date birthdate = rs.getDate("birtthdate");
                    String gender = rs.getString("gender");

                    Log.v(DB_DEBUGGING,user_id);
                    Log.v(DB_DEBUGGING,name);
                    Log.v(DB_DEBUGGING,birthdate+"");
                    Log.v(DB_DEBUGGING,gender);
                }

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
            code = e.getErrorCode()+"";
            Log.v(DB_DEBUGGING, code + "");
            Log.v(DB_DEBUGGING, e.getMessage());
        }

        catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();
            Log.v(DB_DEBUGGING, message);
        }

        return code;
    }
}
