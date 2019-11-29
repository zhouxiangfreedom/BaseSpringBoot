package com.zhou.basespringboot.common.constants;

/**
 * @author zhouxiang
 * @date 2019/1/3 17:14
 */
public enum CodeEnum {
    OK(200, "success"),
    FAIL(600001, "请求失败"),
    PARAM_ERROR(600002, "请求失败，参数错误"),
    ;


    private int code;
    private String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int Code() {
        return code;
    }

    public String Msg() {
        return msg;
    }
}
