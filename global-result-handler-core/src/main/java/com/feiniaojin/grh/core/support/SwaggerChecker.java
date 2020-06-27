package com.feiniaojin.grh.core.support;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;

/**
 * swagger相关类校验器.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 */
public class SwaggerChecker {

  private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerChecker.class);

  private Set<Class> classSet = new HashSet<>();
  private static final String SWAGGER_PATH = "swagger";
  private static final String API_DOCS = "v2/api-docs";

  /**
   * 无参构造.
   */
  public SwaggerChecker() {

    try {
      classSet.add(Class.forName("springfox.documentation.swagger.web.ApiResourceController"));
      classSet.add(Class.forName("springfox.documentation.swagger2.web.Swagger2Controller"));
      classSet.add(Class.forName("springfox.documentation.swagger.web.UiConfiguration"));
    } catch (Exception e) {
      LOGGER.debug("Can not find Swagger2 Classes", e);
    }
  }

  public boolean isSwaggerClass(Object obj) {
    LOGGER.info(obj.getClass().getName());
    return classSet.contains(obj.getClass());
  }

  public boolean isSwaggerClass(Class clazz) {
    return classSet.contains(clazz);
  }

  /**
   * 检查是否是swagger的URL.
   * @param serverHttpRequest serverHttpRequest
   * @return
   */
  public boolean isSwaggerUrl(ServerHttpRequest serverHttpRequest) {

    String servletPath = serverHttpRequest.getURI().getPath();
    if (servletPath.contains(SWAGGER_PATH)
        || servletPath.contains(API_DOCS)) {
      return true;
    }
    return false;
  }
}
