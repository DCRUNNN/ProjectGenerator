package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.ParamDTO;
import cn.edu.nju.dc.projectgeneratorserver.api.bean.TemplateDTO;
import cn.edu.nju.dc.projectgeneratorserver.constant.ParamType;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
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
                insertTemplateParams(templatePO.getId(), templatePO.getParamList());
            }
            return templatePO.getId();
        } catch (DuplicateKeyException e) {
            throw new DBException("template name already exist", e);
        } catch (Throwable e) {
            throw new ServiceException(String.format("fail to insert template, name is [%s]", templatePO.getName()), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTemplate(TemplatePO templatePO) throws DBException {
        try {
            // 设置更新时间
            templatePO.setUpdateTime(DateUtil.getCurrentFormatTime());
            // 1. 更新模板内容
            TemplateContentPO contentPO = new TemplateContentPO();
            contentPO.setContent(templatePO.getContent());
            contentPO.setContentID(templatePO.getContentID());
            templateDao.updateTemplateContent(contentPO);
            // 2. 更新模板信息
            templateDao.updateTemplate(templatePO);
            // 3. 更新模板对应的参数，先删后插
            // 3.1 删除私有模板参数
            paramDao.deletePrivateParamByTemplateID(templatePO.getId());
            // 3.2 删除模板和原有公参、私参的关联关系
            paramDao.deleteRelationByTemplateID(templatePO.getId());
            if (CollectionUtils.isNotEmpty(templatePO.getParamList())) {
                // 3.3 添加模板和参数的关联关系
                insertTemplateParams(templatePO.getId(), templatePO.getParamList());
            }
            return templatePO.getId();
        } catch (DuplicateKeyException e) {
            throw new DBException("template name already exist", e);
        } catch (Throwable e) {
            throw new ServiceException(String.format("fail to update template, name is [%s]", templatePO.getName()), e);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByTemplateID(int templateID) throws DBException {
        int count = 0;
        try {
            // 删除模板
            count = templateDao.deleteByTemplateID(templateID);
            // 删除私有模板参数
            paramDao.deletePrivateParamByTemplateID(templateID);
            // 删除模板和原有公参、私参的关联关系
            paramDao.deleteRelationByTemplateID(templateID);
        } catch (Throwable e) {
            throw new ServiceException(String.format("fail to delete template, id is [%s]", templateID), e);
        }
        return count;
    }

    private void insertTemplateParams(int templateID, List<ParamPO> paramPOList) {
        if (CollectionUtils.isEmpty(paramPOList)) {
            return;
        }
        Map<Integer, List<ParamPO>> typeToParamMap = paramPOList
                .stream()
                .collect(Collectors.groupingBy(ParamPO::getType));
        // 公参，添加模板与公参的绑定关系即可
        typeToParamMap.getOrDefault(ParamType.PublicParam, Lists.newArrayList())
                .stream()
                .map(paramPO -> new TemplateParamRelationPO(templateID, paramPO.getTemplateID()))
                .forEach(relationPO -> paramDao.insertTemplateParamRelation(relationPO));
        // 私参，先添加参数，然后再添加模板与私参的关联关系
        typeToParamMap.getOrDefault(ParamType.PrivateParam, Lists.newArrayList())
                .stream()
                .peek(paramPO -> paramPO.setCreateTime(DateUtil.getCurrentFormatTime()))
                .peek(paramPO -> paramPO.setUpdateTime(DateUtil.getCurrentFormatTime()))
                .map(paramPO -> insertParamAndReturnID(paramPO))
                .map(privateParamID -> new TemplateParamRelationPO(templateID, privateParamID))
                .forEach(relationPO -> paramDao.insertTemplateParamRelation(relationPO));
    }

    private int insertParamAndReturnID(ParamPO paramPO) {
        paramDao.insertParam(paramPO);
        return paramPO.getId(); // 返回主键 ID
    }
}
