package com.my.xposedtargetdemo;

//静态内部类
public class StaticInnerClass {
    int num = 88;

    static class Inner {
        int num = 99;

        public Inner() {
            System.out.println("=== public Inner() ===");
        }

        public String func1(String param) {
            int num1 = new StaticInnerClass().num;
            return "StaticInnerClass_func1-> num = " + (num + num1) + "parma:" + param;
        }
    }

}
