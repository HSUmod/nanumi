package com.nanumi.sub;

import com.nanumi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GoodsFragment extends Fragment {
	private ListView mListView;

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

		mListView = (ListView) view.findViewById(R.id.goodsListView);

		return view;
	}
}
