package com.example.mainview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

//부분화면 정의
public class MainItemView extends LinearLayout {

	TextView writtenText, hashText;

	public MainItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MainItemView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_main, this, true);

		writtenText = (TextView) findViewById(R.id.writtenText);
		hashText = (TextView) findViewById(R.id.hashText);
	}


	public void setWrittenText(String writtenText) {
		this.writtenText.setText(writtenText);
	}

	public void setHashText(String	 hashText) {
		this.hashText.setText(hashText);
	}

}
