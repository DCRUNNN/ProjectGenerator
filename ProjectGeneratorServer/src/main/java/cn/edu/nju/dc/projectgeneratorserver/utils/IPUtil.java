package cn.edu.nju.dc.projectgeneratorserver.utils;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dc
 * @date 2019/12/17 23:07
 *
 * IP工具类
 *
 */
@Slf4j
public class IPUtil
{

    private final static String UNKNOWN = "unknown";

    private final static String LOCALHOST_IPV4 = "127.0.0.1";

    private final static String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    private IPUtil()
    {

    }

    /**
     * 获取登录用户的IP地址
     *
     * @param request request
     * @return IP地址
     */
    public static String getIpAddress(final HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip))
        {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.equals(LOCALHOST_IPV6, ip))
        {
            ip = LOCALHOST_IPV4;
        }
        if (ip.split(",").length > 1)
        {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
