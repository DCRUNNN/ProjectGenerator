package cn.edu.nju.dc.projectgeneratorserver.utils;

import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author dc
 * @date 2019/10/6 2:10
 */
public class RequestHolder {

    public static HttpServletRequest getHttpServletRequest() {
        return Optional.ofNullable(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()))
            .map(ServletRequestAttributes::getRequest)
            .orElseThrow(() -> new ServiceException("fail to get HttpServlet Request"));
    }
}
