package com.tamagohan.cloudnote_android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class NoteListActivity extends ListActivity {
	private JSONArray _jsonArr = null;
	private Context context = this;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_note_list);
        final Map<String, String> params = new HashMap<String, String>();
        params.put("page", "1");
	    DefaultHttpClient httpClient = ((MyCloudNote) this.getApplication()).getHttpClient();
	    Log.d("tmp", "note list activity start");
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
        	  Log.v("EXAMPLE", response);
        	  try {
        		  Log.d("tmp", "parse start");
        		  _jsonArr = new JSONArray(response);
        		  Log.d("tmp", "parse success");
        		  for (int i = 0; i < _jsonArr.length(); i++) {
        			  JSONObject jsonObject = _jsonArr.getJSONObject(i);
        			  Log.d("look",jsonObject.getString("title"));
        			  Log.d("look",jsonObject.getString("body"));
        		  }
        		  Log.d("tmp", "view start");
        	      ArrayList<ArrayList> list = jsonArrayToArrayList(_jsonArr);
        	      Log.d("tmp", "json transfer is finish");
        	  	  NoteListAdapter adapter = new NoteListAdapter(context, R.layout.note_row, list);  
        	  	  Log.d("tmp", "create adapter finish");
        	  	  ListView lv = getListView();
        	  	  LayoutInflater inflater = getLayoutInflater();
        	  	  ViewGroup header = (ViewGroup)inflater.inflate(R.layout.note_list_header, lv, false);
        	  	  lv.addHeaderView(header, null, false);
        		  setListAdapter(adapter);
        		  Button buttonNew = (Button) findViewById(R.id.button_new);
        		  buttonNew.setOnClickListener(new View.OnClickListener() {
        	    		public void onClick(View view) {
        	    			Intent intent = new Intent(NoteListActivity.this, NoteNewActivity.class);
        	    			view.getContext().startActivity(intent);
        	    			Log.d("tmp", "new button!!!");
        	   			}
        	   		});
        	  	  Log.d("tmp", "aaaaaaaaa------------");
        	  	
        	  } catch (JSONException e) {
        		  Log.d("tmp", "parse fail");
        		  // TODO 自動生成された catch ブロック
        		  e.printStackTrace();
        	  }
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

      task.setParamsToUrl( params );
      // タスクを開始
      task.execute();
    }
	
	public ArrayList<ArrayList> jsonArrayToArrayList(JSONArray ja)
    {
        ArrayList<ArrayList> listItems = new ArrayList<ArrayList>();
        try {
        	for (int i = 0; i < ja.length(); i++) {
        		ArrayList<String> listItem = new ArrayList<String>();
        		JSONObject jo = (JSONObject) ja.get(i);
        		listItem.add(jo.getString("title"));
        		listItem.add(jo.getString("body"));
        		listItem.add(jo.getString("id"));
        		listItems.add(listItem);
        	}
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listItems;
    }
}