package cn.gingo.global.result.handler.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 核心配置类.
 *
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
@Data
@ConfigurationProperties(prefix = "global.result.handler")
public class GlobalResultHandlerConfigProperties {

  /**
   * 是否在异常处理器中打印日志，默认为false,不打印.
   */
  private boolean print = false;
}
