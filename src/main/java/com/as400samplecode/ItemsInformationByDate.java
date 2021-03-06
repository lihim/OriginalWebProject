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
 * Created by lihi martin on 1/27/2017.
 */
@WebServlet(name = "ItemsInformationByDate")
public class ItemsInformationByDate extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBUtils dbUtils = new DBUtils();
        dbUtils.updateDatabase();
        try {
            List<Item> allItems = dbUtils.getAllItemsByCardNameandDate(request.getParameter("card_name"), request.getParameter("todayDate"));
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
}
