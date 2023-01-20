package com.jarvis.platform.workflow.modular.process.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月26日
 */
@ApiModel("流任务程数据")
@Getter
@Setter
@ToString
public class ProcessTaskDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2493926236953451523L;

    private String id;

    private String name;

    private String taskDefinitionKey;

    private String processInstanceId;

    private String description;

}
