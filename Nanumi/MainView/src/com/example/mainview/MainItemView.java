package com.example.mainview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//부분화면 정의
public class MainItemView extends LinearLayout {

	TextView contentsText, hashtagText;
	ImageView imageView;

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

		contentsText = (TextView) findViewById(R.id.writtenText);
		hashtagText = (TextView) findViewById(R.id.hashText);
		imageView = (ImageView) findViewById(R.id.imageView);
	}

	public void setContentsText(String contentsText) {
		this.contentsText.setText(contentsText);
	}

	public void setHashText(String hashText) {
		this.hashtagText.setText(hashText);
	}

	public void setImageView(Bitmap imageView) {

		this.imageView.setImageBitmap(imageView);
	}

}
