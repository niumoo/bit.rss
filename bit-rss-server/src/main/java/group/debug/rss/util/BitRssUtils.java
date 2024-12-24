package group.debug.rss.util;

import group.debug.rss.exception.CommonExcpt;
import group.debug.rss.exception.ExcptEnum;

/**
 * @author niulang
 * @date 2024/12/15
 */
public class BitRssUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void check(boolean condition) {
        if (!condition) {
            throw new CommonExcpt(ExcptEnum.PARAM_IS_EMPTY);
        }
    }
}
