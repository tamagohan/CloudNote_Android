package com.tamagohan.cloudnote_android;

import android.os.Handler;
import android.os.Message;

public abstract class HttpPostHandler extends Handler {

  public void handleMessage(Message msg)
  {
    boolean isPostSuccess = msg.getData().getBoolean("http_post_success");
    String http_response = msg.getData().get("http_response").toString();
    Integer http_status = (Integer) msg.getData().get("http_status");

    if( isPostSuccess )
    {
      onPostCompleted( http_response, http_status );
    }
    else
    {
      onPostFailed( http_response, http_status );
    }
  }



  // ’ÊM¬Œ÷‚Ìˆ—
  public abstract void onPostCompleted( String response, Integer status );

  // ’ÊM¸”s‚Ìˆ—
  public abstract void onPostFailed( String response, Integer status );

}