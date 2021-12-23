package com.example.teamproject.data.REST;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Class containing GET request methods
 */

public class GET {
    private Context context;

    public GET(Context context) {
        this.context = context;
    };

    /**
     * takes the customerID and retrieves details from the database
     * @param customerID
     * @param getListener
     */
    public void getBankAccountDetails(String customerID, GetListener getListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();

        String url = "https://34lgqvzvmc.execute-api.eu-west-2.amazonaws.com/v1/customerid?id="+customerID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    getListener.onSuccess(jsonArray);
                }
                catch (Exception e){
                    getListener.onSuccess(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        //Instantiate the RequestQueue and add the request to the queue
        queue.add(stringRequest);
    }


    /**
     * takes a digital card number and calculates the weekly spend
     * @param DigitalCardNumber
     * @param getListener
     */
    public void getWeeklySpend(String DigitalCardNumber, GetListener getListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();

        String url = "https://lzeu1ir8m8.execute-api.eu-west-2.amazonaws.com/v1/cardnumber?card="+DigitalCardNumber;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    getListener.onSuccess(jsonArray);
                }
                catch (Exception e){
                    getListener.onSuccess(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        //Instantiate the RequestQueue and add the request to the queue
        queue.add(stringRequest);
    }


    /**
     * takes the digital card number and collects all transactions, sorting by most recent
     * @param DigitalCardNumber
     * @param getListener
     */
    public void getTransactions(String DigitalCardNumber, GetListener getListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();

        String url = "https://g0r7k6yt3k.execute-api.eu-west-2.amazonaws.com/v1/cardnumber?card="+DigitalCardNumber;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    getListener.onSuccess(jsonArray);
                }
                catch (Exception e){
                    getListener.onSuccess(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        //Instantiate the RequestQueue and add the request to the queue
        queue.add(stringRequest);
    }


    /**
     * takes a customerID and retrieves the user's details from AWS
     * @param CustomerID
     * @param getListener
     */
    public void getUserDetails(String CustomerID, GetListener getListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();

        String url = "https://x9fpvhyjzl.execute-api.eu-west-2.amazonaws.com/v1/customerid?id="+CustomerID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    getListener.onSuccess(jsonArray);
                }
                catch (Exception e){
                    getListener.onSuccess(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        //Instantiate the RequestQueue and add the request to the queue
        queue.add(stringRequest);
    }


    /**
     * gets a list of all users from the aws database
     * @param getListener
     */
    public void getAllUsers(GetListener getListener){
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        //api url for the getAllUsers lambda function
        String url = "https://sh67etgyba.execute-api.eu-west-2.amazonaws.com/v1";
        //executes api url and waits for response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //stores the returned results
                    JSONArray jsonArray = new JSONArray(response);
                    getListener.onSuccess(jsonArray);
                }
                catch (Exception e){
                    getListener.onSuccess(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        //Instantiate the RequestQueue and add the request to the queue
        queue.add(stringRequest);
    }

    /**
     * gets a list of all pots a user has
     * @param digitalCardNumber, getListener
     */
    public void getPots(String digitalCardNumber, GetListener getListener){
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        //api url for the getPots lambda function
        String url = "https://n344fa6gkh.execute-api.eu-west-2.amazonaws.com/v1/number?cardnumber="+digitalCardNumber;
        //executes api url and waits for response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //stores the returned results
                    JSONArray jsonArray = new JSONArray(response);
                    getListener.onSuccess(jsonArray);
                }
                catch (Exception e){
                    getListener.onSuccess(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        //Instantiate the RequestQueue and add the request to the queue
        queue.add(stringRequest);
    }
}
