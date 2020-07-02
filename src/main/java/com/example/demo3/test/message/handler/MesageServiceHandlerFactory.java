package com.example.demo3.test.message.handler;

import com.example.demo3.SpringContext;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 17:32
 */
public class MesageServiceHandlerFactory {

    /**
     *
     * @param topicName
     */
    public static IBaseHanlder hanlder(String topicName) {
        if (topicName.startsWith("box/")) {
            return SpringContext.getBean(ServiceOfBjttsfHandler.class);
        }
        return null;
    }
}
