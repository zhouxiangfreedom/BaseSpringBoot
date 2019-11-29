package com.zhou.basespringboot.dto.rabbitmq;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author zx
 * @date 2019/11/25 16:40
 */
@Data
public class LogDTO implements Serializable {
    private String id = UUID.randomUUID().toString();
    private String msg;

    public LogDTO(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public LogDTO(String msg) {
        this.msg = msg;
    }


    public LogDTO() {
    }
}
