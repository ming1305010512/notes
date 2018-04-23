package com.lm.mybatis.Pinciple.JdkDynamicAgency;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 龙鸣 on 2018/3/13.
 */
public class HelloServiceProxy implements InvocationHandler {

    //真实的服务对象
    private Object target;

    /**
     * 绑定委托对象，并返回一个代理
     * @param target
     * @return
     */
    public Object bind(Object target){
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    /**
     * 通过代理对象调用方法首先进入这个方法
     * @param proxy--代理对象
     * @param method--被调用的方法
     * @param args--方法的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("---------------我是JDK动态代理----------------");
        Object result = null;
        //反射方法前调用
        System.err.println("我准备说Hello");
        //执行方法，相当于调用HelloServiceImpl类的sayHello方法
        result = method.invoke(target,args);
        //反射方法后调用
        System.err.println("我已经说了Hello");
        return result;
    }

    /**
     * 当我使用代理对象来调用该方法是，会自动进入到HelloServiceProxy的invoke方法，
     * 例如，proxy.sayHello("张三")，那么invoke方法的三个参数分别为，proxy,sayHello,张三
     * 代理对象也是会实现HelloSercice接口的
     */
}
