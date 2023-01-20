package com.jarvis.platform.workflow.modular.process.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月21日
 */
@ApiModel("启动流程结果")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class StartProcessResultDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2683447183265119182L;

    private String processInstanceId;

    private String taskId;

}
