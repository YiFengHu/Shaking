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

		//取得通知管理器
		NotificationManager noMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		//當使用者按下通知欄中的通知時要開啟的 Activity
		Intent call = new Intent(context, SelectSportActivity.class);
		//非必要,可以利用intent傳值
		call.putExtra("notiId", 1);
		//建立待處理意圖
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, call, PendingIntent.FLAG_CANCEL_CURRENT); 

		//指定通知欄位要顯示的圖示
		int icon = R.drawable.ic_launcher;
		//指定通知出現時要顯示的文字,幾秒後會消失只剩圖示
		String ticket = "電影時刻通知";
		//何時送出通知,傳入當前時間則立即發出通知
		long when = System.currentTimeMillis();
		
		//指定通知標題
		String title = message;
		//通知內容
		String desc = "Detail: "+message;
				
//		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 通知音效的URI，在這裡使用系統內建的通知音效

		//建立通知物件
		Notification notification = new Notification.Builder(context)
											.setSmallIcon(R.drawable.ic_launcher)
											.setContentTitle(title)
											.setContentText(desc)
											.setContentIntent(pendingIntent)
											.setAutoCancel(true).build();


		//非必要,會在通知圖示旁顯示數字
		notification.number = 5;

		//執行通知
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
