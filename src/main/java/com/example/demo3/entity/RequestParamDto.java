package com.example.demo3.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 10:39
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class RequestParamDto<T> implements Serializable {

    /**
     *
     */
    @JsonProperty("token")
    private String k;
    /**
     *
     */
    @JsonProperty("ts")
    private String t;
    /**
     *
     */
    @JsonProperty("frame_type")
    private String f;
    /**
     *
     */
    @JsonProperty("payload")
    private T p;
    /**
     *
     */
    @JsonProperty("sn")
    private String u;
    /**
     *
     */
    @JsonProperty("password")
    private String w;
    /**
     *
     */
    @JsonProperty("result")
    private String r;
    /**
     *
     */
    @JsonProperty("device_id")
    private String d;
    /**
     *
     */
    @JsonProperty("device_type")
    private String dt;
    /**
     *
     */
    @JsonProperty("device_values")
    private String dv;
    /**
     *
     */
    @JsonProperty("name")
    private String n;
    /**
     *
     */
    @JsonProperty("value")
    private String v;
    /**
     *
     */
    @JsonProperty("quality")
    private String q;
    /**
     *
     */
    @JsonProperty("output")
    private String o;
    /**
     *
     */
    @JsonProperty("event_type")
    private String et;
    /**
     *
     */
    @JsonProperty("event_source")
    private String es;
    /**
     *
     */
    @JsonProperty("event_value")
    private String ev;
    /**
     *
     */
    @JsonProperty("count")
    private String c;

}
