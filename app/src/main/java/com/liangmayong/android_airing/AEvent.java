package com.liangmayong.android_airing;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by LiangMaYong on 2016/9/30.
 */
public class AEvent implements Serializable {

    private String name = "";
    private String pass = "";
    private AEvent2 object;

    public AEvent() {
    }

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

    public Object getObject() {
        return object;
    }

    public void setObject(AEvent2 object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "AEvent{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", object=" + object +
                '}';
    }
}
