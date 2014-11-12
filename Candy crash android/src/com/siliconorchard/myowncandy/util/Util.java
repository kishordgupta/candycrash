package com.siliconorchard.myowncandy.util;
 
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
 
public class Util {
    public static int getRandomIndex(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
 
    public static void resizeDialog(View v, Activity mActivity) {
        int wW = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        int newW = (int) (v.getLayoutParams().width * (wW / 480f));
        int newH = newW * v.getLayoutParams().height
                / v.getLayoutParams().width;
        v.getLayoutParams().width = newW;
        v.getLayoutParams().height = newH;
    }
 
    public static boolean appInstalledOrNot(String uri, Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}