package com.example.lorin.helloworld_coretest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lorin.helloworld_coretest.databinding.ActivityMainBinding;
import com.example.lorin.helloworld_coretest.object.SettingDescription;
import com.example.lorin.helloworld_coretest.object.Student;
import com.example.lorin.helloworld_coretest.object.litepal.CrashMark;
import com.example.lorin.helloworld_coretest.object.litepal.CrashMarkManual;
import com.example.lorin.helloworld_coretest.object.litepal.DBTable1;
import com.example.lorin.helloworld_coretest.object.litepal.DBTable2;
import com.example.lorin.helloworld_coretest.tool.CLogUtil;
import com.example.lorin.helloworld_coretest.tool.CrashDBManager;
import com.example.lorin.helloworld_coretest.tool.TestDownloadService;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends Activity {
    private String debug_APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NotificationDemo";
    private TextView textViewErro;
    private CrashDBManager crashDBManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setSetting(new SettingDescription("DownLoad!", "Crash@", "Intent#", "DBTest$", "Weex", "Page 1"));

        File dir = Environment.getExternalStorageDirectory();
        if (dir.exists()) {
            System.out.println(Environment.getExternalStorageState() + "can read:" + dir.canRead() + "can write:" + dir.canWrite());
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
        debug_APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/download/";// 保存到SD卡路径下
        File destDir = new File(debug_APK_dir);
        CLogUtil.e("TAG1", debug_APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
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
            case R.id.startButton:
                System.out.println("Click to Start!");
                Intent intent = new Intent(MainActivity.this, TestDownloadService.class);
                startService(intent);
                break;

            case R.id.testErrorButton:
                System.out.println("Click to test Error!");
                textViewError.setText("111");
                break;


            case R.id.testIntentButton:
                Intent i = new Intent(MainActivity.this, IntentReceiverActivity.class);
                Bundle bundle = new Bundle();

                //传递List ,这里注意只能传ArrayList
                bundle.putParcelableArrayList("student_list", getIntentData());
                i.putExtras(bundle);
                MainActivity.this.startActivity(i);
                break;


            case R.id.DBTestButton:
//                DBTable1 dbTable1 = new DBTable1();
//                dbTable1.setMyIndex(111);
//                dbTable1.setName("n111");
//                dbTable1.save();
//
//                DBTable2 dbTable2 = new DBTable2();
//                dbTable2.setName2("n222");
//                dbTable2.setMyType("Type2");
//                dbTable2.save();


//                List<CrashMark> allCrash = DataSupport.findAll(CrashMark.class);
//                CLogUtil.d("" + allCrash.size());
//
//                for (CrashMark crashMark : allCrash) {
//                    CLogUtil.d(crashMark.getCcurrenceTime());
//                    CLogUtil.d(crashMark.getCrashMessage());
//                }

                if (null == crashDBManager) {
                    crashDBManager = new CrashDBManager(MainActivity.this);
                }
                List<CrashMarkManual> crashMarkManuals = crashDBManager.query();

                crashDBManager.closeDB();
                crashDBManager = null;
                break;

            case R.id.WeexButton:

                Intent intent2 = new Intent(MainActivity.this, WeexOneActivity.class);
                startActivity(intent2);
                break;

        }
    }
}
