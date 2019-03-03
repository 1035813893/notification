package com.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtContent;
    /**
     * 发消息
     */
    private Button mBtSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    private void initView() {
        mEdtContent = (EditText) findViewById(R.id.edt_content);
        mBtSend = (Button) findViewById(R.id.bt_send);
        mBtSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.edt_content:
                break;
            case R.id.bt_send:
                String content = mEdtContent.getText().toString().trim();
                //发送通知
                NotificationCenter.defaultCenter.postNotification("content", content);
                finish();
                break;
        }
    }
}
