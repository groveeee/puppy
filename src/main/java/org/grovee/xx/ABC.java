package org.grovee.xx;

import org.grovee.di.Automatic;
import org.grovee.init.Component;

/**
 * @author grovee
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年10月26日 10:57:00
 */
@Component(value = "abc")
public class ABC {
    public int i;

    public ABC() {
        this.i = 3;
    }

    @Automatic
    private CQW cqw;
    @Automatic
    private IM im;

    public CQW getCqw() {
        return cqw;
    }

    public void setCqw(CQW cqw) {
        this.cqw = cqw;
    }

    public IM getIm() {
        return im;
    }

    public void setIm(IM im) {
        this.im = im;
    }
}
