package com.lm.mybatis.Pinciple.chlibDynamicAgency;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.security.auth.login.Configuration;
import java.lang.reflect.Method;

/**
 * Created by 龙鸣 on 2018/3/13.
 */
public class HelloServiceCglib implements MethodInterceptor {

    private Object target;

    /**
     * 创建代理对象
     * @param target
     * @return
     */
    public Object getInstance(Object target){
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        //回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }

    //回调方法
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.err.println("----------我是CGLIB动态代理------------");
        //反射方法前调用
        System.err.println("我准备说Hello");
        Object returnObj = methodProxy.invokeSuper(obj,args);
        //反射方法后调用
        System.err.println("我说过了Hello");

        return null;
    }
}
