# global-result-handler-starter

[toc]

# 1. 需求背景

## 1.1 现有问题

在目前前后端分离的开发的场景下，通常的Controller伪代码是这样的:

```java
@GetMapping
@ResponseBody
public Result GeneralPageQuery(Parameter params) {
    
    Result result = new Result();
    
    //1. 校验params参数，非空校验、长度校验
    
    try{
        
        //2. 调用Service的一系列操作
    }cach(Exception e){
        
        //3. 异常处理：一堆丑陋的try...cach，如果由错误码的，还需要手工填充错误码
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

以上代码均来自github流行的项目，通常存在以下几种问题：

**全局异常处理**：处理业务逻辑时可能会产生各种异常，如果逐个对异常进行捕获处理，既显得代码冗余，也影响开发效率。

**接口参数校验及错误提示**：接口参数的某些校验属于重复体力劳动，例如非空校验、长度校验等，可以交给类似 Hibernate Validator 这样的Bean Validation框架进行处理。

**接口错误码**：对外提供的API，处理业务逻辑时发生了异常，不能直接给调用方返回异常堆栈信息，而是要返回这个异常对应的错误码，便于双方开发人员对接。

**统一响应格式**：移动端处理接口返回值时，通常要求将结果封装起来，这个封装的操作大部分开发人员都是自己完成的。

例如要封装成以下响应格式：	

```java
public class ResponseBean{
  private int code ;
  private String msg ;
  private Object data ;
}
```

就需要

```java
ResponseBean bean=new ResponseBean();
bean.setData(data);
return bean;
```

每个接口都手工创建一次，非常的不优雅。`global-result-handler-starter`一次性解决以上所有问题。

---

## 1.2 global-result-handler-starter解决方案

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

# 2. 使用global-result-handler-starter

## 2.1 引入starter

```xml
<dependency>
  <groupId>com.feiniaojin</groupId>
  <artifactId>global-result-handler-starter</artifactId>
  <version>0.2</version>
</dependency>
```

* 目前可用的版本是0.2

## 2.2 通过注解开启统一处理

```java
@EnableGlobalResultHandler
@SpringBootApplication
public class ExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }
}
```

## 6.3 在DTO中设置校验

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

## 6.4 创建异常时加上@ExceptionMapper注解

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

## 6.5 关闭spring mvc的自动匹配

```yaml
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  resources:
    add-mappings: false
```

## 6.6 实现具体业务逻辑

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

## 6.7 demo工程的结构

![](https://mmbiz.qpic.cn/sz_mmbiz_png/sNtDzfAUItIwFAicRorpaF8LjHPOVnqic4b0AzBbRxV8V7Y8wfvjIVoHOkiaxwLLletcgV2sgXKFlTF0gg5KxgkZw/0?wx_fmt=png)

## 6.8 Swagger2支持

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

# 7. 源码地址

以上的所有实现，是基于笔者在实际开发中的需求进行抽象设计的，未必适合所有的情形，欢迎提交PR。

该项目的源码已上传至github，有需要的同学可以clone下来进行二次开发。

github:

> starter: https://github.com/feiniaojin/global-result-handler-starter.git
>
> example: https://github.com/feiniaojin/global-result-handler-starter-example.git

global-result-handler-starter-example需要搭配对应版本的global-result-handler-starter，二者的版本号一致。

 