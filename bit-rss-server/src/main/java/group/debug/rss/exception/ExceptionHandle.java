package group.debug.rss.exception;

import group.debug.rss.util.ApiResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 统一的异常处理
 *
 * @Author niujinpeng
 * @Date 2019/1/7 14:26
 */

@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        if (e instanceof CommonExcpt) {
            CommonExcpt exception = (CommonExcpt)e;
            String msg = exception.getMsg();
            return ApiResultUtil.error(msg);
        }
        log.error("handleException,msg" + e.getMessage(), e);
        if (e instanceof IllegalArgumentException) {
            return ApiResultUtil.error(ExcptEnum.PARAM_IS_EMPTY);
        }
        return ApiResultUtil.error("未知错误");
    }
}