package com.bkw.wangyidagger2.bean;

import com.bkw.wangyidagger2.MainAc2;
import com.bkw.wangyidagger2.MainActivity;

import dagger.Component;

/**
 * 编译该类会在apt目录下生成一个Java文件
 *
 * @author bkw
 */
//TODO 快递员-Component (modules=包裹)
@Component(modules = StudentModule.class)
public interface StudentComponent {

    //TODO 送到收货地址---注入到目标Activity
    void injectActivity(MainActivity mainActivity); //参数是具体的Activity对象

    void injectActivity(MainAc2 mainActivity); //参数是具体的Activity对象
}
