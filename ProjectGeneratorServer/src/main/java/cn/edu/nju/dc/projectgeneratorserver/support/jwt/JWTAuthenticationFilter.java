package cn.edu.nju.dc.projectgeneratorserver.support.jwt;

import cn.edu.nju.dc.projectgeneratorserver.utils.IPUtil;
import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author dc
 * @date 2019/12/17 23:02
 *
 * 身份认证过滤器
 */
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request,
        @Nonnull final HttpServletResponse response, @Nonnull final FilterChain filterChain)
        throws ServletException, IOException {
        // 预请求后，直接返回
        // 返回码必须为 200 否则视为请求失败
        if (StringUtils.equals("OPTIONS", request.getMethod())) {
            return;
        }

        final String token = jwtUtil.getTokenFromRequest(request);
        if (StringUtils.isEmpty(token)) {
            log.info("=> Anonymous<{}> request URL<{}> Method<{}>",
                IPUtil.getIpAddress(request),
                request.getRequestURL(),
                request.getMethod());
        } else {
            final String username = this.jwtUtil.getUsername(token);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UsernamePasswordAuthenticationToken authentication = jwtUtil.getAuthentication(username, token);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
