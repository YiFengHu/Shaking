package com.nptu.dse.shaking.ui.adapter;

import java.util.ArrayList;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.db.AlarmEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlarmListAdapter extends BaseAdapter {

	private static final String TAG = AlarmListAdapter.class.getSimpleName();

	private static final int LAYOUT_ITEM = R.layout.alarm_list_item;
	private static final int ID_INDEX_TEXTVIEW = R.id.mainAlarmListItem_indexTextView;
	private static final int ID_MESSAGE_TEXTVIEW = R.id.mainAlarmListItem_messageTextView;
	private static final int ID_TIME_TEXTVIEW = R.id.mainAlarmListItem_timeTextView;
	private static final int ID_ID_TEXTVIEW = R.id.mainAlarmListItem_idTextView;
	private Context context = null;
	private ArrayList<AlarmEntity> mAlarmList = null;

	public AlarmListAdapter(Context context, ArrayList<AlarmEntity> dataList) {
		this.context = context;
		this.mAlarmList = dataList;
	}
	
	public void updateData(ArrayList<AlarmEntity> mAlarmList) {
		this.mAlarmList = mAlarmList;
	}

	@Override
	public int getCount() {
		return mAlarmList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAlarmList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		
		if(convertView == null){
			LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(LAYOUT_ITEM, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.indexTextView.setText(""+(position+1));
		viewHolder.idTextView.setText(""+mAlarmList.get(position).getId());
		viewHolder.messageTextView.setText(mAlarmList.get(position).getMessage());
		viewHolder.timeTextView.setText(mAlarmList.get(position).getAlarm_hour()+":"+mAlarmList.get(position).getAlarm_minute());

		
		return convertView;
	}

	class ViewHolder {
		private TextView indexTextView = null;
		private TextView idTextView = null;
		private TextView messageTextView = null;
		private TextView timeTextView = null;

		ViewHolder(View rootView) {
			indexTextView = (TextView)rootView.findViewById(ID_INDEX_TEXTVIEW);
			idTextView = (TextView)rootView.findViewById(ID_ID_TEXTVIEW);
			messageTextView = (TextView)rootView.findViewById(ID_MESSAGE_TEXTVIEW);
			timeTextView = (TextView)rootView.findViewById(ID_TIME_TEXTVIEW);

		}

	}

}
