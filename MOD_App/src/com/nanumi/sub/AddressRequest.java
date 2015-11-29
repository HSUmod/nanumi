package com.nanumi.sub;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AddressRequest extends AsyncTask<Void, Void, String> {
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		String result = null;

		try {
			HttpClient client = new DefaultHttpClient();
			String postURL = "http://113.198.80.223/MOD_WAS/SearchAddress.do";
			HttpPost post = new HttpPost(postURL);
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
