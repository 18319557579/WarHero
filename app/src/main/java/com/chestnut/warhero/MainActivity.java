package com.chestnut.warhero;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Daisy", "回调了onNewIntent");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("Daisy", "回调了onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Daisy", "回调了onStart");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Log.d("Daisy", "携带的数据：" + (bundle == null ? "空" : bundle.toString()));
        Log.d("Daisy", "字符串：" + intent.getStringExtra("name"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Daisy", "回调了onResume");
    }

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

        ((Button) findViewById(R.id.btn_token)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("Daisy", "Fetching FCM registration token failed", task.getException());
                                    return;
                                }

                                String token = task.getResult();

                                Log.d("Daisy", "打印Token：" + token);
                            }
                        });
            }
        });
    }
}