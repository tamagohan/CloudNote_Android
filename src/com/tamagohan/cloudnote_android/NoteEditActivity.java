package com.tamagohan.cloudnote_android;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEditActivity extends Activity {
	String title = null;
	String body  = null;
	String id    = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Log.v("activity", "onCreate of noteEditActivity was called.");
        setContentView(R.layout.activity_note_edit);
        final EditText titleText = (EditText) findViewById(R.id.note_title);
        final EditText bodyText  = (EditText) findViewById(R.id.note_body);
		Button updateButton = (Button) findViewById(R.id.note_update);
		Button backButton = (Button) findViewById(R.id.note_back);
		final Bundle extras  = getIntent().getExtras();
		
		titleText.setText(extras.getString("TITLE"));
		bodyText .setText(extras.getString("BODY"));
		
		final DefaultHttpClient httpClient = ((MyCloudNote) this.getApplication()).getHttpClient();
		
	    updateButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			title = titleText.getText().toString();
    		    body  = bodyText .getText().toString();
    		    id    = extras.getString("ID");
    		    final Map<String, String> params = new HashMap<String, String>();
    		    params.put("title", title);
    		    params.put("body",  body);
    		    params.put("id",    id);
    		    params.put("_method", "PUT");
    		    
    		    String path = "notes/" + extras.getString("ID");
    		    exec_post(path, params, httpClient);
   			}
   		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			finish();
   			}
   		});
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
        	  Log.v("http request success", "post success");
        	  Intent intent = new Intent(NoteEditActivity.this, NoteListActivity.class);
        	  intent.putExtra("FROM_ACTIVITY", "edit");
        	  intent.putExtra("MESSAGE",       "�X�V�ɐ������܂����B");
        	  startActivity(intent);
          }

          @Override
          public void onPostFailed(String response, Integer status) {
        	  Log.v("http request error", Integer.toString(status));
        	  Log.d("http request error", response);
        	  String err_msg = "�ʐM�G���[���������܂����B";
        	  if (status == 403){
        		  err_msg = "�X�V�Ɏ��s���܂����B";
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