package org.grovee.xx;

import org.grovee.ioc.di.Automatic;
import org.grovee.ioc.Component;

/**
 * @author grovee
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年10月26日 11:58:00
 */
@Component
public class CQW {
    public int i;

    public CQW() {
        this.i = 5;
    }
    @Automatic
    private ABC abc;

    @Automatic
    private IM im;

    public IM getIm() {
        return im;
    }

    public void setIm(IM im) {
        this.im = im;
    }

    public ABC getAbc() {
        return abc;
    }

    public void setAbc(ABC abc) {
        this.abc = abc;
    }

}
