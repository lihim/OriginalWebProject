package com.as400samplecode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lihi martin on 1/25/2017.
 */
@WebServlet(name = "ItemsInformation")
public class ItemsInformation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("purchase_date");
        String store = request.getParameter("itemCode");
        String transection_amount = request.getParameter("transaction_amount");
        String numberOfPayments = request.getParameter("number_of_payments");
        String cardName = request.getParameter("card_name");

        Item item = new Item(date, store, transection_amount, numberOfPayments, cardName);

        try {
            DBUtils.insertIntoItems(item);

        } catch (Exception e) {
            e.printStackTrace();

        }
        List<Item> itemsList = DBUtils.getAllItemsByCardName(item.getCard_name());

        Gson gson = new Gson();
        String json = gson.toJson(itemsList);
        JsonObject myObj = new JsonObject();
        JsonElement jsonItems = gson.toJsonTree(itemsList);
        myObj.addProperty("success", true);

        myObj.add("itemsInfo", jsonItems);

        response.getWriter().print(myObj.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
