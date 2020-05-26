package com.my.xposedtargetdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;

public class DeviceUtil {

    private static final String TAG = "DeviceUtil";

    private boolean checkReadPhoneStatePermission(Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                        10);
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public String getIMEI(Context context) {
        if (!checkReadPhoneStatePermission(context)) {
            Log.w(TAG, "获取唯一设备号 IMEI : 无权限");
            return "";
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei1 = (String) method.invoke(manager, 0);
            String imei2 = (String) method.invoke(manager, 1);
            if (TextUtils.isEmpty(imei1)) {
                return imei2;
            }
            if (TextUtils.isEmpty(imei2)) {
                return imei1;
            }
            //因为手机卡插在不同位置，获取到的imei1和imei2值有可能会交换，所以取它们的最小值(最大值也行),保证拿到的imei都是同一个
            String imei = "";
            if (imei1.compareTo(imei2) <= 0) {
                imei = imei1;
            } else {
                imei = imei2;
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return manager.getDeviceId();
        }
    }
}
