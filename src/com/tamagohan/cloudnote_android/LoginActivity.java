package com.tamagohan.cloudnote_android;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("EXAMPLE", "onCreate was called.");
        setContentView(R.layout.activity_login);
        Button buttonLogin = (Button) findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			EditText textLogin    = (EditText) findViewById(R.id.text_login);
    			EditText textPassword = (EditText) findViewById(R.id.text_password);
    			String login    = textLogin.getText().toString();
    		    String password = textPassword.getText().toString();
    			
				try {
					StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
					Log.v("EXAMPLE", "parepare for post");
					HttpPost method = new HttpPost( "http://10.0.2.2:3000/user_sessions/create_api" );
					DefaultHttpClient client = new DefaultHttpClient();
					Log.v("EXAMPLE", "http client created.");
    				             
					// POST データの設定
					Log.v("EXAMPLE", "start to create post data.");
					String params = "login=" + login + "&password=" + password;
					StringEntity paramEntity;
					paramEntity = new StringEntity( params );

					paramEntity.setChunked( false );
					paramEntity.setContentType( "application/x-www-form-urlencoded" );
					Log.v("EXAMPLE", "post data created.");
    				             
					method.setEntity( paramEntity );
					Log.v("EXAMPLE", "params is set");
    			
					HttpResponse response = client.execute( method );
					Log.v("EXAMPLE", "post is executed.");
					int status = response.getStatusLine().getStatusCode();
					Log.v("EXAMPLE", Integer.toString(status));
					
					// test
					StringBuilder uriBuilder = new StringBuilder("http://10.0.2.2:3000/notes");
					//HttpGetを生成する
					HttpGet request = new HttpGet(uriBuilder.toString());
					HttpResponse response1 = null;
					Log.v("EXAMPLE", "http request created.");
					//リクエストする
					client.execute(request);
				} catch (UnsupportedEncodingException e) {
					Log.v("EXAMPLE", "UnsupportedEncodingException");
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					Log.v("EXAMPLE", "ClientProtocolException");
					e.printStackTrace();
				} catch (IOException e) {
					Log.v("EXAMPLE", "IOException");
					e.printStackTrace();
				}
    		}
    	});
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
