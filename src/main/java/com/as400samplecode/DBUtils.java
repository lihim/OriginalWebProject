package com.as400samplecode;



import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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


    public static void insertIntoItems(Item item) throws Exception{


        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        try {

            conn = getConnection();

            sql = "insert into items (purchase_date, store, transaction_amount, number_of_payments, debit_amount, additional_payments, card_name) values (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getPurchase_date().trim());
            stmt.setString(2, item.getStore().trim());
            stmt.setString(3, item.getTransaction_amount().trim());
            stmt.setString(4, item.getNumber_of_payments().trim());
            stmt.setString(5, calculateDebitAmount(item.getTransaction_amount(),item.getNumber_of_payments()));
            stmt.setString(6, calculateAdditionalPayments(item.getPurchase_date(),  item.getNumber_of_payments()));
            stmt.setString(7, item.getCard_name());
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


    public static List<Item> getAllItems() {
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        List<Item> itemsStrings  = new ArrayList<Item>();

        try {

            conn = getConnection();

            sql = "select * from items";
            stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            for( int i = 0 ;resultSet.next(); i++) {
                Item item= new Item(
                        resultSet.getString("purchase_date"),
                        resultSet.getString("store"),
                        resultSet.getString("transaction_amount"),
                        resultSet.getString("number_of_payments"),
                        calculateDebitAmount(resultSet.getString("transaction_amount"),resultSet.getString("number_of_payments")),
                        calculateAdditionalPayments(resultSet.getString("purchase_date"),resultSet.getString("number_of_payments")),
                        resultSet.getString("card_name")
                        );

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


    public static List<Item> getAllItemsByCardName(String cardName) {
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        List<Item> itemsStrings  = new ArrayList<Item>();

        cardName = cardName.replace("%20", " ");
        try {

            conn = getConnection();

            sql = "select * from items where card_name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardName);
            ResultSet resultSet = stmt.executeQuery();

            for( int i = 0 ;resultSet.next(); i++) {
                Item item= new Item(
                        resultSet.getString("purchase_date"),
                        resultSet.getString("store"),
                        resultSet.getString("transaction_amount"),
                        resultSet.getString("number_of_payments"),
                        calculateDebitAmount(resultSet.getString("transaction_amount"),resultSet.getString("number_of_payments")),
                        calculateAdditionalPayments(resultSet.getString("purchase_date"),resultSet.getString("number_of_payments")),
                        resultSet.getString("card_name")
                );

                if(item.getCard_name().equals(cardName)) {
                    itemsStrings.add(i, item);
                }
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


    private static String calculateDebitAmount(String transactionAmount, String numberOfPayments){
        float tempTransactionAmount = Integer.parseInt(transactionAmount);
        float tempNumberOfPayments = Integer.parseInt(numberOfPayments);
        float result = Float.parseFloat(new DecimalFormat("##.###").format(tempTransactionAmount/tempNumberOfPayments));

        return String.valueOf(result);
    }

    private static String calculateAdditionalPayments(String date, String numberOfPayments){
        String[] purchaseDate  = null;
        purchaseDate = date.split("-");

        int transactionMonth = Integer.parseInt(purchaseDate[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String thisDay = sdf.format(new Date());

        String[] thisDayArray  = null;
        thisDayArray = thisDay.split("-");

        int thisMonth = Integer.parseInt(thisDayArray[1]);

        if(thisMonth - transactionMonth < 0){
            return String.valueOf(Integer.parseInt(numberOfPayments) - (12-Math.abs(transactionMonth - thisMonth)));
        }
        else{
            return String.valueOf(Integer.parseInt(numberOfPayments) - (Math.abs(transactionMonth - thisMonth)));
        }


    }
}
