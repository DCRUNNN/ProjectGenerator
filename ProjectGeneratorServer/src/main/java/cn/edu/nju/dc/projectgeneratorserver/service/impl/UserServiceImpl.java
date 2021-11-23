package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.dao.PermissionDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.UserDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.UserPO;
import cn.edu.nju.dc.projectgeneratorserver.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dc
 * @date 2021/11/23 21:26
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserPO> getAllUsersWithRole() {
        return userDao.getAllUsersWithRole();
    }

    @Override
    public UserPO getUserBy(String column, Object param) {
        final Map<String, Object> map = new HashMap<>(1);
        map.put(column, param);
        return userDao.getUserBy(map);
    }

    @Override
    public UserPO getUserByUsername(String username) throws UsernameNotFoundException {
        final UserPO userPO = this.getUserBy("username", username);
        if (userPO == null) {
            throw new UsernameNotFoundException("username not found");
        }
        if ("ROLE_ADMIN".equals(userPO.getRoleName())) {
            // 超级管理员所有权限都有
            userPO.setPermissionCodeList(permissionDao.getAllAuthorityCode());
        }
        return userPO;
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}