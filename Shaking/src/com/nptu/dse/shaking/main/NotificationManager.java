package com.nptu.dse.shaking.main;

import java.util.ArrayList;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.activity.SelectSportActivity;
import com.nptu.dse.shaking.resource.IntentKey;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationManager {

	private static String TAG = NotificationManager.class.getSimpleName();
	
	private static final int ID_APP_ICON = R.drawable.ic_launcher;
	
	private static NotificationManager instance = null;
	private Context context = null;
	private android.app.NotificationManager notificationManager = null;
	private Intent intent = null;
	private PendingIntent pendingIntent = null;
	
	private ArrayList<Integer> notificationIdList = null;

	private NotificationManager() {
		context = ApplicationManager.getInstance().getContext();
		notificationManager = (android.app.NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationIdList = new ArrayList<Integer>();
	}

	public static NotificationManager getInstance() {
		if (instance == null) {
			instance = new NotificationManager();
		}
		return instance;
	}

	public void sendNotification(Context context, String message) {

		int notificationId = generateNotificationId();
		
		// ��ϥΪ̫��U�q���椤���q���ɭn�}�Ҫ� Activity
		intent = new Intent(context, SelectSportActivity.class);
		// �D���n,�i�H�Q��intent�ǭ�
		intent.putExtra(IntentKey.INTENT_KEY_NOTIFICATION_ID, notificationId);
		// �إ߫ݳB�z�N��
		pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		// ���w�q���X�{�ɭn��ܪ���r,�X���|�����u�ѹϥ�
		String ticker = context.getString(R.string.notification_ticker_time_to_shake);

		// ���w�q�����D
		String title = message;
		// �q�����e
		String desc = "Detail: " + message;

		// �إ߳q������
		Notification notification = new Notification.Builder(context)
				.setSmallIcon(ID_APP_ICON).setContentTitle(title)
				.setContentText(desc).setContentIntent(pendingIntent)
				.setTicker(ticker).setAutoCancel(true).build();

		// �D���n,�|�b�q���ϥܮ���ܼƦr
		notification.number = 5;

		// ����q��
		notificationManager.notify(notificationId, notification);
	}
	
	public void removeNotification(int notificationId){
		if(notificationIdList!=null && notificationIdList.contains(notificationId)){
			notificationManager.cancel(notificationId);
			notificationIdList.remove((Integer)notificationId);
		}
		
	}
	
	public void removeAllNotification(){
		if(notificationIdList!=null && !notificationIdList.isEmpty()){
			for(Integer notificationId:notificationIdList){
				notificationManager.cancel(notificationId);
			}
			notificationIdList.clear();
		}
	}
	
	private int generateNotificationId(){
		int notificationId = (int)System.currentTimeMillis();
		notificationIdList.add(notificationId);
		return notificationId;
	}
}
