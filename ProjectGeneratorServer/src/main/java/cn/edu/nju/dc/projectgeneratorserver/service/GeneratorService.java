package cn.edu.nju.dc.projectgeneratorserver.service;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.GenerateProjectResultDTO;
import cn.edu.nju.dc.projectgeneratorserver.api.bean.ProjectDTO;
import com.alibaba.fastjson.JSONObject;

/**
 * @author dc
 * @date 2021/12/4 17:36
 */
public interface GeneratorService {

    GenerateProjectResultDTO generateProject(ProjectDTO projectDTO);

    JSONObject generateFormSchema(int templateID);
}
