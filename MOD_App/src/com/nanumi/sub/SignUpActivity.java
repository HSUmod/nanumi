package com.nanumi.sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nanumi.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SignUpActivity extends Activity {
	EditText userid, password, repassword, nickname, email_address, phone_number;
	TextView userid_hint, password_hint, repassword_hint, nickname_hint, city_hint, district_hint, email_address_hint,
			phone_number_hint;
	Button signupBtn;

	Spinner city, district;

	HashMap<String, List<String>> addresses = null;
	List<String> cities = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		userid = (EditText) findViewById(R.id.userid);
		userid_hint = (TextView) findViewById(R.id.userid_hint);

		password = (EditText) findViewById(R.id.password);
		password_hint = (TextView) findViewById(R.id.password_hint);

		repassword = (EditText) findViewById(R.id.repassword);
		repassword_hint = (TextView) findViewById(R.id.repassword_hint);

		nickname = (EditText) findViewById(R.id.nickname);
		nickname_hint = (TextView) findViewById(R.id.nickname_hint);

		city = (Spinner) findViewById(R.id.city);
		city_hint = (TextView) findViewById(R.id.city_hint);

		district = (Spinner) findViewById(R.id.district);
		district_hint = (TextView) findViewById(R.id.district_hint);

		email_address = (EditText) findViewById(R.id.email_address);
		email_address_hint = (TextView) findViewById(R.id.email_address_hint);

		phone_number = (EditText) findViewById(R.id.phone_number);
		phone_number_hint = (TextView) findViewById(R.id.phone_number_hint);

		signupBtn = (Button) findViewById(R.id.signupBtn);

		AddressRequest request = new AddressRequest();
		addresses = new HashMap<String, List<String>>();
		cities = new ArrayList<String>();

		try {
			resultParse(request.execute().get());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userid.setOnFocusChangeListener(focusListener);
		password.setOnFocusChangeListener(focusListener);
		repassword.setOnFocusChangeListener(focusListener);
		nickname.setOnFocusChangeListener(focusListener);
		city.setOnFocusChangeListener(focusListener);
		district.setOnFocusChangeListener(focusListener);
		email_address.setOnFocusChangeListener(focusListener);
		phone_number.setOnFocusChangeListener(focusListener);

		initCitis(cities);

		city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView text = (TextView) view;
				String city = text.getText().toString();

				initDistricts(addresses.get(city));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void resultParse(String result) throws JSONException {

		// Log.d("result", result);
		JSONArray jsonArr = new JSONArray(result);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String city = jsonObj.getString("city");
			String district = jsonObj.getString("district");

			List<String> obj = Arrays.asList(district.split(","));

			Log.d("city", city);
			Log.d("district", district);

			cities.add(city);
			addresses.put(city, obj);
		}
	}

	public void initCitis(List<String> obj) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, obj);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adapter);

	}

	public void initDistricts(List<String> districts) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districts);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		district.setAdapter(adapter);
	}

	View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.userid:
				if (hasFocus) {
					userid_hint.setVisibility(View.VISIBLE);
				} else {
					userid_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.password:
				if (hasFocus) {
					password_hint.setVisibility(View.VISIBLE);
				} else {
					password_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.repassword:
				if (hasFocus) {
					repassword_hint.setVisibility(View.VISIBLE);
				} else {
					repassword_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.nickname:
				if (hasFocus) {
					nickname_hint.setVisibility(View.VISIBLE);
				} else {
					nickname_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.city:
				if (hasFocus) {
					city_hint.setVisibility(View.VISIBLE);
				} else {
					city_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.district:
				if (hasFocus) {
					district_hint.setVisibility(View.VISIBLE);
				} else {
					district_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.email_address:
				if (hasFocus) {
					email_address_hint.setVisibility(View.VISIBLE);
				} else {
					email_address_hint.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.phone_number:
				if (hasFocus) {
					phone_number_hint.setVisibility(View.VISIBLE);
				} else {
					phone_number_hint.setVisibility(View.INVISIBLE);
				}
			default:
				break;
			}

		}
	};

	public void onClickSignUpBtn(View v) {
		// TODO Auto-generated method stub
		UserInfo info = new UserInfo();
		switch (v.getId()) {
		case R.id.signupBtn:
			try {

				info.execute("SignUp.do", userid.getText().toString(), password.getText().toString(),
						nickname.getText().toString(), nickname.getText().toString(), city.getSelectedItem().toString(),
						district.getSelectedItem().toString().toString(), email_address.getText().toString(),
						phone_number.getText().toString()).get();
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

	class AddressRequest extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/SearchAddress.do";
				HttpPost post = new HttpPost(postURL);
				HttpResponse responsePOST = client.execute(post);
				HttpEntity resEntity = responsePOST.getEntity();

				json = new JSONObject(EntityUtils.toString(resEntity));
				if (json.getString("result").equals("ok")) {
					result = json.getString("value");
				} else {
					result = "fail";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.d("result", result);
			return result;

		}

	}

	// 가입 AS.class
	class UserInfo extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... param) {

			// TODO Auto-generated method stub
			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/" + param[0];
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[1]));
				params.add(new BasicNameValuePair("pwd", param[2]));
				params.add(new BasicNameValuePair("nickname", param[3]));
				params.add(new BasicNameValuePair("city", param[4]));
				params.add(new BasicNameValuePair("district", param[5]));
				params.add(new BasicNameValuePair("phone", param[6]));
				params.add(new BasicNameValuePair("email", param[7]));

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

	// 아이디, 닉네임, 이메일 중복 체크, 나중에 쓴다!!(김명준, 나중에 중복체크 할때 쓸 클래스)
//	class UserInfoCheckClass extends AsyncTask<String, Void, Boolean> {
//		@Override
//		protected Boolean doInBackground(String... param) {
//			// TODO Auto-generated method stub
//			JSONObject json = null;
//			boolean flag = false;
//			String str = "";
//			if (param[0].equals("UserIDCheck.do")) {
//				str = "userid";
//			} else if (param[0].equals("UserNicknameCheck.do")) {
//				str = "nickname";
//			} else if (param[0].equals("UserEmailCheck.do")) {
//				str = "email";
//			}
//
//			try {
//				HttpClient client = new DefaultHttpClient();
//				String postURL = "http://223.194.141.168/MOD_WAS/" + param[0];
//				HttpPost post = new HttpPost(postURL);
//				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//
//				params.add(new BasicNameValuePair(str, param[1]));
//				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//				post.setEntity(ent);
//
//				HttpResponse responsePOST = client.execute(post);
//				HttpEntity resEntity = responsePOST.getEntity();
//
//				// result: SIGNUP_ERROR_01 or SIGNUP_COMPLETE
//
//				try {
//					json = new JSONObject(EntityUtils.toString(resEntity));
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (JSONException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				try {
//					if (json.getString("result").equals("fail")) {
//						// fail
//						System.out.println("true");
//						Log.d("MyCheck.class", "아이디 중복됨");
//						flag = true;
//					} else {
//						// success
//
//						Log.d("MyCheck.calss", "아이디 사용 가능");
//						flag = false;
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return flag;
//
//		}
//	}

}
