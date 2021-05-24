package com.my.xposedtargetdemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    //hook后替换原来的方法
    public void hook_and_replace(View view) {
        Util util = new Util();
        String result = util.replace_target_fun(MainActivity.this);
        show_board.setText(result);
    }

    //方法参数是自定义的类
    public void param_class(View view) {
        Util util = new Util();
        ParamClass paramClass = new ParamClass("Dio2", 19);
        String result = util.param_isClass(9, paramClass);
        show_board.setText(result);
    }

    //hook 抽象类方里的方法
    public void abstractClassFunc(View view) {
        ABClass abClass = new ABClass() {
            @Override
            public void say(String sentence) {
                super.say(sentence);
                show_board.setText(name);
            }
        };
        abClass.say("  MainActivity");
    }

    //hook 抽象类方里的方法   2
    public void abstractClassFunc2(View view) {
        new ABClass() {
            @Override
            public void say2(String sentence) {
                super.say2(sentence);
                show_board.setText(name);
            }
        }.say2("  MainActivity 222");
    }

    //hook 抽象类方里的方法   3
    public void abstractClassFunc3(View view) {
        //直接hook抽像类的 方法，所有继承抽象类后的实体类 调用抽像类的方法后 都会被hook住。(其实这一步有些多余,这种hook方法跟第一种一样)
        IMClass imClass = new IMClass();
        imClass.say("+abstractClassFunc3");
        String name = imClass.name;
        show_board.setText(name);
    }

    //静态内部类
    public void static_inner_class_func(View view) {
        StaticInnerClass.Inner innner = new StaticInnerClass.Inner();
        String result = innner.func1("p_MainActivity");
        show_board.setText(result);
    }

    //内部类
    public void inner_class_func(View view) {
        OuterClass outerClass = new OuterClass();
        OuterClass.InternalClass internalClass = outerClass.new InternalClass();
        String result = internalClass.sayit2("origin value");
        show_board.setText(result);
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
