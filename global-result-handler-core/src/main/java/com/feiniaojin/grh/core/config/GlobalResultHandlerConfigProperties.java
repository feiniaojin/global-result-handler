package com.feiniaojin.grh.core.config;


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
  private boolean useValidationMsg = true;

  public boolean isEnableSwagger() {
    return enableSwagger;
  }

  public void setEnableSwagger(boolean enableSwagger) {
    this.enableSwagger = enableSwagger;
  }

  public boolean isUseValidationMsg() {
    return useValidationMsg;
  }

  public void setUseValidationMsg(boolean useValidationMsg) {
    this.useValidationMsg = useValidationMsg;
  }
}
