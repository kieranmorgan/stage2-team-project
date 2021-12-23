package com.example.teamproject.data.REST;

import org.json.JSONArray;

/**
 * @author Evan Hosking
 * Used to get the response from a GET request and isolate it in a separate class
 */

public interface GetListener {
    void onSuccess(JSONArray result);

}
