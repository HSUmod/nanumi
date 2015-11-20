package com.nanumi.sub;

import com.nanumi.R;

import android.app.Activity;
import android.os.Bundle;
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
				// TODO Auto-generated method stub

			}
		});

	}

}
