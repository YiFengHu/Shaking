package com.nptu.dse.shaking.alarm;

import com.example.shaking.R;
import com.nptu.dse.shaking.activity.SelectActivity;
import com.nptu.dse.shaking.main.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private static final String TAG = AlarmReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive");
		Log.d(TAG, intent.toString());
		String msg = intent.getStringExtra("msg");
		Log.d(TAG, "msg=" + msg);
		Toast.makeText(context, "alarm:" + msg, Toast.LENGTH_SHORT).show();
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		//��ϥΪ̫��U�q���椤���q���ɭn�}�Ҫ� Activity
		Intent call = new Intent(context, SelectActivity.class);
		//�D���n,�i�H�Q��intent�ǭ�
		call.putExtra("notiId", 1);
		//�إ߫ݳB�z�N��
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, call, 0); 

		//���w�q�����n��ܪ��ϥ�
		int icon = R.drawable.ic_launcher;
		//���w�q���X�{�ɭn��ܪ���r,�X���|�����u�ѹϥ�
		String ticket = "Shaking time !!!";
		//��ɰe�X�q��,�ǤJ��e�ɶ��h�ߧY�o�X�q��
		long when = System.currentTimeMillis();
		//�إ߳q������
		Notification notification = new Notification(icon, ticket, when);

		//���w�q�����D
		String title = "Let's do some excercise ~~";
		//�q�����e
		String desc = "Click to start...";
		//�]�w�ƥ��T
		notification.setLatestEventInfo(context, title, desc, pIntent);

		//�D���n,�|�b�q���ϥܮ���ܼƦr
		notification.number = 5;

		//����q��
		notificationManager.notify(1, notification);
	}

}
