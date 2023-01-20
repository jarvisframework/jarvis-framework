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
@Table(name = "wf_workflow_button_setting")
@ApiModel("工作流按钮配置")
@Getter
@Setter
@ToString
public class WorkflowButtonSetting extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -9090478703292475596L;

    @ApiModelProperty("版本ID")
    private Long versionId;

    @ApiModelProperty("节点ID")
    private String activityId;

    @ApiModelProperty("类型：1-列表 2-表单")
    private Integer type;

    @ApiModelProperty("按钮编码")
    private String buttonCode;

    @ApiModelProperty("按钮名称")
    private String buttonName;

    @ApiModelProperty("参数")
    private String param;

}
