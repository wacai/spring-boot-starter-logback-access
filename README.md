Use [logback-access](http://logback.qos.ch/access.html) as Web server access log implmenetation for `spring-boot-starter-web` projects.

使用[logback-access](http://logback.qos.ch/access.html)作为 `spring-boot-starter-web` 项目内置 WEB 容器的 Access 日志输出的实现。

## Usage

### pom.xml

```xml
<dependency>
    <groupId>com.wacai</groupId>
    <artifactId>spring-boot-starter-logback-access</artifactId>
    <version>${last-version}</version>
</dependency>

```

### application.properties

```
logback.access.config.path=classpath:logback-access.xml
```

### logback-access.xml

```xml
<configuration>

    <property name="LOG_HOME" value="${LOG_HOME:-${user.dir}/logs}"/>

    <appender name="ACCESS" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>${LOG_HOME}/access.log</File>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_HOME}/access-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%h %l %u %user %date "%r" %s %b</pattern>
        </encoder>
    </appender>

    <appender-ref ref="ACCESS" />
    
    
</configuration>
```
