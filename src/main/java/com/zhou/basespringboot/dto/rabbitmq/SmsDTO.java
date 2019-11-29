package com.zhou.basespringboot.dto.rabbitmq;

import lombok.Data;

/**
 * @author zx
 * @date 2019/11/25 16:40
 */
@Data
public class SmsDTO {
    private String phone = "15615615615";
    private String msg;

    public SmsDTO(String phone, String msg) {
        this.phone = phone;
        this.msg = msg;
    }

    public SmsDTO(String msg) {
        this.msg = msg;
    }

    public SmsDTO() {
    }
}
