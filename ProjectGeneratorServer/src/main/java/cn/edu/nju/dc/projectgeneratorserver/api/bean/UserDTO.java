package cn.edu.nju.dc.projectgeneratorserver.api.bean;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2019/1/8 21:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{

    private Integer userId;

    private String username;

    private String email;

    private String password;

    private Integer roleId;

    private String roleName;

    private List<String> permissionCodeList;
}
