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

	// �ݒ莖��
	protected String request_encoding = "UTF-8";
	private String response_encoding = "UTF-8";

	// ����������
	private Activity parent_activity = null;
	protected String post_url = null;
	protected String get_url = null;
	private Handler ui_handler = null;
	protected List<NameValuePair> post_params = null;

	// �������Ɏg�������o
	protected ResponseHandler<Void> response_handler = null;
	protected String http_err_msg = null;
	private String http_ret_msg = null;
	private int http_status  = 200; 
	private ProgressDialog dialog = null;
	protected DefaultHttpClient httpClient;


	// ������
	public HttpTask( Activity parent_activity, String post_url, Handler ui_handler, DefaultHttpClient httpClient)
	{
		// ������
		this.parent_activity = parent_activity;
		this.post_url = post_url;
		this.get_url = post_url;
		this.ui_handler = ui_handler;
		this.httpClient = httpClient;

		// ���M�p�����[�^�͏����������Cnew���set������
		post_params = new ArrayList<NameValuePair>();
	}


	/* --------------------- POST�p�����[�^ --------------------- */


	// �p�����[�^��ǉ�
	public void addPostParam( String post_name, String post_value )
	{
		post_params.add(new BasicNameValuePair( post_name, post_value ));
	}
	
	/* --------------------- �����{�� --------------------- */


	// �^�X�N�J�n��
	protected void onPreExecute() {
		// �_�C�A���O��\��
		dialog = new ProgressDialog( parent_activity );
		dialog.setMessage("�ʐM���E�E�E");
		dialog.show();

		// ���X�|���X�n���h���𐶐�
		response_handler = new ResponseHandler<Void>() {

			// HTTP���X�|���X����C��M��������G���R�[�h���ĕ�����Ƃ��ĕԂ�
			@Override
			public Void handleResponse(HttpResponse response) throws IOException
			{
				Log.d(
						"posttest",
						"���X�|���X�R�[�h�F" + response.getStatusLine().getStatusCode()
						);

				// ����Ɏ�M�ł����ꍇ��200
				http_status = response.getStatusLine().getStatusCode(); 
				switch (http_status) {
				case HttpStatus.SC_OK:
					Log.d("posttest", "���X�|���X�擾�ɐ���");

					// ���X�|���X�f�[�^���G���R�[�h�ς݂̕�����Ƃ��Ď擾����B
					// ��IOException�̉\������
					HttpTask.this.http_ret_msg = EntityUtils.toString(
							response.getEntity(),
							HttpTask.this.response_encoding
							);
					break;

				case HttpStatus.SC_NOT_FOUND:
					// 404
					Log.d("posttest", "���݂��Ȃ�");
					HttpTask.this.http_err_msg = "404 Not Found";
					break;

				default:
					Log.d("posttest", "�ʐM�G���[");
					HttpTask.this.http_err_msg = "�ʐM�G���[������";
				}

				return null;
			}

		};
	}

	// �^�X�N�I����
	protected void onPostExecute(Void unused) {
		// �_�C�A���O������
		dialog.dismiss();

		// ��M���ʂ�UI�ɓn�����߂ɂ܂Ƃ߂�
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("http_status", http_status);
		if (http_err_msg != null) {
			// �G���[������
			Log.d("http request error", http_err_msg);
			bundle.putBoolean("http_post_success", false);
			bundle.putString("http_response", http_err_msg);
		} else {
			// �ʐM������
			bundle.putBoolean("http_post_success", true);
			bundle.putString("http_response", http_ret_msg);
		}
		message.setData(bundle);

		// ��M���ʂɊ�Â���UI���삳����
		ui_handler.sendMessage(message);
  		}


	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}
}
