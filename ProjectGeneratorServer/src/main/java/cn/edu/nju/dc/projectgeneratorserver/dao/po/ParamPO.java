package cn.edu.nju.dc.projectgeneratorserver.dao.po;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.ParamDTO;
import cn.edu.nju.dc.projectgeneratorserver.utils.ObjectUtil;
import lombok.Data;

/**
 * @author dc
 * @date 2021/11/30 23:10
 */
@Data
public class ParamPO {

    private int id;

    private int templateID;

    private String name;

    private String description;

    // 1：公参；2：私参
    private int type;

    private String createTime;

    private String updateTime;

    public static ParamPO valueOf(ParamDTO paramDTO) {
        return ObjectUtil.deepCloneByJson(paramDTO, ParamPO.class);
    }

    public ParamDTO toDTO() {
        return ObjectUtil.deepCloneByJson(this, ParamDTO.class);
    }
}
