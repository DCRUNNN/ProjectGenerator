package cn.edu.nju.dc.projectgeneratorserver.dao.po;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.ParamDTO;
import cn.edu.nju.dc.projectgeneratorserver.api.bean.TemplateDTO;
import cn.edu.nju.dc.projectgeneratorserver.utils.ObjectUtil;
import java.util.List;
import lombok.Data;

/**
 * @author dc
 * @date 2021/11/24 21:45
 */
@Data
public class TemplatePO {

    private int id;

    // TODO add unique index
    private String name;

    private String description;

    private String author;

    private int contentID;

    private String content;

    private List<ParamPO> paramList;

    private String createTime;

    private String updateTime;

    public static TemplatePO valueOf(TemplateDTO templateDTO) {
        return ObjectUtil.deepCloneByJson(templateDTO, TemplatePO.class);
    }

    public TemplateDTO toDTO() {
        return ObjectUtil.deepCloneByJson(this, TemplateDTO.class);
    }
}
