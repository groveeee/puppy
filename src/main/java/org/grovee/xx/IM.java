package org.grovee.xx;

import org.grovee.di.Automatic;
import org.grovee.ioc.Component;

/**
 * @author grovee
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年10月26日 11:58:00
 */
@Component
public class IM {

    public int i = 7;
    @Automatic
    private ABC abc;
    @Automatic
    private CQW cqw;

    public ABC getAbc() {
        return abc;
    }

    public void setAbc(ABC abc) {
        this.abc = abc;
    }

    public CQW getCqw() {
        return cqw;
    }

    public void setCqw(CQW cqw) {
        this.cqw = cqw;
    }
}
