package cn.edu.nju.dc.projectgeneratorserver.support.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author dc
 * @date 2019/12/17 23:13
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException
    {

        // TODO
        return null;
    }
}
