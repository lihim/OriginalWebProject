package com.as400samplecode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lihi martin on 1/1/2017.
 */
@WebServlet(name = "ItemInformation", urlPatterns = {"/ItemInformation"})
public class ItemInformation extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ItemInformation() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Item> allItems = DBUtils.getAllItemsByCardName(request.getQueryString());
            Gson gson = new Gson();
            String json = gson.toJson(allItems);
            JsonObject myObj = new JsonObject();
            JsonElement jsonItems = gson.toJsonTree(allItems);
            myObj.addProperty("success", true);

            myObj.add("itemsInfo", jsonItems);

            response.getWriter().print(myObj.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * receives a post request from the client and sends to DB handler
     * @param request item code
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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
        List<Item> itemsList = DBUtils.getAllItems();

        Gson gson = new Gson();
        String json = gson.toJson(itemsList);
        JsonObject myObj = new JsonObject();
        JsonElement jsonItems = gson.toJsonTree(itemsList);
        myObj.addProperty("success", true);

        myObj.add("itemsInfo", jsonItems);

        response.getWriter().print(myObj.toString());

    }
}