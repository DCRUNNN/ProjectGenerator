package cn.edu.nju.dc.projectgeneratorserver.support.log.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2019/10/6 0:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogEntity {
    private int id;

    private String username;

    private String description;

    private String methodName;

    private String params;

    private String logType;

    private String requestIp;

    private String costTime;

    private String exceptionDetails;

    private String createTime;
}
