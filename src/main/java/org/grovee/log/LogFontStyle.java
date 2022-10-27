package org.grovee.log;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 日志字体风格
 * @createTime 2022年10月27日 15:54:00
 */
public enum LogFontStyle {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 加粗
     */
    BOLD(1),
    /**
     * 斜体
     */
    ITALIC(3),
    /**
     * 下划线
     */
    UNDERLINE(4);

    private int i;

    LogFontStyle(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
