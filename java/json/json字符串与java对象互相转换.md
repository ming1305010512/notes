网址：www.jb51.net/article/90914.htm

1.json字符串==》java对象
```
public static void jsonStrToJava(){
    //定义两种不同格式的字符串
    String objectStr="{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}";
    String arrayStr="[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";
    //1、使用JSONObject
    JSONObject jsonObject=JSONObject.fromObject(objectStr);
    Student stu=(Student)JSONObject.toBean(jsonObject, Student.class);
    //2、使用JSONArray
    JSONArray jsonArray=JSONArray.fromObject(arrayStr);
    //获得jsonArray的第一个元素
    Object o=jsonArray.get(0);
    JSONObject jsonObject2=JSONObject.fromObject(o);
    Student stu2=(Student)JSONObject.toBean(jsonObject2, Student.class);
    System.out.println("stu:"+stu);
    System.out.println("stu2:"+stu2);
}
```
打印的结果：
stu:Student [name=JSON, age=24, address=北京市西城区]
stu2:Student [name=JSON, age=24, address=北京市西城区]

2.json对象==》字符串
```
public static void convertObject() {
    Student stu=new Student();
    stu.setName("JSON");
    stu.setAge("23");
    stu.setAddress("北京市西城区");
    //1、使用JSONObject
    JSONObject json = JSONObject.fromObject(stu);
    //2、使用JSONArray
    JSONArray array=JSONArray.fromObject(stu);
    String strJson=json.toString();
    String strArray=array.toString();
    System.out.println("strJson:"+strJson);
    System.out.println("strArray:"+strArray);
}
```
打印的结果：
strJson:{"address":"北京市西城区","age":"23","name":"JSON"}
strArray:[{"address":"北京市西城区","age":"23","name":"JSON"}]