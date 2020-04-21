package cn.gingo.global.result.handler.checkers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.HashSet;
import java.util.Set;


/**
 * 检查是否需要处理swagger文档，不处理swagger文档的接口
 */
@Slf4j
public class SwaggerSupportChecker implements SupportChecker {


    private Set<Class> classSet = new HashSet<>();

    public SwaggerSupportChecker() {

        try {
            classSet.add(Class.forName("springfox.documentation.swagger.web.ApiResourceController"));
            classSet.add(Class.forName("springfox.documentation.swagger2.web.Swagger2Controller"));
        } catch (Exception e) {
            log.debug("SwaggerSupportChecker Init Exception", e);
        }
    }

    /**
     * 包含则不处理
     *
     * @param methodParameter
     * @param clazz
     * @return
     */
    @Override
    public boolean check(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> clazz) {

        if (classSet.contains(methodParameter.getContainingClass())) {
            return false;
        }
        return true;
    }
}
