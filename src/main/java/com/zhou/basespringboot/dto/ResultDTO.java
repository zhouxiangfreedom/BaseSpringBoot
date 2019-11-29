package com.zhou.basespringboot.dto;

import com.zhou.basespringboot.common.constants.CodeEnum;
import lombok.Data;

import java.util.Collections;

import static com.zhou.basespringboot.common.constants.CodeEnum.FAIL;
import static com.zhou.basespringboot.common.constants.CodeEnum.OK;

/**
 * @author ZhouXiang
 * @date 2018/8/7 17:12
 */
@Data
public class ResultDTO {
    private Integer code;
    private String msg;
    private Object data;

    private ResultDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = new Object();
    }

    private ResultDTO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultDTO Ok(T data) {
        return new ResultDTO(OK.Code(), OK.Msg(), data);
    }

    public static <T> ResultDTO Fail(T data) {
        return new ResultDTO(FAIL.Code(), FAIL.Msg(), data);
    }

    public static ResultDTO Fail() {
        return new ResultDTO(CodeEnum.FAIL.Code(), CodeEnum.FAIL.Msg(), Collections.emptyMap());
    }

    public static ResultDTO Ok() {
        return new ResultDTO(OK.Code(), OK.Msg(), Collections.emptyMap());
    }

    public static ResultDTO build(CodeEnum codeEnum) {
        return new ResultDTO(codeEnum.Code(), codeEnum.Msg(), Collections.emptyMap());
    }

    public static <T> ResultDTO build(CodeEnum codeEnum, T data) {
        return new ResultDTO(codeEnum.Code(), codeEnum.Msg(), data);
    }
}
