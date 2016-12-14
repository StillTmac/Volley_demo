package com.bw.eventbus;

import android.app.usage.UsageEvents;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusSendActivity extends AppCompatActivity {

    private Button bt_send_shuju;
    private Button bt_jieshou_shuju;
    private TextView textView;
    private boolean isFirstFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        initView();
        initListener();
    }

    private void initListener() {
        //主线程发送数据按钮点击事件处理
        bt_send_shuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4发送消息
                EventBus.getDefault().post(new MessageEvent("主线程发送过来的数据"));
                finish();//发送完消息之后把这个页面销毁掉
            }
        });
        bt_jieshou_shuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstFlag){
                    isFirstFlag = false;
                    //4.注册
                    EventBus.getDefault().register(EventBusSendActivity.this);
                }
            }
        });
    }
    //3、接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void StickyEventBus(StickyEvent event){
        //显示接受的数据
        textView.setText(event.msg);
    }


    private void initView() {
        bt_send_shuju = (Button) findViewById(R.id.bt_send_shuju);
        bt_jieshou_shuju = (Button) findViewById(R.id.bt_jieshou_shuju);
        textView = (TextView) findViewById(R.id.bt_jieguo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //5.解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendActivity.this);
    }
}
