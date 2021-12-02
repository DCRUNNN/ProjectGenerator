package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.dao.ParamDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplateParamRelationPO;
import cn.edu.nju.dc.projectgeneratorserver.service.ParamService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dc
 * @date 2021/11/30 23:57
 */
@Service
@Slf4j
public class ParamServiceImpl implements ParamService {

    @Autowired
    private ParamDao paramDao;

    @Override
    public int insertPublicParam(ParamPO paramPO) {
        log.info("paramPO=[{}]", paramPO);
        return paramDao.insertParam(paramPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTemplateParam(ParamPO paramPO) {
        int templateID = paramPO.getTemplateID();
        if (templateID <= 0) {
            throw new ServiceException(String.format("[insertTemplateParam] invalid templateID [%d]", templateID));
        }
        paramDao.insertParam(paramPO);
        TemplateParamRelationPO relationPO = new TemplateParamRelationPO();
        relationPO.setTemplateID(templateID);
        relationPO.setParamID(paramPO.getId());
        return paramDao.insertTemplateParamRelation(relationPO);
    }

    @Override
    public List<ParamPO> listByTemplateID(int templateID) {
        return paramDao.listByTemplateID(templateID);
    }

    @Override
    public PageInfo<ParamPO> listAllPublicParams(int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> paramDao.listAllPublicParam());
    }
}
