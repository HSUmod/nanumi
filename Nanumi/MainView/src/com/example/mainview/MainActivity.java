package com.example.mainview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	MainViewAdapter mainAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview);
		mainAdapter = new MainViewAdapter();
		listView.setAdapter(mainAdapter);

	}

	class MainViewAdapter extends BaseAdapter {

		String[] written = { "1번", "2번", "3번", "4번", "5번", "6번", "7번", "8번", "9번", "10번" };
		String[] hash = { "#1번", "#2번", "#3번", "#4번", "#5번", "#6번", "#7번", "#8번", "#9번", "#10번" };

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return written.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return written[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			MainItemView view = new MainItemView(getApplicationContext());
			view.setWrittenText(written[position]);
			view.setHashText(hash[position]);

			return view;
		}

	}
}
