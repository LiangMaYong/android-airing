package com.liangmayong.android_airing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liangmayong.airing.Airing;
import com.liangmayong.airing.AiringContent;
import com.liangmayong.airing.OnAiringEvent;
import com.liangmayong.airing.OnAiringListener;

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
        }).register("aevent", new OnAiringEvent<AEvent>() {
            @Override
            public void onEvent(AEvent event, String action) {
                Toast.makeText(getApplicationContext(), action + "\n" + event.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AEvent aEvent = new AEvent();
        aEvent.setName("lmy");
        AEvent2 aEvent2 = new AEvent2();
        aEvent2.setName("IDJALKJDOKL");
        aEvent.setObject(aEvent2);
        aEvent.setPass("202020");
        Airing.getDefault().sender("aevent").postToTarget(aEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Airing.getDefault().observer(this).unregister();
    }
}
