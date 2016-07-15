package com.example.lorin.helloworld_coretest.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.lorin.helloworld_coretest.CustomApplication;
import com.example.lorin.helloworld_coretest.object.litepal.CrashMark;
import com.example.lorin.helloworld_coretest.object.litepal.CrashMarkManual;
import com.example.lorin.helloworld_coretest.tool.ExitAppUtils;

/**
 * 自定义系统的Crash捕捉类，用Toast替换系统的对话框
 * 将软件版本信息，设备信息，出错信息保存在sd卡中
 *
 * @author Lorin
 */

public class CustomCrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CRASH_TAG";
    private Context mContext;
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
    private static CustomCrashHandler mInstance = new CustomCrashHandler();
    private CrashDBManager crashDBManager = null;
    private CustomCrashHandler() {
    }

    /**
     * 单例模式，保证只有一个CustomCrashHandler实例存在
     *
     * @return
     */
    public static CustomCrashHandler getInstance() {
        return mInstance;
    }

    /**
     * 异常发生时，系统回调的函数，我们在这里处理一些操作
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 信息储存到SDcard
        savaInfoToSD(mContext, ex);

        //提示用户即将退出信息
        showToast(mContext, "出问题了额，退出下！");
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // android.os.Process.killProcess(android.os.Process.myPid());
        // System.exit(1);

        ExitAppUtils.getInstance().exit();

    }

    /**
     * 为我们的应用程序设置自定义Crash处理
     */
    public void setCustomCrashHanler(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 显示提示信息，需要在线程中显示Toast
     *
     * @param context
     * @param msg
     */
    private void showToast(final Context context, final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     *
     * @param context
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        map.put("packageName", mPackageInfo.packageName);
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);

        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);

        return map;
    }

    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();

        Log.e(TAG, mStringWriter.toString());
        return mStringWriter.toString();
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     *
     * @param context
     * @param ex
     * @return
     */
    private String savaInfoToSD(Context context, Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        sb.append("Time").append(" = ").append(createTime).append("\n");
        sb.append(obtainExceptionInfo(ex));

//        //第三方ORM SQL工具
//        CrashMark crashMark = new CrashMark();
//        crashMark.setCrashMessage(sb.toString());
//        crashMark.setCcurrenceTime(createTime);
//        crashMark.setHasSent(false);
//        crashMark.save();
        if (null == crashDBManager) {
            crashDBManager = new CrashDBManager(mContext);
        }
        CrashMarkManual crashMarkManual = new CrashMarkManual();
        crashMarkManual.setCrash_message(sb.toString());
        crashMarkManual.setCurrence_time(createTime);
        crashMarkManual.setHas_sent(0);
        crashDBManager.add(crashMarkManual);
        crashDBManager.closeDB();
        crashDBManager = null;


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(SDCARD_ROOT + File.separator + "crash" + File.separator);
            if (!dir.exists()) {
                dir.mkdir();
            }

            try {
                fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".log";

                BufferedWriter out = null;
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
                out.write(sb.toString());
                out.flush();
                out.close();
                // FileOutputStream fos = new FileOutputStream(fileName);
                // fos.write(sb.toString().getBytes());
                // fos.flush();
                // fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return fileName;

    }

    /**
     * 将毫秒数转换成yyyy-MM-dd的格式，一天一个文件保存
     *
     * @param milliseconds
     * @return
     */
    private String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String times = format.format(new Date(milliseconds));

        return times;
    }
}