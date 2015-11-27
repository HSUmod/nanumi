package com.example.imagesendtest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final int RESULT_LOAD_IMAGE = 1;
	private static final String SERVER_ADDRESS = "http://223.194.141.168/MOD_WAS/";
	private static String imageName = null;// =
	private static String imagePath = null;

	ImageView imageToUpload;
	Button bUploadImage, sendBtn;
	EditText uploadImageName, downloadImageName;
	EditText userid, contents, city, district, major, sub, selectionWay, hashtag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageToUpload = (ImageView) findViewById(R.id.imageToUpload);

		bUploadImage = (Button) findViewById(R.id.bUploadImage);
		sendBtn = (Button) findViewById(R.id.sendBtn);

		////////////////////////////////////////////
		userid = (EditText) findViewById(R.id.userid);
		contents = (EditText) findViewById(R.id.contents);
		city = (EditText) findViewById(R.id.city);
		district = (EditText) findViewById(R.id.district);
		major = (EditText) findViewById(R.id.major);
		sub = (EditText) findViewById(R.id.sub);
		selectionWay = (EditText) findViewById(R.id.selectionWay);
		hashtag = (EditText) findViewById(R.id.hashtag);

		// imageToUpload.setOnClickListener(this);
		bUploadImage.setOnClickListener(this);

		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new UploadImage().execute(userid.getText().toString(), contents.getText().toString(),
						city.getText().toString(), district.getText().toString(), major.getText().toString(),
						sub.getText().toString(), selectionWay.getText().toString(), hashtag.getText().toString());

			}
		});
	}

	private class UploadImage extends AsyncTask<String, Void, Void> {
		public UploadImage() {
			super();
		}

		@Override
		protected Void doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			String url = SERVER_ADDRESS + "WritingGoods.do";
			HttpPost post = new HttpPost(url);

			File file = new File(imagePath);
			FileBody bin = new FileBody(file);
			MultipartEntityBuilder multipart = MultipartEntityBuilder.create();
			multipart.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			multipart.addPart("image", bin);
			multipart.addTextBody("userid", params[0]);
			multipart.addTextBody("contents", params[1]);
			multipart.addTextBody("city", params[2]);
			multipart.addTextBody("district", params[3]);
			multipart.addTextBody("major", params[4]);
			multipart.addTextBody("sub", params[5]);
			multipart.addTextBody("selectionWay", params[6]);
			multipart.addTextBody("hashtag", params[7]);
			post.setEntity(multipart.build());

			InputStream inputStream = null;
			try {

				HttpResponse httpResponse = client.execute(post);

				System.out.println("ȣ��7");
				HttpEntity httpEntity = httpResponse.getEntity();

				System.out.println("ȣ��8");
				inputStream = httpEntity.getContent();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "image uploaded", Toast.LENGTH_SHORT).show();
		}

	}

	private HttpParams getHttpRequestParams() {
		HttpParams httpRequestParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
		HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
		return httpRequestParams;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.imageToUpload:
			break;
		case R.id.bUploadImage:
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
			intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULT_LOAD_IMAGE);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
			Uri selectedImage = data.getData();
			imageName = getImageNameToUri(data.getData());
			imageToUpload.setImageURI(selectedImage);

		}
	}

	public String getImageNameToUri(Uri data) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(data, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		imagePath = cursor.getString(column_index);
		String imgName = imagePath.substring(imagePath.lastIndexOf("/") + 1);

		return imgName;
	}

}