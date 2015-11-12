package com.example.test;

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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText userid;
	EditText pwd;
	EditText nickname;
	EditText address;
	EditText email;

	TextView result;
	Button sendBtn;
	Button checkBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		userid = (EditText) findViewById(R.id.userid);
		pwd = (EditText) findViewById(R.id.pwd);
		nickname = (EditText) findViewById(R.id.nickname);
		address = (EditText) findViewById(R.id.address);
		email = (EditText) findViewById(R.id.email);

		result = (TextView) findViewById(R.id.result);
		sendBtn = (Button) findViewById(R.id.sendBtn);
		checkBtn = (Button) findViewById(R.id.checkBtn);

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserJsp jsp = new UserJsp();

				try {
					jsp.execute(userid.getText().toString(), pwd.getText().toString(), nickname.getText().toString(),
							address.getText().toString(), email.getText().toString()).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		checkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IDCheck id = new IDCheck();

				try {
					if (id.execute(userid.getText().toString()).get()) {
						Toast.makeText(getApplicationContext(), "아이디 중복됨", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "아이디 사용 가능", Toast.LENGTH_SHORT).show();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	class UserJsp extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			// TODO Auto-generated method stub
			try {
				System.out.println("돌아갑니다.2");

				// param[];
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://223.194.141.168/MOD_WAS/SignUp.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("userid", param[0]));

				params.add(new BasicNameValuePair("pwd", param[1]));
				params.add(new BasicNameValuePair("nickname", param[2]));
				params.add(new BasicNameValuePair("address", param[3]));
				params.add(new BasicNameValuePair("email", param[4]));
				System.out.println("돌아갑니다.3");

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse responsePOST = client.execute(post);

				HttpEntity resEntity = responsePOST.getEntity();

				if (resEntity != null) {
					Log.i("RESPONSE", EntityUtils.toString(resEntity));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "aa";
		}
	}

	class IDCheck extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... param) {
			// TODO Auto-generated method stub

			JSONObject json = null;
			boolean flag = false;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://223.194.141.168/MOD_WAS/UserIDCheck.do";
				HttpPost post = new HttpPost(postURL);
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse responsePOST = client.execute(post);
				HttpEntity resEntity = responsePOST.getEntity();

				// result: SIGNUP_ERROR_01 or SIGNUP_COMPLETE

				System.out.println("엥?1");
				try {
					json = new JSONObject(EntityUtils.toString(resEntity));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					if (json.getString("result").equals("중복")) {
						// fail
						System.out.println("true");
						Log.d("중복됨", "zzzzzzzzzzzzzzzz");
						flag = true;
					} else {
						// success

						Log.d("아이디 사용 가능", "zzzzzzzzzzzzzzzz");
						flag = false;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return flag;

		}
	}

}
