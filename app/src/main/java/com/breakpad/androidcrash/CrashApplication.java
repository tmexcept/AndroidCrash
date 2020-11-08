package com.breakpad.androidcrash;

import android.app.Application;

import com.breakpad.crash.BreakpadCrash;
import com.breakpad.javacrash.CrashHandler;

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getCrashHander().init(this);
        BreakpadCrash.init(this);
    }
}
