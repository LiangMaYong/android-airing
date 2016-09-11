package com.liangmayong.airing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * AiringReceiver
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringReceiver extends BroadcastReceiver {

    // eventListener
    private OnAiringListener eventListener;
    // action
    private String action;
    // airingName
    private String airingName;
    // target
    private Object target;

    public AiringReceiver(Object target, String airingName, String action, OnAiringListener eventListener) {
        this.target = target;
        this.airingName = airingName;
        this.action = action;
        this.eventListener = eventListener;
    }

    /**
     * getAction
     *
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * getTarget
     *
     * @return target
     */
    public Object getTarget() {
        return target;
    }

    /**
     * getAiringName
     *
     * @return airingName
     */
    public String getAiringName() {
        return airingName;
    }

    /**
     * getEventListener
     *
     * @return eventListener
     */
    public OnAiringListener getAiringListener() {
        return eventListener;
    }

    @Override
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(getAiringName() + getAction())) {
            if (getAiringListener() != null) {
                Bundle bundle = intent.getExtras();
                Object object = AiringExtras.get(airingName + action);
                int what = -1;
                if (bundle != null) {
                    try {
                        what = bundle.getInt(AiringContent.AIRING_WHAT_EXTRA, -1);
                        bundle.remove(AiringContent.AIRING_WHAT_EXTRA);
                    } catch (Exception e) {
                    }
                }
                getAiringListener().onAiring(new AiringContent(getTarget(),
                        getAiringName().substring(0, getAiringName().length() - 1), getAction(), what, bundle, object));
            }
        }
    }
}
