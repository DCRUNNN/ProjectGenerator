package cn.edu.nju.dc.projectgeneratorserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dc
 * @date 2019/12/21 23:33
 */
@RestController
public class UserController
{

    @Autowired
    private UserDetailsService userDetailsService;
}
