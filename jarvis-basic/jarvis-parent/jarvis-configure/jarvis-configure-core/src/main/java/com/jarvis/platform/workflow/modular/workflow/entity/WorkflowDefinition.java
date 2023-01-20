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
 * @version 1.0.0 2022年7月20日
 */
@Table(name = "wf_workflow_definition")
@ApiModel("流程定义")
@Getter
@Setter
@ToString
public class WorkflowDefinition extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 168729890653749439L;

    @ApiModelProperty("系统ID")
    private Long systemId;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("流程编码")
    private String code;

    @ApiModelProperty("流程名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

}
