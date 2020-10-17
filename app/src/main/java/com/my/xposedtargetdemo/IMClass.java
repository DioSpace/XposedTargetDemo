package com.my.xposedtargetdemo;

public class IMClass extends ABClass {
    @Override
    public void say(String sentence) {
        super.say(sentence);
        name += "+IMClass";
    }
}
