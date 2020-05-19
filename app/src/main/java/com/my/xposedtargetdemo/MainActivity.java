package com.my.xposedtargetdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = (TextView) findViewById(R.id.show_board_id);
    }

    //util 里的 普通方法
    public void ordinaryFunc(View view) {
        Util util = new Util();
        String result = util.ordinaryFunc("Dio", "wonam", 20);
        show_board.setText(result);
    }

    //hook 匿名内部类方法
    public void internalClassFunc(View view) {
        ABClass abClass = new ABClass() {
            @Override
            public void say(String sentence) {
                super.say(sentence);
                show_board.setText(name);
            }
        };
        abClass.say("  MainActivity");
    }

    //hook 匿名内部类方法
    public void internalClassFunc2(View view) {
        new ABClass() {
            @Override
            public void say2(String sentence) {
                super.say2(sentence);
                show_board.setText(name);
            }
        }.say2("  MainActivity 222");
    }

    //判断权限
    private boolean checkPermission(Context context, String permName, String pkgName) {
        PackageManager pm = context.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permName, pkgName)) {
            System.out.println(pkgName + "has permission : " + permName);
            return true;
        } else {
            System.out.println(pkgName + "not has permission : " + permName);
            String[] permissions = new String[]{permName};
            ActivityCompat.requestPermissions(this, permissions, 100);
            return false;
        }
    }

    //拦截系统方法    篡改IMEI设备号
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void os_imei_function(View view) {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (checkPermission(this, Manifest.permission.READ_PHONE_STATE, "com.my.xposedtargetdemo")) {
//            String imei = tm.getImei(); // 取出 IMEI(设备id)
            String imei = tm.getDeviceId(); // 取出 IMEI 需要 api26以上
            String imsi = tm.getSubscriberId(); //IMSI 与你的手机卡是绑定关系 用于区别移动用户的有效信息 IMSI是用户的标识
            String result = "imei:" + imei + " imsi:" + imsi;
            show_board.setText(result);
        }
    }

    // wifi 拦截IP地址
    public void wifi_ip_function(View view) {
        String ipAddress = NetWorkUtils.getLocalIpAddress(this);
        show_board.setText("ip : " + ipAddress);
    }

    //GPRS 拦截IP地址
    public void GPRS_ip_function(View view) {
        String ipAddress = NetWorkUtils.getLocalIpAddress();
        show_board.setText("ip : " + ipAddress);
    }

    //hook ui控件 并更改显示
    public void ui_function(View view) {
        show_board.setText("UI 原始显示值");
    }

    //Student 里的 构造方法
    public void construction_func(View view) {
        Student stu = new Student("Leo", "man", 18, 58.5, false);
        show_board.setText(stu.toString());
    }

    //util 里的静态方法
    public void staic_func(View view) {
        String result = Util.myInfo("Lily", 98);
        show_board.setText(result);
    }

}
