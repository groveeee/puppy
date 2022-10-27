package org.grovee.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 日志处理类
 * @createTime 2022年10月27日 16:06:00
 */
public class Log {

    /**
     * 生成格式化日志
     * @param color  颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param style    样式代号：0:正常 1:加粗 3:斜体 4:下划线
     * @param content 要打印的内容
     */
    public static String getFormatLogString(String content, LogFontColor color, LogFontStyle style) {
        content = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+LogConstant.SEPARATOR+content;
        boolean hasType = style.getI() != 1 && style.getI() != 3 && style.getI() != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", color.getI(), content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", color.getI(), style.getI(), content);
        }
    }

    /**
     * 生成普通日志
     * @param content 日志内容
     * @return 格式化字符串
     */
    public static String getInfoString(String content) {
        content = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+LogConstant.SEPARATOR+content;
            return String.format("\033[%dm%s\033[0m", LogFontColor.GREEN.getI(), content);
    }

    /**
     * 打印普通日志
     * @param content 日志内容
     */
    public static void info(String content){
        System.out.println(getInfoString(content));
    }

    /**
     * 打印成功日志
     * @param content
     */
    public static void success(String content){
        System.out.println(getFormatLogString(content,LogFontColor.YELLOW,LogFontStyle.BOLD));
    }

    /**
     * 打印错误日志
     * @param content
     */
    public static void error(String content){
        System.out.println(getFormatLogString(content,LogFontColor.RED,LogFontStyle.BOLD));
    }

}
