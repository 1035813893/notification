package com.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * wujian
 */
public class MainActivity extends AppCompatActivity implements NotificationListener, View.OnClickListener {

    /**
     * 线是内容
     */
    private TextView mTxtContent;
    /**
     * 第二个界面
     */
    private Button mBtSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //添加监听
        NotificationCenter.defaultCenter.addListener("content", this);
    }

    private void initView() {
        mTxtContent = (TextView) findViewById(R.id.txt_content);
        mBtSecond = (Button) findViewById(R.id.bt_second);
        mBtSecond.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除监听
        NotificationCenter.defaultCenter.removeListener(this);
    }

    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals("content")) {
            String content = (String) notification.object;
            mTxtContent.setText(content);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_second:
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }
}
