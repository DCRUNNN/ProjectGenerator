package cn.edu.nju.dc.projectgeneratorserver.controller;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.ParamDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import cn.edu.nju.dc.projectgeneratorserver.service.ParamService;
import cn.edu.nju.dc.projectgeneratorserver.support.log.annotation.Log;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResult;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dc
 * @date 2021/12/1 0:02
 */
@RestController
@RequestMapping("/api/param")
@Slf4j
public class ParamController {

    @Autowired
    private ParamService paramService;

    @PreAuthorize("hasAuthority('param:insert')")
    @RequestMapping(path = "/insertPublicParam", method = RequestMethod.POST)
    @Log(description = "新增公参")
    public BaseResult insertPublicParam(@RequestBody ParamDTO paramDTO) {
        return BaseResultUtil.createOkResult(paramService.insertPublicParam(ParamPO.valueOf(paramDTO)));
    }

    @PreAuthorize("hasAuthority('param:update')")
    @RequestMapping(path = "/updatePublicParam", method = RequestMethod.POST)
    @Log(description = "更新公参")
    public BaseResult updatePublicParam(@RequestBody ParamDTO paramDTO) {
        return BaseResultUtil.createOkResult(paramService.updatePublicParam(ParamPO.valueOf(paramDTO)));
    }

    @PreAuthorize("hasAuthority('param:delete')")
    @RequestMapping(path = "/deletePublicParam", method = RequestMethod.GET)
    @Log(description = "删除公参")
    public BaseResult deletePublicParam(@RequestParam int paramID) {
        return BaseResultUtil.createOkResult(paramService.deleteByParamID(paramID));
    }

    @PreAuthorize("hasAuthority('param:list')")
    @RequestMapping(path = "/listByTemplateID", method = RequestMethod.GET)
    public BaseResult listByTemplateID(@RequestParam int templateID) {
        return BaseResultUtil.createOkResult(paramService.listByTemplateID(templateID));
    }

    @PreAuthorize("hasAuthority('param:list')")
    @RequestMapping(path = "/listAllPublicParams", method = RequestMethod.GET)
    public BaseResult listAllPublicParams(@RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "0") Integer size) {
        return BaseResultUtil.createOkResult(paramService.listAllPublicParams(page, size));
    }

}
