package com.my.xposedtargetdemo;

public class OuterClass {
    int waibu = 12;

    public void say2() {
        System.out.println("这是外部类当中的方法");
    }

    class InternalClass {
        int neibu = 13;

        public void sayit() {
            System.out.println("这是内部类里面的方法");
            System.out.println("使用内部类和外部类当中的数值进行想加的结果是" + (neibu + waibu));
            //之所以内部类可以使用外部类的属性是因为在创建对象的时候，已经给内部类的对象附加了一个外部类的对象，内部类的对象是建立在外部类对象的基础上的。
        }

        public String sayit2(String param) {
            param += " _ sayit2";
            return param;
        }
    }
}
