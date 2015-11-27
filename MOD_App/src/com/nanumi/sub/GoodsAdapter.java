package com.nanumi.sub;

import java.util.List;

import com.nanumi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsAdapter extends BaseAdapter {
	List<GoodsDTO> goodsList;

	public GoodsAdapter(List<GoodsDTO> goodsList) {
		this.goodsList = goodsList;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_goods_list_item, parent, false);

			TextView userid = (TextView) convertView.findViewById(R.id.tvGoodsUserid);
			//			TextView distance = (TextView) convertView.findViewById(R.id.tvDistance);
			TextView postingTime = (TextView) convertView.findViewById(R.id.tvPostingTime);
			ImageView img = (ImageView) convertView.findViewById(R.id.ivGoodsImg);
			TextView contents = (TextView) convertView.findViewById(R.id.tvContents);
			TextView hashtag = (TextView) convertView.findViewById(R.id.tvHashtag);
			Button apply = (Button) convertView.findViewById(R.id.btnApply);

			userid.setText(goodsList.get(position).getUserid());
			// distance
			postingTime.setText(goodsList.get(position).getPostingTime());
			img.setImageBitmap(goodsList.get(position).getImg());
			contents.setText(goodsList.get(position).getContents());
			hashtag.setText(goodsList.get(position).getHashtag());
			// btn
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

}
