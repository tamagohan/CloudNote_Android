package com.tamagohan.cloudnote_android;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.util.Log;

public class MyCloudNote extends Application {

    private DefaultHttpClient httpClient;

    public DefaultHttpClient getHttpClient() {
    	Log.d("a", "get");
        return httpClient;
    }

    public void setHttpClient(DefaultHttpClient httpClient) {
    	Log.d("a", "set");
        this.httpClient = httpClient;
    }
}