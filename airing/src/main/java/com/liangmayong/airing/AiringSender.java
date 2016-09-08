package com.liangmayong.airing;

import android.os.Bundle;

import java.lang.reflect.Method;

/**
 * AiringSender
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringSender {

    // methods
    private static Method sendAction;
    // action
    private String action = "";
    // airing
    private Airing airing;

    public AiringSender(Airing airing, String action) {
        this.action = action;
        this.airing = airing;
    }

    /**
     * sendEmpty
     *
     * @return sender
     */
    public AiringSender sendEmpty() {
        _send(null, null);
        return this;
    }

    /**
     * sendExtras
     *
     * @param extras extras
     * @return sender
     */
    public AiringSender sendExtras(Bundle extras) {
        _send(extras, null);
        return this;
    }

    /**
     * sendWhat
     *
     * @param what what
     * @return sender
     */
    public AiringSender sendWhat(int what) {
        Bundle extras = new Bundle();
        extras.putInt(AiringContent.AIRING_WHAT_EXTRA, what);
        _send(extras, null);
        return this;
    }

    /**
     * sendObject
     *
     * @param object object
     * @return sender
     */
    public AiringSender sendObject(Object object) {
        _send(null, object);
        return this;
    }

    /**
     * send
     *
     * @param bundle bundle
     * @return sender
     */
    private AiringSender _send(Bundle bundle, Object object) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (sendAction == null) {
            try {
                sendAction = Airing.class.getDeclaredMethod("sendAction", String.class, Bundle.class, Object.class);
                sendAction.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (sendAction != null) {
            try {
                sendAction.invoke(airing, action, bundle, object);
            } catch (Exception e) {
            }
        }
        return this;
    }
}
