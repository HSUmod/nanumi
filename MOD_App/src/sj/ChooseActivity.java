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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.nanumi.R;
import com.nanumi.R.layout;

public class ChooseActivity extends Activity{
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
			resultParse(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		adapter = new ApplicationAdapter(applicationList);
		listView.setAdapter(adapter);		
	}
	
	private void resultParse(String result) throws Exception {
		JSONArray jsonArr = new JSONArray(result);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String articleNum = jsonObj.getString("articleNum");
			
			if(!this.articleNum.equals(articleNum)) continue;
			
			String userid = jsonObj.getString("userid");
			String state = jsonObj.getString("state");
			
			System.out.println("===========start=============");
			System.out.println("=============================");
			
			System.out.println("=============================");
			System.out.println("===========end=============");
			ApplicationsDTO item = new ApplicationsDTO(articleNum,userid,state);
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
				String postURL = "http://113.198.80.223/MOD_WAS/Applications.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("articleNum", param[0]));

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
					if (json.getString("result").equals("READ_OK")) {
						result = json.getString("applications");
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
