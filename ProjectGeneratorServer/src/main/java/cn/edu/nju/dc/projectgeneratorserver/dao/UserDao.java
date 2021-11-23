package cn.edu.nju.dc.projectgeneratorserver.dao;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.UserPO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dc
 * @date 2019/1/5 21:01
 */
@Mapper
@Repository
public interface UserDao
{

    List<UserPO> getAllUsersWithoutRole();

    List<UserPO> getAllUsersWithRole();

    UserPO getUserBy(Map<String, Object> param);

}
