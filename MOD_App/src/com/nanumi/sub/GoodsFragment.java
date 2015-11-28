package com.nanumi.sub;

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
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nanumi.R;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GoodsFragment extends Fragment {
	List<GoodsDTO> goodsList;
	private ListView mListView;
	private GoodsAdapter mAdapter;

	public static GoodsFragment newInstance() {
		GoodsFragment fragment = new GoodsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_list, container, false);

		goodsList = new ArrayList<GoodsDTO>();
		ReadReq readReq = new ReadReq();
		try {
			SharedPreferences pref = this.getActivity().getSharedPreferences("Login", 0);
			String result = readReq.execute(pref.getString("uuid", "")).get();

			resultParse(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mListView = (ListView) view.findViewById(R.id.goodsListView);
		mAdapter = new GoodsAdapter(goodsList);
		mListView.setAdapter(mAdapter);

		return view;
	}

	private void resultParse(String result) throws Exception {
		JSONArray jsonArr = new JSONArray(result);
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String articleNum = jsonObj.getString("articleNum");
			String userid = jsonObj.getString("userid");
			String city = jsonObj.getString("city");
			String district = jsonObj.getString("district");
			String major = jsonObj.getString("major");
			String sub = jsonObj.getString("sub");
			String contents = jsonObj.getString("contents");
			String hashtag = jsonObj.getString("hashtag");
			String selectionWay = jsonObj.getString("selectionWay");
			String state = jsonObj.getString("state");
			String postingTime = jsonObj.getString("postingTime");
			String image = jsonObj.getString("image");
			byte[] backToBytes = Base64.decodeBase64(image);
			String ruserid = jsonObj.getString("ruserid");

			GoodsDTO item = new GoodsDTO(articleNum, userid, city, district, major, sub, contents, hashtag, selectionWay, postingTime, backToBytes, state, ruserid);
			goodsList.add(item);
		}
	}

	class ReadReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/ReadGoods.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uuid", param[0]));

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
					if (json.getString("result").equals("ok")) {
						result = json.getString("goods");
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
