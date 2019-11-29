package com.zhou.basespringboot.dto.rabbitmq;

import lombok.Data;

import java.util.UUID;

/**
 * @author zx
 * @date 2019/11/25 16:40
 */
@Data
public class OrderDTO {
    private String id = UUID.randomUUID().toString();
    private String orderNo;

    public OrderDTO(String id, String orderNo) {
        this.id = id;
        this.orderNo = orderNo;
    }

    public OrderDTO(String orderNo) {
        this.orderNo = orderNo;
    }

    public OrderDTO() {
    }
}
