package cn.edu.nju.dc.projectgeneratorserver.service;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.TemplateDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplatePO;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.DBException;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author dc
 * @date 2021/11/24 22:06
 */
public interface TemplateService {

    int insertTemplate(TemplatePO templatePO) throws DBException;

    int updateTemplate(TemplatePO templatePO) throws DBException;

    int deleteByTemplateID(int templateID) throws DBException;

    TemplateDTO getById(int templateID);

    PageInfo<TemplateDTO> listAll(int page, int size);
}
