package cn.edu.nju.dc.projectgeneratorserver.controller;

import cn.edu.nju.dc.projectgeneratorserver.service.LogService;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResult;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dc
 * @date 2019/10/6 16:07
 */
@RestController
@RequestMapping(path = "/api/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PreAuthorize("hasAuthority('log:list')")
    @RequestMapping(path = "/listLogsByType", method = RequestMethod.GET)
    public BaseResult listLogsByType(@RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "0") Integer size, @RequestParam String logType) {
        return BaseResultUtil.createOkResult(logService.listAllByType(page, size, logType));
    }

    @PreAuthorize("hasAuthority('log:list')")
    @RequestMapping(path = "/listByTimeAndType", method = RequestMethod.GET)
    public BaseResult listByTimeAndType(@RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "0") Integer size, @RequestParam String logType, @RequestParam String startTime,
        @RequestParam String endTime) {
        return BaseResultUtil.createOkResult(logService.listByTimeAndType(page, size, logType, startTime, endTime));
    }

    @PreAuthorize("hasAuthority('log:delete')")
    @RequestMapping(path = "/deleteLogByType", method = RequestMethod.GET)
    public BaseResult deleteLogByType(@RequestParam String logType) {
        return BaseResultUtil.createOkResult(logService.deleteByType(logType));
    }

    @PreAuthorize("hasAuthority('log:delete')")
    @RequestMapping(path = "/deleteLogById", method = RequestMethod.GET)
    public BaseResult deleteLogById(@RequestParam Integer logId) {
        return BaseResultUtil.createOkResult(logService.deleteById(logId));
    }
}
