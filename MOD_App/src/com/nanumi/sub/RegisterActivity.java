package com.nanumi.sub;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nanumi.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final String SERVER_ADDRESS = "http://113.198.80.223/MOD_WAS/";
	private String imagePath = null;

	ImageView imageToUpload;
	Button bUploadImage, registerBtn;
	EditText contents, hashtag;
	Spinner city, district, major, sub;
	String selectionWay = null;
	TextView userid;

	RadioGroup radioGroup;

	HashMap<String, List<String>> addresses = null;
	List<String> cities = null;

	HashMap<String, List<String>> categorize = null;
	ArrayList<String> majors = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
		bUploadImage = (Button) findViewById(R.id.bUploadImage);
		registerBtn = (Button) findViewById(R.id.registerBtn);
		userid = (TextView) findViewById(R.id.userid);
		contents = (EditText) findViewById(R.id.contents);
		hashtag = (EditText) findViewById(R.id.hashtag);
		city = (Spinner) findViewById(R.id.city);
		district = (Spinner) findViewById(R.id.district);
		major = (Spinner) findViewById(R.id.major);
		sub = (Spinner) findViewById(R.id.sub);

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

		addresses = new HashMap<String, List<String>>();
		cities = new ArrayList<String>();

		categorize = new HashMap<String, List<String>>();
		majors = new ArrayList<String>();

		SharedPreferences pref = getSharedPreferences("Login", 0);
		String id = pref.getString("uuid", "").split("-")[0];
		userid.setText(id);

		bUploadImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
				intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, RESULT_LOAD_IMAGE);
			}
		});

		AddressRequest request = new AddressRequest();

		try {
			resultParse(request.execute().get());
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

		initCitis(cities);
		initMajors(majors);

		city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView text = (TextView) view;
				String city = text.getText().toString();

				initDistricts(addresses.get(city));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		major.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView text = (TextView) view;
				String major = text.getText().toString();
				initSubs(categorize.get(major));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		registerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new UploadImage().execute(userid.getText().toString(), contents.getText().toString(),
						city.getSelectedItem().toString(), district.getSelectedItem().toString(),
						major.getSelectedItem().toString(), sub.getSelectedItem().toString(), selectionWay.toString(),
						hashtag.getText().toString());

				//

				finish();

			}
		});

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.selectionWay0:
					selectionWay = "0";
					break;
				case R.id.selectionWay1:
					selectionWay = "1";
					break;
				default:
					break;
				}
			}
		});
	}

	private void resultParse(String result) throws JSONException {

		// 임시 데이터 저장
		String major = "대분류";
		String sub = "중분류,소분류";
		List<String> ls = Arrays.asList(sub.split(","));
		majors.add(major);
		categorize.put(major, ls);

		JSONArray jsonArr = new JSONArray(result);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			String city = jsonObj.getString("city");
			String district = jsonObj.getString("district");

			List<String> obj = Arrays.asList(district.split(","));
			cities.add(city);
			addresses.put(city, obj);
		}
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
			Log.d("===================", "==========================");
			Log.d("imagePath", imagePath);
			Log.d("===================", "==========================");
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

				HttpEntity httpEntity = httpResponse.getEntity();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
			Uri selectedImage = data.getData();
			imagePath = getImagePath(data.getData());
			imageToUpload.setImageURI(selectedImage);

		}
	}

	public String getImagePath(Uri data) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(data, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void initCitis(List<String> obj) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, obj);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adapter);

	}

	public void initDistricts(List<String> districts) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districts);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		district.setAdapter(adapter);
	}

	public void initMajors(List<String> obj) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, obj);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		major.setAdapter(adapter);
	}

	public void initSubs(List<String> subs) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subs);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sub.setAdapter(adapter);
	}

}
