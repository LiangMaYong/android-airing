package com.liangmayong.android_airing;

import android.os.Bundle;
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
        Airing.get("Air").sender("main").sendObject(this).sendWhat(1).sendEmpty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Airing.unregister(this);
    }
}
