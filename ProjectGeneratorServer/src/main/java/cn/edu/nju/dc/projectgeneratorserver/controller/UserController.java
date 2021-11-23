package cn.edu.nju.dc.projectgeneratorserver.controller;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.UserDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.UserPO;
import cn.edu.nju.dc.projectgeneratorserver.service.UserService;
import cn.edu.nju.dc.projectgeneratorserver.support.jwt.JWTUtil;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResult;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResultUtil;
import java.security.Principal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dc
 * @date 2019/12/21 23:33
 */
@RestController
@RequestMapping(path = "/api/user")
@Validated
public class UserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public BaseResult login(@RequestBody UserDTO userDto)
    {
        if (StringUtils.isEmpty(userDto.getUsername()) && StringUtils.isEmpty(userDto.getEmail()))
        {
            return BaseResultUtil.createFailedResult("用户名或邮箱不能为空");
        }
        if (StringUtils.isEmpty(userDto.getPassword()))
        {
            return BaseResultUtil.createFailedResult("密码不能为空");
        }

        UserPO dbUser = null;
        // 用户名登录
        if (StringUtils.isNotEmpty(userDto.getUsername()))
        {
            dbUser = userService.getUserByUsername(userDto.getUsername());
            if (dbUser == null)
            {
                return BaseResultUtil.createFailedResult("找不到" + userDto.getUsername() + "用户");
            }
        }

        if (StringUtils.isNotEmpty(userDto.getEmail()))
        {
            dbUser = userService.getUserBy("email", userDto.getEmail());
            if (dbUser == null)
            {
                return BaseResultUtil.createFailedResult("找不到" + userDto.getEmail() + "用户");
            }
            userDto.setUsername(dbUser.getUsername());
        }

        if (!userService.verifyPassword(userDto.getPassword(), dbUser.getPassword()))
        {
            return BaseResultUtil.createFailedResult("密码错误");
        }

        return BaseResultUtil.createOkResult(createToken(userDto));
    }

    @RequestMapping(path = "/getUserInfo", method = RequestMethod.GET)
    public BaseResult getUserInfo(final Principal user)
    {
        final UserDTO userDB = userService.getUserByUsername(user.getName()).toDto();
        return BaseResultUtil.createOkResult(userDB);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public BaseResult logout(final Principal user)
    {
        return BaseResultUtil.createOkResult();
    }

    private String createToken(final UserDTO userDto)
    {
        final String username = userDto.getUsername();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.sign(username, userDetails.getAuthorities());
    }
}
