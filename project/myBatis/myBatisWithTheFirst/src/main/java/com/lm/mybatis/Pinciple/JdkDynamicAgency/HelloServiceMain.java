package com.lm.mybatis.Pinciple.JdkDynamicAgency;

/**
 * Created by 龙鸣 on 2018/3/13.
 */
public class HelloServiceMain {
    public static void main(String[] args) {
        HelloServiceProxy helloServiceProxy = new HelloServiceProxy();
        HelloService proxy = (HelloService) helloServiceProxy.bind(new HelloServiceImpl());
        proxy.sayHello("张三");
    }
}
