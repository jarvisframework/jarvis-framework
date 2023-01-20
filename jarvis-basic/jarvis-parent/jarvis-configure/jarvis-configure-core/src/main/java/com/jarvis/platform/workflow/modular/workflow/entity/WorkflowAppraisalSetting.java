package com.jarvis.platform.workflow.modular.workflow.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月27日
 */
@Table(name = "wf_workflow_appraisal_setting")
@ApiModel("工作流辅助意见")
@Getter
@Setter
@ToString
public class WorkflowAppraisalSetting extends AbstractLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 7084017762889935997L;

    @ApiModelProperty("版本ID")
    private Long versionId;

    @ApiModelProperty("节点ID")
    private String activityId;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("配置值")
    private String value;
}
