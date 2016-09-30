package com.liangmayong.android_airing;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liangmayong.airing.Airing;
import com.liangmayong.airing.AiringContent;
import com.liangmayong.airing.OnAiringListener;
import com.liangmayong.airing.OnEventCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Airing.getDefault().observer(this).register("main", new OnAiringListener() {
            @Override
            public void onAiring(AiringContent content) {
                Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
            }
        }).register("action", new OnAiringListener() {
            @Override
            public void onAiring(AiringContent content) {
                Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
            }
        }).register("aevent", new OnEventCallback<AEvent>() {
            @Override
            public void onEvent(AEvent event) {
                Toast.makeText(getApplicationContext(), event.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Airing.getDefault().sender("main").send(0).sendEmpty();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Airing.getDefault().sender("main").send(0);
            }
        }, 2000);
        AEvent aEvent = new AEvent();
        aEvent.setName("lmy");
        aEvent.setPass("202020");
        Airing.getDefault().sender("aevent").sendEvent(aEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Airing.getDefault().observer(this).unregister();
    }
}
