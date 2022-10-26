package org.grovee;

import org.grovee.ioc.init.ComponentScanAndInitAndStart;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ComponentScanAndInitAndStart.start(8888);
    }
}
