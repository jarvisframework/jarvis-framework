package com.jarvis.platform.workflow.modular.workflow.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
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
@Table(name = "wf_workflow_assist_comment")
@ApiModel("工作流辅助意见")
@Getter
@Setter
@ToString
public class WorkflowAssistComment extends AbstractSortLongIdEntity {

    /**
    *
    */
    private static final long serialVersionUID = 1899952861193249729L;

    @ApiModelProperty("版本ID")
    private Long versionId;

    @ApiModelProperty("节点ID")
    private String activityId;

    @ApiModelProperty("意见")
    private String message;
}
