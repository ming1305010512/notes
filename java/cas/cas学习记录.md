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

5、创建一个Maven的web项目（充当与cas客户端1）
加入依赖：
```
<dependency>
      <groupId>org.jasig.cas.client</groupId>
      <artifactId>cas-client-core</artifactId>
      <version>3.2.1</version>
    </dependency>
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.16</version>
    </dependency>
```
在web.xml中加入过滤器：
```
<!-- 用于单点退出，该过滤器用于实现单点登出功能，可选配置-->
  <listener>							 <listener-class>org.jasig.cas.client.session.SingleSignOutHttpSessionListener</listener-class>
  </listener>
  <!-- 该过滤器用于实现单点登出功能，可选配置 -->
  <filter>
    <filter-name>CAS Single Sign Out Filter</filter-name>
    <filter-class>org.jasig.cas.client.session.SingleSignOutFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>CAS Single Sign Out Filter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <filter>
    <filter-name>CAS Filter</filter-name>
    <filter-class>org.jasig.cas.client.authentication.AuthenticationFilter</filter-class>
    <init-param>
      <param-name>casServerLoginUrl</param-name>
      <param-value>https://server.zhoubang85.com:8443/cas/login</param-value>
    </init-param>
    <init-param>
      <param-name>serverName</param-name>
      <param-value>http://client1.zhoubang85.com:18080</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CAS Filter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!-- 该过滤器负责对Ticket的校验工作，必须启用它 -->
  <filter>
    <filter-name>CAS Validation Filter</filter-name>
    <filter-class>
      org.jasig.cas.client.validation.Cas10TicketValidationFilter</filter-class>
    <init-param>
      <param-name>casServerUrlPrefix</param-name>
      <param-value>https://server.zhoubang85.com:8443/cas</param-value>
    </init-param>
    <init-param>
      <param-name>serverName</param-name>
      <param-value>http://client1.zhoubang85.com:18080</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CAS Validation Filter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!--
      该过滤器负责实现HttpServletRequest请求的包裹，比如允许开发者通过HttpServletRequest的getRemoteUser()方法获得SSO登录用户的登录名，可选配置。
      -->
  <filter>
    <filter-name>CAS HttpServletRequest Wrapper Filter</filter-name>
    <filter-class>						org.jasig.cas.client.util.HttpServletRequestWrapperFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>CAS HttpServletRequest Wrapper Filter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!--
      该过滤器使得开发者可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。比如AssertionHolder.getAssertion().getPrincipal().getName()。
  -->
  <filter>
    <filter-name>CAS Assertion Thread Local Filter</filter-name>
    <filter-class>org.jasig.cas.client.util.AssertionThreadLocalFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>CAS Assertion Thread Local Filter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```
访问：http://client1.zhoubang85.com:18080/，会自动跳转到CAS认证界面
https://server.zhoubang85.com:8443/cas/login?service=http%3A%2F%2Fclient1.zhoubang85.com%3A18080%2F,因为没有登陆过CAS认证系统，CAS认证系统拦截你访问的客户端应用，首先进入到认证系统登陆界面，同时URL后面加上你想访问的地址信息，登陆成功后，会跳转到http://client1.zhoubang85.com:18080/http://client1.zhoubang85.com:18080/;jsessionid=976B04415BED56517271994CECE23015

***错误解决：***如果出现PKIX path building failed错误
原因：客户端run的jdk和server端run的jdk版本不同，仔细检查一下，run的是不是你导入证书的那个jdk，还有生成证书是需注意“您的名字和姓氏”这一项中，需天cas server的host name

6、证书生成步骤：
- 1.生成证书，在cmd窗口输入以下命令：
```
keytool -genkey -alias ssodemo -keyalg RSA -keysize 1024 -keypass zhoubang -validity 365 -keystore c:\zhoubang.keystore -storepass zhoubang
```
-alias后面的别名自定义，-keypass指定证书密码，注意-storepass和前面的keypass的密码相同，不然tomcat配置https会访问失败 -keystore指定证书的位置，
第一个让你输入“您的名字和姓氏是什么”，必须输入在***C:\Windows\System32\drivers\etc\hosts***文件中加入的服务端的域名，首先cas只能通过域名来访问，不能通过ip访问，其次，如果不这么做，在最后cas回调转入你想访问的客户端应用的时候，会出现No subject alternative names present错误异常信息
- 2.导出证书：在命令窗口输入以下命令
```
keytool -export -alias ssodemo -keystore c:\zhoubang.keystore -file c:\ssodemo.crt -storepass zhoubang
```
【说明】：-alias后面的名称要与生成证书的命令里面的alias的名称一致. –keystore后面指定证书存放的位置，这里我放在C盘根目录，同时证书名称要与【生成证书】对应的命令里的keystore名称一致.这里是zhoubang.keystore，-file后面才crt路径，我也指定在c盘根目录. –storepass的证书密码要与上面输入的密码一致.
- 3.客户端导入证书：在cmd输入以下命令
```
keytool -import -keystore %JAVA_HOME%\jre\lib\security\cacerts -file c:\ ssodemo.crt -alias ssodemo
使用上面的我发生了错误，改成：
keytool -import -alias ssodemo -keystore cacerts -file d:\ssodemo.crt
说明：输入该命令的前提是需要进入C:\Program Files\Java\jre1.8.0_131\lib\security>，然后在输入该命令

```
【说明】：-file指定证书的位置，也就是上一步导出证书的位置，即c:\ ssodemo.crt 命令中指定了JAVA_HOME，意思是将证书导入到客户端证书库，也就是jdk证书库中.因为客户端应用运行在本地，需要jdk的支持。

回车之后，会让你输入密钥库口令，注意，这里的密码必须要输入changeit，不能输入上面指定的密码zhoubang，切记，否则导入客户端证书会有问题，如果是多台机器演示，需要在每一台客户端导入该证书，步骤都是一样的。当看到提示“是否信任此证书”，输入y回车即可