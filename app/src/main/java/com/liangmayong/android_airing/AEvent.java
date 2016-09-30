package com.liangmayong.android_airing;

import com.liangmayong.airing.AiringEvent;

/**
 * Created by LiangMaYong on 2016/9/30.
 */
public class AEvent implements AiringEvent {

    private String name = "";
    private String pass = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "AEvent{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
