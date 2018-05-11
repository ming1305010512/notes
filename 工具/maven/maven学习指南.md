###命令
- 1、创建一个web项目
```
    mvn archetype:generate \
        -DarchetypeGroupId=org.apache.maven.archetypes \
        -DarchetypeArtifactId=maven-archetype-webapp \
        -DgroupId=com.mycompany.app \
        -DartifactId=my-webapp
```
- 2、创建一个简单项目
```
    mvn -B archetype:generate \
      -DarchetypeGroupId=org.apache.maven.archetypes \
      -DgroupId=com.mycompany.app \
      -DartifactId=my-app
```
- 3、将依赖包导出
```
mvn dependency:copy-dependencies 
会将依赖包导出到target/dependence文件夹下
mvn dependency:copy-dependencies -DoutputDirectory=lib
```