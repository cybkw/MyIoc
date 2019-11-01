package com.bkw.annotation;

import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectTool {
    private static final String TAG = "InjectTool.class";

    /**
     * 处理注解注入
     *
     * @param object
     */
    public static void inject(Object object) {

        injectSetContentView(object);

        injectBindView(object);

        injectOnClick(object);

        injectEvent(object);
    }

    /**
     * 兼容Android 点击，长按事件
     *
     * @param object
     */
    private static void injectEvent(final Object object) {
        Class<?> activityClass = object.getClass();

        Method[] declaredMethods = activityClass.getDeclaredMethods();
        for (final Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);

            //这种方式是不可以的，因为是动态变化的
//            OnClickCommon annotation = declaredMethod.getAnnotation(OnClickCommon.class);

            //这种情况也不可行
//            OnBaseCommon baseCommon = declaredMethod.getAnnotation(OnBaseCommon.class);

            //取得方法之上的所有注解
            Annotation[] annotations = declaredMethod.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //注解中必须包含OnBaseCommon
                OnBaseCommon onBaseCommon = annotationType.getAnnotation(OnBaseCommon.class);
                if (null == onBaseCommon) {
                    //跳过本次循环，进行下一次循环
                    continue;
                }

                //获取事件三要素
                String setCommonListener = onBaseCommon.setCommonListener();
                //View.OnClickListener
                Class setCommonObjectListener = onBaseCommon.setCommonObjectListener();
                //onClick
                String callbackMethod = onBaseCommon.callbackMethod();


                try {
                    //获取控件id
                    Method method = annotationType.getDeclaredMethod("value");
                    method.setAccessible(true);
                    int viewID = (int) method.invoke(annotation);

                    //初始化控件，得到View
                    Method findViewById = activityClass.getMethod("findViewById", int.class);
                    Object viewObject = findViewById.invoke(object, viewID);

                    //设置事件
                    //view.getClass().getMethod("setOnClickListener",View.OnClickListener.class);
                    Method viewMethod = viewObject.getClass().getMethod(setCommonListener, setCommonObjectListener);

                    //执行 view.setOnclickListener
                    Object proxy = Proxy.newProxyInstance(setCommonObjectListener.getClassLoader(),
                            new Class[]{setCommonObjectListener},
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                    return declaredMethod.invoke(object, null);
                                }
                            });

                    //使用动态代理替换成开发者自己的方法
                    viewMethod.invoke(viewObject, proxy);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    /**
     * 控件点击事件
     * 处理事件注入
     */
    private static void injectOnClick(final Object object) {
        Class<?> activity = object.getClass();

        //获取activity中所有的方法
        Method[] methods = activity.getDeclaredMethods();
        for (final Method method : methods) {
            method.setAccessible(true);
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick == null) {
                continue;
            }

            int[] viewIds = onClick.value();
            if (viewIds.length <= 0) {
                break;
            }
            try {
                Method findViewById = activity.getMethod("findViewById", int.class);
                //设置点击监听事件
                for (int viewId : viewIds) {
                    final Object resultView = findViewById.invoke(object, viewId);
                    View view = (View) resultView;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //执行方法
                            try {
                                method.invoke(object);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 控件注入
     */
    private static void injectBindView(Object object) {
        Log.d(TAG, ">>>>>>>>injectBindView");
        Class<?> activity = object.getClass();

        //取得activity中所有public修饰的属性成员
        Field[] fields = activity.getDeclaredFields();

        //取得所有被@BindView修饰的属性
        for (Field field : fields) {
            field.setAccessible(true);
            BindView bindView = field.getAnnotation(BindView.class);
            if (null == bindView) {
                continue; //跳过且继续
            }

            int viewId = bindView.value();

            try {
                //取得findViewById函数
                Method findViewById = activity.getMethod("findViewById", int.class);
                //执行findViewById函数
                Object resultView = findViewById.invoke(object, viewId);
                //button=findViewById(R.id.button) 控件名=布局控件id
                field.set(object, resultView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 布局注入
     */
    private static void injectSetContentView(Object object) {
        Log.d(TAG, ">>>>>>>>injectSetContentView");
        Class<?> activity = object.getClass();
        ContentView annotation = activity.getAnnotation(ContentView.class);
        if (null == annotation) {
            Log.e("InjectTool", "@ContentView 注解为空");
            return;
        }

        //取得用户设置的布局Id
        int layoutId = annotation.value();

        try {
            //需要执行setContentView函数,所以要反射取得该方法
            Method setContentView = activity.getMethod("setContentView", int.class);
            //执行setContentView方法
            setContentView.invoke(object, layoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * */
}
