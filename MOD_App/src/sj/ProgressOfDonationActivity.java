package sj;

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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanumi.R;

public class ProgressOfDonationActivity extends Activity {
	Button btnCancle, btnFinish;
	ImageView chat;
	TextView senderId, receiverId, contents;
	// public static int SENDER = 0;
	// public static int RECEIVER = 1;
	int flag;
	String articleNum;

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
		articleNum = intent.getStringExtra("articleNum");

		chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 문자 화면으로 가기
			}
		});

		switch (flag) {
		case 0:// 주는사람
			btnCancle.setText("채택 취소");
			//btnFinish.setText("나눔 완료");
			btnFinish.setVisibility(View.INVISIBLE);
			break;

		case 1:// 받는사람
			btnCancle.setText("나눔 취소");
			btnFinish.setText("인수 완료");
			break;
		}

		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder dlg = new AlertDialog.Builder(
						ProgressOfDonationActivity.this);
				dlg.setTitle("나눔을 취소하시겠습니까?");
				dlg.setPositiveButton("네",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SendCancleReq cancleReq = new SendCancleReq();
								try {
									String result = cancleReq.execute(
											articleNum).get();
									if (!result.equals("fail")) {
										ProgressOfDonationActivity.this
												.finish();
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}

							}
						});
				dlg.setNegativeButton("아니요", null);
				dlg.show();
			}
		});
		btnFinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				if (btn.getText().toString().equals("나눔 완료")) {
					AlertDialog.Builder dlg = new AlertDialog.Builder(
							ProgressOfDonationActivity.this);
					dlg.setTitle("나눔을 완료하셨습니까?");
					dlg.setPositiveButton("네",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SendReq sendReq = new SendReq();
									try {
										String result = sendReq.execute(
												articleNum, "senderComplete")
												.get();
										if (result.equals("ok")) {
											ProgressOfDonationActivity.this
													.finish();
										}
									} catch (InterruptedException e) {
										e.printStackTrace();
									} catch (ExecutionException e) {
										e.printStackTrace();
									}

								}
							});

					dlg.setNegativeButton("아니요", null);
					dlg.show();
				} else if (btn.getText().toString().equals("인수 완료")) {
					AlertDialog.Builder dlg = new AlertDialog.Builder(
							ProgressOfDonationActivity.this);
					dlg.setTitle("인수를 완료하셨습니까?");
					dlg.setPositiveButton("네",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SendReq sendReq = new SendReq();
									try {
										String result = sendReq.execute(
												articleNum, "receiverComplete")
												.get();
										if (result.equals("ok")) {
											ProgressOfDonationActivity.this
													.finish();
										}
									} catch (InterruptedException e) {
										e.printStackTrace();
									} catch (ExecutionException e) {
										e.printStackTrace();
									}

								}
							});
					dlg.setNegativeButton("아니요", null);
					dlg.show();
				}
			}
		});

	}

	class SendCancleReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/CancleChoice.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("articleNum", param[0]));

				UrlEncodedFormEntity encodeEntity = new UrlEncodedFormEntity(
						params, HTTP.UTF_8);
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
					Log.d("sj", json.toString());
					if (json.getString("result").equals("ok")) {
						result = "ok";
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

	class SendReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/DonateComplete.do";
				HttpPost post = new HttpPost(postURL);				
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("articleNum", param[0]));
				params.add(new BasicNameValuePair("state", param[1]));

				UrlEncodedFormEntity encodeEntity = new UrlEncodedFormEntity(
						params, HTTP.UTF_8);
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
					Log.d("sj", json.toString());
					if (json.getString("result").equals("ok")) {
						result = "ok";
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
