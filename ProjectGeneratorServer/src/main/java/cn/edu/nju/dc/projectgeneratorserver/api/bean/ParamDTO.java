package cn.edu.nju.dc.projectgeneratorserver.api.bean;

import lombok.Data;

/**
 * @author dc
 * @date 2021/12/1 0:02
 */
@Data
public class ParamDTO {

    private int id;

    private int templateID;

    private String nameEN;

    private String nameCN;

    private String description;

    // 参数对应的类型，如字符串、整型、数组等
    private String fieldType;

    // 1：公参；2：私参
    private int type;

    private String createTime;

    private String updateTime;
}
