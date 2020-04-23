package com.my.xposedtargetdemo;

public class Student {
    String name;
    String gender;
    int age;
    static double points;
    boolean isPassed;

    //构造方法
    public Student(String name, String gender, int age, double points, boolean isPassed) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.points = points;
        this.isPassed = isPassed;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", points=" + points +
                ", isPassed=" + isPassed +
                '}';
    }
}
