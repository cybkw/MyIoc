package com.bkw.wangyidagger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bkw.wangyidagger2.bean.DaggerStudentComponent;
import com.bkw.wangyidagger2.bean.Student;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //普通写法
//        student = new Student();


        //把当前Activity注入给Dagger2
        DaggerStudentComponent.create().injectActivity(this);

        //得到Student的实例
        Log.e("TAG", "" + student.hashCode() + ",name=" + student.getName());
    }
}
