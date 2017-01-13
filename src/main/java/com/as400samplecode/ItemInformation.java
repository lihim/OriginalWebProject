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
        try {
            DBUtils.insertIntoItems(itemCode);
            response.getWriter().print("{}");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}