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
		//當使用者按下通知欄中的通知時要開啟的 Activity
		Intent call = new Intent(context, SelectActivity.class);
		//非必要,可以利用intent傳值
		call.putExtra("notiId", 1);
		//建立待處理意圖
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, call, 0); 

		//指定通知欄位要顯示的圖示
		int icon = R.drawable.ic_launcher;
		//指定通知出現時要顯示的文字,幾秒後會消失只剩圖示
		String ticket = "Shaking time !!!";
		//何時送出通知,傳入當前時間則立即發出通知
		long when = System.currentTimeMillis();
		//建立通知物件
		Notification notification = new Notification(icon, ticket, when);

		//指定通知標題
		String title = "Let's do some excercise ~~";
		//通知內容
		String desc = "Click to start...";
		//設定事件資訊
		notification.setLatestEventInfo(context, title, desc, pIntent);

		//非必要,會在通知圖示旁顯示數字
		notification.number = 5;

		//執行通知
		notificationManager.notify(1, notification);
	}

}
