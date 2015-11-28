package com.nanumi.main;

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
import com.nanumi.sub.LoginActivity;
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
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {
	private Button btn1, btn2, btn3, btn4;
	private ViewPager mPager;
	private UnderlinePageIndicator mIndicator;
	private DrawerLayout drawer;
	private LinearLayout leftNavi, rightNavi;
	private Button logoutBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setButton();
		setPager();
		setIndicator();
		setDrawer();
		setLeftNavi();
		setRightNavi();
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
					mPager.setCurrentItem(3);
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
}
