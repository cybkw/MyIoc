package com.bkw.wangyidagger2.bean;

import dagger.Module;
import dagger.Provides;

/**
 * @author bkw
 * @Module 相当于对Student对象进行一层封装
 */
//TODO 耳机的包裹
@Module
public class StudentModule {

    /**
     * @Provides注解表示将该对象暴露给外部
     * @return
     */
    @Provides
    public Student getStudent() {
        return new Student();
    }
}
