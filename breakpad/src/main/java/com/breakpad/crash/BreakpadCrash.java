package com.breakpad.crash;

import android.content.Context;

import java.io.File;

public class BreakpadCrash {
    static {
        System.loadLibrary("breakpadcrash-lib");
    }

    public static void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        File file = new File(applicationContext.getExternalCacheDir(), "native_crash");
        if (!file.exists()) {
            file.mkdirs();
        }
        initNativeCrash(file.getAbsolutePath());
    }

    public static native void initNativeCrash(String file);
}
