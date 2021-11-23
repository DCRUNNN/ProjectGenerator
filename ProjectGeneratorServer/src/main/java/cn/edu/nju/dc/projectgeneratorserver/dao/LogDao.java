package cn.edu.nju.dc.projectgeneratorserver.dao;

import cn.edu.nju.dc.projectgeneratorserver.support.log.domain.LogEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

/**
 * @author dc
 * @date 2019/10/6 0:48
 */
@Mapper
@Repository
public interface LogDao {
    int insertLog(@Param("log") LogEntity log) throws DuplicateKeyException;

    int deleteByType(@Param("logType") String logType);

    int deleteById(@Param("logId") int logId);

    List<LogEntity> listAllByType(@Param("logType") String logType);

    List<LogEntity> listByTimeAndType(@Param("logType") String logType, @Param("startTime") String startTime,
        @Param("endTime") String endTime);
}
