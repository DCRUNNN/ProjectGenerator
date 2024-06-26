package cn.edu.nju.dc.projectgeneratorserver.dao;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplateParamRelationPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

/**
 * @author dc
 * @date 2021/11/30 23:14
 */
@Mapper
@Repository
public interface ParamDao {

    int insertParam(@Param("param") ParamPO paramPO) throws DuplicateKeyException;

    int updateParamByIDAndType(@Param("param") ParamPO paramPO);

    int insertTemplateParamRelation(@Param("relation") TemplateParamRelationPO relationPO) throws DuplicateKeyException;

    int deleteRelationByTemplateID(@Param("templateID") int templateID);

    int deleteByParamID(@Param("paramID") int paramID);

    int deletePrivateParamByTemplateID(@Param("templateID") int templateID);

    boolean isParamInUse(@Param("paramID") int paramID);

    List<ParamPO> listByTemplateID(@Param("templateID") int templateID);

    List<ParamPO> listAllPublicParam();
}
