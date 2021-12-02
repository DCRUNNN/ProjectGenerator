package cn.edu.nju.dc.projectgeneratorserver.api.bean;

import java.util.List;
import lombok.Data;

/**
 * @author dc
 * @date 2021/11/24 22:07
 */
@Data
public class TemplateDTO {

    private int id;

    private String name;

    private String description;

    private String author;

    private int contentID;

    private String content;

    private List<ParamDTO> paramList;

    private String createTime;

    private String updateTime;
}
