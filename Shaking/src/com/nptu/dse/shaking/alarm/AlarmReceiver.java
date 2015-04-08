package com.nptu.dse.shaking.alarm;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.activity.DialogActivity;
import com.nptu.dse.shaking.activity.SelectSportActivity;
import com.nptu.dse.shaking.db.AlarmEntity;
import com.nptu.dse.shaking.main.ApplicationManager;
import com.nptu.dse.shaking.main.MainActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private static final String TAG = AlarmReceiver.class.getSimpleName();
	
	public static final String BUNDLE_KEY_ALARM_MESSAGE = "BUNDLE_KEY_ALARM_MESSAGE"; 
	private Context context = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Log.i(TAG, "onReceive");
		Log.d(TAG, intent.toString());
		String message = intent.getStringExtra(AlarmAgent.INTENT_KEY_MESSAGE);
		Log.d(TAG, "msg=" + message);
//		Toast.makeText(ApplicationManager.getInstance().getContext(), "alarm:" + message, Toast.LENGTH_SHORT).show();
		
		long triggerMillis = intent.getLongExtra(AlarmAgent.INTENT_KEY_TIME, 0L);
		long nextTriggerMillis = triggerMillis+AlarmManager.INTERVAL_DAY;
		
		AlarmEntity alarmEntity = new AlarmEntity();
		alarmEntity.setAlarm_hour(intent.getIntExtra(AlarmAgent.INTENT_KEY_HOUR, 0));
		alarmEntity.setAlarm_minute(intent.getIntExtra(AlarmAgent.INTENT_KEY_MINUTE, 0));
		alarmEntity.setAlarm_time(nextTriggerMillis);
		alarmEntity.setId((int)System.currentTimeMillis());
		AlarmAgent alrmAgent = AlarmAgent.getInstance(context);
		
		alrmAgent.deleteFromDB(alarmEntity.getId());
		alrmAgent.setAlarm(intent.getIntExtra(AlarmAgent.INTENT_KEY_HOUR, 0), intent.getIntExtra(AlarmAgent.INTENT_KEY_MINUTE, 0), true);

		//���o�q���޲z��
		NotificationManager noMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		//��ϥΪ̫��U�q���椤���q���ɭn�}�Ҫ� Activity
		Intent call = new Intent(context, SelectSportActivity.class);
		//�D���n,�i�H�Q��intent�ǭ�
		call.putExtra("notiId", 1);
		//�إ߫ݳB�z�N��
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, call, PendingIntent.FLAG_CANCEL_CURRENT); 

		//���w�q�����n��ܪ��ϥ�
		int icon = R.drawable.ic_launcher;
		//���w�q���X�{�ɭn��ܪ���r,�X���|�����u�ѹϥ�
		String ticket = "�q�v�ɨ�q��";
		//��ɰe�X�q��,�ǤJ��e�ɶ��h�ߧY�o�X�q��
		long when = System.currentTimeMillis();
		
		//���w�q�����D
		String title = message;
		//�q�����e
		String desc = "Detail: "+message;
				
//		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // �q�����Ī�URI�A�b�o�̨ϥΨt�Τ��ت��q������

		//�إ߳q������
		Notification notification = new Notification.Builder(context)
											.setSmallIcon(R.drawable.ic_launcher)
											.setContentTitle(title)
											.setContentText(desc)
											.setContentIntent(pendingIntent)
											.setAutoCancel(true).build();


		//�D���n,�|�b�q���ϥܮ���ܼƦr
		notification.number = 5;

		//����q��
		noMgr.notify(1, notification);
		
		goToDialogActivity(message);
		
		RingAgent.getInstance().setDefaultRingTone();
		RingAgent.getInstance().startRing();
	}
	
	private void goToDialogActivity(String message){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_KEY_ALARM_MESSAGE, message);
		intent.putExtras(bundle);
		
		intent.setClass(context, DialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	private void showDialog(){
	}

}
