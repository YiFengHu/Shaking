package com.nptu.dse.shaking.db;

public class DBRepository {

	protected static final String DATABASE_NAME = "moneybook.db";
	protected static final String DATABASE_TABLE = "Detail";

	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String ALARM_HOUR = "alarm_hour";
	public static final String ALARM_MINUTE = "alarm_minute";
	public static final String ALARM_TIME = "alarm_time";


	protected static String createDBSyntax() {
		String createDBSytax = "CREATE TABLE " + DATABASE_TABLE + "(" + ID
				+ " LONG, " + MESSAGE + " TEXT, "
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
	
	public static String getInsertSyntax(long id, String alarmMessage ,int hoursOfDay, int minute, long time) {
		String insertSyntax = "INSERT INTO " + DATABASE_TABLE + " VALUES( "+id+", "+alarmMessage+", "+hoursOfDay+", "+minute+" );";
		return insertSyntax;
	}

}
