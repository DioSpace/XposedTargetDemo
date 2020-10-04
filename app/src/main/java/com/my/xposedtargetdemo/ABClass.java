package com.my.xposedtargetdemo;

abstract class ABClass {

    String name = "ABClass  ";//抽象类的属性是不会被调用的，除了方法

    public void say(String sentence) {
        name += sentence;
    }

    public void say2(String sentence) {
        name += sentence;
    }
}
