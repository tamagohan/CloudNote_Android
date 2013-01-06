package com.tamagohan.cloudnote_android;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class HttpPostTask extends HttpTask {

	public HttpPostTask(Activity parent_activity, String post_url,
			Handler ui_handler, DefaultHttpClient httpClient) {
		super(parent_activity, post_url, ui_handler, httpClient);
	}
	
	// メイン処理
	@Override
	protected Void doInBackground(Void... unused) {

		Log.d("posttest", "postします");

		// URL
		URI url = null;
		try {
			url = new URI( post_url );
			Log.d("posttest", "URLはOK");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			http_err_msg = "不正なURL";
			return null;
		}

		// POSTパラメータ付きでPOSTリクエストを構築
		HttpPost request = new HttpPost( url );
		
		try {
			// 送信パラメータのエンコードを指定
			request.setEntity(new UrlEncodedFormEntity(post_params, request_encoding));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			http_err_msg = "不正な文字コード";
			return null;
		}

		// POSTリクエストを実行
		Log.d("posttest", "POST開始");
		try {
			httpClient.execute(request, response_handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			http_err_msg = "プロトコルのエラー";
		} catch (IOException e) {
			e.printStackTrace();
			http_err_msg = "IOエラー";
		}
		return null;
	}
}