package org.grovee.web.controller;

import org.grovee.ioc.di.Automatic;
import org.grovee.mc.anno.Controller;
import org.grovee.mc.anno.PathVariable;
import org.grovee.mc.anno.RequestMapping;
import org.grovee.mc.anno.RequestParam;
import org.grovee.mc.constant.RequestMethod;
import org.grovee.xx.TestService;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 测试的控制器
 * @createTime 2022年10月26日 23:31:00
 */

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Automatic
    private TestService service;

    @RequestMapping(value = "/xxx",method = RequestMethod.POST)
    public String test(@RequestParam(value = "id") Integer id,@RequestParam("name") String name){
        return service.doSome(id,name);
    }

    @RequestMapping(value = "/{id}")
    public String testPathVariable(@PathVariable("id") Integer id){
        return service.doSome(id, "亚索");
    }

}
