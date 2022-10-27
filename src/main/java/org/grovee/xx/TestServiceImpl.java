package org.grovee.xx;

import org.grovee.ioc.Service;

/**
 * @author grovee
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年10月26日 11:30:00
 */
@Service
public class TestServiceImpl implements TestService{
    @Override
    public String doSome() {
        return "success";
    }
}
