package com.nptu.dse.shaking.db;

public class DBRepository {

	protected static final String DATABASE_NAME = "moneybook.db";
	protected static final String DATABASE_TABLE = "Detail";

	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String ALARM_TIME = "alarm_time";
	public static final String CREATE_TIME = "create_time";

	protected static String createDBSyntax() {
		String createDBSytax = "CREATE TABLE " + DATABASE_TABLE + "(" + ID
				+ " INTEGER PRIMARY KEY, " + MESSAGE + " TEXT NOT NULL, "
				+ ALARM_TIME + " TIMESTAMP, " + CREATE_TIME + " TIMESTAMP"
				+ ");";

		return createDBSytax;
	}

	public static String getAllDetailDescSyntax() {
		String getAllDetail = "SELECT * FROM " + DATABASE_TABLE + " ORDER BY "
				+ ALARM_TIME + " DESC;";
		return getAllDetail;
	}

	public static String getAllDetailAscSyntax() {
		String allDetailSyntax = "SELECT * FROM " + DATABASE_TABLE
				+ " ORDER BY " + ALARM_TIME + " ASC;";
		return allDetailSyntax;
	}

}
