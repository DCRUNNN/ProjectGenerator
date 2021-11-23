package cn.edu.nju.dc.projectgeneratorserver.api;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.UserDTO;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResult;

/**
 * @author dc
 * @date 2019/1/8 21:14
 */
public interface UserMgr
{

    BaseResult login(UserDTO userDto);

    BaseResult insertUser(UserDTO userDto);

    BaseResult getUserById(int userId);

    BaseResult getUserByName(String username);

    BaseResult updateUser(UserDTO userDto);

    BaseResult deleteUserById(int userId);

    BaseResult deleteUserByName(String username);

    BaseResult getAllUsers();

}
