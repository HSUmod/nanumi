package com.nanumi.sub;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.nanumi.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 1. 로그인 처리
 * 2. uuid 받아오기
 * 3. uuid preferences에 저장하기
 * 
 * ---- 우선순위 하
 * 1. 로그인 예외 처리
 * 
 * @author saehyun
 *
 */
public class LoginTestActivity extends Activity {
	private EditText etId, etPw;
	private Button btLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etId = (EditText) findViewById(R.id.etId);
		etPw = (EditText) findViewById(R.id.etPw);
		btLogin = (Button) findViewById(R.id.btLogin);
		btLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String inputId = etId.getText().toString();
				String inputPw = etPw.getText().toString();
				
				LoginReq loginReq = new LoginReq();
				loginReq.execute(inputId, inputPw);
			}
		});

	}

	class LoginReq extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... param) {
			JSONObject json = null;
			boolean flag = false;

			try {
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));
				params.add(new BasicNameValuePair("pwd", param[1]));

				UrlEncodedFormEntity encodeEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				String postURL = "http://223.194.141.168/MOD_WAS/Login.do";
				HttpPost post = new HttpPost(postURL);
				post.setEntity(encodeEntity);

				HttpClient client = new DefaultHttpClient();
				HttpResponse resPost = client.execute(post);
				HttpEntity resEntity = resPost.getEntity();

				try {
					json = new JSONObject(EntityUtils.toString(resEntity));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					if (json.getString("result").startsWith("LOGIN_ERROR")) {
						Log.d("Login Fail", json.getString("result"));
						flag = true;
					} else {
						String uuid = json.getString("result");
						Log.d("Login Success", uuid);
						flag = false;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return flag;

		}
	}
}
