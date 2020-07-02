package com.example.demo3.test.client;

import java.nio.charset.Charset;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo3.test.message.handler.IBaseHanlder;
import com.example.demo3.test.message.handler.MesageServiceHandlerFactory;
import com.unittec.customize.iot.mqtt.container.MessageAcceptor;

import cn.hutool.core.util.StrUtil;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/30 10:02
 */
@Component
public class ClientMessageacceptor implements MessageAcceptor {

    /**
     *
     * @param topic
     * @param msg
     */
    @Override
    public void accept(String topic, byte[] msg) {
        try {
            if (StringUtils.isEmpty(topic) || msg == null) {
                System.out.println("参数为空!");
                return;
            }
            String str = StrUtil.str(msg, Charset.defaultCharset());
            System.out.println(topic + ":" + str);
            //
            IBaseHanlder hanlder = MesageServiceHandlerFactory.hanlder(topic);
            hanlder.processor(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
