package sj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanumi.R;

public class ProgressOfDonationActivity extends Activity {
	Button btnCancle, btnFinish;
	ImageView chat;
	TextView senderId,receiverId,contents;
	//public static int SENDER = 0;
	//public static int RECEIVER = 1;
	int flag;
	public ProgressOfDonationActivity() {
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_progress_of_donation);
		btnCancle = (Button) findViewById(R.id.btn_cancleChoice);
		btnFinish = (Button) findViewById(R.id.btn_finishDonate);
		chat = (ImageView) findViewById(R.id.img_chat);
		senderId = (TextView) findViewById(R.id.tv_senderId);
		receiverId = (TextView) findViewById(R.id.tv_receiverId);
		contents = (TextView) findViewById(R.id.tv_contents);
		
		Intent intent = getIntent();
		flag = intent.getIntExtra("flag", 0); // 0: 주는사람 1: 받는사람
		senderId.setText(intent.getStringExtra("senderId"));
		receiverId.setText(intent.getStringExtra("receiverId"));
		contents.setText(intent.getStringExtra("contents"));
		
		chat.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//TODO 문자 화면으로 가기				
			}
		});
		
		switch (flag) {
		case 0://주는사람
			btnCancle.setText("채택 취소");
			btnFinish.setText("나눔 완료");
			break;

		case 1://받는사람
			btnCancle.setText("?????");
			btnFinish.setText("인수 완료");
			break;
		}
		
		btnCancle.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO 채택 취소				
			}
		});
		
		
		
		btnFinish.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				if(btn.getText().toString().equals("나눔 완료"))
				{
					
				}
				else if(btn.getText().toString().equals("인수 완료")){
					
				} 				
			}
		});
		
	}
	
	
}
