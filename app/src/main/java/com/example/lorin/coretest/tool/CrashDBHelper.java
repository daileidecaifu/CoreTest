package com.example.lorin.coretest.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lorin on 16/2/3.
 */
public class CrashDBHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "ld_crash_test.db";
  private static final int DATABASE_VERSION = 1;

  public CrashDBHelper(Context context) {
    //CursorFactory设置为null,使用默认值
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  //数据库第一次被创建时onCreate会被调用
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS crash"
        + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, currence_time VARCHAR, crash_message VARCHAR, has_sent INTEGER)");
  }

  //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
  }
}
