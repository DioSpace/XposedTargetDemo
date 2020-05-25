package com.my.xposedtargetdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

public class DeviceUtil {

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

    /*
     * 获取IMEI
     * */
    @SuppressLint("MissingPermission")
    public String getIMEI(Context context) {
        if (!checkReadPhoneStatePermission(context)) {
            Log.w(TAG, "获取唯一设备号-getIMEI: 无权限");
            return "";
        }
        String imei1 = "";
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != mTelephonyMgr) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei1 = mTelephonyMgr.getImei(0);
                Log.i(TAG, "Android版本大于o-26-优化后的获取---imei-1:" + imei1);
            } else {
                try {
                    imei1 = getDoubleImei(mTelephonyMgr, "getDeviceIdGemini", 0);
                } catch (Exception e) {
                    try {
                        imei1 = getDoubleImei(mTelephonyMgr, "getDeviceId", 0);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Log.e(TAG, "get device id fail: " + e.toString());
                }
            }
        }

        Log.i(TAG, "优化后的获取---imei1：" + imei1);
        return imei1;
    }

    @SuppressLint("MissingPermission")
    public String getIMEI2(Context context) {
        if (!checkReadPhoneStatePermission(context)) {
            Log.w(TAG, "获取唯一设备号-getIMEI2: 无权限");
            return "";
        }
        String imei2 = "";
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != mTelephonyMgr) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei2 = mTelephonyMgr.getImei(1);
                mTelephonyMgr.getMeid();
                Log.i(TAG, "Android版本大于o-26-优化后的获取---imei-2:" + imei2);
            } else {
                try {
                    imei2 = getDoubleImei(mTelephonyMgr, "getDeviceIdGemini", 1);
                } catch (Exception e) {
                    try {
                        imei2 = getDoubleImei(mTelephonyMgr, "getDeviceId", 1);
                    } catch (Exception ex) {
                        Log.e(TAG, "get device id fail: " + e.toString());
                    }
                }
            }
        }

        Log.i(TAG, "优化后的获取--- imei2:" + imei2);
        return imei2;
    }

    /**
     * 获取双卡手机的imei
     */
    private String getDoubleImei(TelephonyManager telephony, String predictedMethodName, int slotID) throws Exception {
        String inumeric = null;

        Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
        Class<?>[] parameter = new Class[1];
        parameter[0] = int.class;
        Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
        Object[] obParameter = new Object[1];
        obParameter[0] = slotID;
        Object ob_phone = getSimID.invoke(telephony, obParameter);
        if (ob_phone != null) {
            inumeric = ob_phone.toString();
        }
        return inumeric;
    }
}
