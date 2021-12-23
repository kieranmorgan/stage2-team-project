package com.example.teamproject.data.REST;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHandler {
    private static VolleyHandler mInstance;
    private RequestQueue mRequestQueue;
    private VolleyHandler(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized VolleyHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHandler(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}