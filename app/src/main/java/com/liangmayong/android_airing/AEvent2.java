package com.liangmayong.android_airing;

import java.io.Serializable;

/**
 * Created by LiangMaYong on 2016/9/30.
 */
public class AEvent2 implements Serializable {

    private String name = "";

    public AEvent2() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AEvent2{" +
                "name='" + name + '\'' +
                '}';
    }
}
