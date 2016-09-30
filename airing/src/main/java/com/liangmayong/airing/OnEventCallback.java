package com.liangmayong.airing;

/**
 * Created by LiangMaYong on 2016/9/30.
 */
public abstract class OnEventCallback<T extends AiringEvent> implements OnAiringListener {

    public abstract void onEvent(T event);

    @Override
    public void onAiring(AiringContent content) {
        if (content.getEvent() != null) {
            try {
                T event = (T) content.getEvent();
                onEvent(event);
            } catch (Exception e) {
            }
        }
    }
}
