package com.example.demo3.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 13:42
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ControlOfRequestParamDto implements Serializable {

    private static final long serialVersionUID = -7946404549472468806L;
    /**
     *
     */
    @JsonProperty("device_name")
    private String d;
    /**
     *
     */
    @JsonProperty("output")
    private List<ControlOutputDto> o;

}
