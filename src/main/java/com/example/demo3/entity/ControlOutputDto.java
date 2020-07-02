package com.example.demo3.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: chenjiawang
 * @CreateDate: 2020/6/29 13:44
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ControlOutputDto implements Serializable {

    private static final long serialVersionUID = 1377605030754688866L;
    /**
     * Point name.即点名。字符串表示。
     */
    @JsonProperty("point_name")
    private String n;

    /**
     * Point value.即点的值
     */
    @JsonProperty("value")
    private Double v;
}
