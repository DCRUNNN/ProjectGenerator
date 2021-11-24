package cn.edu.nju.dc.projectgeneratorserver.dao;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplatePO;
import cn.edu.nju.dc.projectgeneratorserver.support.log.domain.LogEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

/**
 * @author dc
 * @date 2021/11/24 21:48
 */
@Mapper
@Repository
public interface TemplateDao {

    int insertTemplate(@Param("template") TemplatePO templatePO) throws DuplicateKeyException;

    TemplatePO getByID(@Param("templateID") int templateID);

    List<TemplatePO> listAll();
}
