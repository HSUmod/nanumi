package com.example.mainview;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView listView;
	MainViewAdapter mainAdapter;
	ArrayList<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview);
		mainAdapter = new MainViewAdapter();

		// ������ �߰�

		ReceiveGoods goods = new ReceiveGoods();
		try {
			resultParse(goods.execute().get());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainAdapter.addItems(goodsList);
		listView.setAdapter(mainAdapter);

	}

	private void resultParse(String result) throws JSONException {
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
			String postingTime = jsonObj.getString("postingTime");
			String imgBuf = jsonObj.getString("image");
			// ����
			byte[] backToBytes = Base64.decodeBase64(imgBuf);

			//

			// byte[] imgByte = imgBuf.getBytes();
			// Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByte, 0,
			// imgByte.length);
			GoodsDTO item = new GoodsDTO(articleNum, userid, city, district, major, sub, contents, hashtag,
					selectionWay, postingTime, backToBytes);

			goodsList.add(item);
		}
	}

	class ReceiveGoods extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... param) {

			// TODO Auto-generated method stub
			JSONObject json = null;
			String result = null;
			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://223.194.141.168/MOD_WAS/ReadGoods.do";
				HttpPost post = new HttpPost(postURL);
				HttpResponse responsePOST = client.execute(post);

				HttpEntity httpEntity = responsePOST.getEntity();

				json = new JSONObject(EntityUtils.toString(httpEntity));

				if (json.getString("result").equals("READ_COMPLETE")) {
					result = json.getString("goods");
				} else {
					result = "fail";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

	}

	class MainViewAdapter extends BaseAdapter {

		ArrayList<GoodsDTO> items = new ArrayList<GoodsDTO>();

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		public void addItems(ArrayList<GoodsDTO> item) {
			items = item;
			notifyDataSetChanged();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			System.out.println("4");
			MainItemView view = null;

			if (convertView == null) {
				view = new MainItemView(getApplicationContext());
			} else {
				view = (MainItemView) convertView;
			}

			GoodsDTO curItem = items.get(position);

			view.setContentsText(curItem.getContents());
			view.setHashText(curItem.getHashtag());

			view.setImageView(curItem.getImg());
			return view;
		}

	}
}