package cn.edu.nju.dc.projectgeneratorserver.api.bean;

import lombok.Data;

/**
 * @author dc
 * @date 2021/12/13 0:45
 */
@Data
public class GenerateProjectResultDTO {

    private String targetZipPath;

    private String downloadFileName;
}
