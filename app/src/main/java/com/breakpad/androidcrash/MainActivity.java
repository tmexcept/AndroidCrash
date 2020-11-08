package com.breakpad.androidcrash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.breakpad.jnibug.CrashTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_java).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = null;
                Log.e("Crash", "length:" + str.length());
            }
        });

        findViewById(R.id.btn_ndk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashTest crashTest = new CrashTest();
                crashTest.testNativeCrash();
            }
        });
    }
}
