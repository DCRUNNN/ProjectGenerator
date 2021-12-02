package cn.edu.nju.dc.projectgeneratorserver.service;

import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * @author dc
 * @date 2021/11/30 23:38
 */
public interface ParamService {

    int insertPublicParam(ParamPO paramPO);

    int insertTemplateParam(ParamPO paramPO);

    List<ParamPO> listByTemplateID(int templateID);

    PageInfo<ParamPO> listAllPublicParams(int page, int size);
}
