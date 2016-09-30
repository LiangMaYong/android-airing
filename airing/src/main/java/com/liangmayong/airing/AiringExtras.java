package com.liangmayong.airing;

import java.util.HashMap;
import java.util.LinkedList;
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
    private static Map<String, LinkedList<AiringEvent>> extrasMap = new HashMap<String, LinkedList<AiringEvent>>();

    /**
     * put
     *
     * @param name  name
     * @param event event
     */
    public synchronized static void put(String name, AiringEvent event) {
        if (event == null) {
            event = new NullOBJ();
        }
        if (event != null) {
            if (extrasMap.containsKey(name)) {
                extrasMap.get(name).add(event);
            } else {
                LinkedList<AiringEvent> list = new LinkedList<AiringEvent>();
                list.add(event);
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
    public synchronized static AiringEvent get(String name) {
        if (extrasMap.containsKey(name)) {
            LinkedList<AiringEvent> list = extrasMap.get(name);
            if (!list.isEmpty()) {
                AiringEvent event = list.getFirst();
                list.removeFirst();
                if (event instanceof NullOBJ) {
                    return null;
                }
                return event;
            }
        }
        return null;
    }

    private static class NullOBJ implements AiringEvent {
    }
}
