# global-result-handler-starter

[toc]



![grh.png](https://wx1.sbimg.cn/2020/06/27/2BVNj.png)

# 1. 需求背景

在前后端分离的开发场景下，通常的Controller伪代码是这样的:

```java
@GetMapping
@ResponseBody
public Result GeneralPageQuery(Parameter params) {
    
    Result result = new Result();
    
    //1. 校验params参数，非空校验、长度校验
    
    try{
        
        //2. 调用Service的一系列操作
    }catch(Exception e){
        
        //3. 异常处理：一堆丑陋的try...catch，如果有错误码的，还需要手工填充错误码
    }
    
    return result;
}
```

或者这样：

```java
@RequestMapping(value = "/content", method = RequestMethod.GET)
@ResponseBody
public CommonResult<HomeContentResult> content() {
    HomeContentResult contentResult = homeService.content();
    return CommonResult.success(contentResult);
}
```

以上代码均来自github高星流行的项目，通常存在以下几种问题：

* 手工进行封装统一响应格式

  为方便移动端处理接口返回值，通常要求将结果按照约定的格式封装起来。这个封装的操作大部分开发人员都是自己完成的。例如上文中的

  ```java
  Result result = new Result();
  CommonResult.success(contentResult);
  ```

  每一个接口都需要开发人员手工创建一个Result、CommonResult，属于低效的重复劳动。

  一般的，通常需要将处理结果封装到类似如下格式的Java Bean中

  ```java
  public class ResponseBean{
    private int code ;
    private String msg ;
    private Object data ;
  }
  ```

  每次有返回结果时，执行

  ```java
  ResponseBean bean=new ResponseBean();
  bean.setData(data);
  return bean;
  ```

* 混乱的异常处理体系
  * 虽然`@ExceptionHandler`已经存在了很多年，但是很多程序员还是习惯在每个接口进行异常捕获，然后根据不同的异常，封装异常码到上述的Result、CommonResult中，满篇的try……catch既显得代码冗余，也影响开发效率，还影响代码的可读性。
  * 对外提供的API，处理业务逻辑时发生了异常，不能直接给调用方返回异常堆栈信息，而是要返回这个异常对应的错误码，便于双方开发人员对接。
  * 异常类并没有和异常错误码进行关联，在抛出异常时，需要到枚举类、到定义类中找到异常对应的异常码，既容易出错，也非常低效。

* 接口参数校验及错误提示

  * 接口参数的某些校验属于重复体力劳动，例如非空校验、长度校验等，可以交给类似` Hibernate Validator` 这样的Bean Validation框架进行处理。
  * Validator校验出来的异常，还需要跟匹配成开发者自定义的异常，方便返回错误码。

* Http异常转换

  在进行REST微服务开发时，Http协议本身会返回各种状态码，我们希望统一转成200，并返回自定义的异常码。

以上所有问题，`global-result-handler-starter`一次性解决。

# 2. global-result-handler-starter案例代码

以下是使用`global-result-handler-starter`进行开发的代码：

```java
@PostMapping
public Long add(@RequestBody TaskDTO.Info info) {
    if(log.isDebugEnabled()){
        log.debug("TaskDTO.Info=[{}]",gson.toJson(info));
    }
    return taskService.add(info);
}

@GetMapping
public TaskDTO.Page pageList(TaskDTO.Query query) {

    if(log.isDebugEnabled()){
        log.debug("TaskDTO.Query=[{}]",gson.toJson(query));
    }
   TaskDTO.Page page= taskService.pageList(query);
   if(log.isDebugEnabled()){
        log.debug("TaskDTO.Page=[{}]",gson.toJson(page));
    }
   return page;
}

@GetMapping("/{id}")
public TaskDTO.DetailInfo detail(@PathVariable Long id) {

    if(log.isDebugEnabled()){
        log.debug("Getting Task detail,id=[{}]",id);
    }
    TaskDTO.DetailInfo detailInfo = taskService.getById(id);
    if(log.isDebugEnabled()){
        log.debug("Task detail=[{}]",gson.toJson(detailInfo));
    }
    return detailInfo;
}

@PutMapping("/{id}")
public void update(@PathVariable String id, TaskDTO.Info info) {
    if(log.isDebugEnabled()){
        log.debug("TaskDTO.Info=[{}]",gson.toJson(info));
    }
    taskService.updateById(info);
}

@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
    if(log.isDebugEnabled()){
        log.debug("Deleting Task ,id=[{}]",id);
    }
    taskService.deleteById(id);
}
```

以上代码具有以下特点

* 代码优雅、简洁、直观

* 在DTO中结合Validator进行校验

* 直接返回所需要的结果，不需要每个接口都new ResponseBean()

* 统一异常处理，业务异常结合异常码，也是阿里巴巴《Java开发手册》所推荐的

  异常的抛出主要在service层，例如

  ```java
  public TaskDTO.Info getInfoById(Long id) {
  	Task info = taskExMapper.selectByPrimaryKey(id);
  	if(info == null){
  		throw new CommonException.NotFoundException();
  	}
  	return entity2Info(info);
  }
  ```

  而CommonException.NotFoundException异常在抛出时，已经定义了异常码

  ```java
  @ExceptionMapper(code = 1404,msg = "Not found！")
  public static class NotFoundException extends RuntimeException {
  }
  ```

# 3. global-result-handler-starter使用教程

## 3.1 引入starter

**global-result-handler-starter**目前已发布到Maven中央仓库，可以直接在项目的pom文件中引入。

```xml
<dependency>
  <groupId>com.feiniaojin.grh</groupId>
  <artifactId>global-result-handler-starter</artifactId>
  <version>0.3</version>
</dependency>
```

* 目前可用的版本是0.3

## 3.2 通过注解开启统一处理

```java
@EnableGlobalResultHandler
@SpringBootApplication
public class ExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }
}
```

## 3.3 在DTO中设置校验

grh的接口参数校验，采用的是Hibernate Validator原生的方法，并未提供任何新增的校验方法，保证了代码的可移植性，只要是Hibernate Validator支持的校验方式，grh即兼容。

如果要扩展Hibernate Validator的校验功能，只要Hibernate Validator支持，则grh也会支持。

```java
@Data
public class RequestDTO {

    @NotNull(message = "userId is null ")
    private Long userId;

    @NotNull(message = "userName is null ")
    @Length(min = 6, max = 12)
    private String userName;

    @NotNull(message = "age is null ")
    @Range(min = 18, max = 50)
    private Integer age;
}
```

## 3.4 创建业务异常时加上@ExceptionMapper注解

在创建业务异常时，使用`@ExceptionMapper`进行注解，并设置异常错误码和提示信息。

> 推荐实践：同一个模块的业务异常，在同一个"XxxxExceptions"类中创建静态内部类，便于分组和维护，同模块的错误码拥有相同的前缀，例如用户中心相关的异常采用10xx～20xx码段。

```java
public class ExampleExceptions {

    @ExceptionMapper(code = 1024, msg = "UnCheckedException")
    public static class UnCheckedException extends RuntimeException {

    }

    @ExceptionMapper(code = 2048, msg = "CheckedException")
    public static class CheckedException extends Exception {

    }
}
```

## 3.5 关闭spring mvc的自动匹配

```yaml
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  resources:
    add-mappings: false
```

## 3.6 实现具体业务逻辑

以下代码来自example工程的`ExampleController`

```java
/**
 * class {@code ExampleController} 使用案例的Controller.
 *
 * @author feiniaojin
 */
@Controller
@RequestMapping("/example")
@Slf4j
@Validated
@Api("ExampleController")
public class ExampleController {

  @Resource
  private ExampleService exampleService;

  /**
   * 测试空返回值.
   */
  @RequestMapping("/void")
  @ResponseBody
  @ApiOperation(value = "测试返回空值", notes = "查询数据库中某个的学生信息")
  public void testVoidResponse() {

  }

  @RequestMapping("/validate")
  @ResponseBody
  public void testValidateException(@Validated RequestDto dto) {
    log.info(dto.toString());
  }

  @RequestMapping("/success")
  @ResponseBody
  public RequestDto testSuccess(RequestDto dto) {
    log.info(dto.toString());
    return dto;
  }

  /**
   * 测试抛出运行时异常的处理.
   *
   * @param dto 入参
   * @return 直接返回，未处理
   */
  @RequestMapping("/runtime")
  @ResponseBody
  public RequestDto testRuntimeException(RequestDto dto) {
    log.info(dto.toString());
    exampleService.testUnCheckedException();
    return dto;
  }

  /**
   * 测试受检异常的情形.
   *
   * @param dto 入参
   * @return 未处理，直接将入参返回
   * @throws Exception 首检异常
   */
  @RequestMapping("/checked")
  @ResponseBody
  public RequestDto testCheckedException(RequestDto dto) throws Exception {
    log.info(dto.toString());
    exampleService.testCheckedException();
    return dto;
  }

  /**
   * 测试抛出{@code Throwable} 的情形.
   *
   * @param dto 入参
   * @return 未处理，直接返回
   * @throws Throwable 抛出Throwable异常
   */
  @RequestMapping("/throwable")
  @ResponseBody
  public RequestDto testThrowable(RequestDto dto) throws Throwable {
    log.info(dto.toString());
    throw new Throwable();
  }

  /**
   * 测试Controller中方法对参数进行校验的情形.
   *
   * @param userId 非空
   */
  @RequestMapping("/method")
  @ResponseBody
  public void testMethod(@NotNull Long userId) {
    log.info("" + userId);

  }

  /**
   * 测试Controller中方法对参数进行校验的情形.
   */
  @RequestMapping("/str")
  @ResponseBody
  public String testString() {
    log.info("");
    return "testString";
  }
}
```

## 3.7 demo工程的结构

![a.png](https://s1.ax1x.com/2020/05/21/YbJd76.png)

## 3.8 Swagger2支持

从0.2版本开始，添加对Swagger2的支持。

* 添加Swagger2的依赖，目前只测试了2.6.0版本的

  ```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.6.0</version>
  </dependency>
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.6.0</version>
  </dependency>
  ```

* 在工程中添加springmvc的静态路径

  ```yaml
  spring:
    mvc:
      throw-exception-if-no-handler-found: true
      static-path-pattern: "*.html"
      add-mappings: false
  ```

  如果不添加，grh将会拦截html

## 3.9 自定义返回格式和默认异常码

从0.3版本开始，支持自定义默认异常码和统一返回的格式。

要支持自定义默认异常码和统一返回的格式，可以参考`global-result-handler-defaults`，该模块提供了很多默认的实现。

具体步骤如下：

### 3.9.1 增加maven依赖

增加`global-result-handler-def`的依赖

```xml
<dependency>
    <groupId>com.feiniaojin.grh</groupId>
    <artifactId>global-result-handler-def</artifactId>
    <version>0.3</version>
</dependency>
```

`global-result-handler-def`只有一些定义的interface注解，不包含任何实现，也不包含任何第三方库，可以直接在api接口定义中引入。

### 3.9.2 提供自定义实现

参考`global-result-handler-defaults`，对需要自定义的内容进行实现。

### 3.9.3 Spring Boot中配置实例化

将自定义的组件配置到Spring Boot中，即可替换默认的实现。

# 4. 版本历史

## 0.1

基本功能的实现，用于作者个人项目，实现的功能有：

* 统一异常处理
* 参数校验并返回
* 统一返回格式
* 异常与错误码关联

## 0.2

* 增加对swagger的支持

## 0.3

* 支持自定义默认错误码
* 支持自定义返回格式
* 支持自定义http状态码与异常关联
* 支持自定义参数校验异常与异常关联
* example工程中测试spring boot跳转静态页面

# 5. 源码地址

以上的所有实现，是基于笔者在实际开发中的需求进行抽象设计的，未必适合所有的情形，欢迎提交PR。

该项目的源码已上传至github，有需要的同学可以clone下来进行二次开发。

github:

> starter: https://github.com/feiniaojin/global-result-handler.git
>
> example: https://github.com/feiniaojin/global-result-handler-starter-example.git

global-result-handler-starter-example需要搭配对应版本的global-result-handler-starter，二者的版本号一致。

联系方式：

> 微信：qyj000100
>
> 邮箱：943868899@qq.com