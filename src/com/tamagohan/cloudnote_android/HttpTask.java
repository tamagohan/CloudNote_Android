package com.tamagohan.cloudnote_android;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpTask extends AsyncTask<Void, Void, Void> {

	// 設定事項
	protected String request_encoding = "UTF-8";
	private String response_encoding = "UTF-8";

	// 初期化事項
	private Activity parent_activity = null;
	protected String post_url = null;
	protected String get_url = null;
	private Handler ui_handler = null;
	protected List<NameValuePair> post_params = null;

	// 処理中に使うメンバ
	protected ResponseHandler<Void> response_handler = null;
	protected String http_err_msg = null;
	private String http_ret_msg = null;
	private int http_status  = 200; 
	private ProgressDialog dialog = null;
	protected DefaultHttpClient httpClient;


	// 生成時
	public HttpTask( Activity parent_activity, String post_url, Handler ui_handler, DefaultHttpClient httpClient)
	{
		// 初期化
		this.parent_activity = parent_activity;
		this.post_url = post_url;
		this.get_url = post_url;
		this.ui_handler = ui_handler;
		this.httpClient = httpClient;

		// 送信パラメータは初期化せず，new後にsetさせる
		post_params = new ArrayList<NameValuePair>();
	}


	/* --------------------- POSTパラメータ --------------------- */


	// パラメータを追加
	public void addPostParam( String post_name, String post_value )
	{
		post_params.add(new BasicNameValuePair( post_name, post_value ));
	}
	
	/* --------------------- 処理本体 --------------------- */


	// タスク開始時
	protected void onPreExecute() {
		// ダイアログを表示
		dialog = new ProgressDialog( parent_activity );
		dialog.setMessage("通信中・・・");
		dialog.show();

		// レスポンスハンドラを生成
		response_handler = new ResponseHandler<Void>() {

			// HTTPレスポンスから，受信文字列をエンコードして文字列として返す
			@Override
			public Void handleResponse(HttpResponse response) throws IOException
			{
				Log.d(
						"posttest",
						"レスポンスコード：" + response.getStatusLine().getStatusCode()
						);

				// 正常に受信できた場合は200
				http_status = response.getStatusLine().getStatusCode(); 
				switch (http_status) {
				case HttpStatus.SC_OK:
					Log.d("posttest", "レスポンス取得に成功");

					// レスポンスデータをエンコード済みの文字列として取得する。
					// ※IOExceptionの可能性あり
					HttpTask.this.http_ret_msg = EntityUtils.toString(
							response.getEntity(),
							HttpTask.this.response_encoding
							);
					break;

				case HttpStatus.SC_NOT_FOUND:
					// 404
					Log.d("posttest", "存在しない");
					HttpTask.this.http_err_msg = "404 Not Found";
					break;

				default:
					Log.d("posttest", "通信エラー");
					HttpTask.this.http_err_msg = "通信エラーが発生";
				}

				return null;
			}

		};
	}

	// タスク終了時
	protected void onPostExecute(Void unused) {
		// ダイアログを消す
		dialog.dismiss();

		// 受信結果をUIに渡すためにまとめる
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("http_status", http_status);
		if (http_err_msg != null) {
			// エラー発生時
			Log.d("http request error", http_err_msg);
			bundle.putBoolean("http_post_success", false);
			bundle.putString("http_response", http_err_msg);
		} else {
			// 通信成功時
			bundle.putBoolean("http_post_success", true);
			bundle.putString("http_response", http_ret_msg);
		}
		message.setData(bundle);

		// 受信結果に基づいてUI操作させる
		ui_handler.sendMessage(message);
  		}


	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}
}
