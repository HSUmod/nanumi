package sj;

import java.io.IOException;
import java.util.ArrayList;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nanumi.R;
import com.nanumi.R.layout;

public class ChooseActivity extends Activity {
	List<ApplicationsDTO> applicationList;
	ApplicationAdapter adapter;
	String articleNum;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_application_list);
		listView = (ListView) findViewById(R.id.applicationsListView);

		Intent intent = getIntent();
		articleNum = intent.getStringExtra("articleNum");

		applicationList = new ArrayList<ApplicationsDTO>();

		ReadReq readReq = new ReadReq();
		try {
			String result = readReq.execute(articleNum).get();

			if (!result.equals("fail")) {
				resultParse(result);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		adapter = new ApplicationAdapter(applicationList, articleNum);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
				AlertDialog.Builder dlg = new AlertDialog.Builder(ChooseActivity.this);
				dlg.setTitle(applicationList.get(position).getUserid() + " 님을 채택하시겠습니까?");
				dlg.setPositiveButton("네",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) 
							{
								SendReq sendReq = new SendReq();
								if (!sendReq.execute(applicationList.get(position).getUserid(), articleNum).equals("fail")) {
									finish();
								}else {
									//채택을 했는데 서버에 어떠한 오류가 발생해서 fail이 왔을 경우 예외처리
								}
							}
						});
				dlg.setNegativeButton("아니요", null);
				dlg.show();
			}
		}); //TODO
	}

	private void resultParse(String result) throws Exception {
		JSONArray jsonArr = new JSONArray(result);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String articleNum = jsonObj.getString("articleNum");

			if (!this.articleNum.equals(articleNum))
				continue;

			String userid = jsonObj.getString("userid");
			String state = jsonObj.getString("state");
			String time = jsonObj.getString("postingTime");


			ApplicationsDTO item = new ApplicationsDTO(articleNum, state,
					userid, time);
			applicationList.add(item);
		}
	}

	class ReadReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/MyGoodsApplicationList.do";
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
					if (json.getString("result").equals("ok")) {
						result = json.getString("value");
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
				String postURL = "http://113.198.80.223/MOD_WAS/Choice.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));
				params.add(new BasicNameValuePair("articleNum", param[1]));

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
