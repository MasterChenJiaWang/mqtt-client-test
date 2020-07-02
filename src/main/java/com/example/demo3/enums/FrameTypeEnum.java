package com.example.demo3.enums;

import org.springframework.util.StringUtils;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 10:31
 */
public enum FrameTypeEnum {

    /**
     *
     */
    LOGIN("login", "登陆帧"), LOGOUT("logout", "注销帧"), DATA("data", "数据帧"), EVENT("event", "事件帧"),
    CONTROL("control", "控制帧"), CONFIG("config", "配置帧"), EVENT_CACHE("event_cache", "事件缓存帧"),
    DATA_CACHE("data_cache", "数据缓存帧"), HEARTBEAT("heartbeat", "心跳帧"), RESERVED("reserved", "保留");

    /**
     *
     */
    private final String code;

    /**
     *
     */
    private final String name;

    FrameTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过code取枚举
     *
     * @param code
     * @return
     */
    public static FrameTypeEnum getTypeByValue(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (FrameTypeEnum enums : FrameTypeEnum.values()) {
            if (code.equals(enums.getCode())) {
                return enums;
            }
        }
        return null;
    }

    /**
     * 通过code取描述
     *
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        for (FrameTypeEnum enums : FrameTypeEnum.values()) {
            if (code.equals(enums.getCode())) {
                return enums.getName();
            }
        }
        return "";
    }
}
