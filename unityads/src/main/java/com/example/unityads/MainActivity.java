package com.example.unityads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class MainActivity extends LifecycleLogActivity {
    private String adUnitId = "Interstitial_Android";
    private String adUnitId_IncentiveAdvertising = "Rewarded_Android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void playInterstitialAdvertising(View view) {
        UnityAds.load(adUnitId, new MyUnityAdsLoadListener(adUnitId));
    }

    public void playIncentiveAdvertising(View view) {
        UnityAds.load(adUnitId_IncentiveAdvertising, new MyUnityAdsLoadListener(adUnitId_IncentiveAdvertising));
    }

    public void toBanner(View view) {
        startActivity(new Intent(this, ShowBannersActivity.class));
    }

    class MyUnityAdsLoadListener implements IUnityAdsLoadListener {
        private String adUnitId;

        public MyUnityAdsLoadListener(String adUnitId) {
            this.adUnitId = adUnitId;
        }

        @Override
        public void onUnityAdsAdLoaded(String placementId) {
            UnityAds.show(MainActivity.this, adUnitId, new UnityAdsShowOptions(), showListener);
        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
            LogUtil.d("Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
        }
    }

    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {
            UnityAds.show(MainActivity.this, adUnitId, new UnityAdsShowOptions(), showListener);
        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
            LogUtil.d("Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
        }
    };

    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
            LogUtil.d("Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
        }

        @Override
        public void onUnityAdsShowStart(String placementId) {
            LogUtil.d("onUnityAdsShowStart: " + placementId);
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {
            LogUtil.d("onUnityAdsShowClick: " + placementId);
        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
            LogUtil.d("onUnityAdsShowComplete: " + placementId + ", 状态: " + state);
        }
    };

    public void DisplayInterstitialAd () {
        UnityAds.load(adUnitId, loadListener);
    }


}