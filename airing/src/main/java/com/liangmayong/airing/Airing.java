package com.liangmayong.airing;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Airing
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class Airing {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////

    private static String TAG = "Airing";

    /**
     * isDebugable
     *
     * @return true or false
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    private static boolean isDebugable() {
        try {
            ApplicationInfo info = getApplication().getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
    }

    // application
    private static Application application;

    /**
     * getApplication
     *
     * @return application
     */
    private static Application getApplication() {
        if (application == null) {
            synchronized (Airing.class) {
                if (application == null) {
                    try {
                        Class<?> clazz = Class.forName("android.app.ActivityThread");
                        Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
                        if (currentActivityThread != null) {
                            Object object = currentActivityThread.invoke(null);
                            if (object != null) {
                                Method getApplication = object.getClass().getDeclaredMethod("getApplication");
                                if (getApplication != null) {
                                    application = (Application) getApplication.invoke(object);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return application;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////

    // airingMap
    private static Map<String, Airing> airingMap = new HashMap<String, Airing>();

    /**
     * getDefault
     *
     * @return airing
     */
    public static Airing getDefault() {
        return get(getApplication().getPackageName());
    }

    /**
     * get
     *
     * @param airingName airingName
     * @return airing
     */
    public static Airing get(String airingName) {
        if (airingMap.containsKey(airingName)) {
            return airingMap.get(airingName);
        } else {
            Airing eventSink = new Airing();
            eventSink.setName(airingName);
            airingMap.put(airingName, eventSink);
            return eventSink;
        }
    }

    /**
     * unregister
     *
     * @param object object
     */
    public static void unregister(Object object) {
        for (Map.Entry<String, Airing> entry : airingMap.entrySet()) {
            entry.getValue().unregisterObject(object);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////

    private String airingName = "";

    /**
     * cache event receiver
     */
    private Map<Object, Map<String, BroadcastReceiver>> receiverMap = new HashMap<Object, Map<String, BroadcastReceiver>>();

    /**
     * setName
     *
     * @param airingName airingName
     */
    private void setName(String airingName) {
        this.airingName = airingName;
    }

    /**
     * getName
     *
     * @return action prefix
     */
    private String getName() {
        return airingName + ".";
    }

    /**
     * sender
     *
     * @param action action
     * @return action
     */
    public AiringSender sender(String action) {
        return new AiringSender(this, action);
    }

    /**
     * observer
     *
     * @param object object
     * @return action
     */
    public AiringObserver observer(Object object) {
        return new AiringObserver(this, object);
    }

    /**
     * sendAction
     *
     * @param action action
     * @param bundle bundle
     */
    @SuppressWarnings("unused")
    private void sendAction(String action, Bundle bundle, Object object) {
        Intent intent = new Intent();
        String newAction = getName() + action;
        if (isDebugable()) {
            Log.d(TAG, "send Airing:" + newAction + " extras:" + bundle);
        }
        intent.setAction(newAction);
        if (bundle != null && !bundle.isEmpty()) {
            intent.putExtras(bundle);
        }
        AiringExtras.put(newAction, object);
        getApplication().sendBroadcast(intent);
    }

    /**
     * register
     *
     * @param object        object
     * @param actions       actions
     * @param eventListener eventListener
     * @return airing
     */
    @SuppressWarnings("unused")
    private Airing registerActions(Object object, final String[] actions, final OnAiringListener eventListener) {
        if (actions != null) {
            for (int i = 0; i < actions.length; i++) {
                registerAction(object, actions[i], eventListener);
            }
        }
        return this;
    }

    /**
     * register
     *
     * @param object        context
     * @param action        action
     * @param eventListener eventListener
     * @return airing
     */
    private Airing registerAction(Object object, final String action, final OnAiringListener eventListener) {
        Map<String, BroadcastReceiver> map = null;
        if (receiverMap.containsKey(object)) {
            map = receiverMap.get(object);
        } else {
            map = new HashMap<String, BroadcastReceiver>();
        }
        if (map.containsKey(action)) {
            unregisterAction(object, action);
        }
        BroadcastReceiver broadcastReceiver = new AiringReceiver(getName(), action, eventListener);
        IntentFilter filter = new IntentFilter();
        filter.addAction(getName() + action);
        getApplication().registerReceiver(broadcastReceiver, filter);
        map.put(action, broadcastReceiver);
        receiverMap.put(object, map);
        return this;
    }

    /**
     * unregisterAction
     *
     * @param object object
     * @param action action
     */
    private void unregisterAction(Object object, final String action) {
        if (receiverMap.containsKey(object)) {
            Map<String, BroadcastReceiver> map = receiverMap.get(object);
            if (map.containsKey(action)) {
                try {
                    BroadcastReceiver broadcastReceiver = map.get(action);
                    getApplication().unregisterReceiver(broadcastReceiver);
                } catch (Exception e) {
                }
                map.remove(action);
            }
            if (map.isEmpty()) {
                receiverMap.remove(object);
            } else {
                receiverMap.put(object, map);
            }
        }
    }

    /**
     * unregisterActions
     *
     * @param object  object
     * @param actions actions
     */
    @SuppressWarnings("unused")
    private void unregisterActions(Object object, final String[] actions) {
        if (actions != null) {
            for (int i = 0; i < actions.length; i++) {
                unregisterAction(object, actions[i]);
            }
        }
    }

    /**
     * unregisterObject
     *
     * @param object object
     */
    private void unregisterObject(Object object) {
        if (receiverMap.containsKey(object)) {
            Map<String, BroadcastReceiver> map = receiverMap.get(object);
            for (BroadcastReceiver receiver : map.values()) {
                try {
                    getApplication().unregisterReceiver(receiver);
                } catch (Exception e) {
                }
            }
            receiverMap.remove(object);
        }
    }

}
