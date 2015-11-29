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
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
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
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private String imagePath = null;

	ImageView imageToUpload;
	Button buttonCamera, buttonGallery, registerBtn;
	EditText contents, hashtag;
	Spinner citySpin, districtSpin, majorSpin, subSpin;
	String selectionWay = null;
	TextView useridTextView;
	RadioGroup radioGroup;

	HashMap<String, List<String>> addresses = null;
	List<String> cities = null;
	HashMap<String, List<String>> categorize = null;
	ArrayList<String> majors = null;
	String initCity, initDistrict = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
		buttonCamera = (Button) findViewById(R.id.buttonCamera);
		buttonGallery = (Button) findViewById(R.id.buttonGallery);
		registerBtn = (Button) findViewById(R.id.registerBtn);
		useridTextView = (TextView) findViewById(R.id.userid);
		contents = (EditText) findViewById(R.id.contents);
		hashtag = (EditText) findViewById(R.id.hashtag);
		citySpin = (Spinner) findViewById(R.id.city);
		districtSpin = (Spinner) findViewById(R.id.district);
		majorSpin = (Spinner) findViewById(R.id.major);
		subSpin = (Spinner) findViewById(R.id.sub);

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

		addresses = new HashMap<String, List<String>>();
		cities = new ArrayList<String>();
		categorize = new HashMap<String, List<String>>();
		majors = new ArrayList<String>();

		// id_초기화!
		SharedPreferences pref = getSharedPreferences("Login", 0);
		String id = pref.getString("uuid", "").split("-")[0];
		useridTextView.setText(id);

		// address_초기화
		AddressRequest address = new AddressRequest();
		InitAddressRequest initAddress = new InitAddressRequest();
		try {
			resultParse(address.execute().get());
			initAddressParse(initAddress.execute(id).get());

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

		citySpin.setOnItemSelectedListener(new OnItemSelectedListener() {
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

		majorSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

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
				new UploadImage().execute(useridTextView.getText().toString(), contents.getText().toString(),
						citySpin.getSelectedItem().toString(), districtSpin.getSelectedItem().toString(),
						majorSpin.getSelectedItem().toString(), subSpin.getSelectedItem().toString(),
						selectionWay.toString(), hashtag.getText().toString());

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

	private void initAddressParse(String result) throws JSONException {
		JSONArray jsonArr = new JSONArray(result);
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			initCity = jsonObj.getString("city").toString().trim();
			initDistrict = jsonObj.getString("district").toString().trim();

			Log.d("start", initCity);
			Log.d("start", initDistrict);
		}
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

	public void CameraAndGallery(View v) {
		switch (v.getId()) {
		case R.id.buttonCamera:

			Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
			// ******** code for crop image
			intentCamera.putExtra("crop", "true");
			intentCamera.putExtra("aspectX", 0);
			intentCamera.putExtra("aspectY", 0);
			intentCamera.putExtra("outputX", 200);
			intentCamera.putExtra("outputY", 150);

			try {

				intentCamera.putExtra("return-data", true);
				startActivityForResult(intentCamera, PICK_FROM_CAMERA);

			} catch (ActivityNotFoundException e) {
				// Do nothing for now
			}

			break;
		case R.id.buttonGallery:
			Intent intentGallery = new Intent();
			// call android default gallery
			intentGallery.setType("image/*");
			intentGallery.setAction(Intent.ACTION_GET_CONTENT);
			// ******** code for crop image
			intentGallery.putExtra("crop", "true");
			intentGallery.putExtra("aspectX", 0);
			intentGallery.putExtra("aspectY", 0);
			intentGallery.putExtra("outputX", 200);
			intentGallery.putExtra("outputY", 150);

			try {

				intentGallery.putExtra("return-data", true);
				startActivityForResult(Intent.createChooser(intentGallery, "Complete action using"), PICK_FROM_GALLERY);

			} catch (ActivityNotFoundException e) {
				// Do nothing for now
			}
			break;
		default:
			break;
		}
	}

	private class UploadImage extends AsyncTask<String, Void, Void> {
		public UploadImage() {
			super();
		}

		@Override
		protected Void doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			String url = "http://113.198.80.223/MOD_WAS/WritingGoods.do";
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PICK_FROM_CAMERA) {
			try {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					imageToUpload.setImageBitmap(photo);

				}

			} catch (NullPointerException e) {

			}

		}

		if (requestCode == PICK_FROM_GALLERY) {
			try {
				Bundle extras2 = data.getExtras();
				if (extras2 != null) {
					Bitmap photo = extras2.getParcelable("data");
					imageToUpload.setImageBitmap(photo);

				}
			} catch (NullPointerException e) {

			}
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
		citySpin.setAdapter(adapter);

		for (int i = 0; i < obj.size(); i++) {
			if (obj.get(i).equals(initCity)) {
				citySpin.setSelection(i);
			}
		}
	}

	public void initDistricts(List<String> districts) {

		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districts);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		districtSpin.setAdapter(adapter);
		for (int i = 0; i < districts.size(); i++) {
			if (districts.get(i).equals(initDistrict)) {
				districtSpin.setSelection(i);
			}
		}
	}

	public void initMajors(List<String> obj) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, obj);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		majorSpin.setAdapter(adapter);
	}

	public void initSubs(List<String> subs) {
		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subs);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subSpin.setAdapter(adapter);
	}



}
