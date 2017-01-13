package com.as400samplecode;

import java.util.Date;

/**
 * Created by lihi martin on 12/31/2016.
 */
public class Item {
    private String date;
    private String item;
    private String store;
    private String transaction_amount;
    private String number_of_payments;
      // Date String date;

    Item(String date, String store, String transaction_amount, String number_of_payments){
        this.date = date;
        this.store = store;
        this.transaction_amount = transaction_amount;
        this.number_of_payments = number_of_payments;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getNumber_of_payments() {
        return number_of_payments;
    }

    public void setNumber_of_payments(String number_of_payments) {
        this.number_of_payments = number_of_payments;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


}

