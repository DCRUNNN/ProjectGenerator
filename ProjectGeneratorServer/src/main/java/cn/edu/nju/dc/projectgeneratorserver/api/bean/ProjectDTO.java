package cn.edu.nju.dc.projectgeneratorserver.api.bean;

import java.util.List;
import lombok.Data;

/**
 * @author dc
 * @date 2021/12/12 16:02
 */
@Data
public class ProjectDTO {

    private int templateID;

    private List<ParamValueDTO> paramValueDTOList;

    @Data
    public static class ParamValueDTO {

        private String name;

        private Object value;
    }
}
