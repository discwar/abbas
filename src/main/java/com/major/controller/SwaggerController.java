package com.major.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * swagger 接口
 */
@Controller
@RequestMapping("/swagger")
public class SwaggerController {

//    @RequiresPermissions("tool:swagger:view")
    @GetMapping()
    public String index() {
        return "redirect:" + "/swagger-ui.html";
    }

}
