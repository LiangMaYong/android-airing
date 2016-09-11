package com.liangmayong.android_airing;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liangmayong.airing.Airing;
import com.liangmayong.airing.AiringContent;
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
        }).register("action", new OnAiringListener() {
            @Override
            public void onAiring(AiringContent content) {
                Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Airing.getDefault().sender("main").sendObject(this).sendWhat(1).sendEmpty();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Airing.getDefault().sender("main").sendWhat(1);
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Airing.getDefault().observer(this).unregister();
    }
}
