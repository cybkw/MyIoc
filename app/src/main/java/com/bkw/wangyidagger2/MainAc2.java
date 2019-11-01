package com.bkw.wangyidagger2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bkw.wangyidagger2.bean.DaggerStudentComponent;
import com.bkw.wangyidagger2.bean.Student;

import javax.inject.Inject;

public class MainAc2 extends AppCompatActivity {

    @Inject
    Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerStudentComponent.create().injectActivity(this);

        //得到Student的实例
        Log.e("TAG", "" + student.hashCode() + ",name=" + student.getName());
    }
}
