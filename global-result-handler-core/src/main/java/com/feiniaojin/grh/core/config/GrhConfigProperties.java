package com.feiniaojin.grh.core.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 核心配置类.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 * @since 0.1
 */
@ConfigurationProperties(prefix = "grh")
@EnableConfigurationProperties
public class GrhConfigProperties {

    private boolean useValidationMsg = true;

    public boolean isUseValidationMsg() {
        return useValidationMsg;
    }

    public void setUseValidationMsg(boolean useValidationMsg) {
        this.useValidationMsg = useValidationMsg;
    }
}
