package cn.edu.nju.dc.projectgeneratorserver.controller;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.TemplateDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplatePO;
import cn.edu.nju.dc.projectgeneratorserver.service.TemplateService;
import cn.edu.nju.dc.projectgeneratorserver.support.log.annotation.Log;
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
 * @date 2021/11/24 22:16
 */
@RestController
@RequestMapping(path = "/api/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PreAuthorize("hasAuthority('template:insert')")
    @RequestMapping(path = "/insert", method = RequestMethod.POST)
    @Log(description = "新增模板")
    public BaseResult insertTemplate(@RequestBody TemplateDTO templateDTO) {
        return BaseResultUtil.createOkResult(templateService.insertTemplate(TemplatePO.valueOf(templateDTO)));
    }

    @PreAuthorize("hasAuthority('template:list')")
    @RequestMapping(path = "/getByTemplateID", method = RequestMethod.GET)
    public BaseResult getByTemplateID(@RequestParam int templateID) {
        return BaseResultUtil.createOkResult(templateService.getById(templateID));
    }

    @PreAuthorize("hasAuthority('template:list')")
    @RequestMapping(path = "/listAllTemplates", method = RequestMethod.GET)
    public BaseResult listAllTemplates(@RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "0") Integer size) {
        return BaseResultUtil.createOkResult(templateService.listAll(page, size));
    }

}
