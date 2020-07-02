package com.example.demo3.test.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unittec.customize.iot.mqtt.api.client.RsocketClientSession;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/30 9:54
 */
@Component
public class TtsfClient {
    @Autowired
    private RsocketClientSession clientSession;

    /**
     * 天拓四方topic
     */
    public static String TOPIC_BJTTSF_BASE_CODE = "box/";

    @PostConstruct
    public void client() {
        if (clientSession == null) {
            return;
        }
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "login/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "data/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "event/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "controlack/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "configack/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "eventcache/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "datacache/*");
        clientSession.sub(TOPIC_BJTTSF_BASE_CODE + "heartbeat/*");
    }
}
