package cn.edu.nju.dc.projectgeneratorserver.controller;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.ProjectDTO;
import cn.edu.nju.dc.projectgeneratorserver.service.GeneratorService;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResult;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dc
 * @date 2021/12/4 17:35
 */
@RestController
@RequestMapping(path = "/api/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @PreAuthorize("hasAuthority('template:list')")
    @RequestMapping(path = "/generateProject", method = RequestMethod.POST)
    public BaseResult generateProject(@RequestBody ProjectDTO projectDTO) {
        return BaseResultUtil.createOkResult(generatorService.generateProject(projectDTO));
    }

    @PreAuthorize("hasAuthority('template:list')")
    @RequestMapping(path = "/getFormSchema", method = RequestMethod.GET)
    public BaseResult getFormSchema(@RequestParam int templateID) {
        return BaseResultUtil.createOkResult(generatorService.generateFormSchema(templateID));
    }
}
