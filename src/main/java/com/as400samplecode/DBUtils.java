package com.as400samplecode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sun.security.jgss.GSSCaller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lihi.Martin on 1/12/2017.
 */
public class DBUtils {

    private static Connection getConnection(){
        String connectionUrl = "jdbc:mysql://localhost:3306/testing";
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionUrl, "root", "lihi3263"   );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }



    public static void insertIntoItems(String item) throws Exception{


        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        try {

            conn = getConnection();

            sql = "insert into items (item) values (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.trim());
            stmt.execute();

            stmt.close();
            stmt = null;

            conn.close();
            conn = null;

        }
        catch(Exception e){System.out.println(e);}

        finally {

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }

                conn = null;
            }
        }


    }


    public static List<String> getAllItems() {
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        List<String> itemsStrings  = new ArrayList<String>();

        try {

            conn = getConnection();

            sql = "select * from items";
            stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            for( int i = 0 ;resultSet.next(); i++) {
                String item= resultSet.getString("item");

                itemsStrings.add(i, item);
            }


            stmt.close();
            stmt = null;

            conn.close();
            conn = null;

        }
        catch(Exception e){System.out.println(e);}

        finally {

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }

                conn = null;
            }
        }
        return itemsStrings;
    }
}
