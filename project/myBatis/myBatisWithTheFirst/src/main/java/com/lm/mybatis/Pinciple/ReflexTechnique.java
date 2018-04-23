package com.lm.mybatis.Pinciple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 龙鸣 on 2018/3/13.
 */
public class ReflexTechnique {
    public void sayHello(String name){
        System.err.println("hello:"+name);
    }

    //测试入口
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //通过反射创建ReflexTechnique对象
        Object service = Class.forName(ReflexTechnique.class.getName()).newInstance();
        //获取服务方法
        Method method = service.getClass().getMethod("sayHello",String.class);
        //反射调用方法
        method.invoke(service,"龙鸣");
    }
}
