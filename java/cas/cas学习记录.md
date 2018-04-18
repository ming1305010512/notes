1、概念：
CAS是一个单点登录框架，开始有耶鲁大学的一个组织开发，后来鬼apereo管，开源，遵循apache 2.0协议，github代码地址：https://github.com/apereo/cas


2、将包导入idea后，修改配置
在web-inf下的deployerConfigContext.xml中修改
```
<bean class="org.jasig.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler"
					p:httpClient-ref="httpClient" p:requireSecure="false"/>
```
加上了p:requireSecure="false",目的为了简单，不启用HTTPS
在spring-configuration下的ticketGrantingTicketCookieGenerator.xml中修改
```
<bean id="ticketGrantingTicketCookieGenerator" class="org.jasig.cas.web.support.CookieRetrievingCookieGenerator"
		p:cookieSecure="false"
		p:cookieMaxAge="-1"
		p:cookieName="CASTGC"
		p:cookiePath="/cas" />
```
将p:cookieSecure设置为false
同样将spring-configuration下的warnCokieGenerator.xml
```
<bean id="warnCookieGenerator" class="org.jasig.cas.web.support.CookieRetrievingCookieGenerator"
		p:cookieSecure="false"
		p:cookieMaxAge="-1"
		p:cookieName="CASPRIVACY"
		p:cookiePath="/cas" />
```
将p:cookieSecure修改为false

3、下载
学习网址参考:https://blog.csdn.net/u010475041/article/details/77893463
官方提供了手脚架工具（https://casinitializr.herokuapp.com/），可以自己去定义自己的项目

4、将cas放到tomcat启动之后，修改配置，来支持mysql数据库交互验证
第一步：\WEB-INF\ deployerConfigContext.xml文件
```
<bean id="authenticationManager" class="org.jasig.cas.authentication.PolicyBasedAuthenticationManager">
        <constructor-arg>
            <map>
                <!--
                   | IMPORTANT
                   | Every handler requires a unique name.
                   | If more than one instance of the same handler class is configured, you must explicitly
                   | set its name to something other than its default name (typically the simple class name).
                   -->
                <entry key-ref="proxyAuthenticationHandler" value-ref="proxyPrincipalResolver" />
                <!-- <entry key-ref="primaryAuthenticationHandler" value-ref="primaryPrincipalResolver" /> -->
                <!-- key-ref指定自己的本地数据库访问 -->
                <entry key-ref="dbAuthHandler" value-ref="primaryPrincipalResolver"/>
            </map>
        </constructor-arg>

        <!-- Uncomment the metadata populator to allow clearpass to capture and cache the password
             This switch effectively will turn on clearpass.
        <property name="authenticationMetaDataPopulators">
           <util:list>
              <bean class="org.jasig.cas.extension.clearpass.CacheCredentialsMetaDataPopulator"
                    c:credentialCache-ref="encryptedMap" />
           </util:list>
        </property>
        -->

        <!--
           | Defines the security policy around authentication. Some alternative policies that ship with CAS:
           |
           | * NotPreventedAuthenticationPolicy - all credential must either pass or fail authentication
           | * AllAuthenticationPolicy - all presented credential must be authenticated successfully
           | * RequiredHandlerAuthenticationPolicy - specifies a handler that must authenticate its credential to pass
           -->
        <property name="authenticationPolicy">
            <bean class="org.jasig.cas.authentication.AnyAuthenticationPolicy" />
        </property>
    </bean>
```
目的是指定自己的本地数据库访问
然后在加入2个bean的配置，如下：
```
<!-- 指定c3p0数据源 -->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="com.mysql.jdbc.Driver" />
	<property ame="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/myProject?useUnicode=true&amp;characterEncoding=UTF-8" />
	<property name="user" value="root" />
	<property name="password" value="root
</bean>

```
```
 <!--访问本地数据库  -->
    <bean id="dbAuthHandler"
      class="org.jasig.cas.adaptors.jdbc.QueryDatabaseAuthenticationHandler"
      p:dataSource-ref="dataSource"
      p:sql="SELECT u.`password` FROM `user` u WHERE u.`user_name` = ?" />
```
重新访问cas，输入数据库中存在的用户名和密码，登陆成功，说明配置无误