package com.tamagohan.cloudnote_android;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;

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
	
    // GET通信を実行
	private void exec_get(String url, Map<String, String> params, DefaultHttpClient httpClient) {

      // 非同期タスクを定義
      HttpGetTask task = new HttpGetTask(
        this,
        "http://10.0.2.2:3000/" + url,

        // タスク完了時に呼ばれるUIのハンドラ
        new HttpPostHandler(){

          @Override
          public void onPostCompleted(String response, Integer status) {
            // 成功ならばノートのリスト画面へ遷移
        	  Log.v("EXAMPLE", "post success");
          }

          @Override
          public void onPostFailed(String response, Integer status) {
        	  Log.v("EXAMPLE", Integer.toString(status));
        	  Log.d("http request error", response);
        	  String err_msg = "通信エラーが発生しました。";
        	  if (status == 403){
        		  err_msg = "ログインに失敗しました。\nログインIDまたはパスワードが間違っています。";
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

      // タスクを開始
      task.execute();
    }
}