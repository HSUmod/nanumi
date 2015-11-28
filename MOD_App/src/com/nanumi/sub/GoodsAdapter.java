package com.nanumi.sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import sj.ApplicationsDTO;
import sj.ChooseActivity;
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

import com.nanumi.R;

public class GoodsAdapter extends BaseAdapter {
	List<GoodsDTO> goodsList;
	List<ApplicationsDTO> applicationList;

	public GoodsAdapter(List<GoodsDTO> goodsList,
			List<ApplicationsDTO> applicationList) {
		this.goodsList = goodsList;
		this.applicationList = applicationList;
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

	// class ViewHolder {
	// public TextView userid;
	// public TextView postingTime;
	// public ImageView img;
	// public TextView contents;
	// public TextView hashtag;
	// public Button apply;
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Context context = parent.getContext();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_goods_list_item,
					parent, false);

		}
		TextView userid = (TextView) convertView
				.findViewById(R.id.tvGoodsUserid);
		TextView postingTime = (TextView) convertView
				.findViewById(R.id.tvPostingTime);
		ImageView img = (ImageView) convertView.findViewById(R.id.ivGoodsImg);
		TextView contents = (TextView) convertView
				.findViewById(R.id.tvContents);
		TextView hashtag = (TextView) convertView.findViewById(R.id.tvHashtag);
		Button apply = (Button) convertView.findViewById(R.id.btnApply);
		final String articleNum = goodsList.get(position).getArticleNum();
		String userId = goodsList.get(position).getUserid();
		final int state = Integer.parseInt(goodsList.get(position).getState());

		userid.setText(userId);
		postingTime.setText(goodsList.get(position).getPostingTime());
		img.setImageBitmap(goodsList.get(position).getImg());
		contents.setText(goodsList.get(position).getContents());
		hashtag.setText(goodsList.get(position).getHashtag());

		SharedPreferences pref = context.getSharedPreferences("Login", 0);
		final String uuid = pref.getString("uuid", "empty");

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
							// 채택하기 액티비티
							Intent intent = new Intent(context,
									ChooseActivity.class);
							intent.putExtra("articleNum",
									goodsList.get(position).getArticleNum());
							context.startActivity(intent);
						} else {
							// TODO 나눔 진행 상태 보기 페이지 ?
							Intent intent = new Intent();
							intent.putExtra("flag",0); //주는 사람
							intent.putExtra("senderId", goodsList.get(position).getUserid());
							intent.putExtra("contents", goodsList.get(position).getContents());
							for(int i = 0 ; i < applicationList.size() ; i++){
								if(applicationList.get(i).getArticleNum().equals(articleNum)&&applicationList.get(i).getState().equals("1")) {
									intent.putExtra("receiverId",applicationList.get(i).getUserid());
								}
							}//여기서 예외 발생할 가능성은 없긴 한데 혹시 있을지도 모름
							
							
							context.startActivity(intent);
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
						if (applicationList.get(i).getArticleNum()
								.equals(articleNum)
								&& applicationList.get(i).getUserid()
										.equals(uuid))
							apply.setText("신청 취소");
					}
				}

				apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Button btn = (Button) v;
						SendReq sendReq = new SendReq();
						if (btn.getText().toString().equals("신청")) {
							try {
								if (send(sendReq, uuid, position, "apply"))
									btn.setText("신청 취소");
							} catch (Exception e) {
								e.printStackTrace();
							}

						} else if (btn.getText().toString().equals("신청 취소")) {
							try {
								if (send(sendReq, uuid, position, "cancle"))
									btn.setText("신청");
							} catch (Exception e) {
								e.printStackTrace();
							}

						} else if (btn.getText().toString().equals("나눔 진행중")) {
							if (goodsList.get(position).getRuserid()
									.equals(uuid.split("-")[0])) { 
								// 내가 나눔 대상자인 경우
								Intent intent = new Intent();
								intent.putExtra("flag",1); //받는 사람
								intent.putExtra("senderId", goodsList.get(position).getUserid());
								intent.putExtra("contents", goodsList.get(position).getContents());
								for(int i = 0 ; i < applicationList.size() ; i++){
									if(applicationList.get(i).getArticleNum().equals(articleNum)&&applicationList.get(i).getState().equals("1")) {
										intent.putExtra("receiverId",applicationList.get(i).getUserid());
									}
								}//여기서 예외 발생할 가능성은 없긴 한데 혹시 있을지도 모름
								
								context.startActivity(intent);
							}else {
								//btn.setEnabled(false);
							}
						} else if (btn.getText().toString().equals("나눔 완료")) {

						}
					}
				});
			}
		}
		return convertView;
	}

	public boolean send(SendReq sendReq, String uuid, int position,
			String currentState) throws Exception {
		String result = sendReq.execute(uuid.split("-")[0],
				goodsList.get(position).getArticleNum(), currentState).get();
		if (!result.equals("fail")) {
			return true;
		} else {
			return false;
		}
	}

	public void addGoods(GoodsDTO item) {
		goodsList.add(item);
		notifyDataSetChanged();
	}

	public void removeGoods(int position) {
		goodsList.remove(position);
		notifyDataSetChanged();
	}

	class SendReq extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... param) {
			JSONObject json = null;
			String result = null;

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://113.198.80.223/MOD_WAS/Apply.do";
				HttpPost post = new HttpPost(postURL);

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", param[0]));
				params.add(new BasicNameValuePair("articleNum", param[1]));
				params.add(new BasicNameValuePair("currentState", param[2]));

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
