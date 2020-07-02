package com.example.demo3.test.message.handler;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 14:21
 */
public interface IBaseHanlder {

    /**
     *
     * @param receive
     * @return
     */
    byte[] processor(byte[] receive);

}
