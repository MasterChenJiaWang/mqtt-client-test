package com.example.demo3.test.message.handler;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo3.entity.ResponseParamDto;
import com.example.demo3.enums.FrameTypeEnum;
import com.example.demo3.service.BjttsfService;
import com.unittec.customize.iot.mqtt.api.client.RsocketClientSession;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * 北京天拓四方 对接
 *
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 14:19
 */
@Component
public class ServiceOfBjttsfHandler implements IBaseHanlder {

    @Autowired
    private BjttsfService bjttsfService;
    @Autowired
    private RsocketClientSession clientSession;

    /**
     * 缓存设备号
     */
    public static final FIFOCache<String, String> CACHE_DEVICE_NO = CacheUtil.newFIFOCache(100);
    /**
     *
     */
    public static final FIFOCache<String, String> CACHE_TOKEN = CacheUtil.newFIFOCache(100, 10 * 60 * 1000);

    /**
     * 缓存token
     */
    private static String token_ram_cache = "";
    /**
     * 天拓四方topic
     */
    public static String TOPIC_BJTTSF_BASE_CODE = "box/";

    @Override
    public byte[] processor(byte[] receive) {
        String str = StrUtil.str(receive, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONUtil.parseObj(str);
        //
        String token = jsonObject.get("k") == null ? "" : jsonObject.get("k").toString();
        String frameType = jsonObject.get("f") == null ? "" : jsonObject.get("f").toString();
        FrameTypeEnum typeByValue = FrameTypeEnum.getTypeByValue(frameType);
        if (null == typeByValue) {
            return null;
        }
        String data = "";
        String deviceId = "";
        switch (typeByValue) {
            case LOGIN:
                JSONObject receiveData =
                    jsonObject.get("p") == null ? null : JSONUtil.parseObj(jsonObject.get("p").toString());
                if (null == receiveData) {
                    break;
                }
                token = IdUtil.simpleUUID();
                token_ram_cache = token;
                CACHE_TOKEN.put(token_ram_cache, token_ram_cache);
                // 设备ID
                deviceId = receiveData.get("u") == null ? "" : receiveData.get("u").toString();
                ResponseParamDto<JSONObject> responseParamDto = bjttsfService.login(token, receiveData);
                data = JSONUtil.toJsonStr(responseParamDto);
                //
                ResponseParamDto<JSONObject> responseParamDto1 = bjttsfService.loginOfResponse(token, "success");
                // 登陆响应帧
                clientSession.pub(TOPIC_BJTTSF_BASE_CODE + deviceId,
                    StrUtil.bytes(JSONUtil.toJsonStr(responseParamDto1)), true, MqttQoS.AT_LEAST_ONCE.value())
                    .subscribe();
                // publish(message, connection, config, "box/" + deviceId, MqttQoS.AT_LEAST_ONCE,
                // StrUtil.bytes(JSONUtil.toJsonStr(responseParamDto1)));
                break;
            // 注销
            case LOGOUT:
                break;
            case DATA:
                JSONArray receiveData2 =
                    jsonObject.get("p") == null ? null : JSONUtil.parseArray(jsonObject.get("p").toString());
                if (null == receiveData2) {
                    break;
                }
                // 设备ID
                deviceId = CACHE_DEVICE_NO.get(token_ram_cache);
                if (!checkToken(deviceId, token)) {
                    break;
                }
                ResponseParamDto<JSONObject> data1 = bjttsfService.data(token_ram_cache, receiveData2);
                data = JSONUtil.toJsonStr(data1);
                break;
            case EVENT:
                JSONArray receiveData3 =
                    jsonObject.get("p") == null ? null : JSONUtil.parseArray(jsonObject.get("p").toString());
                if (null == receiveData3) {
                    break;
                }
                // 设备ID
                deviceId = CACHE_DEVICE_NO.get(token_ram_cache);
                if (!checkToken(deviceId, token)) {
                    break;
                }
                ResponseParamDto<JSONObject> event = bjttsfService.event(token_ram_cache, receiveData3);
                data = JSONUtil.toJsonStr(event);
                break;
            case CONTROL:
                JSONArray receiveData4 =
                    jsonObject.get("p") == null ? null : JSONUtil.parseArray(jsonObject.get("p").toString());
                if (null == receiveData4) {
                    break;
                }
                // 设备ID
                deviceId = CACHE_DEVICE_NO.get(token_ram_cache);
                if (!checkToken(deviceId, token)) {
                    break;
                }
                ResponseParamDto<JSONObject> responseParamDto2 =
                    bjttsfService.controlOfResponse(token_ram_cache, receiveData4);
                data = JSONUtil.toJsonStr(responseParamDto2);
                break;
            case CONFIG:
                break;
            case EVENT_CACHE:
                JSONArray receiveData6 =
                    jsonObject.get("p") == null ? null : JSONUtil.parseArray(jsonObject.get("p").toString());
                if (null == receiveData6) {
                    break;
                }
                // 设备ID
                deviceId = CACHE_DEVICE_NO.get(token_ram_cache);
                if (!checkToken(deviceId, token)) {
                    break;
                }
                ResponseParamDto<JSONObject> eventCache = bjttsfService.eventCache(token_ram_cache, receiveData6);
                data = JSONUtil.toJsonStr(eventCache);
                break;
            case DATA_CACHE:
                JSONArray receiveData7 =
                    jsonObject.get("p") == null ? null : JSONUtil.parseArray(jsonObject.get("p").toString());
                if (null == receiveData7) {
                    break;
                }
                // 设备ID
                deviceId = CACHE_DEVICE_NO.get(token_ram_cache);
                if (!checkToken(deviceId, token)) {
                    break;
                }
                ResponseParamDto<JSONObject> dataCache = bjttsfService.dataCache(token_ram_cache, receiveData7);
                data = JSONUtil.toJsonStr(dataCache);
                break;
            case HEARTBEAT:
                JSONObject receiveData8 =
                    jsonObject.get("p") == null ? null : JSONUtil.parseObj(jsonObject.get("p").toString());
                if (null == receiveData8) {
                    break;
                }
                // 设备ID
                deviceId = CACHE_DEVICE_NO.get(token_ram_cache);
                if (!checkToken(deviceId, token)) {
                    break;
                }
                ResponseParamDto<JSONObject> heartbeatOfRequst =
                    bjttsfService.heartbeatOfRequst(token_ram_cache, receiveData8);
                data = JSONUtil.toJsonStr(heartbeatOfRequst);
                //
                ResponseParamDto<JSONObject> heartbeatOfResponse = bjttsfService.heartbeatOfResponse(token, "success");
                //
                clientSession.pub(TOPIC_BJTTSF_BASE_CODE + deviceId,
                    StrUtil.bytes(JSONUtil.toJsonStr(heartbeatOfResponse)), true, MqttQoS.AT_LEAST_ONCE.value())
                    .subscribe();
                // publish(message, connection, config, "box/" + deviceId, MqttQoS.AT_LEAST_ONCE,
                // StrUtil.bytes(JSONUtil.toJsonStr(heartbeatOfResponse)));
                break;
            case RESERVED:
                break;
            default:
                break;
        }
        return StrUtil.bytes(data);
    }

    /**
     *
     * @param deviceNum
     * @param token
     */
    private boolean checkToken(String deviceNum, String token) {
        String oldToken = CACHE_TOKEN.get(token_ram_cache);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(oldToken) || !token.equals(oldToken)) {
            // 主动注销
            ResponseParamDto<JSONObject> logout = bjttsfService.logout(token);
            clientSession.pub(TOPIC_BJTTSF_BASE_CODE + deviceNum, StrUtil.bytes(JSONUtil.toJsonStr(logout)), true,
                MqttQoS.AT_MOST_ONCE.value()).subscribe();
            return false;
        }
        return true;
    }

}
