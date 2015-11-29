package com.nanumi.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nanumi.R;
import com.nanumi.sub.AddressRequest;
import com.nanumi.sub.GoodsDTO;
import com.nanumi.sub.GoodsFragment;
import com.nanumi.sub.LoginActivity;
import com.nanumi.sub.RegisterActivity;
import com.viewpagerindicator.UnderlinePageIndicator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private Button btn1, btn2, btn3, btn4;
	private ViewPager mPager;
	private UnderlinePageIndicator mIndicator;
	private DrawerLayout drawer;
	private LinearLayout leftNavi, rightNavi;
	private Button logoutBtn, orderBtn;
	private Spinner orderByCity, orderByDistrict;
	private Map<String, List<String>> addresses;
	List<String> cities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AddressRequest request = new AddressRequest();
		addresses = new HashMap<String, List<String>>();
		cities = new ArrayList<String>();

		try {
			resultParse(request.execute().get());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		setButton();
		setPager();
		setIndicator();
		setDrawer();
		setLeftNavi();
		setRightNavi();
	}

	private void resultParse(String result) throws JSONException {
		JSONArray jsonArr = new JSONArray(result);

		cities.add("전체");
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String city = jsonObj.getString("city");
			String district = jsonObj.getString("district");
			district = "전체," + district;
			List<String> obj = Arrays.asList(district.split(","));

			cities.add(city);
			addresses.put(city, obj);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.btnLeftNavi:
			if (drawer.isDrawerOpen(leftNavi)) {
				drawer.closeDrawer(leftNavi);
			} else {
				drawer.openDrawer(leftNavi);
			}
			break;
		case R.id.btnRightNavi:
			if (drawer.isDrawerOpen(rightNavi)) {
				drawer.closeDrawer(rightNavi);
			} else {
				drawer.openDrawer(rightNavi);
			}
			break;
		}

		return true;
	}

	private void setButton() {
		btn1 = (Button) findViewById(R.id.fragBtn1);
		btn2 = (Button) findViewById(R.id.fragBtn2);
		btn3 = (Button) findViewById(R.id.fragBtn3);
		btn4 = (Button) findViewById(R.id.fragBtn4);

		OnClickListener btnListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.fragBtn1:
					mPager.setCurrentItem(0);
					break;
				case R.id.fragBtn2:
					mPager.setCurrentItem(1);
					break;
				case R.id.fragBtn3:
					mPager.setCurrentItem(2);
					break;
				case R.id.fragBtn4:

					Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		};
		btn1.setOnClickListener(btnListener);
		btn2.setOnClickListener(btnListener);
		btn3.setOnClickListener(btnListener);
		btn4.setOnClickListener(btnListener);
	}

	private void setPager() {
		mPager = (ViewPager) findViewById(R.id.viewPagerArea);
		mPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
	}

	private void setIndicator() {
		mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		mIndicator.setFades(false);
	}

	private void setDrawer() {
		drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
	}

	private void setLeftNavi() {
		leftNavi = (LinearLayout) findViewById(R.id.leftNavi);
		orderByCity = (Spinner) findViewById(R.id.naviOrderByCity);
		orderByDistrict = (Spinner) findViewById(R.id.naviOrderByDistrict);
		orderBtn = (Button) findViewById(R.id.btnOrder);

		initCitis(cities);
		orderByCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String city = ((TextView) view).getText().toString();
				if (!city.equals("전체")) {
					initDistricts(addresses.get(city));
				} else {
					initDistricts(null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		orderBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchReq searchReq = new SearchReq();

				String result = null;
				String city = orderByCity.getSelectedItem().toString();
				String district = orderByDistrict.getSelectedItem().toString();

			}
		});
	}

	private void initCitis(List<String> obj) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, obj);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		orderByCity.setAdapter(adapter);
	}

	private void initDistricts(List<String> districts) {
		ArrayAdapter<String> adapter;

		if (districts == null) {
			orderByDistrict.setAdapter(null);
		} else {
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districts);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			orderByDistrict.setAdapter(adapter);
		}
	}

	private void setRightNavi() {
		rightNavi = (LinearLayout) findViewById(R.id.rightNavi);
		logoutBtn = (Button) findViewById(R.id.btnLogout);

		logoutBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences pref = getSharedPreferences("Login", 0);

				LogoutReq logoutReq = new LogoutReq();
				try {
					String result = logoutReq.execute(pref.getString("uuid", "")).get();
					System.out.println("Logout R:  " + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

				SharedPreferences.Editor edit = pref.edit();
				edit.clear();
				edit.commit();

				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	class LogoutReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/Logout.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uuid", param[0]));

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
					result = json.getString("result");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}
	}

	class SearchReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/SearchGoods.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("type", param[0]));
				params.add(new BasicNameValuePair("city", param[0]));
				params.add(new BasicNameValuePair("district", param[0]));

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
						result = json.getString("goods");
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
