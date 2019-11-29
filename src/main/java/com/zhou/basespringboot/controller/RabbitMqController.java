package com.zhou.basespringboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhou.basespringboot.dto.ResultDTO;
import com.zhou.basespringboot.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rabbitmq exercise
 *
 * @author zx
 * @date 2019/5/8 10:42
 */
@RestController
@RequestMapping("/amqp")
public class RabbitMqController {
    @Autowired
    private RabbitMqService rabbitMqService;

    @RequestMapping("/sendLog")
    public ResultDTO sendLog() throws JsonProcessingException {
        rabbitMqService.sendLog();
        return ResultDTO.Ok();
    }


    @RequestMapping("/sendMail")
    public ResultDTO sendMail() throws JsonProcessingException {
        rabbitMqService.sendMail();
        return ResultDTO.Ok();
    }

    @RequestMapping("/sendOrder")
    public ResultDTO sendOrder() throws JsonProcessingException {
        rabbitMqService.sendOrder();
        return ResultDTO.Ok();
    }

    @RequestMapping("/sendSms")
    public ResultDTO sendSms() throws JsonProcessingException {
        rabbitMqService.sendSms();
        return ResultDTO.Ok();
    }
}
