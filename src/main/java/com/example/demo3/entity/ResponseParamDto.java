package com.example.demo3.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 11:19
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ResponseParamDto<T> implements Serializable {

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
}
