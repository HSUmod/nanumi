package com.nanumi.main;

import com.nanumi.R;
import com.viewpagerindicator.UnderlinePageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
	private Button btn1, btn2, btn3, btn4;
	private ViewPager mPager;
	private UnderlinePageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setButton();
		setPager();
		setIndicator();
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
