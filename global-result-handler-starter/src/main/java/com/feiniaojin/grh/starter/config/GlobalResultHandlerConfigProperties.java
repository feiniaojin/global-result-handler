package com.feiniaojin.grh.starter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 核心配置类.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.1
 * @since 0.1
 */
@ConfigurationProperties(prefix = "global.result.handler")
public class GlobalResultHandlerConfigProperties {
  private boolean enableSwagger = true;
}
