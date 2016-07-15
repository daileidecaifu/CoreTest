package com.example.lorin.helloworld_coretest;

import android.app.Application;
import android.util.Log;

import com.example.lorin.helloworld_coretest.tool.CustomCrashHandler;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;

import org.litepal.LitePalApplication;

/**
 * Created by lorin on 16/1/19.
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());

        LitePalApplication.initialize(this);

        WXEnvironment.addCustomOptions("appName", "TBSample");
        WXSDKEngine.initialize(this, null);

        Log.d("Tag","Git Test");

    }

}
