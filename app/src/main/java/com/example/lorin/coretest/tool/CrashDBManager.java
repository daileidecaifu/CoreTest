package com.example.lorin.coretest.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lorin.coretest.object.litepal.CrashMarkManual;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorin on 16/2/3.
 */
public class CrashDBManager {

  private CrashDBHelper helper;
  private SQLiteDatabase db;

  public CrashDBManager(Context context) {
    helper = new CrashDBHelper(context);
    //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
    //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
    db = helper.getWritableDatabase();
  }

  /**
   * add CrashMarkManual
   */
  public void add(CrashMarkManual crashMarkManual) {
    db.beginTransaction();  //开始事务
    try {
      db.execSQL("INSERT INTO crash VALUES(null, ?, ?, ?)",
          new Object[]{crashMarkManual.getCurrence_time(), crashMarkManual.getCrash_message(),
              crashMarkManual.getHas_sent()});
      db.setTransactionSuccessful();  //设置事务成功完成
    } finally {
      db.endTransaction();    //结束事务
    }
  }

  /**
   * add crashMarkManual
   */
  public void add(List<CrashMarkManual> crashMarkManuals) {
    db.beginTransaction();  //开始事务
    try {
      for (CrashMarkManual crashMarkManual : crashMarkManuals) {
        String sql = "INSERT INTO crash VALUES(null, ?, ?, ?)";
        db.execSQL(sql,
            new Object[]{crashMarkManual.getCurrence_time(), crashMarkManual.getCrash_message(),
                crashMarkManual.getHas_sent()});
      }
      db.setTransactionSuccessful();  //设置事务成功完成
    } finally {
      db.endTransaction();    //结束事务
    }
  }


  /**
   * query all crashMarks, return list
   *
   * @return List<crashMarks>
   */
  public List<CrashMarkManual> query() {
    ArrayList<CrashMarkManual> crashMarkManuals = new ArrayList<CrashMarkManual>();
    Cursor c = queryTheCursor();
    while (c.moveToNext()) {
      CrashMarkManual crashMarkManual = new CrashMarkManual();

      crashMarkManual.set_id(c.getInt(c.getColumnIndex("_id")));
      crashMarkManual.setCurrence_time(c.getString(c.getColumnIndex("currence_time")));
      crashMarkManual.setCrash_message(c.getString(c.getColumnIndex("crash_message")));
      crashMarkManual.setHas_sent(c.getInt(c.getColumnIndex("has_sent")));

      crashMarkManuals.add(crashMarkManual);
    }
    c.close();
    return crashMarkManuals;
  }

  /**
   * query all persons, return cursor
   *
   * @return Cursor
   */
  public Cursor queryTheCursor() {
    Cursor c = db.rawQuery("SELECT * FROM crash", null);
    return c;
  }

  /**
   * query unsent crashMarks, return list
   *
   * @return List<crashMarks>
   */
  public List<CrashMarkManual> queryUnsent() {
    ArrayList<CrashMarkManual> crashMarks = new ArrayList<CrashMarkManual>();
    Cursor c = queryUnSentCursor();
    while (c.moveToNext()) {
      CrashMarkManual crashMarkManual = new CrashMarkManual();

      crashMarkManual.set_id(c.getInt(c.getColumnIndex("_id")));
      crashMarkManual.setCurrence_time(c.getString(c.getColumnIndex("currence_time")));
      crashMarkManual.setCrash_message(c.getString(c.getColumnIndex("crash_message")));
      crashMarkManual.setHas_sent(c.getInt(c.getColumnIndex("has_sent")));

      crashMarks.add(crashMarkManual);
    }
    c.close();
    return crashMarks;
  }

  /**
   * query unsent persons, return cursor
   *
   * @return Cursor
   */
  public Cursor queryUnSentCursor() {
    Cursor c = db.rawQuery("SELECT * FROM crash where has_sent=?", new String[]{"0"});
    return c;
  }

  /**
   * close database
   */
  public void closeDB() {
    db.close();
  }
}
