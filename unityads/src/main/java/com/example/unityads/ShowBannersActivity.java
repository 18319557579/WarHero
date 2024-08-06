package com.example.unityads;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class ShowBannersActivity extends AppCompatActivity {
    String topAdUnitId = "Banner_Android";
    String bottomAdUnitId = "bottom_banner";

    // This banner view object will be placed at the top of the screen:
    BannerView topBanner;
    // This banner view object will be placed at the bottom of the screen:
    BannerView bottomBanner;
    // View objects to display banners:
    RelativeLayout topBannerView;
    RelativeLayout bottomBannerView;
    // Buttons to show the banners:
    Button showTopBannerButton;
    Button showBottomBannerButton;
    // Buttons to destroy the banners:
    Button hideTopBannerButton;
    Button hideBottomBannerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_banners);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        showTopBannerButton = findViewById(R.id.btn_show_top);
        hideTopBannerButton = findViewById(R.id.btn_hide_top);
        showBottomBannerButton = findViewById(R.id.btn_show_bottom);
        hideBottomBannerButton = findViewById(R.id.btn_hide_bottom);

        showTopBannerButton.setEnabled(true);
        showBottomBannerButton.setEnabled(true);
        hideTopBannerButton.setEnabled(false);
        hideBottomBannerButton.setEnabled(false);

        showTopBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopBannerButton.setEnabled(false);
                topBanner = new BannerView(ShowBannersActivity.this, topAdUnitId, new UnityBannerSize(320, 50));
                topBanner.setListener(bannerListener);
                topBannerView = findViewById(R.id.rl_top_banner);
                LoadBannerAd(topBanner, topBannerView);

                topBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.d("被点击了");
                    }
                });
            }
        });

        hideTopBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topBannerView.removeAllViews();
                topBannerView = null;
                topBanner = null;
                showTopBannerButton.setEnabled(true);
                hideTopBannerButton.setEnabled(false);
            }
        });

        showBottomBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomBannerButton.setEnabled(false);
                bottomBanner = new BannerView(ShowBannersActivity.this, bottomAdUnitId, new UnityBannerSize(320, 50));
                bottomBanner.setListener(bannerListener);
                bottomBannerView = findViewById(R.id.rl_bottom_banner);
                LoadBannerAd(bottomBanner, bottomBannerView);
            }
        });

        hideBottomBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBannerView.removeAllViews();
                bottomBannerView = null;
                bottomBanner = null;
                showBottomBannerButton.setEnabled(true);
                hideBottomBannerButton.setEnabled(false);
            }
        });


    }

    private BannerView.IListener bannerListener = new BannerView.IListener() {
        @Override
        public void onBannerLoaded(BannerView bannerAdView) {
            // Called when the banner is loaded.
            LogUtil.d("onBannerLoaded: " + bannerAdView.getPlacementId());
            // Enable the correct button to hide the ad
            (bannerAdView.getPlacementId().equals("Banner_Android") ? hideTopBannerButton : hideBottomBannerButton).setEnabled(true);
        }

        @Override
        public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
            LogUtil.d("Unity Ads failed to load banner for " + bannerAdView.getPlacementId() + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage);
            // Note that the BannerErrorInfo object can indicate a no fill (refer to the API documentation).
        }

        @Override
        public void onBannerClick(BannerView bannerAdView) {
            // Called when a banner is clicked.
            LogUtil.d("onBannerClick: " + bannerAdView.getPlacementId());
        }

        @Override
        public void onBannerLeftApplication(BannerView bannerAdView) {
            // Called when the banner links out of the application.
            LogUtil.d("onBannerLeftApplication: " + bannerAdView.getPlacementId());
        }
    };

    public void LoadBannerAd(BannerView bannerView, RelativeLayout bannerLayout) {
        // Request a banner ad:
        bannerView.load();
        // Associate the banner view object with the banner view:
        bannerLayout.addView(bannerView);
    }

    public void simulatedClickTop(View view) {
//        topBanner.performClick();  //只能用于点击事件的分发，但是并不是做到了模拟点击
        simulateClick(topBanner);
    }

    /**
     * 模拟点击
     * @param view 任意一个控件（View）
     */
    public void simulateClick(View view) {
        long downTime = SystemClock.uptimeMillis();
        int[] ints = new int[2];

        view.dispatchTouchEvent(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN,
                ints[0] + 5, ints[1] + 5, 0));
        view.dispatchTouchEvent(MotionEvent.obtain(downTime + 200, downTime + 200, MotionEvent.ACTION_UP,
                ints[0] + 5, ints[1] + 5, 0));
    }
}