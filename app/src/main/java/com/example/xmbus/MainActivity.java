package com.example.xmbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.xmbus.bus.Subscribe;
import com.example.xmbus.bus.ThreadMode;
import com.example.xmbus.bus.XmBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        XmBus.getInstance().regist(this);
    }


    @Subscribe(ThreadMode.BACKGROUND)
    public void receive(String msg)
    {
        System.out.println("receive "+Thread.currentThread().getName());
        System.out.println(msg);
    }

    @Subscribe(ThreadMode.MAIN)
    public void receive2(String msg)
    {
        System.out.println("receive2 "+Thread.currentThread().getName());
        System.out.println(msg);
    }

    public void goClick(View view) {
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
}