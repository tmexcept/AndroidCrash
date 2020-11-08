package com.breakpad.jnibug;

public class CrashTest {
    static {
        System.loadLibrary("bugly-lib");
    }
    public native String testNativeCrash();
}
