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

    /**
     * 测试@RequestParam注解
     * @param id ID
     * @param name 姓名
     * @return success
     */
    @RequestMapping(value = "/xxx",method = RequestMethod.POST)
    public String test(@RequestParam(value = "id") Integer id,@RequestParam("name") String name){
        return service.doSome(id,name);
    }

    /**
     * 测试@PathVariable注解 ResultFUL风格请求
     * @param fid FID
     * @return success
     */
    @RequestMapping(value = "/{fid}")
    public String testPathVariable(@PathVariable("fid") Integer fid){
        return service.doSome(fid, "亚索");
    }
    @RequestMapping(value = "/{fid}/{xid}")
    public String testPathVariables(@PathVariable("fid") Integer fid,@PathVariable("xid") Integer xid){
        System.out.println("xid:"+xid);
        return service.doSome(fid, "瞎子");
    }

}
