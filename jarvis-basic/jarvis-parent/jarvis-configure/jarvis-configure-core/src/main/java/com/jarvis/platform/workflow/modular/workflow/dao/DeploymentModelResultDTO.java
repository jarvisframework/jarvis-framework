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
@ApiModel("添加流程模型")
@Getter
@Setter
@ToString
public class DeploymentModelResultDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4167404678809888748L;

    private String deploymentId;

    private String processDefinintionKey;

}
