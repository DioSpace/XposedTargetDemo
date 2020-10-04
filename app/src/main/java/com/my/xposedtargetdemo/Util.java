package com.my.xposedtargetdemo;

public class Util {

    //普通方法
    public String ordinaryFunc(String name, String gender, int age) {
        String result = "姓名:" + name + "\t性别:" + gender + "年龄:" + age;
        return result;
    }

    //参数是自定义的类
    public String param_isClass(int index, ParamClass paramClass) {
        return index + " : " + paramClass.name + "\t\t" + paramClass.age;
    }

    //重载方法
    public String judgeByAge(int age) {
        String result = "";
        if (age < 18) {
            result = "未成年";
        } else if (age >= 18 && age < 60) {
            result = "中年人";
        } else {
            result = "老年人";
        }
        return result;
    }

    //重载的对照方法
    public String judgeByAge(int age, int grade) {
        return "对照方法";
    }


    //函数
    public int func1(int num) {
        return num + 10;
    }

    public int func2(int num) {
        return num * 10;
    }

    //静态方法
    static String myInfo(String name, int grade) {
        if (grade > 90) {
            return name + " ,你是一个优秀的学生";
        } else if (grade >= 60 && grade <= 90) {
            return name + "是一个中等生";
        } else {
            return name + "是一个差学生";
        }
    }
}
