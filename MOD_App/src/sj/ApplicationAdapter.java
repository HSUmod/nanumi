package sj;

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

import com.nanumi.R;
import com.nanumi.sub.GoodsDTO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ApplicationAdapter extends BaseAdapter {
	List<ApplicationsDTO> applicationList;
	List<GoodsDTO> goodsList;
	String articleNum;
	
	public ApplicationAdapter(List<ApplicationsDTO> applicantList, String articleNum) {
		this.applicationList = applicantList;
		this. articleNum = articleNum;
	}

	@Override
	public int getCount() {
		
		return applicationList.size();
	}

	@Override
	public Object getItem(int position) {
		return applicationList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		final Context context = parent.getContext();
		if(convertView == null) { 
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_application_list_item, parent, false);
		}
		TextView userId =(TextView) convertView.findViewById(R.id.tvApplicantUserId);
		TextView distance = (TextView) convertView.findViewById(R.id.tvApplicantDistance);
		TextView time = (TextView) convertView.findViewById(R.id.tvApplicantTime);
		
		final String id = applicationList.get(position).getUserid();
		userId.setText(id);
		time.setText(applicationList.get(position).getTime());
		
		
		
		return convertView;
	}
}
