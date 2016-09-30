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
        return send(-1);
    }

    /**
     * send
     *
     * @param what what
     * @return sender
     */
    public AiringSender send(int what) {
        Bundle extras = new Bundle();
        extras.putInt(AiringContent.AIRING_WHAT_EXTRA, what);
        _send(extras, null);
        return this;
    }

    /**
     * send
     *
     * @param what   what
     * @param extras extras
     * @return sender
     */
    public AiringSender send(int what, Bundle extras) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putInt(AiringContent.AIRING_WHAT_EXTRA, what);
        _send(extras, null);
        return this;
    }

    /**
     * sendEvent
     *
     * @param event event
     * @return sender
     */
    public AiringSender sendEvent(AiringEvent event) {
        _send(null, event);
        return this;
    }

    /**
     * send
     *
     * @param bundle bundle
     * @return sender
     */
    private AiringSender _send(Bundle bundle, AiringEvent event) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (sendAction == null) {
            try {
                sendAction = Airing.class.getDeclaredMethod("sendAction", String.class, Bundle.class, AiringEvent.class);
                sendAction.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (sendAction != null) {
            try {
                sendAction.invoke(airing, action, bundle, event);
            } catch (Exception e) {
            }
        }
        return this;
    }
}
