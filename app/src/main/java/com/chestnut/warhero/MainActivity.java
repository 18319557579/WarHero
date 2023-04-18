package com.chestnut.warhero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Button crashButton = new Button(this);
        crashButton.setText("Test Crash");
        crashButton.setOnClickListener(new View.OnClickListener() {
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

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}