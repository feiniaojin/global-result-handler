package com.feiniaojin.grh.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 核心配置类.
 *
 * @author feiniaojin
 * @version 0.1
 * @since 0.1
 */
@Data
@ConfigurationProperties(prefix = "global.result.handler")
public class GlobalResultHandlerConfigProperties {

  private boolean enableSwagger = true;
}
