package cn.edu.nju.dc.projectgeneratorserver.dao.po;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.UserDTO;
import cn.edu.nju.dc.projectgeneratorserver.utils.ObjectUtil;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2021/11/23 21:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {

    private Integer userId;

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 3, message = "用户名长度不能小于3")
    private String username;

    @NotEmpty(message = "邮箱不能为空")
    @Email
    private String email;

    @JSONField(serialize = false)
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能小于6")
    private String password;

    private Integer roleId;

    //来自联表查询
    private String roleName;

    // 用户的角色对应的权限code
    private List<String> permissionCodeList;

    public static UserPO valueOf(UserDTO userDto) {
        return ObjectUtil.deepCloneByJson(userDto, UserPO.class);
    }

    public UserDTO toDto() {
        return ObjectUtil.deepCloneByJson(this, UserDTO.class);
    }
}

