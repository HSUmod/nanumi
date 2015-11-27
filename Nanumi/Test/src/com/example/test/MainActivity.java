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
	Button registerBtn;

	Button checkIdBtn;
	Button checkNickNameBtn;
	Button checkEmailBtn;
	Button updateBtn;
	Button deleteBtn;

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
		registerBtn = (Button) findViewById(R.id.registerBtn);

		checkIdBtn = (Button) findViewById(R.id.checkId);
		checkNickNameBtn = (Button) findViewById(R.id.checkNickName);
		checkEmailBtn = (Button) findViewById(R.id.checkEmail);

		updateBtn = (Button) findViewById(R.id.updateBtn);
		deleteBtn = (Button) findViewById(R.id.deleteBtn);

	}

	// ȸ�� ���� or ����
	public void memberInfo(View v) {
		// TODO Auto-generated method stub
		MemberInfo info = new MemberInfo();

		switch (v.getId()) {
		case R.id.registerBtn:
			try {
				info.execute("SignUp.do", userid.getText().toString(), pwd.getText().toString(),
						nickname.getText().toString(), address.getText().toString(), email.getText().toString()).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.updateBtn:
			try {
				info.execute("ModifyUserInfo.do", userid.getText().toString(), pwd.getText().toString(),
						nickname.getText().toString(), address.getText().toString(), email.getText().toString()).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		default:
			break;

		}

	}

	// ���̵�, �г���, �̸��� �ߺ�üũ
	public void myCheck(View v) {

		// TODO Auto-generated method stub
		MyCheck check = new MyCheck();

		switch (v.getId()) {
		case R.id.checkId:
			try {
				if (check.execute("UserIDCheck.do", userid.getText().toString()).get()) {
					Toast.makeText(getApplicationContext(), "���̵� �ߺ���", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "���̵� ��� ����", Toast.LENGTH_SHORT).show();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.checkNickName:
			try {
				if (check.execute("UserNicknameCheck.do", nickname.getText().toString()).get()) {
					Toast.makeText(getApplicationContext(), "�г��� �ߺ���", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "�г��� ��� ����", Toast.LENGTH_SHORT).show();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.checkEmail:
			try {
				if (check.execute("UserEmailCheck.do", email.getText().toString()).get()) {
					Toast.makeText(getApplicationContext(), "�̸��� �ߺ���", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "�̸��� ��� ����", Toast.LENGTH_SHORT).show();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	// ����, ���� AS.class
	class MemberInfo extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... param) {

			// TODO Auto-generated method stub
			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://223.194.141.168/MOD_WAS/" + param[0];
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("userid", param[1]));
				params.add(new BasicNameValuePair("pwd", param[2]));
				params.add(new BasicNameValuePair("nickname", param[3]));
				params.add(new BasicNameValuePair("address", param[4]));
				params.add(new BasicNameValuePair("email", param[5]));
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
			return null;

		}
	}

	// ���̵�, �г���, �̸��� �ߺ� üũ AS.class
	class MyCheck extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... param) {
			// TODO Auto-generated method stub
			JSONObject json = null;
			boolean flag = false;
			String str = "";
			if (param[0].equals("UserIDCheck.do")) {
				str = "userid";
			} else if (param[0].equals("UserNicknameCheck.do")) {
				str = "nickname";
			} else if (param[0].equals("UserEmailCheck.do")) {
				str = "email";
			}

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://223.194.141.168/MOD_WAS/" + param[0];
				HttpPost post = new HttpPost(postURL);
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair(str, param[1]));
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				post.setEntity(ent);

				HttpResponse responsePOST = client.execute(post);
				HttpEntity resEntity = responsePOST.getEntity();

				// result: SIGNUP_ERROR_01 or SIGNUP_COMPLETE

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
					if (json.getString("result").equals("duplicated")) {
						// fail
						System.out.println("true");
						Log.d("MyCheck.class", "���̵� �ߺ���");
						flag = true;
					} else {
						// success

						Log.d("MyCheck.calss", "���̵� ��� ����");
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