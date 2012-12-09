package com.tamagohan.cloudnote_android;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class NoteListActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        final Map<String, String> params = new HashMap<String, String>();
	    DefaultHttpClient httpClient = ((MyCloudNote) this.getApplication()).getHttpClient();
	    exec_get("notes", params, httpClient);
	}
	
    // GET�ʐM�����s
	private void exec_get(String url, Map<String, String> params, DefaultHttpClient httpClient) {

      // �񓯊��^�X�N���`
      HttpGetTask task = new HttpGetTask(
        this,
        "http://10.0.2.2:3000/" + url,

        // �^�X�N�������ɌĂ΂��UI�̃n���h��
        new HttpPostHandler(){

          @Override
          public void onPostCompleted(String response, Integer status) {
            // �����Ȃ�΃m�[�g�̃��X�g��ʂ֑J��
        	  Log.v("EXAMPLE", "post success");
        	  Log.v("EXAMPLE", response);
        	  try {
        		  Log.d("tmp", "parse start");
        		  JSONArray _jsonArr = new JSONArray(response);
        		  Log.d("tmp", "parse success");
        		  for (int i = 0; i < _jsonArr.length(); i++) {
        			  JSONObject jsonObject = _jsonArr.getJSONObject(i);
        			  Log.d("look",jsonObject.getString("title"));
        			  Log.d("look",jsonObject.getString("body"));
        		  }
        	  } catch (JSONException e) {
        		  Log.d("tmp", "parse fail");
        		  // TODO �����������ꂽ catch �u���b�N
        		  e.printStackTrace();
        	  }
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

      // �^�X�N���J�n
      task.execute();
    }
}