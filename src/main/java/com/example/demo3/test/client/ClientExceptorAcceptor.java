package com.example.demo3.test.client;

import org.springframework.stereotype.Component;

import com.unittec.customize.iot.mqtt.container.ExceptorAcceptor;

/**
 * 客户端异常处理
 *
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/30 10:11
 */
@Component
public class ClientExceptorAcceptor implements ExceptorAcceptor {
    @Override
    public void accept(Throwable throwable) {
        System.out.println("客户端 异常处理>>>>>>>>>");
        System.out.println(throwable.getMessage());
    }
}
