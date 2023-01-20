package com.jarvis.platform.workflow.modular.workflow.dao;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月25日
 */
@ApiModel("流程模型XML")
@Getter
@Setter
@ToString
public class ModelXmlDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Integer version;

    private String xml;

}
