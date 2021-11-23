package cn.edu.nju.dc.projectgeneratorserver.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2019/1/12 17:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationPO {
    private int operationId;

    private String operation;
}
