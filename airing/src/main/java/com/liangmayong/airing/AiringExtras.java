package com.liangmayong.airing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AiringExtras
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringExtras {

    private AiringExtras() {
    }

    // extras map
    private static Map<String, List<Object>> extrasMap = new HashMap<String, List<Object>>();

    /**
     * put
     *
     * @param name   name
     * @param object object
     */
    public synchronized static void put(String name, Object object) {
        if (object != null) {
            if (extrasMap.containsKey(name)) {
                extrasMap.get(name).add(object);
            } else {
                List<Object> list = new ArrayList<Object>();
                list.add(object);
                extrasMap.put(name, list);
            }
        }
    }

    /**
     * get
     *
     * @param name name
     * @return object
     */
    public synchronized static Object get(String name) {
        if (extrasMap.containsKey(name)) {
            List<Object> list = extrasMap.get(name);
            if (!list.isEmpty()) {
                Object object = list.get(0);
                list.remove(0);
                return object;
            }
        }
        return null;
    }
}
