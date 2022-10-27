package org.grovee.web.controller;

import org.grovee.mc.anno.Controller;
import org.grovee.mc.anno.RequestMapping;
import org.grovee.mc.enums.RequestMethod;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 测试的控制器
 * @createTime 2022年10月26日 23:31:00
 */

@Controller
@RequestMapping(value = "/test")
public class TestController {
    @RequestMapping(value = "/xxx",method = RequestMethod.GET)
    public String test(){
        return "success";
    }
}
