package com.tamagohan.cloudnote_android;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;

public class MyCloudNote extends Application {

    private DefaultHttpClient httpClient;
    private Integer currentNotePage = 2;

    public DefaultHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    
    public Integer getCurrentNotePage() {
    	return currentNotePage;
    }
    
    public void incrementCurrentNotePage() {
    	this.currentNotePage = this.currentNotePage + 1;
    }
    
    public void clearCurrentNotePage() {
    	this.currentNotePage = 2;
    }
}