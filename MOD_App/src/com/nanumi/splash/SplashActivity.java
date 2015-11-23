package com.nanumi.splash;

import com.nanumi.R;
import com.nanumi.main.MainActivity;
import com.nanumi.sub.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_DURATION = 1000;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		setIntent();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(intent);
				finish();
			}
		}, SPLASH_DISPLAY_DURATION);
	}

	private void setIntent() {
		SharedPreferences pref = getSharedPreferences("Login", 0);
		String uuid = pref.getString("uuid", "empty");
		
		if (!uuid.equals("empty")) {
			intent = new Intent(SplashActivity.this, MainActivity.class);
		} else {
			intent = new Intent(SplashActivity.this, LoginActivity.class);
		}
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.fade, R.anim.hold);
	}

}