## strr-admin

### 项目介绍
- strr-admin-common 通用模块：基础CRUD封装及工具类
- strr-admin-service 权限管理模块：基础框架
- strr-admin-generator 代码生成器
- strr-admin-demo 示例

### 使用
1. 拉取代码
```
git clone https://github.com/strr0/strr-admin
```
2. 代码发布本地仓库
```
mvn deploy
```
3. 运行strr-admin-service模块的schema.sql文件
4. 新建项目引入依赖
```
pom.xml
...
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.3.7.RELEASE</spring-boot.version>
        <spring.version>5.2.12.RELEASE</spring.version>
        <mybatis-boot.version>2.2.0</mybatis-boot.version>
        <mybatis.version>3.5.7</mybatis.version>
        <mysql.version>8.0.22</mysql.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>com.strr</groupId>
            <artifactId>strr-admin-service</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <mainClass>com.strr.Application</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
...
```
```
application.yml
server:
  port: 8081
spring:
  application:
    name: admin
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/strr_admin?serverTimezone=UTC
    username: root
    password: password
mybatis:
  mapper-locations: classpath:mapper/*/*.xml
  configuration:
    # 下划线驼峰映射
    map-underscore-to-camel-case: true
```
```
Application.java
...
@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```