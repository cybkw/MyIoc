package com.bkw.study1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.bkw.annotation.BindView;
import com.bkw.annotation.ContentView;
import com.bkw.annotation.InjectTool;
import com.bkw.annotation.OnClick;
import com.bkw.annotation.OnClickCommon;

/**
 * 布局文件注入
 *
 * @author bkw
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.button)
    Button button;

    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.button3)
    Button button3;

    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectTool.inject(this);

        button.setText("控件注入成功");
    }

    @OnClick({R.id.button, R.id.button2})
    public void show() {
        Log.d("TAG", "事件注入成功");
    }

    @OnClickCommon(R.id.button3)
    public void show2() {
        Log.d("TAG", "兼容 点击事件");
    }

    @OnClickCommon(R.id.button4)
    public void show3() {
        Log.d("TAG", "兼容 长按事件");
    }
}
