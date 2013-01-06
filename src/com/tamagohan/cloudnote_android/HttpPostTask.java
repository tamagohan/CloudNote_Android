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
	
	// ���C������
	@Override
	protected Void doInBackground(Void... unused) {

		Log.d("posttest", "post���܂�");

		// URL
		URI url = null;
		try {
			url = new URI( post_url );
			Log.d("posttest", "URL��OK");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			http_err_msg = "�s����URL";
			return null;
		}

		// POST�p�����[�^�t����POST���N�G�X�g���\�z
		HttpPost request = new HttpPost( url );
		
		try {
			// ���M�p�����[�^�̃G���R�[�h���w��
			request.setEntity(new UrlEncodedFormEntity(post_params, request_encoding));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			http_err_msg = "�s���ȕ����R�[�h";
			return null;
		}

		// POST���N�G�X�g�����s
		Log.d("posttest", "POST�J�n");
		try {
			httpClient.execute(request, response_handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			http_err_msg = "�v���g�R���̃G���[";
		} catch (IOException e) {
			e.printStackTrace();
			http_err_msg = "IO�G���[";
		}
		return null;
	}
}