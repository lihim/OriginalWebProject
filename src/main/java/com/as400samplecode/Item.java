
package com.as400samplecode;

/**
 * Created by lihi martin on 12/31/2016.
 */
public class Item {
    private String purchase_date;
    private String store;
    private String transaction_amount;
    private String number_of_payments;
    private String debit_amount;
    private String additional_payments;
    private String card_name;
      // Date String purchase_date;



    Item(String purchase_date, String store, String transaction_amount, String number_of_payments, String debit_amount, String additional_payments, String card_name){
        this.purchase_date = purchase_date;
        this.store = store;
        this.transaction_amount = transaction_amount;
        this.number_of_payments = number_of_payments;
        this.debit_amount = debit_amount;
        this.additional_payments = additional_payments;
        this.card_name = card_name;
//;
    }

    Item(String purchase_date, String store, String transaction_amount, String number_of_payments, String card_name){
        this.purchase_date = purchase_date;
        this.store = store;
        this.transaction_amount = transaction_amount;
        this.number_of_payments = number_of_payments;
        this.debit_amount = null;
        this.additional_payments = null;
        this.card_name = card_name;
//;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }


    public String getDebit_amount() {
        return debit_amount;
    }

    public void setDebit_amount(String debit_amount) {
        this.debit_amount = debit_amount;
    }

    public String getAdditional_payments() {
        return additional_payments;
    }

    public void setAdditional_payments(String additional_payments) {
        this.additional_payments = additional_payments;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
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

}

