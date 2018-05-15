**1、@ConfigurationProperties注解**

结合@EnableAutoConfiguration,会自动将属性配置文件转换成java bean,并会向spring中注入该bean,
并以prefix+类的名称作为bean的名称

**2、MvcUriComponentsBuilder类含义**

MvcUriComponentsBuilder类似于ServletUriComponentsBuilder，但是可以直接从控制器获取URI信息

存在一个controller
```
@Controller  
@RequestMapping("/user")  
public class UserController {  
  
    @RequestMapping("/{id}")  
    public String view(@PathVariable("id") Long id) {  
        return "view";  
    }  
  
    @RequestMapping("/{id}")  
    public A getUser(@PathVariable("id") Long id) {  
        return new A();  
    }  
  
}  
```
我们可以通过如下方式得到
```
//需要静态导入 import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;  
@Test  
public void test() {  
    MockHttpServletRequest req = new MockHttpServletRequest();  
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));  
  
    //MvcUriComponentsBuilder类似于ServletUriComponentsBuilder，但是直接从控制器获取  
    //类级别的  
    System.out.println(  
            fromController(UserController.class).build().toString()  
    );  
  
    //方法级别的  
    System.out.println(  
            fromMethodName(UserController.class, "view", 1L).build().toString()  
    );  
  
    //通过Mock方法调用得到  
    System.out.println(  
            fromMethodCall(on(UserController.class).getUser(2L)).build()  
    );  
}  
```

**3、RedirectAttributes类**

是Spring mvc 3.1版本之出来的一个功能，专门用于重定向之后还能带参数跳转的的工具类

有两种带参的方式：
（1）redirectAttributes.addAttributie("param",value);这种方法相当于在重定向链接地址追加传递的参数，如：
```
redirectAttributes.addAttributie("param1",value1);

redirectAttributes.addAttributie("param2",value2);

return:"redirect：/path/list" 
```
以上重定向的方法等同于 return:"redirect：/path/list？param1=value1&param2=value2 " ，注意这种方法直接将传递的参数暴露在链接地址上，非常的不安全，慎用。


（2）redirectAttributes.addFlashAttributie("param",value); 
这种方法是隐藏了参数，链接地址上不直接暴露，但是能且只能在重定向的 “页面” 获取param参数值。其原理就是放到session中，session在跳到页面后马上移除对象。
如果是重定向一个controller中是获取不到该param属性值的。 
除非在controller中用(@RequestPrama(value = "param")String param)注解，采用传参的方式。如：
````
redirectAttributes.addFlashAttributie("param1",value1);

redirectAttributes.addFlashAttributie("param2",value2);

return:"redirect：/path/list.jsp" 
````
在以上参数均可在list.jsp页面使用EL表达式获取到参数值${param}

controller获得redirectAttributes重定向的值例如：
````
redirectAttributes.addFlashAttributie("param1",value1);

redirectAttributes.addFlashAttributie("param2",value2);

return:"redirect：/path/list/"

@RequestMapping("list")
public List<Student> list(@RequestPrama(value = "param1")String  param1,
   @RequestPrama(value = "param2")String  param2,...
){
    //TODO
    //your code

}
````

**4、CommandLineRunner类含义**
如果在SpringBoot应用启动的时候需要执行特定的动作，可以利用CommandLineRunner。实现了CommandLineRunner接口的Component会在所有Spring Beans都初始化之后，SpringApplication.run()之前执行，
非常适合在应用程序启动之初进行一些数据初始化的工作。


**5、@EnableAutoConfiguration含义**

这个注释告诉SpringBoot“猜”你将如何想配置Spring,基于你已经添加jar依赖项。如果spring-boot-starter-web已经添加Tomcat和Spring MVC,这个注释自动将假设您正在开发一个web应用程序并添加相应的spring设置。


**6、@RunWith含义**
@RunWith就是一个运行器，@RunWith(JUnit4.class)就是指用JUnit4来运行
@RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境



**7、@AutoConfigureMockMvc**
Note the use of the @AutoConfigureMockMvc together with @SpringBootTest to inject a MockMvc instance.


**8、Files类(java.nio.file.Files)**
（1）createDirectory方法
```
public static Path createDirectory(Path dir,
                                   FileAttribute<?>... attrs)
                            throws IOException
```
创建一个目录,FileAttribute为文件属性
```
public interface FileAttribute<T> {
    /**
     * Returns the attribute name.
     *
     * @return The attribute name
     */
    String name();

    /**
     * Returns the attribute value.
     *
     * @return The attribute value
     */
    T value();
}
```
（2）copy方法
```
public static long copy(InputStream in, Path target, CopyOption... options)
        throws IOException
```
（3）walk方法
 local()产生本地文件构成的file数组，walk()产生指目录下由整个目录树 中所有文件构成的List<File>,
 ```
  public static Stream<Path> walk(Path start,
                                     int maxDepth,
                                     FileVisitOption... options)
         throws IOException
 ```


**9、Path类的resolve方法**

Path接口中resolve方法的作用相当于把当前路径当成父目录，而把参数中的路径当成子目录或是其中的文件，进行解析之后得到一个新路径；


**8和9、操作文件（很重要）**

看到了就进入链接查看具体内容
https://blog.csdn.net/ai_xao/article/details/78267774

**10、JAVA 8 Lambda表达式 Stream**
Lambda 表达式实质是一个可以实现某一接口的匿名内部类的中的方法 
Lambda表达式组成部分：
（1）括号包起来的参数 (若参数仅有一个 可省略括号)
（2）一个箭头 既 (-> 操作符)
（3）方法体，可以是单个语句，也可以是语句块 (若是单个语句 则可以直接写出结尾不需要; 结束，若为语句块则两边需加入花括号，每局结尾以; 结束)

**注意：** 参数可以写类型，也可以不写，方法可以有返回，也可以无返回,如果是一条语句，结果可以作为默认为返回值， 如果有多个语句，需要加上return
 例如:
 ````
 List<String> array = Arrays.asList("xiaowang","laowang");
 Collections.sort(array, (str1,str2) -> str1.length() - str2.length() );
 Collections.sort(array, (String str1,String str2) -> str1.length() - str2.length() );
 Collections.sort(array, (String str1,String str2) -> { System.out.println(str1 + " " +str2);
     return str1.length() - str2.length(); });
 ````
 
 **Lambda表达式 需要先了解JDK 8 中新添加的一个类 java.util.stream**
 参考网址：http://www.runoob.com/java/java8-streams.html
Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。
Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。
这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。
元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。

+--------------------+       +------+   +------+   +---+   +-------+
| stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
+--------------------+       +------+   +------+   +---+   +-------+

以上的流程转换为 Java 代码为：
```
List<Integer> transactionsIds = 
widgets.stream()
             .filter(b -> b.getColor() == RED)
             .sorted((x,y) -> x.getWeight() - y.getWeight())
             .mapToInt(Widget::getWeight)
             .sum();
```

**什么是 Stream？**

Stream（流）是一个来自数据源的元素队列并支持聚合操作
- 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算
- **数据源** 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等
- **聚合操作** 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等

和以前的Collection操作不同， Stream操作还有两个基础的特征：
- **Pipelining:** 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
- **内部迭代：** 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现

**生成流**
在 Java 8 中, 集合接口有两个方法来生成流：
- stream() − 为集合创建串行流
- parallelStream() − 为集合创建并行流。
````
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
````
下面的还是参考：http://www.runoob.com/java/java8-streams.html


11、@ConfigurationProperties含义
它可以把同类的配置信息自动封装成实体类