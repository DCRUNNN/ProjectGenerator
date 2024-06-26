package cn.edu.nju.dc.projectgeneratorserver.support.jwt;

import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResultUtil;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author dc
 * @date 2019/12/17 23:02
 *
 * Json Web Token 入口点
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    /**
     * 当访问的资源没有权限时被调用
     */
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
        final AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.getWriter().print(BaseResultUtil.createUnauthorizedResult().toString());
        response.getWriter().close();
    }
}
