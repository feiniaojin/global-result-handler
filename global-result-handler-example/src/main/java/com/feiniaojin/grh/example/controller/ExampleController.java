package com.feiniaojin.grh.example.controller;

import com.feiniaojin.grh.example.dto.RequestDto;
import com.feiniaojin.grh.example.dto.ResponseDto;
import com.feiniaojin.grh.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;


/**
 * class {@code ExampleController} 使用案例的Controller.
 *
 * @author qinyujie
 */
@Controller
@RequestMapping("/example")
@Validated
public class ExampleController {

    private Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Resource
    private ExampleService exampleService;

    /**
     * 测试空返回值.
     */
    @RequestMapping("/void")
    @ResponseBody
    public void testVoidResponse() {

    }

    @RequestMapping("/validate")
    @ResponseBody
    public void testValidateException(@Validated RequestDto dto) {
        logger.info(dto.toString());
    }

    @RequestMapping("/success")
    @ResponseBody
    public RequestDto testSuccess(@Validated RequestDto dto) {
        logger.info(dto.toString());
        return dto;
    }

    @RequestMapping("/get")
    @ResponseBody
    public ResponseDto get(Long id) {
        logger.info("id=" + id);
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
        logger.info(dto.toString());
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
        logger.info(dto.toString());
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
        logger.info(dto.toString());
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
        logger.info("" + userId);

    }

    /**
     * 不支持的http方法调用.
     * POST接口，使用GET进行请求
     *
     * @param userId 非空
     */
    @RequestMapping(value = "/methodPost", method = RequestMethod.POST)
    @ResponseBody
    public void testMethodNotSupport(Long userId) {
        logger.info("" + userId);

    }

    /**
     * 测试Controller中方法对参数进行校验的情形.
     * @return
     */
    @RequestMapping("/jsonStr")
    @ResponseBody
    public String jsonStr() {
        logger.info("");
        return "jsonStr";
    }

    /**
     * 测试Controller中方法对参数进行校验的情形.
     * @return
     */
    @RequestMapping("/str")
    public String str() {
        logger.info("");
        return "view";
    }
}
