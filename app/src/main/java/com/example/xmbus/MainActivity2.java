package com.example.xmbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.xmbus.bus.Subscribe;
import com.example.xmbus.bus.XmBus;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void butClick(View view) {
        XmBus.getInstance().post("我来啦");
    }
}