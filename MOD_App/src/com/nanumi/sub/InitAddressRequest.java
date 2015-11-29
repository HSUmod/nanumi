package com.nanumi.sub;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;

class InitAddressRequest extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		String result = null;
		try {
			HttpClient client = new DefaultHttpClient();
			String postURL = "http://113.198.80.223/MOD_WAS/getUserAddress.do";
			HttpPost post = new HttpPost(postURL);

			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("userid", params[0]));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(param, HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);
			HttpEntity resEntity = responsePOST.getEntity();

			json = new JSONObject(EntityUtils.toString(resEntity));
			if (json.getString("result").equals("ok")) {
				result = json.getString("value");
			} else {
				result = "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}