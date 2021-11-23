package cn.edu.nju.dc.projectgeneratorserver.service;

import cn.edu.nju.dc.projectgeneratorserver.support.exception.DBException;
import cn.edu.nju.dc.projectgeneratorserver.support.log.domain.LogEntity;
import com.github.pagehelper.PageInfo;

/**
 * @author dc
 * @date 2019/10/6 0:41
 */
public interface LogService {

    Integer insertLog(LogEntity log) throws DBException;

    PageInfo<LogEntity> listAllByType(int page, int size, String logType);

    PageInfo<LogEntity> listByTimeAndType(int page, int size, String logType, String startTime, String endTime);

    Integer deleteByType(String logType);

    Integer deleteById(int logId);
}
