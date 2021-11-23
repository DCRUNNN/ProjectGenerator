package cn.edu.nju.dc.projectgeneratorserver.support.security;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.UserPO;
import cn.edu.nju.dc.projectgeneratorserver.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author dc
 * @date 2019/12/17 23:13
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final UserPO userPO = userService.getUserByUsername(username);
        // 权限
        final List<SimpleGrantedAuthority> authorities =
            userPO.getPermissionCodeList().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        // 角色
        authorities.add(new SimpleGrantedAuthority(userPO.getRoleName()));
        return new org.springframework.security.core.userdetails.User(userPO.getUsername(),
            userPO.getPassword(),
            authorities);
    }
}
