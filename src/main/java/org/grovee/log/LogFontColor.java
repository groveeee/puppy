package org.grovee.log;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 日志字体颜色
 * @createTime 2022年10月27日 15:54:00
 */
public enum LogFontColor {
    RED(31),
    YELLOW(32),
    ORANGE(33),
    BLUE(34),
    PURPLE(35),
    GREEN(36),
    ;
    private int i;


    LogFontColor(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
