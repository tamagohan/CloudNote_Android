package com.tamagohan.cloudnote_android;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {
	private DefaultHttpClient httpClient = new DefaultHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("EXAMPLE", "onCreate was called.");
        setContentView(R.layout.activity_login);
        Button buttonLogin = (Button) findViewById(R.id.button_login);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.login_fail);
        builder.setPositiveButton(R.string.button_close_dialog, new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int which) {
        	setResult(RESULT_OK);
        	}
        });
        
	    ((MyCloudNote) this.getApplication()).setHttpClient(httpClient);

	    buttonLogin.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			EditText textLogin    = (EditText) findViewById(R.id.text_login);
    			EditText textPassword = (EditText) findViewById(R.id.text_password);
    			final String login    = textLogin.getText().toString();
    		    final String password = textPassword.getText().toString();
    		    final Map<String, String> params = new HashMap<String, String>();
    		    params.put("login",    login);
    		    params.put("password", password);
    		    exec_post("user_sessions/create_api", params, httpClient);
   			}
   		});
   	}
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    // �߂�{�^���𖳌�������
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                // �_�C�A���O�\���ȂǓ���̏������s�������ꍇ�͂����ɋL�q
                // �e�N���X��dispatchKeyEvent()���Ăяo������true��Ԃ�
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
    
    // POST�ʐM�����s
    private void exec_post(String url, Map<String, String> params, final DefaultHttpClient httpClient) {

      // �񓯊��^�X�N���`
      HttpPostTask task = new HttpPostTask(
        this,
        Constants.SERVER_URL + url,

        // �^�X�N�������ɌĂ΂��UI�̃n���h��
        new HttpPostHandler(){

          @Override
          public void onPostCompleted(String response, Integer status) {
            // �����Ȃ�΃m�[�g�̃��X�g��ʂ֑J��
        	  Log.v("EXAMPLE", "post success");
        	  Intent intent = new Intent(LoginActivity.this, NoteListActivity.class);
        	  startActivity(intent);
          }

          @Override
          public void onPostFailed(String response, Integer status) {
        	  Log.v("EXAMPLE", Integer.toString(status));
        	  Log.d("http request error", response);
        	  String err_msg = "�ʐM�G���[���������܂����B";
        	  if (status == 403){
        		  err_msg = "���O�C���Ɏ��s���܂����B\n���O�C��ID�܂��̓p�X���[�h���Ԉ���Ă��܂��B";
        	  }
            Toast.makeText(
              getApplicationContext(), 
              err_msg, 
              Toast.LENGTH_LONG
            ).show();
          }
        },
        httpClient
      );
      // �p�����[�^��ݒ�
      for (Map.Entry<String, String> entry : params.entrySet()) {
    	  String key = entry.getKey();
    	  String val = entry.getValue();
    	  task.addPostParam( key, val );
      }

      // �^�X�N���J�n
      task.execute();
    }
}
