package cn.edu.nju.dc.projectgeneratorserver.dao;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.ResourcePO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author dc
 * @date 2019/1/12 17:16
 */
@Mapper
@Repository
public interface PermissionDao {

    /**
     * 找到所有权限可控资源
     *
     * @return 资源列表
     */
    List<ResourcePO> getResourceWithOperation();

    /**
     * 找到所有权限可控资源
     *
     * @param roleId 角色id
     * @return 资源列表
     */
    List<ResourcePO> getRoleWithResourceByRoleId(@Param("roleId") int roleId);

    /**
     * 获取所有权限代码
     *
     * @return 代码列表
     */
    List<String> getAllAuthorityCode();
}
