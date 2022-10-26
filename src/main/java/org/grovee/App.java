package org.grovee;

import org.grovee.context.ApplicationContext;
import org.grovee.scan.ComponentScanAndInit;
import org.grovee.xx.ABC;
import org.grovee.xx.CQW;
import org.grovee.xx.IM;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        new ComponentScanAndInit();
        Object object = ApplicationContext.getObject(ABC.class);
        System.out.println(object);
        Object abc = ApplicationContext.getObject("abc");
        System.out.println(abc);
        System.out.println(ApplicationContext.getObject(CQW.class));
        System.out.println(ApplicationContext.getObject(IM.class));

        //ABC abc = new ABC();
        //CQW cqw = new CQW();
        //abc.setCqw(cqw);
        //cqw.setAbc(abc);
        //System.out.println("aaaa");
        //System.out.println(abc.getCqw().i);
        //System.out.println(cqw.getAbc().getCqw().i);
        //System.out.println(abc);
        //System.out.println(cqw);
    }
}
