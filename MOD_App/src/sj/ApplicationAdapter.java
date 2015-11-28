package sj;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nanumi.R;
import com.nanumi.sub.GoodsDTO;

public class ApplicationAdapter extends BaseAdapter {
	List<ApplicationsDTO> applicationList;
	List<GoodsDTO> goodsList;
	
	public ApplicationAdapter(List<ApplicationsDTO> applicantList) {
		this.applicationList = applicantList;
		
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final Context context = parent.getContext();
		if(convertView == null) { 
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_application_list_item, parent, false);
			TextView userId =(TextView) convertView.findViewById(R.id.tvApplicantUserId);
			TextView distance = (TextView) convertView.findViewById(R.id.tvApplicantDistance);
			TextView time = (TextView) convertView.findViewById(R.id.tvApplicantTime);
			
			//userId.setText(applicationList.get(position).get(userId));
			//distance.setText(applicationList.get(position).get(distance));
			//time.setText(applicationList.get(position).get(time));

		}
		return null;
	}

}
