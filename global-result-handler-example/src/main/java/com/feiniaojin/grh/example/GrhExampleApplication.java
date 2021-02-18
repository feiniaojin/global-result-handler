package com.feiniaojin.grh.example;

import com.feiniaojin.grh.boot.starter.EnableGlobalResultHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO:Add the description of this class.
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
@SpringBootApplication(scanBasePackages = {"com.feiniaojin.grh.example"})
@EnableGlobalResultHandler
public class GrhExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrhExampleApplication.class, args);
    }
}
