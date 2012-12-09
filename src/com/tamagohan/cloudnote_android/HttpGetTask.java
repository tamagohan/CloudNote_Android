package com.tamagohan.cloudnote_android;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

public class HttpGetTask extends HttpTask {


	public HttpGetTask(Activity parent_activity, String get_url,
			Handler ui_handler, DefaultHttpClient httpClient) {
		super(parent_activity, get_url, ui_handler, httpClient);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}
	
	// �p�����[�^��url�ɑg�ݍ���
	public void setParamsToUrl( Map<String, String> params )
	{
		get_url = get_url + "?a=bb";
	}
	
	// ���C������
	@Override
	protected Void doInBackground(Void... unused) {

		Log.d("posttest", "post���܂�");

		// URL
		URI url = null;

		try {
			url = new URI( get_url );
			Log.d("posttest", "URL��OK");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			http_err_msg = "�s����URL";
			return null;
		}

		// GET�p�����[�^�t����GET���N�G�X�g���\�z
		HttpGet request = new HttpGet( url );

		// GET���N�G�X�g�����s
		Log.d("posttest", "GET�J�n");
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