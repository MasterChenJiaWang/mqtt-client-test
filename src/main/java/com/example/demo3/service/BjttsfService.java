package com.example.demo3.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo3.entity.ControlOfRequestParamDto;
import com.example.demo3.entity.RequestParamDto;
import com.example.demo3.entity.ResponseParamDto;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 北京天拓四方处理服务
 *
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 10:53
 */
@Component
public class BjttsfService {
    /**
     *
     */
    private static final String PASSWORD_KEY_CODE = "qwdjsQOWIE-=88,/.,;L12OJKDFBDFijkljkbhjk";

    /**
     *
     */
    private static final String USER_NAME_CODE = "admin";

    /**
     * 智能盒子启动后，向工业互联网云平台发起登录请求帧，包括智能盒子 SN 号，注册密码等 信息，云平台通过登录响应帧返回给智能盒子其登陆结果。登陆失败后，注册报文每间隔 3S 发 送一次直到登陆成功
     *
     * @param token
     * @param receiveData
     */
    public ResponseParamDto<JSONObject> login(String token, JSONObject receiveData) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setK(token);
        data.setT(System.currentTimeMillis() + "");
        if (receiveData == null) {
            data.setP(createObject("failed"));
            return data;
        }
        String userName = receiveData.get("u") == null ? "" : receiveData.get("u").toString();
        String password = receiveData.get("w") == null ? "" : receiveData.get("w").toString();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            data.setP(createObject("failed"));
            return data;
        }
        if (!USER_NAME_CODE.equals(userName) || !PASSWORD_KEY_CODE.equals(DigestUtil.md5Hex(password))) {
            data.setP(createObject("failed"));
            return data;
        }
        data.setP(createObject("success"));
        data.setF("login");
        return data;
    }

    /**
     * 登录响应
     *
     * @param result
     * @return
     */
    public ResponseParamDto<JSONObject> loginOfResponse(String token, String result) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setT(System.currentTimeMillis() + "");
        JSONObject object = createObject(result);
        object.put("k", token);
        data.setP(object);
        data.setF("login");
        data.setK(token);
        return data;
        // // 登陆响应帧
        // publish(message, connection, config, "box/" + sn, MqttQoS.AT_LEAST_ONCE,
        // StrUtil.bytes(JSONUtil.toJsonStr(data)));

    }

    /**
     *
     * @param result
     * @return
     */
    private JSONObject createObject(String result) {
        JSONObject map = new JSONObject();
        map.put("r", result);
        return map;
    }

    /**
     * 在 token 过期时间到、或收到无效 token 的帧、或平台主动发起注销时，云平台需向智能盒 子发送注销帧触发智能盒子重新发起登陆流程。
     *
     * @return
     */
    public ResponseParamDto<JSONObject> logout(String token) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setK(token);
        data.setT(System.currentTimeMillis() + "");
        JSONObject map = new JSONObject();
        data.setP(map);
        // 注销帧
        data.setF("logout");
        return data;
        // // 推送消息
        // publish(message, connection, config, "box/" + sn, MqttQoS.AT_MOST_ONCE,
        // StrUtil.bytes(JSONUtil.toJsonStr(data)));
    }

    /**
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> data(String token, JSONArray receiveData) {
        return data("data", token, receiveData);
    }

    /**
     * 数据帧 对于每个信息点，其上传的信息内容包括：数据点名，数据点值，数据点时间戳，数据点质 量戳共 4 个字段： 数据上报机制支持两种：定时上报和变化上报。定时上报是按照以一定时间周期上报信息点 数据（只包含上报类型
     * 定时上报的信息点），变化上报是定时对信息点数据进行比较，超过设定 的变化率的信息点数据进行上报
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> data(String frameType, String token, JSONArray receiveData) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setF(frameType);
        data.setK(token);
        data.setT(System.currentTimeMillis() + "");
        if (receiveData == null) {
            data.setP(createObject("failed"));
            return data;
        }
        for (Object o : receiveData) {
            JSONObject o1 = (JSONObject)o;
            String deviceId = o1.get("d") == null ? "" : o1.get("d").toString();
            String deviceType = o1.get("dt") == null ? "" : o1.get("dt").toString();
            JSONArray devicevalues =
                o1.get("dv") == null ? new JSONArray(0) : JSONUtil.parseArray(o1.get("dv").toString());
            if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(deviceType)) {
                data.setP(createObject("failed"));
                return data;
            }
            if (devicevalues.size() == 0) {
                data.setP(createObject("failed"));
                return data;
            }
            for (Object jsonObject : devicevalues) {
                JSONObject jsonObject1 = (JSONObject)jsonObject;
                // Dot name,即数据点名称。字符串表示。
                String name = jsonObject1.get("n") == null ? "" : jsonObject1.get("n").toString();
                // value,即数据值。根据数据类型来决定该字段的表示方式。
                String value = jsonObject1.get("v") == null ? "" : jsonObject1.get("v").toString();
                // Quality of data,即数据质量戳。字符串表示。0:数据好，1:数据坏。可有
                // 可无，无质量戳默认为 0
                int quality = jsonObject1.get("q") == null ? 0 : Integer.parseInt(jsonObject1.get("q").toString());
                // 添加到数据库

            }
        }
        data.setP(createObject("success"));
        return data;
    }

    public ResponseParamDto<JSONObject> event(String token, JSONArray receiveData) {
        return event("event", token, receiveData);
    }

    /**
     * 事件帧 当智能盒子自身故障发生需要上传的事件、对数据点进行计算发生的事件和挂载的设备发生 了故障上传事件，此时，智能盒子都需要把该事件发送到云平台。
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> event(String frameType, String token, JSONArray receiveData) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setK(token);
        data.setF(frameType);
        data.setT(System.currentTimeMillis() + "");
        if (receiveData == null) {
            data.setP(createObject("failed"));
            return data;
        }
        for (Object jsonObject : receiveData) {
            JSONObject t = (JSONObject)jsonObject;
            // Event type。即事件类型。字符串表示。”normal”：普通事件；”alarm”：报警事
            // 件；” fault”：故障事件。
            String eventType = t.get("et") == null ? "" : t.get("et").toString();
            // Event source。即事件来源。表示该事件的生产者。字符串表示。”gateway”：盒
            // 子自身；”calculate”：运算产生；”device”：设备产生。
            String eventSource = t.get("es") == null ? "" : t.get("es").toString();
            //
            String eventValue = t.get("ev") == null ? "" : t.get("ev").toString();
            // TimeStamps，即该事件生成时的时间戳。毫秒值形式，字符串表示。可有可无
            String ts = t.get("t") == null ? "" : t.get("t").toString();
            DateTime parse = null;
            if (StringUtils.isEmpty(ts)) {
                parse = DateUtil.parse(ts, "yyyy-MM-dd HH:mm:ss");
            }
            // 添加到数据库
        }
        data.setP(createObject("success"));
        return data;
    }

    /**
     * 控制帧 云平台可以远程控制智能盒子外挂设备的启停、远程设置外挂设备的参数值等。 1) 远程设备控制请求帧
     *
     * @param controlOfRequestParamDtos
     *            {'d':'','o':[{'n':'','v':''}]}
     * @return
     */
    public RequestParamDto<List<ControlOfRequestParamDto>> controlOfRequest(String token,
        List<ControlOfRequestParamDto> controlOfRequestParamDtos) {
        RequestParamDto<List<ControlOfRequestParamDto>> data = new RequestParamDto<>();
        data.setK(token);
        data.setF("control");
        data.setT(System.currentTimeMillis() + "");
        data.setP(controlOfRequestParamDtos);
        return data;
        // 推送消息
        // publish(message, connection, config, "box/" + sn, MqttQoS.AT_LEAST_ONCE,
        // StrUtil.bytes(JSONUtil.toJsonStr(data)));
    }

    /**
     * 2) 远程设备控制响应帧 解析设备发过来的数据
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> controlOfResponse(String token, JSONArray receiveData) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setK(token);
        data.setF("control");
        data.setT(System.currentTimeMillis() + "");
        if (receiveData == null) {
            data.setP(createObject("failed"));
            return data;
        }
        for (Object jsonObject : receiveData) {
            JSONObject t = (JSONObject)jsonObject;
            // Device name.即设备名。字符串表示
            String deviceName = t.get("d") == null ? "" : t.get("d").toString();
            // output,控制帧输出点的键值，在此为 json 数组。
            JSONArray output = t.get("o") == null ? new JSONArray(0) : JSONUtil.parseArray(t.get("o").toString());
            if (output.size() == 0) {
                continue;
            }
            for (Object o : output) {
                // Point name.即点名。字符串表示。
                String pointName = t.get("n") == null ? "" : t.get("n").toString();
                // Result，即远程控制结果。字符串表示。”1”：成功，”0”:失败。
                String result = t.get("r") == null ? "" : t.get("r").toString();
            }
            // 添加到数据库
        }
        data.setP(createObject("success"));
        return data;
    }

    /**
     * 当转发通道发生故障时，需要把发送给云端的转发协议帧缓存到本地，并在转发通道恢复正 常通信时，把缓存到本地的数据继续发送到云端。 1) 事件缓存帧
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> eventCache(String token, JSONArray receiveData) {
        return event("event_cache", token, receiveData);
    }

    /**
     * 数据缓存帧
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> dataCache(String token, JSONArray receiveData) {
        return data("data_cache", token, receiveData);
    }

    /**
     * 主要用于智能盒子定时向云端发送心跳帧。 1) 心跳请求帧
     *
     * @param receiveData
     * @return
     */
    public ResponseParamDto<JSONObject> heartbeatOfRequst(String token, JSONObject receiveData) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setK(token);
        data.setF("heartbeat");
        data.setT(System.currentTimeMillis() + "");
        if (receiveData == null) {
            data.setP(createObject("failed"));
            return data;
        }
        // 心跳请求帧个数。整形表示。
        int count = receiveData.get("c") == null ? 1 : Integer.parseInt(receiveData.get("c").toString());
        data.setP(createObject("success"));
        return data;
    }

    /**
     * 心跳响应帧
     *
     * @param result
     * @return
     */
    public ResponseParamDto<JSONObject> heartbeatOfResponse(String token, String result) {
        ResponseParamDto<JSONObject> data = new ResponseParamDto<>();
        data.setK("");
        data.setF("heartbeat");
        data.setT(System.currentTimeMillis() + "");
        // 心跳请求帧个数。整形表示。
        JSONObject map = new JSONObject();
        // Result，即心跳接收结果。字符串表示。”1”：成功，”0”:失败
        map.put("r", result);
        data.setP(map);
        return data;
        //
        // // 推送消息
        // publish(message, connection, config, "box/" + sn, MqttQoS.AT_LEAST_ONCE,
        // StrUtil.bytes(JSONUtil.toJsonStr(data)));
    }

}
