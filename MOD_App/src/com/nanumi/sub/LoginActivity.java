package com.nanumi.sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
import com.nanumi.main.MainActivity;

import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText etId, etPw;
	private Button btnLogin, btnSearchId, btnSignUp;
	private boolean loginFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setInputText();
		setButton();
	}

	private void setInputText() {
		etId = (EditText) findViewById(R.id.etId);
		etPw = (EditText) findViewById(R.id.etPw);
	}

	private void setButton() {
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String inputId = etId.getText().toString();
				String inputPw = etPw.getText().toString();
				LoginReq loginReq = new LoginReq();

				try {
					String uuid = loginReq.execute(inputId, inputPw).get();
					if (!uuid.equals("fail")) {
						SharedPreferences pref = getSharedPreferences("Login", 0);
						SharedPreferences.Editor edit = pref.edit();
						edit.putString("uuid", uuid);
						edit.commit();
						loginFlag = !loginFlag;
					} else {
						// 예외 처리
						Toast.makeText(getApplicationContext(), "gg", Toast.LENGTH_SHORT).show();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

				if (loginFlag) {
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});

		btnSearchId = (Button) findViewById(R.id.btnSearchID);
		btnSearchId.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SearchIdActivity.class);
				startActivity(intent);
			}
		});

		btnSignUp = (Button) findViewById(R.id.btnSignUp);
		btnSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.fade, R.anim.hold);
	}

	class LoginReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/Login.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));
				params.add(new BasicNameValuePair("pwd", param[1]));

				UrlEncodedFormEntity encodeEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				post.setEntity(encodeEntity);

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
					if (json.getString("result").equals("ok")) {
						result = json.getString("value");
					} else {
						result = "fail";
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}
	}
}
