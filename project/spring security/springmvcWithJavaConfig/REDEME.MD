使用注解的方式实现spingmvc
学习网站：https://segmentfault.com/a/1190000004343063?_ea=575820

###1、几个方法的作用
- 1、addResourceHandlers(ResourceHandlerRegistry registry)
```
//重写该方法获取静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指的是文件放置的目录，addResourceHandler指的是对外暴露的访问路径
        registry.addResourceHandler("/asserts/**").addResourceLocations("classpath:/asserts/");
    }
```
当你请求http://localhost:8083/asserts/1.png时，会把classpath下的/asserts/1.png返回。注意：这里的静态资源是放置在会把classpath下的。

- 2、configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
```
@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
```

此时会注册一个默认的Handler：DefaultServletHttpRequestHandler，这个Handler也是用来处理静态文件的，它会尝试映射/*。当DispatcherServelt
映射/时（/ 和/* 是有区别的），并且没有找到合适的Handler来处理请求时，就会交给DefaultServletHttpRequestHandler 来处理。注意：这里的静态资
源是放置在web根目录下，而非WEB-INF 下。 可能这里的描述有点不好懂（我自己也这么觉得），所以简单举个例子，例如：在webroot目录下有一个图片：1.png 
我们知道Servelt规范中web根目录（webroot）下的文件可以直接访问的，但是由于DispatcherServlet配置了映射路径是：/ ，它几乎把所有的请求都拦截了，从
而导致1.png 访问不到，这时注册一个DefaultServletHttpRequestHandler 就可以解决这个问题。其实可以理解为DispatcherServlet破坏了Servlet的一个特性
（根目录下的文件可以直接访问），DefaultServletHttpRequestHandler是帮助回归这个特性的。

**题外话：**

/和/*　有什么区别？
/会拦截除了jsp以外的所有url，/* 会拦截所有url，包括jsp。例如：在webroot下面有一个test.jsp,当DispatcherServlet 配置映射/ 时，浏览器输入：
http://localhost:8083/test.jsp 这个jsp是可以直接访问的，并且不经过DispatcherServlet ，而当DispatcherServlet 配置映射/* 时，这个请求就会被
DispatcherServlet 拦截。