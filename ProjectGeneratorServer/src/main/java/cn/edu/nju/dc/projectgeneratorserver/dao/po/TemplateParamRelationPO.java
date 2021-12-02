package cn.edu.nju.dc.projectgeneratorserver.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2021/11/30 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateParamRelationPO {

    private int templateID;

    private int paramID;
}
