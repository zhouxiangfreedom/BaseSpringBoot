package com.zhou.basespringboot.dto.rabbitmq;

import lombok.Data;

/**
 * @author zx
 * @date 2019/11/25 16:40
 */
@Data
public class MailDTO {
    private String receiver = "zhouxiang@peilian.com";
    private String content;

    public MailDTO(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    public MailDTO(String content) {
        this.content = content;
    }

    public MailDTO() {
    }
}
