package cn.edu.nju.dc.projectgeneratorserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author dc
 * @date 2018/12/29 23:44
 */
@Controller
@ApiIgnore
public class SwaggerController
{

    @RequestMapping("/api")
    public String redirect()
    {
        return "redirect:swagger-ui.html";
    }
}
