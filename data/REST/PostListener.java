package com.example.teamproject.data.REST;

/**
 * @author Evan Hosking
 * Used to get the response from a POST request and isolate it in a separate class
 */

public interface PostListener {
    void onSuccess(boolean result, String CustomerID, String FirstName, String Email);
}
