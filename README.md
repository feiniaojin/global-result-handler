# global-result-handler-starter

## 背景

在进行Java Web开发时，经常会遇到以下几种情形：

**全局异常处理**：处理业务逻辑时可能会产生各种异常，如果逐个对异常进行捕获处理，既显得代码冗余，也影响开发效率。

**接口参数校验及错误提示**：接口参数的某些校验属于重复体力劳动，例如非空校验、长度校验等，可以交给类似 Hibernate Validator 这样的Bean Validation框架进行处理。

**接口错误码**：对外提供的API，处理业务逻辑时发生了异常，不能直接给调用方返回异常堆栈信息，而是要返回这个异常对应的错误码，便于双方开发人员对接。

**统一响应格式**：移动端处理接口返回值时，统一的响应格式有利于双方人身安全以及团队和谐。

## 使用

### 1. 引入global-result-handler-starter

目前global-result-handler-starter并没有打包发布到maven中心仓库，所以需要先clone项目。

```shell
git clone https://github.com/feiniaojin/global-result-handler-starter.git
```

### 2. 本地打包

采用idea或者其他ide引入global-result-handler-starter，执行install

### 3.  在项目的pom中引入maven依赖

```xml
 <dependency>
            <groupId>cn.gingo</groupId>
            <artifactId>global-result-handler-starter</artifactId>
            <version>${latest.version}</version>
 </dependency>
```

${latest.version}：代表最新的版本。

### 4. 注解开启global-result-handler

```java
@EnableGlobalResultHandler
@SpringBootApplication
public class ExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }
}
```



### 更多细节

关注公众号MarkWord，《JavaWeb全局结果处理》一文有详细的说明文档。

[点击查看《JavaWeb全局结果处理》](https://mp.weixin.qq.com/s/Ljs-SW5yhXxY8EjtOaYVHQ)

