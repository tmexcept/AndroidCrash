//
// Created by Administrator on 2020/11/5.
//


#include <jni.h>
#include <android/log.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_breakpad_jnibug_CrashTest_testNativeCrash(JNIEnv *env, jclass clazz) {

    __android_log_print(ANDROID_LOG_INFO, "native", "xxxxxxxxxx");

    int *p = NULL;
    *p = 10;

}
