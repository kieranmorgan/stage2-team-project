package com.example.teamproject.data.REST;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Evan Hosking
 * Class containing POST request methods
 */

public class POST {
    private Context context;

    public POST(Context context) {
        this.context = context;
    }

    /**
     * takes the user's name and password and authenticates details stored in aws database
     * @param username
     * @param password
     * @param postListener
     */
    public void authenticate(String username, String password, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://g3yizxc97f.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        JSONObject data = (JSONObject) response.get("body");
                        String customerID = data.getString("customerid");
                        String firstName = data.getString("firstname");
                        String email = data.getString("email");
                        postListener.onSuccess(true, customerID, firstName, email);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                   e.printStackTrace();
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * takes the user's details, and passes them into a lambda function which stores them in the
     * database
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param email
     * @param password
     * @param postListener
     */
    public void register(String firstName, String lastName, String phoneNumber, String email, String password, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://aj8vi5gczg.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("firstname", firstName);
            postData.put("lastname", lastName);
            postData.put("phonenumber", phoneNumber);
            postData.put("email", email);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        JSONObject data = (JSONObject) response.get("body");
                        String customerID = data.getString("customerid");
                        String firstName = data.getString("firstname");
                        String email = data.getString("email");
                        postListener.onSuccess(true, customerID, firstName, email);
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * creates a new user account (bank account not StuBank account) with a set balance
     * @param customerID
     * @param balance
     * @param lastName
     * @param postListener
     */
    public void createAccount(String customerID, double balance, String lastName, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://h50o9yayr4.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("customerid", customerID);
            postData.put("balance", balance);
            postData.put("lastname", lastName);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * takes an admins id and passsword, and authenticates their login through aws lambda function
     * @param AdminID
     * @param password
     * @param postListener
     */
    public void authenticateAdmin(String AdminID, String password, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        //api url for the adminLogin lambda function
        String postUrl = "https://eckj9i91e6.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        //stores the given username and password
        try {
            postData.put("AdminID", AdminID);
            postData.put("password", password);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //executes the url and waits for response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //gets status code to check if the login was a succes
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        //gets the details of the logged in admin
                        JSONObject data = (JSONObject) response.get("body");
                        String adminID = data.getString("AdminID");
                        String firstName = data.getString("firstname");
                        String email = data.getString("email");
                        //returns that the function worked nad the admin details
                        postListener.onSuccess(true, adminID, firstName, email);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    //returns false and null values to show that the login attempt failed
                    e.printStackTrace();
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     *
     * @param DigitalCardNumber
     * @param balance
     * @param Time
     * @param Date
     * @param Transactor
     * @param postListener
     */
    public void generateTransferTransaction(String DigitalCardNumber, double balance, String Time, String Date, String Transactor, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://buagzf8sdb.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("DigitalCardNumber", DigitalCardNumber);
            postData.put("Balance", balance);
            postData.put("Type", "Transfer");
            postData.put("Time", Time);
            postData.put("Date", Date);
            postData.put("Recipient", Transactor);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Creates an admin account with the given user details
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param email
     * @param password
     * @param postListener
     */
    public void registerAdmin(String firstName, String lastName, String phoneNumber, String email, String password, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        //api call for adminRegister lambda function
        String postUrl = "https://4tvny0q4ah.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        //stores the given user data
        try {
            postData.put("firstname", firstName);
            postData.put("lastname", lastName);
            postData.put("phonenumber", phoneNumber);
            postData.put("email", email);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //executes url code with postData object containing the given user data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //gets status code to check if succeeded
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        //stores in the data so it can be used for the current user
                        JSONObject data = (JSONObject) response.get("body");
                        String customerID = data.getString("adminid");
                        String firstName = data.getString("firstname");
                        String email = data.getString("email");
                        //returns the credentials
                        postListener.onSuccess(true, customerID, firstName, email);
                    } else {
                        throw new Exception();
                    }// error for if the api call failed, return false and null values
                } catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Creates a purchase transaction, allowing users to record purchases from shops/retailers
     * @param DigitalCardNumber
     * @param balance
     * @param Time
     * @param Date
     * @param shopName
     * @param postListener
     */
    public void generatePurchaseTransaction(String DigitalCardNumber, double balance, String Time, String Date, String shopName, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://bedpadgmlf.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("DigitalCardNumber", DigitalCardNumber);
            postData.put("Balance", balance);
            postData.put("Type", "Purchase");
            postData.put("Time", Time);
            postData.put("Date", Date);
            postData.put("Recipient", shopName);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Withdraws funds from specified bank account
     * @param DigitalCardNumber
     * @param balance
     * @param Time
     * @param Date
     * @param postListener
     */
    public void generateWithdrawTransaction(String DigitalCardNumber, double balance, String Time, String Date, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://mprwjijyvj.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("DigitalCardNumber", DigitalCardNumber);
            postData.put("Balance", balance);
            postData.put("Type", "Withdraw");
            postData.put("Time", Time);
            postData.put("Date", Date);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Deletes user based on customerID, for admin use only
     * @param custID
     * @param postListener
     */
    public void deleteUser(String custID, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://82p7x0w28g.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("customerid", custID);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Creates a pot associated to a bank account
     * @param potName
     * @param potDescription
     * @param digitalCardNumber
     * @param amount
     * @param time
     * @param date
     * @param postListener
     */
    public void createPot(String potName, String potDescription, String digitalCardNumber, double amount, String time, String date, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://pnt04siqvk.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("potname", potName);
            postData.put("digitalcardnumber", digitalCardNumber);
            postData.put("potdescription", potDescription);
            postData.put("amount", amount);
            postData.put("type", "Pot");
            postData.put("time", time);
            postData.put("date", date);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Deletes current users pot (associated with bank account), also adds pot balance back to associated account
     * @param potName
     * @param digitalCardNumber
     * @param postListener
     */
    public void deletePot(String potName, String digitalCardNumber, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://3k4zdhwl8l.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("DigitalCardNumber", digitalCardNumber);
            postData.put("PotName", potName);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * Adds funds to current users pot
     * @param potName
     * @param digitalCardNumber
     * @param amount
     * @param time
     * @param date
     * @param postListener
     */
    public void addToPot(String potName, String digitalCardNumber, double amount, String time, String date, PostListener postListener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getRequestQueue();
        String postUrl = "https://zqyg39wcx6.execute-api.eu-west-2.amazonaws.com/v1";
        JSONObject postData = new JSONObject();
        try {
            postData.put("PotName", potName);
            postData.put("DigitalCardNumber", digitalCardNumber);
            postData.put("Amount", amount);
            postData.put("Time", time);
            postData.put("Date", date);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorCode = response.get("statusCode").toString();
                    if (errorCode.equals("200")) {
                        postListener.onSuccess(true, null, null, null);
                    }
                    else {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    postListener.onSuccess(false, null, null, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }



}



