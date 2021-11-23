package cn.edu.nju.dc.projectgeneratorserver.service;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.UserPO;
import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author dc
 * @date 2021/11/23 21:16
 */
public interface UserService
{

    List<UserPO> getAllUsersWithRole();

    UserPO getUserBy(String column, Object param);

    UserPO getUserByUsername(String username)
        throws UsernameNotFoundException;

    boolean verifyPassword(String rawPassword, String encodedPassword);

}