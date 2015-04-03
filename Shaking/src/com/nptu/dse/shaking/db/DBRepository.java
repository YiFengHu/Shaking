package com.nptu.dse.shaking.db;

public class DBRepository {

	protected static final String DATABASE_NAME = "Shaking.db";
	protected static final String DATABASE_TABLE = "alarm";

	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String ALARM_HOUR = "alarm_hour";
	public static final String ALARM_MINUTE = "alarm_minute";
	public static final String ALARM_TIME = "alarm_time";


	protected static String createAlarmTableSyntax() {
		String createDBSytax = "CREATE TABLE " + DATABASE_TABLE + "(" + ID
				+ " INTEGER, " + MESSAGE + " TEXT, "
				+ ALARM_HOUR + " INTEGER, " + ALARM_MINUTE + " INTEGER, "
				+ ALARM_TIME+" LONG );";

		return createDBSytax;
	}

	public static String getAllAlarmDataSyntax() {
		String getAllAlarmSyntax = "SELECT * FROM " + DATABASE_TABLE + " ;";
		return getAllAlarmSyntax;
	}
	
	public static String getAllAlarmCountSyntax() {
		String getAlarmCountSyntax = "SELECT COUNT(*) FROM " + DATABASE_TABLE + " ;";
		return getAlarmCountSyntax;
	}
	
	public static String getInsertSyntax(int id, String alarmMessage ,int hoursOfDay, int minute, long time) {
		String insertSyntax = "INSERT INTO " + DATABASE_TABLE + " ("+ID+", "+MESSAGE+","+ALARM_HOUR+", "+ALARM_MINUTE+", "+ALARM_TIME+") VALUES( "+id+", '"+alarmMessage+"', "+hoursOfDay+", "+minute+", "+time+" );";
		return insertSyntax;
	}
	
	public class TableIndex{
		public static final int ID = 0;
		public static final int MESSAGE = 1;
		public static final int ALARM_HOUR = 2;
		public static final int ALARM_MINUTE = 3;
		public static final int ALARM_TIME = 4;
	}

}
