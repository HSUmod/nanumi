package com.nanumi.main;

import com.nanumi.R;
import com.nanumi.sub.LoginTestActivity;
import com.viewpagerindicator.UnderlinePageIndicator;

import android.content.Intent;
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
	private LinearLayout navigation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setButton();
		setPager();
		setIndicator();

		// drawer setting
		drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
		navigation = (LinearLayout) findViewById(R.id.navigation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			if (drawer.isDrawerOpen(navigation)) {
				drawer.closeDrawer(navigation);
			} else {
				drawer.openDrawer(navigation);
			}
			break;
		case R.id.action_login:
			Intent intent = new Intent(MainActivity.this, LoginTestActivity.class);
			startActivity(intent);
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

}
