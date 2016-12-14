package com.bw.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button bt_send;
    private Button bt_send_nian;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EventBusSendActivity.class);
                startActivity(intent);
            }
        });
        bt_send_nian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2.发送粘性事件
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                Intent intent = new Intent(MainActivity.this,EventBusSendActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initData() {
        //1.注册广播
        EventBus.getDefault().register(MainActivity.this);
    }

    private void initView() {
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_send_nian = (Button) findViewById(R.id.bt_send_nian);
        tv = (TextView) findViewById(R.id.tv_result);

    }

    //5接受消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent event){
        //显示我们的消息
        tv.setText(event.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //2.解注册
        EventBus.getDefault().unregister(MainActivity.this);

    }
}
