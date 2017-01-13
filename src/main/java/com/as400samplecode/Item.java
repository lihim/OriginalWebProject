package com.as400samplecode;

import java.util.Date;

/**
 * Created by lihi martin on 12/31/2016.
 */
public class Item {
    private Date date;
    private String item;
    private String store;
    private int transaction_amount;
    private int number_of_payments;
      // Date String date;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(int transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public int getNumber_of_payments() {
        return number_of_payments;
    }

    public void setNumber_of_payments(int number_of_payments) {
        this.number_of_payments = number_of_payments;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


}

