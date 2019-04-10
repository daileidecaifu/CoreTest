package com.example.lorin.coretest;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lorin.coretest.object.SettingDescription;
import com.example.lorin.coretest.object.Student;
import com.example.lorin.coretest.object.litepal.CrashMarkManual;
import com.example.lorin.coretest.tool.CLogUtil;
import com.example.lorin.coretest.tool.CrashDBManager;
import com.example.lorin.helloworld_coretest.R;
import com.example.lorin.helloworld_coretest.databinding.ActivityMainBinding;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Loirn
 */
public class MainActivity extends Activity {

  private String debugApkDir =
      Environment.getExternalStorageDirectory().getAbsolutePath() + "/NotificationDemo";
  private TextView textViewErro;
  private CrashDBManager crashDBManager = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setSetting(
        new SettingDescription("DownLoad!", "Crash@", "Intent#", "DBTest$", "Weex", "Page 1"));

    File dir = Environment.getExternalStorageDirectory();
    if (dir.exists()) {
      Log.d("CoreTest",
          Environment.getExternalStorageState() + "can read:" + dir.canRead() + "can write:" + dir
              .canWrite());
    }


  }

  private ArrayList<Student> getIntentData() {

    Student stu = new Student();
    stu.setAge(108);
    stu.setName("s1");

    Student stu2 = new Student();
    stu2.setAge(109);
    stu2.setName("s2");

    Student stu3 = new Student();
    stu3.setAge(110);
    stu3.setName("s3");
    ArrayList<Student> list = new ArrayList<Student>();
    list.add(stu);
    list.add(stu2);
    list.add(stu3);

    return list;
  }


  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);

  }

  private void initAPKDir() {
    debugApkDir =
        Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/download/";
    // 保存到SD卡路径下
    File destDir = new File(debugApkDir);
    CLogUtil.e("TAG1", debugApkDir);
    if (!destDir.exists()) {
      // 判断文件夹是否存在
      destDir.mkdirs();
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void click(View view) {

    switch (view.getId()) {
//      case R.id.startButton:
//        Log.d("CoreTest", "Click to start!");
//        Intent intent = new Intent(MainActivity.this, TestDownloadService.class);
//        startService(intent);
//        break;

//      case R.id.testErrorButton:
//        Log.d("CoreTest", "Click to test Error!");
//        textViewError.setText("111");
//        break;

      case R.id.testIntentButton:
        Intent i = new Intent(MainActivity.this, IntentReceiverActivity.class);
        Bundle bundle = new Bundle();

        //传递List ,这里注意只能传ArrayList
        bundle.putParcelableArrayList("student_list", getIntentData());
        i.putExtras(bundle);
        MainActivity.this.startActivity(i);
        break;

      case R.id.DBTestButton:

        if (null == crashDBManager) {
          crashDBManager = new CrashDBManager(MainActivity.this);
        }
        List<CrashMarkManual> crashMarkManuals = crashDBManager.query();

        crashDBManager.closeDB();
        crashDBManager = null;
        break;

//      case R.id.WeexButton:
//
//        Intent intent2 = new Intent(MainActivity.this, WeexOneActivity.class);
//        startActivity(intent2);
//        break;

      default:
        break;

    }
  }
}
