package com.breakpad.javacrash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static final String FILE_NAME_SUFFIX = ".log";
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static Context mContext;
    private static CrashHandler crashHandler;
    private CrashHandler() {
    }
    public static CrashHandler getCrashHander(){
        if (crashHandler == null){
            synchronized (CrashHandler.class){
                if (crashHandler == null){
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    public void init(@NonNull Context context) {
        //默认为：RuntimeInit#KillApplicationHandler
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 当程序中有未被捕获的异常，系统将会调用这个方法
     *
     * @param t 出现未捕获异常的线程
     * @param e 得到异常信息
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            //自行处理：保存本地
            File file = dealException(t, e);
            //上传服务器
            //......
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            //交给系统默认程序处理
            if (mDefaultCrashHandler != null) {
                mDefaultCrashHandler.uncaughtException(t, e);
            }
        }
    }

    /**
     * 导出异常信息到SD卡
     *
     * @param e
     */
    private File dealException(Thread t, Throwable e) throws Exception{
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        File f = new File(mContext.getExternalCacheDir().getAbsoluteFile(), "crash_info");
        if (!f.exists()) {
            f.mkdirs();
        }
        File crashFile = new File(f, time + FILE_NAME_SUFFIX);
        //往文件中写入数据
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(crashFile)));
        pw.println(time);
        pw.println("Thread: " + t.getName());
        pw.println(getPhoneInfo());
        e.printStackTrace(pw); //写入crash堆栈
        pw.close();
        return crashFile;
    }


    private String getPhoneInfo() throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        StringBuilder sb = new StringBuilder();
        //App版本
        sb.append("App Version: ");
        sb.append(pi.versionName);
        sb.append("_");
        sb.append(pi.versionCode + "\n");

        //Android版本号sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT + "\n");

        //手机制造商sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER + "\n");

        //手机型号sb.append("Model: ");
        sb.append(Build.MODEL + "\n");
        //CPU架构
        sb.append("CPU: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
        } else {
            sb.append(Build.CPU_ABI);
        }
        return sb.toString();
    }

}
