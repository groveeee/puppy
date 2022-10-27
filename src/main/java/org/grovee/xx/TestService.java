package org.grovee.xx;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 测试的Service接口
 * @createTime 2022年10月27日 12:08:00
 */
public interface TestService {
    /**
     * 控制器调用的方法
     * @return success
     */
    String doSome(Integer id,String name);
}
