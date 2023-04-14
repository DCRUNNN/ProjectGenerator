package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.dao.LogDao;
import cn.edu.nju.dc.projectgeneratorserver.service.LogService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.DBException;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import cn.edu.nju.dc.projectgeneratorserver.support.log.domain.LogEntity;
import cn.edu.nju.dc.projectgeneratorserver.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author dc
 * @date 2019/10/6 0:48
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @Async("logTaskExecutor")
    public Integer insertLog(LogEntity log) throws DBException {
        log.setCreateTime(DateUtil.getCurrentFormatTime());
        try {
            logDao.insertLog(log);
            return log.getId();
        }
        catch (DuplicateKeyException e) {
            throw new DBException("log id already exist", e);
        }
        catch (Throwable e) {
            throw new ServiceException(String.format("fail to insert log, log info is [%s]", log.toString()), e);
        }
    }

    @Override
    @Async("logTaskExecutor")
    public Integer deleteByType(String logType) {
        try {
            return logDao.deleteByType(logType);
        }
        catch (Throwable e) {
            throw new DBException(String.format("fail to delete log by type [%s]", logType), e);
        }
    }

    @Override
    @Async("logTaskExecutor")
    public Integer deleteById(int logId) {
        try {
            return logDao.deleteById(logId);
        }
        catch (Throwable e) {
            throw new DBException(String.format("fail to delete log by id [%d]", logId), e);
        }
    }

    @Override
    public PageInfo<LogEntity> listAllByType(int page, int size, String logType) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> logDao.listAllByType(logType));
    }

    @Override
    public PageInfo<LogEntity> listByTimeAndType(int page, int size, String logType, String startTime, String endTime) {
        return PageHelper.startPage(page, size)
            .doSelectPageInfo(() -> logDao.listByTimeAndType(logType, startTime, endTime));
    }
}
