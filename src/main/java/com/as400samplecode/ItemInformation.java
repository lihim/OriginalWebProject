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
import java.util.Arrays;
import java.util.List;

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
            List<String> allItems = DBUtils.getAllItems();

            response.getWriter().print(Arrays.asList(allItems));
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


        String itemCode = request.getParameter("itemCode");
      //  PrintWriter out = response.getWriter();
        try {
            DBUtils.insertIntoItems(itemCode);

        } catch (Exception e) {
            e.printStackTrace();

        }
        List<String> itemsList = DBUtils.getAllItems();

        Gson gson = new Gson();
        String json = gson.toJson(itemsList);
        JsonObject myObj = new JsonObject();
        JsonElement jsonItems = gson.toJsonTree(itemsList);
        myObj.addProperty("success", true);
//        for(String item : itemsList){
//            if(item.contains("aaa")){
//                myObj.addProperty("success", false);
//                break;
//            }
//            else {
//                Item newItem = new Item();
//                newItem.setItem(item);
//            }
//        }
        myObj.add("itemsInfo", jsonItems);

        response.getWriter().print(myObj.toString());
     //   out.close();
    }
}