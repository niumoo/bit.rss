package group.debug.rss.exception;

import lombok.Getter;

/**
 * @author https://www.wdbyte.com
 * @date 2023/11/22
 */
@Getter
public enum ExcptEnum {
    SYSTEM_ERROR(-1, "system error"),
    JWT_TOKEN_EXPIRED(1002, "jwt token expired"),
    AUTHENTICATION_FAILED(1003, "authentication failed"),
    USER_NOT_FOUND(1005, "user not found"),
    PARAM_IS_EMPTY(1010, "param is empty"),
    ;
    private Integer code;
    private String msg;

    ExcptEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
