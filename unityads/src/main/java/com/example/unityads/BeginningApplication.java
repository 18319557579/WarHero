package com.example.unityads;

import android.app.Application;
import android.util.Log;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;

public class BeginningApplication extends Application {
    private String unityGameID = "5255042";
    private Boolean testMode = true;

    @Override
    public void onCreate() {
        super.onCreate();

        UnityAds.initialize(this, unityGameID, testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                LogUtil.d("UnityAds 初始化完成");
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                LogUtil.d("UnityAds 初始化失败。类型: " + error + ", 信息: " + message);
            }
        });
    }
}
