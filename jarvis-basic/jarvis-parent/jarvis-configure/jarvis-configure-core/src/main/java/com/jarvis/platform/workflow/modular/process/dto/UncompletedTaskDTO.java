package com.jarvis.platform.workflow.modular.process.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月22日
 */
@ApiModel("待办任务参数")
@Getter
@Setter
@ToString
public class UncompletedTaskDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -702499219428706791L;

    @ApiModelProperty("流程编码")
    private String workflowCode;

    @ApiModelProperty("设置代流程启动用户ID，如果为未设置时，则默认为当前登录用户")
    private String userId;

}
