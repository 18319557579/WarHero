package com.chestnut.warhero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseCrashlytics.getInstance().setUserId("daisy" + System.currentTimeMillis());

        FirebaseCrashlytics.getInstance().log("daisy_warhero");

        ((Button) findViewById(R.id.btn_crash)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
                crashlytics.setCustomKey("my_string_key", "foo" /* string value */);
                crashlytics.setCustomKey("my_bool_key", true /* boolean value */);
                crashlytics.setCustomKey("my_double_key", 1.0 /* double value */);
                crashlytics.setCustomKey("my_float_key", 1.0f /* float value */);
                crashlytics.setCustomKey("my_int_key", 1 /* int value */);

                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });


        ((Button) findViewById(R.id.btn_not)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    throw new NullPointerException();
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    Log.d("Daisy", "不严重：" + e);
                }
            }
        });

        ((Button) findViewById(R.id.btn_enable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
            }
        });

        ((Button) findViewById(R.id.btn_anr)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true) {
                    Log.d("Daisy", "我就不停的输出");
                }
            }
        });
    }
}