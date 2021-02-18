# global-result-handler-starter

[toc]

# 1. 需求背景

在前后端分离的开发场景下，接口的开发通常有几种需求：

## 1.1 封装统一的返回格式

为方便移动端处理接口返回值，通常要求将结果按照约定的格式封装起来，例如将操作结果封装到下面名为**ResponseBean**的JavaBean中：

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
bean.setCode(0);
bean.setMsg("success");
return bean;
```

## 1.2 统一异常处理及错误码

在开发中，接口执行过程发生异常，不能直接返回异常堆栈信息，需要将异常信息包装成异常码统一返回。

伪代码是这样的:

```java
@GetMapping
@ResponseBody
public Result GeneralPageQuery(Parameter params) {
    
    Result result = new Result();
    //TODO 1.校验params参数，非空校验、长度校验
    try{
        //TODO 2.调用Service的一系列操作
        //TODO 3.将操作结果设置到result对象中
    }catch(Exception e){
        //TODO 4.异常处理：一堆丑陋的try...catch，如果有错误码的，还需要手工填充错误码
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

## 1.3 接口参数校验及友好的错误提示

* 接口参数的某些校验属于重复体力劳动，例如非空校验、长度校验等，可以交给类似` Hibernate Validator` 这样的Bean Validation框架进行处理。
* Validator校验出来的异常，还需要跟匹配成开发者自定义的异常，方便返回错误码。

## 1.4 Http异常转换

在进行REST微服务开发时，Http协议本身会返回各种状态码，我们希望统一转成200，并返回自定义的异常码，例如：

发生404的http错误时，需要将http响应码改为200，并返回

```json
｛
	"code":"1404",
	"msg":"接口不存在"
｝
```

# 2. quick start

## 2.1 导入pom依赖

**global-result-handler-boot-starter**目前已发布到Maven中央仓库，可以直接在项目的pom文件中引入。

```xml
<dependency>
  <groupId>com.feiniaojin.grh</groupId>
  <artifactId>global-result-handler-boot-starter</artifactId>
  <version>1.0</version>
</dependency>
```

* 目前可用的版本是1.0

## 2.2 在启动类上通过注解开启统一处理

```java
@EnableGlobalResultHandler
@SpringBootApplication
public class ExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }
}
```

## 2.3 请求参数校验

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

## 2.4 统一异常处理及返回封装

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

## 2.5 关闭spring mvc的自动匹配

```yaml
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  resources:
    add-mappings: false
```

# 3. 案例代码

以下是使用`global-result-handler-boot-starter`进行开发的代码，细节见**global-result-handler-example**：

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
   * 测试空返回值，直接返回空值，自动封装.
   */
  @RequestMapping("/void")
  @ResponseBody
  public void testVoidResponse() {

  }

   /**
   * 测试抛出参数校验异常的情景
   */
  @RequestMapping("/validate")
  @ResponseBody
  public void testValidateException(@Validated RequestDto dto) {
    log.info(dto.toString());
  }

   /**
   * 测试在方法入参进行参数校验的情景
   */
  @RequestMapping("/success")
  @ResponseBody
  public RequestDto testSuccess(@Validated RequestDto dto) {
    log.info(dto.toString());
    return dto;
  }

   /**
   * 测试成功返回单个对象的情景
   */
  @RequestMapping("/get")
  @ResponseBody
  public ResponseDto get(Long id) {
    log.info("id=" + id);
    return exampleService.getById(id);
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
   * 不支持的http方法调用.
   * POST接口，使用GET进行请求
   *
   * @param userId
   */
  @RequestMapping(value = "/methodPost", method = RequestMethod.POST)
  @ResponseBody
  public void testMethodNotSupport(Long userId) {
    log.info("" + userId);

  }

  /**
   * 测试Controller中方法对参数进行校验的情形.
   */
  @RequestMapping("/jsonStr")
  @ResponseBody
  public String jsonStr() {
    log.info("");
    return "jsonStr";
  }

  /**
   * 测试Controller中方法对参数进行校验的情形.
   */
  @RequestMapping("/str")
  public String str() {
    log.info("");
    return "view";
  }
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
