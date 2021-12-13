package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.dao.ParamDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import cn.edu.nju.dc.projectgeneratorserver.service.ParamService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import cn.edu.nju.dc.projectgeneratorserver.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        paramPO.setCreateTime(DateUtil.getCurrentFormatTime());
        paramPO.setUpdateTime(DateUtil.getCurrentFormatTime());
        return paramDao.insertParam(paramPO);
    }

    @Override
    public int updatePublicParam(ParamPO paramPO) {
        // TODO 发送消息，更新模板中的引用
        paramPO.setUpdateTime(DateUtil.getCurrentFormatTime());
        return paramDao.updateParam(paramPO);
    }

    @Override
    public int deleteByParamID(int paramID) {
        // 在使用的参数不能删除
        if (paramDao.isParamInUse(paramID)) {
            throw new ServiceException("不允许删除正在使用的参数");
        }
        return paramDao.deleteByParamID(paramID);
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
