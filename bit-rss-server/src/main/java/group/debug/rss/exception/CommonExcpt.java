package group.debug.rss.exception;

import lombok.Getter;

/**
 * @author https://www.wdbyte.com
 * @date 2023/11/22
 */
@Getter
public class CommonExcpt extends RuntimeException {
    private Integer code;
    private String msg;

    public CommonExcpt(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonExcpt(ExcptEnum excptEnum) {
        super(String.format("code:%d,msg:%s", excptEnum.getCode(), excptEnum.getMsg()));
        this.code = excptEnum.getCode();
        this.msg = excptEnum.getMsg();
    }

    public static CommonExcpt create(ExcptEnum excptEnum) {
        return new CommonExcpt(excptEnum);
    }
}
