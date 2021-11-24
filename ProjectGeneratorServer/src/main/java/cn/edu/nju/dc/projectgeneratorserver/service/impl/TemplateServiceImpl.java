package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.TemplateDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.TemplateDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplatePO;
import cn.edu.nju.dc.projectgeneratorserver.service.TemplateService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.DBException;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author dc
 * @date 2021/11/24 22:09
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Override
    public int InsertTemplate(TemplatePO templatePO) throws DBException {
        try {
            templateDao.insertTemplate(templatePO);
            return templatePO.getId();
        }
        catch (DuplicateKeyException e) {
            throw new DBException("template id already exist", e);
        }
        catch (Throwable e) {
            throw new ServiceException(String.format("fail to insert template, name is [%s]", templatePO.getName()), e);
        }
    }

    @Override
    public TemplateDTO getById(int templateID) {
        return Optional.ofNullable(templateDao.getByID(templateID))
            .map(TemplatePO::toDTO)
            .orElseThrow(() -> new DBException(String.format("fail to get template info by id [%s]", templateID)));
    }

    @Override
    public PageInfo<TemplateDTO> listAll(int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> templateDao.listAll());
    }
}
