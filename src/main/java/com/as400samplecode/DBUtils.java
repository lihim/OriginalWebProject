package com.as400samplecode;



import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Lihi.Martin on 1/12/2017.
 */
public class DBUtils {

    private int cardPurchaseDay = 2;
    private Connection getConnection(){
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


    public void insertIntoItems(Item item) throws Exception{


        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        try {

            conn = getConnection();

            sql = "insert into items (purchase_date, store, transaction_amount, number_of_payments, debit_amount, additional_payments, card_name, deleted) values (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getPurchase_date().trim());
            stmt.setString(2, item.getStore().trim());
            stmt.setString(3, item.getTransaction_amount().trim());
            stmt.setString(4, String.valueOf(item.getNumber_of_payments()));
            stmt.setString(5, calculateDebitAmount(item.getTransaction_amount(),String.valueOf(item.getNumber_of_payments())));
            int additionalPayments = Integer.parseInt(calculateAdditionalPayments(item.getPurchase_date(),  String.valueOf(item.getNumber_of_payments())));
            if(additionalPayments < 0){
                stmt.setString(6, "0");
                stmt.setString(7, item.getCard_name());
                stmt.setString(8, "1");
            }
            else {
                stmt.setString(6, calculateAdditionalPayments(item.getPurchase_date(), String.valueOf(item.getNumber_of_payments())));
                stmt.setString(7, item.getCard_name());
                stmt.setString(8, "0");
            }
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


    public void insertIntoItems(String item) throws Exception{


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


    public List<Item> getAllItems() {
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        List<Item> itemsStrings  = new ArrayList<Item>();

        try {

            conn = getConnection();

            sql = "select * from items where deleted = FALSE ";
            stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            for( int i = 0 ;resultSet.next(); i++) {
                Item item= new Item(
                    resultSet.getString("purchase_date"),
                    resultSet.getString("store"),
                    resultSet.getString("transaction_amount"),
                    Integer.parseInt(resultSet.getString("number_of_payments")),
                    resultSet.getString("debit_amount"),
                    Integer.parseInt(resultSet.getString("additional_payments")),
                    resultSet.getString("card_name"));

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
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }
            }
        }
        return itemsStrings;
    }

    public List<Item> getAllItemsByCardName(String cardName) {
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        List<Item> itemsStrings  = new ArrayList<Item>();
        cardName = cardName.replace("%20", " ");
        try {
            conn = getConnection();
            sql = "select * from items where card_name = ? and deleted = false";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardName);
            ResultSet resultSet = stmt.executeQuery();

            for( int i = 0 ;resultSet.next(); i++) {
                Item item= new Item(
                        resultSet.getString("purchase_date"),
                        resultSet.getString("store"),
                        resultSet.getString("transaction_amount"),
                        Integer.valueOf(resultSet.getString("number_of_payments")),
                        calculateDebitAmount(resultSet.getString("transaction_amount"),resultSet.getString("number_of_payments")),
                        Integer.valueOf(calculateAdditionalPayments(resultSet.getString("purchase_date"),resultSet.getString("number_of_payments"))),
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

    public List<Item> getAllItemsByCardNameandDate(String cardName, String date) throws ParseException {
        List<Item> itemsListToReturn = new ArrayList<Item>();
        List<Item> itemsList = getAllItemsByCardName(cardName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdf.parse(date);

        for(Item item : itemsList){
            Date purchaseDay = sdf.parse(item.getPurchase_date());
            if(today.compareTo(purchaseDay) > 0){
                if((item.getNumber_of_payments() -(today.getMonth()-purchaseDay.getMonth())) >0){
                    itemsListToReturn.add(item);
                }
            }
        }

        return itemsList;
    }

    public List<Item> getAllItemsByDate(String date) {
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;
        List<Item> itemsStrings  = new ArrayList<Item>();

        try {

            conn = getConnection();

            sql = "select * from items B where B.purchase_date >= ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, date);
            ResultSet resultSet = stmt.executeQuery();

            for( int i = 0 ;resultSet.next(); i++) {
                Item item= new Item(
                        resultSet.getString("purchase_date"),
                        resultSet.getString("store"),
                        resultSet.getString("transaction_amount"),
                        Integer.valueOf(resultSet.getString("number_of_payments")),
                        calculateDebitAmount(resultSet.getString("transaction_amount"),resultSet.getString("number_of_payments")),
                        Integer.valueOf(calculateAdditionalPayments(resultSet.getString("purchase_date"),resultSet.getString("number_of_payments"))),
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
            }
        }
        return itemsStrings;
    }


    private  String calculateDebitAmount(String transactionAmount, String numberOfPayments){
        float tempTransactionAmount = Integer.parseInt(transactionAmount);
        float tempNumberOfPayments = Integer.parseInt(numberOfPayments);
        float result = Float.parseFloat(new DecimalFormat("##.###").format(tempTransactionAmount/tempNumberOfPayments));

        return String.valueOf(result);
    }

    private  String calculateAdditionalPayments(String date, String numberOfPayments){
        String[] purchaseDate  = null;
        purchaseDate = date.split("-");

        int transactionMonth = Integer.parseInt(purchaseDate[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String thisDay = sdf.format(new Date());

        String[] thisDayArray  = null;
        thisDayArray = thisDay.split("-");
/*        int thisMonth = Integer.parseInt(thisDayArray[1]);
        int thisyear = Integer.parseInt(thisDayArray[0]);*/

        int todayYear = 2016;
        int todayMonth = 5;
        int todayDay = 12;



        if(todayMonth - transactionMonth < 0){
            if(todayYear == Integer.parseInt(purchaseDate[0])){
               //todo exception for illegal input
            }
            return String.valueOf(Integer.parseInt(numberOfPayments) - (12-Math.abs(transactionMonth - todayMonth)));
        }
        else{
            return String.valueOf(Integer.parseInt(numberOfPayments) - (Math.abs(transactionMonth - todayMonth)));
        }


    }

    public void updateDatabase(){
        List<Item> itemsList = getAllItems();
        for(Item item: itemsList){
            updateRecod(item );
        }
    }

    public boolean updateRecod(Item item){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String thisDay = sdf.format(new Date());
        String[] thisDayArray  = thisDay.split("-");
        String[] purchaseDate  = item.getPurchase_date().split("-");

        //int todayYear = Integer.valueOf(thisDayArray[0]);
        //int todayMonth = Integer.valueOf(thisDayArray[1]);
        //int todayDay = Integer.valueOf(thisDayArray[2]);
        int todayYear = 2016;
        int todayMonth = 5;
        int todayDay = 12;

        int purchaseYear = Integer.valueOf(purchaseDate[0]);
        int purchaseMonth = Integer.valueOf(purchaseDate[1]);
        int purchaseDay = Integer.valueOf(purchaseDate[2]);


        if(todayYear == purchaseYear){
            if(todayMonth == purchaseMonth){
                return true;
            }
            else{//todayMonth > purchaseMonth
                    if(Integer.valueOf(item.getNumber_of_payments()) >= 1){
                        if(item.getNumber_of_payments() - todayMonth +1 <0){
                            deleteRecode(item);
                        }else {
                            if(todayMonth - purchaseMonth -1 != item.getAdditional_payments()) {
                                item.setAdditional_payments(item.getAdditional_payments() - todayMonth + 1);
                                updateAdditionalPaymentsRecord(item, item.getAdditional_payments());
                            }
                        }
                    }

            }

        }
        else{//purchase year was a year before
           // if()
        }

        return false;

    }




    public void updateAdditionalPaymentsRecord(Item item, int newAdditionalPayments){
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;

        try {
            conn = getConnection();

            sql = "update items set additional_payments =? where purchase_date = ? and debit_amount = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(String.valueOf(newAdditionalPayments)));
            stmt.setString(2, item.getPurchase_date());
            stmt.setDouble(3, Double.parseDouble(item.getDebit_amount()));
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
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }
            }
        }

    }

    public void deleteRecode(Item item){
        String sql = null;
        PreparedStatement stmt=null;
        Connection conn=null;

        try {
            conn = getConnection();

            sql = "update items set deleted = true where purchase_date = ? and store = ? and transaction_amount = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getPurchase_date());
            stmt.setString(2, item.getStore());
            stmt.setString(3, item.getTransaction_amount());

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
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlex) {
                    // ignore -- as we can't do anything about it here
                }
            }
        }

    }


}
