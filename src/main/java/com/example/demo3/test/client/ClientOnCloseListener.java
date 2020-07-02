package com.example.demo3.test.client;

import org.springframework.stereotype.Component;

import com.unittec.customize.iot.mqtt.container.OnCloseListener;

/**
 *
 * 客户端 关闭监听器
 *
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/30 10:12
 */
@Component
public class ClientOnCloseListener implements OnCloseListener {
    @Override
    public void start() {
        System.out.println("正在运行 关闭监听器   start......");
        System.out.println("ClientOnCloseListener    start ");
    }
}
