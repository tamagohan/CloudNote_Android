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

public class NoteNewActivity extends Activity {
	String title = null;
	String body  = null;
	String id    = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("EXAMPLE", "onCreate of noteNewActivity was called.");
        setContentView(R.layout.activity_note_new);
        final EditText titleText = (EditText) findViewById(R.id.note_title);
        final EditText bodyText  = (EditText) findViewById(R.id.note_body);
		Button createButton = (Button) findViewById(R.id.note_create);
		Button backButton =   (Button) findViewById(R.id.note_back);
		
		final DefaultHttpClient httpClient = ((MyCloudNote) this.getApplication()).getHttpClient();
		
	    createButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			title = titleText.getText().toString();
    		    body  = bodyText .getText().toString();
    		    final Map<String, String> params = new HashMap<String, String>();
    		    params.put("title", title);
    		    params.put("body",  body);
    		    
    		    String path = "notes/";
    		    exec_post(path, params, httpClient);
   			}
   		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			finish();
   			}
   		});
   	}
    
    // POST通信を実行
    private void exec_post(String url, Map<String, String> params, final DefaultHttpClient httpClient) {

      // 非同期タスクを定義
      HttpPostTask task = new HttpPostTask(
        this,
        Constants.SERVER_URL + url,

        // タスク完了時に呼ばれるUIのハンドラ
        new HttpPostHandler(){

          @Override
          public void onPostCompleted(String response, Integer status) {
            // 成功ならばノートのリスト画面へ遷移
        	  Log.v("http request success", "post success");
        	  Intent intent = new Intent(NoteNewActivity.this, NoteListActivity.class);
        	  intent.putExtra("FROM_ACTIVITY", "new");
        	  intent.putExtra("MESSAGE",       "作成に成功しました。");
        	  startActivity(intent);
          }

          @Override
          public void onPostFailed(String response, Integer status) {
        	  Log.v("http request error", Integer.toString(status));
        	  Log.d("http request error", response);
        	  String err_msg = "通信エラーが発生しました。";
        	  if (status == 403){
        		  err_msg = "更新に失敗しました。";
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
      // パラメータを設定
      for (Map.Entry<String, String> entry : params.entrySet()) {
    	  String key = entry.getKey();
    	  String val = entry.getValue();
    	  task.addPostParam( key, val );
      }

      // タスクを開始
      task.execute();
    }
}