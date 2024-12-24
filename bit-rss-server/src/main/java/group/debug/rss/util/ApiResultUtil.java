package group.debug.rss.util;

import com.alibaba.fastjson2.JSON;

import group.debug.rss.exception.ExcptEnum;
import group.debug.rss.model.ApiResult;

/**
 * @author https://www.wdbyte.com
 * @date 2023/11/22
 */
public class ApiResultUtil {

    private static Integer SUCCESS_CODE = 200;
    private static String SUCCESS_MESSAGE = "success";
    private static Integer ERROR_CODE = -1;
    private static String ERROR_MESSAGE = "error";

    public static String success() {
        return JSON.toJSONString(new ApiResult(SUCCESS_CODE, SUCCESS_MESSAGE, new Object()));
    }

    public static String success(Object data) {
        return JSON.toJSONString(new ApiResult(SUCCESS_CODE, SUCCESS_MESSAGE, data));
    }

    public static String error(Object data) {
        return JSON.toJSONString(new ApiResult(ERROR_CODE, ERROR_MESSAGE, data));
    }

    public static String error(ExcptEnum excptEnum) {
        return JSON.toJSONString(new ApiResult(excptEnum.getCode(), excptEnum.getMsg(), null));
    }
}
