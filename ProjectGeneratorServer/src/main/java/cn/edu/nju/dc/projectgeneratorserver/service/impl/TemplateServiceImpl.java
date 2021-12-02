package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.ParamDTO;
import cn.edu.nju.dc.projectgeneratorserver.api.bean.TemplateDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.ParamDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.TemplateDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplateContentPO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplatePO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplateParamRelationPO;
import cn.edu.nju.dc.projectgeneratorserver.service.TemplateService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.DBException;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import cn.edu.nju.dc.projectgeneratorserver.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dc
 * @date 2021/11/24 22:09
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private ParamDao paramDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTemplate(TemplatePO templatePO) throws DBException {
        try {
            // 设置模板时间
            templatePO.setCreateTime(DateUtil.getCurrentFormatTime());
            templatePO.setUpdateTime(DateUtil.getCurrentFormatTime());
            // 保存模板内容
            TemplateContentPO contentPO = new TemplateContentPO();
            contentPO.setContent(templatePO.getContent());
            templateDao.insertTemplateContent(contentPO);
            // 保存模板信息
            templatePO.setContentID(contentPO.getContentID());
            templateDao.insertTemplate(templatePO);
            // 保存模板参数
            if (CollectionUtils.isNotEmpty(templatePO.getParamList())) {
                templatePO.getParamList()
                    .stream()
                    .map(paramPO -> new TemplateParamRelationPO(templatePO.getId(), paramPO.getId()))
                    .forEach(relation -> paramDao.insertTemplateParamRelation(relation));
            }
            return templatePO.getId();
        }
        catch (DuplicateKeyException e) {
            throw new DBException("template name already exist", e);
        }
        catch (Throwable e) {
            throw new ServiceException(String.format("fail to insert template, name is [%s]", templatePO.getName()), e);
        }
    }

    @Override
    public TemplateDTO getById(int templateID) {
        List<ParamDTO> paramDTOList =
            paramDao.listByTemplateID(templateID).stream().map(ParamPO::toDTO).collect(Collectors.toList());
        TemplateDTO result = Optional.ofNullable(templateDao.getByID(templateID))
            .map(TemplatePO::toDTO)
            .orElseThrow(() -> new DBException(String.format("fail to get template info by id [%s]", templateID)));
        result.setParamList(paramDTOList);
        return result;
    }

    @Override
    public PageInfo<TemplateDTO> listAll(int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> templateDao.listAll());
    }
}
