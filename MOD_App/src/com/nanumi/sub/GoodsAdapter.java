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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nanumi.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import sj.ApplicationsDTO;
import sj.ChooseActivity;

public class GoodsAdapter extends BaseAdapter {
	List<GoodsDTO> goodsList;
	List<ApplicationsDTO> applicationList;

	public GoodsAdapter(List<GoodsDTO> goodsList) {
		this.goodsList = goodsList;
		applicationList = new ArrayList<ApplicationsDTO>();
	}

	@Override
	public int getCount() {
		return goodsList.size();
	}

	@Override
	public Object getItem(int position) {
		return goodsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_goods_list_item, parent, false);

			TextView userid = (TextView) convertView.findViewById(R.id.tvGoodsUserid);
			// TextView distance = (TextView)
			// convertView.findViewById(R.id.tvDistance);
			TextView postingTime = (TextView) convertView.findViewById(R.id.tvPostingTime);
			ImageView img = (ImageView) convertView.findViewById(R.id.ivGoodsImg);
			TextView contents = (TextView) convertView.findViewById(R.id.tvContents);
			TextView hashtag = (TextView) convertView.findViewById(R.id.tvHashtag);
			Button apply = (Button) convertView.findViewById(R.id.btnApply);
			String articleNum = goodsList.get(position).getArticleNum();

			// final String articleNum =
			// goodsList.get(position).getArticleNum();
			String userId = goodsList.get(position).getUserid();
			final int state = Integer.parseInt(goodsList.get(position).getState());
			userid.setText(userId);
			// distance
			postingTime.setText(goodsList.get(position).getPostingTime());
			img.setImageBitmap(goodsList.get(position).getImg());
			contents.setText(goodsList.get(position).getContents());
			hashtag.setText(goodsList.get(position).getHashtag());

			// btn
			// listener

			SharedPreferences pref = context.getSharedPreferences("Login", 0);
			final String uuid = pref.getString("uuid", "empty");
			ReadReq readReq = new ReadReq();
			try {
				String result = readReq.execute(uuid).get();
				resultParse(result);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!uuid.equals("empty")) {
				String myId = uuid.split("-")[0];
				if (userId.equals(myId)) { // 내가 올린 글
					if (state == GoodsDTO.NO_CHOOSE) { // state == 0
						apply.setText("채택하기");
					} else if (state == GoodsDTO.CHOOSE) { // state == 1
						apply.setText("나눔 진행중");
					} else if (state == GoodsDTO.SHARE_FINISH) { // state == 2
						apply.setText("나눔 완료");
					}
					apply.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (state == GoodsDTO.NO_CHOOSE) { // state == 0
								//채택하기 액티비티
								Intent intent = new Intent(context, ChooseActivity.class);
								intent.putExtra("articleNum", goodsList.get(position).getArticleNum());

								context.startActivity(intent);

							} else {
								// TODO 나눔 진행 상태 보기 페이지 ?
							}
						}
					});

				} else {
					if (state == GoodsDTO.CHOOSE) { // state == 1
						apply.setText("나눔 진행중");
					} else if (state == GoodsDTO.SHARE_FINISH) { // state == 2
						apply.setText("나눔 완료");
					} else {
						apply.setText("신청");
						for (int i = 0; i < applicationList.size(); i++) {
							if (applicationList.get(i).getArticleNum().equals(articleNum) && applicationList.get(i).getUserid().equals(uuid))
								apply.setText("신청 취소");
						}
					}

					apply.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Button btn = (Button) v;
							if (btn.getText().toString().equals("신청")) {
								//TODO 신청하기 수행 (서버에 articleNum 이랑 userid 넘겨줌, db에 추가하기)
								btn.setText("신청 취소");

							} else if (btn.getText().toString().equals("신청 취소")) {
								//TODO 신청취소 수행 (서버에 articleNum 이랑 userid 넘겨줌, db에서 지우기)
								btn.setText("신청");

							} else if (btn.getText().toString().equals("나눔 진행중")) {
								if (goodsList.get(position).getRuserid().equals(uuid)) {
									// TODO 나눔 진행 상태 보기 페이지 ?
								}
							} else if (btn.getText().toString().equals("나눔 완료")) {
								// TODO 할꺼 없음..?
							}
							//							SendReq sendReq = new SendReq();
							//							try {
							//								String result = sendReq
							//										.execute(
							//												uuid,
							//												goodsList.get(position)
							//														.getArticleNum()).get();
							//								if (!result.equals("fail")) {
							//
							//								} else {
							//									// TODO 서버 전송 실패
							//								}
							//							} catch (InterruptedException e) {
							//
							//								e.printStackTrace();
							//							} catch (ExecutionException e) {
							//
							//								e.printStackTrace();
							//							} //함수로 빼던가 하던가
						}
					});

				}
			} else {

			}

		}

		return convertView;
	}

	public void addGoods(GoodsDTO item) {
		goodsList.add(item);
		notifyDataSetChanged();
	}

	public void removeGoods(int position) {
		goodsList.remove(position);
		notifyDataSetChanged();
	}

	private void resultParse(String result) throws Exception {
		JSONArray jsonArr = new JSONArray(result);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String articleNum = jsonObj.getString("articleNum");
			String userid = jsonObj.getString("userid");
			String state = jsonObj.getString("state");

			System.out.println("===========start=============");
			System.out.println("===========end=============");
			ApplicationsDTO item = new ApplicationsDTO(articleNum, userid, state);
			applicationList.add(item);
		}
	}

	class SendReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://223.194.141.168/MOD_WAS/Send.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));
				params.add(new BasicNameValuePair("articleNum", param[1]));

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
					if (json.getString("result").equals("Success")) {
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

	class ReadReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/ApplicationList.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));

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

}
