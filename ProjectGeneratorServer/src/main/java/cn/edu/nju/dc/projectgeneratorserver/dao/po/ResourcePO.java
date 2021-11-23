package cn.edu.nju.dc.projectgeneratorserver.dao.po;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2019/1/12 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcePO
{

    private String resource;

    private List<OperationPO> handleList;
}
