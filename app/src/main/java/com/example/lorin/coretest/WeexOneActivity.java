package com.example.lorin.coretest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lorin.helloworld_coretest.R;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lorin on 16/7/8.
 */
public class WeexOneActivity extends Activity {

  RelativeLayout viewGroup;
  private static final String DEFAULT_IP = "10.100.0.158";
  // your_current_IP
  private static String currentIp = DEFAULT_IP;
  private static final String WEEX_INDEX_URL =
      "http://" + currentIp + ":12580/examples/build/index.js";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weex_one);
    viewGroup = (RelativeLayout) findViewById(R.id.viewGroup);
    WXSDKInstance mInstance = new WXSDKInstance(this);

    mInstance.registerRenderListener(new IWXRenderListener() {
      @Override
      public void onViewCreated(WXSDKInstance instance, View view) {
        viewGroup.addView(view);
      }

      @Override
      public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

      }

      @Override
      public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

      }

      @Override
      public void onException(WXSDKInstance instance, String errCode, String msg) {

      }
    });

    renderPage(mInstance, getPackageName(), WXFileUtils.loadFileContent("tech_list.js", this),
        WEEX_INDEX_URL, null);


  }

  protected void renderPage(WXSDKInstance mInstance, String packageName, String template,
      String source, String jsonInitData) {
    Map<String, Object> options = new HashMap<>();
    options.put(WXSDKInstance.BUNDLE_URL, source);
    mInstance.render(
        packageName,
        template,
        options,
        jsonInitData,
        WXViewUtils.getScreenWidth(this),
        WXViewUtils.getScreenHeight(this),
        WXRenderStrategy.APPEND_ASYNC);
  }
}
